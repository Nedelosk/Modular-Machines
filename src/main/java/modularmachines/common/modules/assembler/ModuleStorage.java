package modularmachines.common.modules.assembler;

import java.util.List;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.core.ModularMachines;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

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
	public Module getModuleForIndex(int index) {
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
    	for(int i = 0;i < modules.size();i++){
    		Module module = modules.get(i);
    		NBTTagCompound tagCompound = new NBTTagCompound();
    		module.writeToNBT(tagCompound);
    		tagCompound.setString("Data", module.getData().getRegistryName().toString());
    		tagCompound.setInteger("Position", i);
    		tagList.appendTag(tagCompound);
    	}
    	compound.setTag("Modules", tagList);
    	return compound;
    }
    
    public void readFromNBT(NBTTagCompound compound){
    	NBTTagList tagList = compound.getTagList("Modules", 10);
    	for(int i = 0;i < tagList.tagCount();i++){
    		NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
    		String registryName = tagCompound.getString("Data");
    		ModuleData data = ModularMachines.DATAS.getValue(new ResourceLocation(registryName));
    		int position = tagCompound.getInteger("Position");
    		Module module = data.createModule(this);
    		module.readFromNBT(tagCompound);
    		modules.set(position, module);
    	}
    }

}
