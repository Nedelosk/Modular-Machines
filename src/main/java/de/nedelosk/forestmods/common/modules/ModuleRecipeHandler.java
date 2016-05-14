package de.nedelosk.forestmods.common.modules;

import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.recipes.IRecipeHandler;
import de.nedelosk.forestmods.library.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.library.recipes.IRecipeNBTSerializer;

public class ModuleRecipeHandler implements IRecipeHandler {

	private final IModuleMachine machine;

	public ModuleRecipeHandler(IModuleMachine machine) {
		this.machine = machine;
	}

	@Override
	public IRecipeJsonSerializer getJsonSerialize() {
		if(machine instanceof IRecipeJsonSerializer){
			return (IRecipeJsonSerializer) machine;
		}
		return null;
	}

	@Override
	public IRecipeNBTSerializer getNBTSerialize() {
		if(machine instanceof IRecipeNBTSerializer){
			return (IRecipeNBTSerializer) machine;
		}
		return null;
	}

	@Override
	public String getRecipeCategory() {
		return machine.getRecipeCategory();
	}

	@Override
	public boolean matches(Object[] craftingModifiers) {
		return true;
	}
}
