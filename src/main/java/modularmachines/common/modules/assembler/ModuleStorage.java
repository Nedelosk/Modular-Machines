package modularmachines.common.modules.assembler;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.ModularMachines;
import modularmachines.common.utils.Log;

public class ModuleStorage implements IModuleStorage {

	public final IModuleLogic logic;
	public final List<Module> modules;
	
	public ModuleStorage(IModuleLogic logic, List<Module> modules) {
		this.logic = logic;
		this.modules = modules;
	}
	
	@Override
	public List<Module> getModules() {
		return modules;
	}

	@Override
	public Module getModule(int index) {
		for(Module module : modules){
			if(module.getIndex() == index){
				return module;
			}
		}
		return null;
	}
	
	@Override
	public int getPosition(Module module) {
		return modules.indexOf(module);
	}
	
	@Override
	public Module getModuleAtPosition(int position) {
		return modules.get(position);
	}

	@Override
	public IModuleLogic getLogic() {
		return logic;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	NBTTagList tagList = new NBTTagList();
		for (Module module : modules) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			module.writeToNBT(tagCompound);
			ResourceLocation location = module.getData().getRegistryName();
			tagCompound.setString("Data", location.toString());
			tagList.appendTag(tagCompound);
		}
    	compound.setTag("Modules", tagList);
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	modules.clear();
    	NBTTagList tagList = compound.getTagList("Modules", 10);
    	for(int i = 0;i < tagList.tagCount();i++){
    		NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
    		String registryName = tagCompound.getString("Data");
    		ModuleData data = ModularMachines.dataRegistry.getValue(new ResourceLocation(registryName));
    		if(data == null){
				Log.warn("Failed");
				continue;
			}
    		Module module = data.createModule();
    		module.readFromNBT(tagCompound);
    		modules.add(module);
    	}
    }

}
