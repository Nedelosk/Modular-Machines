package modularmachines.common.modules;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IDropComponent;
import modularmachines.api.modules.components.IModelComponent;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.ModularMachines;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketExtractModule;
import modularmachines.common.network.packets.PacketInjectModule;
import modularmachines.common.utils.Log;

public class ModuleHandler implements IModuleHandler {
	
	private final IModuleProvider provider;
	private final Map<IModulePosition, IModule> modules;
	private final ImmutableList<IModulePosition> positions;
	
	public ModuleHandler(IModuleProvider provider, IModulePosition... positions) {
		this.provider = provider;
		this.modules = new HashMap<>(positions.length);
		for (IModulePosition position : positions) {
			modules.put(position, null);
		}
		this.positions = ImmutableList.copyOf(positions);
	}
	
	@Nullable
	@Override
	public IModule getModule(IModulePosition position) {
		return modules.get(position);
	}
	
	@Override
	public boolean hasModule(IModulePosition position) {
		return modules.get(position) != null;
	}
	
	@Override
	public boolean canHandle(IModulePosition position) {
		return modules.containsKey(position);
	}
	
	@Override
	public Collection<IModulePosition> getValidPositions() {
		return positions;
	}
	
	@Override
	public Collection<IModule> getModules() {
		return modules.values().stream().filter(Objects::nonNull).collect(Collectors.toSet());
	}
	
	@Override
	public boolean insertModule(IModulePosition position, IModuleDataContainer container, ItemStack itemStack, boolean simulate) {
		if (hasModule(position) || !canHandle(position) || !provider.isValidModule(position, container)) {
			return false;
		}
		if (simulate) {
			return true;
		}
		ItemStack moduleStack = itemStack.copy();
		moduleStack.setCount(1);
		IModule module = ModuleManager.factory.createModule(this, position, container, moduleStack);
		modules.put(position, module);
		ILocatable locatable = provider.getContainer().getLocatable();
		locatable.markLocatableDirty();
		World world = locatable.getWorldObj();
		BlockPos blockPos = locatable.getCoordinates();
		if (!world.isRemote) {
			int index = -1;
			if (provider instanceof IModuleComponent) {
				IModule parent = ((IModuleComponent) provider).getProvider();
				index = parent.getIndex();
			}
			PacketHandler.sendToNetwork(new PacketInjectModule(provider.getContainer(), index, getPositionIndex(position), itemStack.copy()), blockPos, world);
		} else {
			if (provider instanceof IModuleComponent) {
				IModuleComponent component = ((IModuleComponent) provider);
				IModule parent = component.getProvider();
				IModelComponent modelComponent = parent.getInterface(IModelComponent.class);
				if (modelComponent != null) {
					modelComponent.setModelNeedReload(true);
				}
			}
			IModelComponent modelComponent = module.getInterface(IModelComponent.class);
			if (modelComponent != null) {
				modelComponent.setModelNeedReload(true);
			}
			world.markBlockRangeForRenderUpdate(blockPos, blockPos);
		}
		return true;
	}
	
	@Override
	public List<ItemStack> extractModule(IModulePosition position, boolean simulate) {
		if (!hasModule(position) || !canHandle(position)) {
			return Collections.emptyList();
		}
		IModule module = modules.get(position);
		List<IModule> extractedModules = new ArrayList<>();
		extractedModules.add(module);
		IModuleProvider moduleProvider = module.getInterface(IModuleProvider.class);
		if (moduleProvider != null) {
			extractedModules.addAll(moduleProvider.getModules());
		}
		NonNullList<ItemStack> drops = NonNullList.create();
		for (IModule otherModule : extractedModules) {
			drops.add(otherModule.createItem());
			otherModule.getInterfaces(IDropComponent.class).forEach(c -> c.addDrops(drops));
		}
		if (simulate) {
			return drops;
		}
		modules.put(position, null);
		ILocatable locatable = provider.getContainer().getLocatable();
		locatable.markLocatableDirty();
		World world = locatable.getWorldObj();
		BlockPos blockPos = locatable.getCoordinates();
		if (!world.isRemote) {
			int index = -1;
			if (provider instanceof IModuleComponent) {
				IModule parent = ((IModuleComponent) provider).getProvider();
				index = parent.getIndex();
			}
			PacketHandler.sendToNetwork(new PacketExtractModule(provider.getContainer(), index, getPositionIndex(position)), blockPos, world);
		} else {
			if (provider instanceof IModule) {
				IModule parent = (IModule) provider;
				IModelComponent modelComponent = parent.getInterface(IModelComponent.class);
				if (modelComponent != null) {
					modelComponent.setModelNeedReload(true);
				}
			}
			world.markBlockRangeForRenderUpdate(blockPos, blockPos);
		}
		return drops;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		for (Map.Entry<IModulePosition, IModule> entry : modules.entrySet()) {
			IModule module = entry.getValue();
			if (module == null) {
				continue;
			}
			IModuleData moduleData = module.getData();
			NBTTagCompound tagCompound = new NBTTagCompound();
			module.writeToNBT(tagCompound);
			tagCompound.setString("D", moduleData.getRegistryName().toString());
			tagCompound.setInteger("I", getPositionIndex(entry.getKey()));
			tagList.appendTag(tagCompound);
		}
		compound.setTag("Modules", tagList);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		for (IModulePosition position : positions) {
			modules.put(position, null);
		}
		NBTTagList tagList = compound.getTagList("Modules", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			int index = tagCompound.getInteger("I");
			String registryName = tagCompound.getString("D");
			IModuleData data = ModularMachines.dataRegistry.getValue(new ResourceLocation(registryName));
			if (data == null) {
				Log.warn("Failed to load a module of a module handler.");
				continue;
			}
			IModulePosition position = getPosition(index);
			if (position == null) {
				Log.warn("Failed to load a module of a module handler: Data:{}", data);
				continue;
			}
			modules.put(position, ModuleManager.factory.createModule(tagCompound, this, data, position));
		}
	}
	
	@Nullable
	public IModulePosition getPosition(int index) {
		if (index < 0 || index >= positions.size()) {
			return null;
		}
		return positions.get(index);
	}
	
	@Override
	public int getPositionIndex(IModulePosition position) {
		return positions.indexOf(position);
	}
	
	@Override
	public IModuleProvider getProvider() {
		return provider;
	}
}
