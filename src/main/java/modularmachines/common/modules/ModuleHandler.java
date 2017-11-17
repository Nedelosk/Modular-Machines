package modularmachines.common.modules;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModulePosition;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.common.ModularMachines;
import modularmachines.common.utils.Log;

public class ModuleHandler implements IModuleHandler {
	
	private final IModuleProvider provider;
	private final Map<IModulePosition, Module> modules;
	private final ImmutableList<IModulePosition> positions;
	
	public ModuleHandler(IModuleProvider provider, IModulePosition... positions) {
		this.provider = provider;
		this.modules = new HashMap<>(positions.length);
		for(IModulePosition position : positions){
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
	public Collection<IModulePosition> getValidPositions() {
		return positions;
	}
	
	@Override
	public Collection<Module> getModules() {
		return modules.values().stream().filter(Objects::nonNull).collect(Collectors.toSet());
	}
	
	@Override
	public boolean insertModule(IModulePosition position, IModuleDataContainer container, ItemStack itemStack) {
		if(!modules.containsKey(position)){
			return false;
		}
		Module module = container.getData().createModule(this, position, container, itemStack);
		modules.put(position, module);
		provider.getContainer().getLocatable().markLocatableDirty();
		return true;
	}
	
	@Override
	public ItemStack extractModule(IModulePosition position) {
		provider.getContainer().getLocatable().markLocatableDirty();
		return ItemStack.EMPTY;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		NBTTagList tagList = new NBTTagList();
		for(Map.Entry<IModulePosition, Module> entry : modules.entrySet()){
			Module module = entry.getValue();
			if(module == null){
				continue;
			}
			ModuleData moduleData = module.getData();
			NBTTagCompound tagCompound = new NBTTagCompound();
			module.writeToNBT(tagCompound);
			tagCompound.setString("D", moduleData.getRegistryName().toString());
			tagCompound.setInteger("I", positions.indexOf(entry.getKey()));
			tagList.appendTag(tagCompound);
		}
		compound.setTag("Modules", tagList);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound){
		for(IModulePosition position : positions){
			modules.put(position, null);
		}
		NBTTagList tagList = compound.getTagList("Modules", 10);
		for(int i = 0;i < tagList.tagCount();i++){
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			int index = tagCompound.getInteger("I");
			String registryName = tagCompound.getString("D");
			ModuleData data = ModularMachines.dataRegistry.getValue(new ResourceLocation(registryName));
			if(data == null){
				Log.warn("Failed to load a module of a module handler.");
				continue;
			}
			IModulePosition position = getPosition(index);
			if(position == null){
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
	private IModulePosition getPosition(int index){
		if(index < 0 || index >= positions.size()){
			return null;
		}
		return positions.get(index);
	}
	
	@Override
	public IModuleProvider getProvider() {
		return provider;
	}
}
