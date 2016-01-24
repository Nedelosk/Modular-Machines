package nedelosk.modularmachines.api.modular.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiModuleContainer<M extends IModule, P extends IProducer> implements IModuleContainer<List<ModuleStack<M, P>>> {
	
	private List<ModuleStack<M, P>> moduleStacks;
	
	public MultiModuleContainer(List<ModuleStack<M, P>> moduleStacks) {
		this.moduleStacks = moduleStacks;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		if(nbt.hasKey("Stacks")){
			NBTTagList nbtList = nbt.getTagList("Stacks", 10);
			moduleStacks = new ArrayList<ModuleStack<M, P>>();
			for(int i = 0;i < nbtList.tagCount();i++){
				NBTTagCompound nbtTag = nbtList.getCompoundTagAt(i);
				
			}
		}
	}
 
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		if(moduleStacks != null && moduleStacks.isEmpty()){
			NBTTagList nbtList = new NBTTagList();
			for(ModuleStack<M, P> stack : moduleStacks){
				if(stack != null){
					NBTTagCompound nbtTag = new NBTTagCompound();
					stack.writeToNBT(nbtTag, modular);
					nbtList.appendTag(nbtTag);
				}
			}
			nbt.setTag("Stacks", nbtList);
		}
	}

	@Override
	public List<ModuleStack<M, P>> getModuleStack() {
		return moduleStacks;
	}
}
