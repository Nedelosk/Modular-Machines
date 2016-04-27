package de.nedelosk.forestmods.common.modules;

import de.nedelosk.forestmods.library.recipes.IRecipeHandler;
import de.nedelosk.forestmods.library.recipes.IRecipeJsonSerializer;
import de.nedelosk.forestmods.library.recipes.IRecipeNBTSerializer;

public class ModuleRecipeHandler implements IRecipeHandler {

	private final ModuleMachine producer;

	public ModuleRecipeHandler(ModuleMachine producer) {
		this.producer = producer;
	}

	@Override
	public IRecipeJsonSerializer getJsonSerialize() {
		return producer;
	}

	@Override
	public IRecipeNBTSerializer getNBTSerialize() {
		return producer;
	}

	@Override
	public String getRecipeCategory() {
		return producer.getRecipeCategory();
	}

	@Override
	public boolean matches(Object[] craftingModifiers) {
		return true;
	}
}
