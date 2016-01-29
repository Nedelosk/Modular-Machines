package nedelosk.modularmachines.api.modular.basic.container.module;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiModuleContainer<M extends IModule> implements IMultiModuleContainer<M, List<ModuleStack<M>>> {

	private List<ModuleStack<M>> moduleStacks = new ArrayList();

	public MultiModuleContainer(ModuleStack<M> stack) {
		moduleStacks.add(stack);
	}

	public MultiModuleContainer() {
	}

	@Override
	public void addStack(int index, ModuleStack<M> stack) {
		moduleStacks.add(index, stack);
	}

	@Override
	public void addStack(ModuleStack<M> stack) {
		moduleStacks.add(stack);
	}

	@Override
	public void setStacks(List<ModuleStack<M>> collection) {
		this.moduleStacks = collection;
	}

	@Override
	public ModuleStack<M> getStack(int index) {
		return moduleStacks.get(index);
	}

	@Override
	public ModuleStack<M> getStack(String guiName) {
		for ( ModuleStack<M> stack : moduleStacks ) {
			if (stack.getModule().getModuleUID().equals(guiName)) {
				return stack;
			}
		}
		return null;
	}

	@Override
	public int getIndex(ModuleStack<M> stack) {
		if (stack == null) {
			return -1;
		}
		return moduleStacks.indexOf(stack);
	}

	@Override
	public List<ModuleStack<M>> getStacks() {
		return moduleStacks;
	}

	/* NBT */
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		if (nbt.hasKey("Stacks")) {
			NBTTagList nbtList = nbt.getTagList("Stacks", 10);
			moduleStacks = new ArrayList<ModuleStack<M>>();
			for ( int i = 0; i < nbtList.tagCount(); i++ ) {
				NBTTagCompound nbtTag = nbtList.getCompoundTagAt(i);
				moduleStacks.add(ModuleStack.loadFromNBT(nbtTag, modular));
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		if (moduleStacks != null && moduleStacks.isEmpty()) {
			NBTTagList nbtList = new NBTTagList();
			for ( ModuleStack<M> stack : moduleStacks ) {
				if (stack != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					stack.writeToNBT(nbtTag, modular);
					nbtList.appendTag(nbtTag);
				}
			}
			nbt.setTag("Stacks", nbtList);
		}
	}
}
