package nedelosk.modularmachines.api.modular.basic.container.module;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MultiModuleContainer<M extends IModule<S>, S extends IModuleSaver> implements IMultiModuleContainer<M, S, List<ModuleStack<M, S>>> {

	private List<ModuleStack<M, S>> moduleStacks = new ArrayList();

	public MultiModuleContainer(ModuleStack<M, S> stack) {
		moduleStacks.add(stack);
	}

	public MultiModuleContainer() {
	}

	@Override
	public void addStack(int index, ModuleStack<M, S> stack) {
		moduleStacks.add(index, stack);
	}

	@Override
	public void addStack(ModuleStack<M, S> stack) {
		moduleStacks.add(stack);
	}

	@Override
	public void setStacks(List<ModuleStack<M, S>> collection) {
		this.moduleStacks = collection;
	}

	@Override
	public void setStack(ModuleStack<M, S> stackModule, String moduleUID) {
		for ( ModuleStack<M, S> stack : moduleStacks ) {
			if (stack.getModule().getName(stack).equals(moduleUID)) {
				moduleStacks.set(moduleStacks.indexOf(stack), stackModule);
			}
		}
	}

	@Override
	public ModuleStack<M, S> getStack(int index) {
		return moduleStacks.get(index);
	}

	@Override
	public ModuleStack<M, S> getStack(String moduleUID) {
		for ( ModuleStack<M, S> stack : moduleStacks ) {
			if (stack.getModule().getName(stack).equals(moduleUID)) {
				return stack;
			}
		}
		return null;
	}

	@Override
	public int getIndex(ModuleStack<M, S> stack) {
		if (stack == null) {
			return -1;
		}
		return moduleStacks.indexOf(stack);
	}

	@Override
	public List<ModuleStack<M, S>> getStacks() {
		return moduleStacks;
	}

	/* NBT */
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		if (nbt.hasKey("Stacks")) {
			NBTTagList nbtList = nbt.getTagList("Stacks", 10);
			moduleStacks = new ArrayList<ModuleStack<M, S>>();
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
			for ( ModuleStack<M, S> stack : moduleStacks ) {
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
