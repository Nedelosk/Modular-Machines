package modularmachines.common.modules;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
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
	private final Map<IModulePosition, Module> modules;
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
	public Module getModule(IModulePosition position) {
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
	public Collection<Module> getModules() {
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
		Module module = container.getData().createModule(this, position, container, moduleStack);
		modules.put(position, module);
		ILocatable locatable = provider.getContainer().getLocatable();
		locatable.markLocatableDirty();
		World world = locatable.getWorldObj();
		BlockPos blockPos = locatable.getCoordinates();
		if (!world.isRemote) {
			int index = provider instanceof Module ? ((Module) provider).getIndex() : -1;
			PacketHandler.sendToNetwork(new PacketInjectModule(provider.getContainer(), index, getPositionIndex(position), itemStack.copy()), blockPos, world);
		} else {
			if (provider instanceof Module) {
				Module parent = (Module) provider;
				parent.setModelNeedReload(true);
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
		Module module = modules.get(position);
		List<ItemStack> itemStacks = module.getDrops();
		if (simulate) {
			return itemStacks;
		}
		modules.put(position, null);
		ILocatable locatable = provider.getContainer().getLocatable();
		locatable.markLocatableDirty();
		World world = locatable.getWorldObj();
		BlockPos blockPos = locatable.getCoordinates();
		if (!world.isRemote) {
			int index = provider instanceof Module ? ((Module) provider).getIndex() : -1;
			PacketHandler.sendToNetwork(new PacketExtractModule(provider.getContainer(), index, getPositionIndex(position)), blockPos, world);
		} else {
			if (provider instanceof Module) {
				Module parent = (Module) provider;
				parent.setModelNeedReload(true);
			}
			world.markBlockRangeForRenderUpdate(blockPos, blockPos);
		}
		return itemStacks;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList tagList = new NBTTagList();
		for (Map.Entry<IModulePosition, Module> entry : modules.entrySet()) {
			Module module = entry.getValue();
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
			Module module = data.createModule();
			module.onLoadModule(this, position);
			module.readFromNBT(tagCompound);
			modules.put(position, module);
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
