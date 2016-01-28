package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraftforge.common.util.ForgeDirection;

public class RecipeManagerEnergy extends RecipeManager {

	public RecipeManagerEnergy() {
	}

	public RecipeManagerEnergy(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifier) {
		super(modular, recipeName, materialModifier, inputs, craftingModifier);
	}

	@Override
	public boolean removeMaterial() {
		if (modular == null || modular.getManager() == null || modular.getManager().getEnergyHandler() == null) {
			return false;
		}
		if (modular.getManager().getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, materialModifier, false) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeItem[] inputs,
			Object... craftingModifier) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs, craftingModifier);
	}
}
