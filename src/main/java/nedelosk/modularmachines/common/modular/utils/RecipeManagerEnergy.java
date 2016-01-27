package nedelosk.modularmachines.common.modular.utils;

import crazypants.enderio.machine.recipe.RecipeInput;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import net.minecraftforge.common.util.ForgeDirection;

public class RecipeManagerEnergy extends RecipeManager {

	public RecipeManagerEnergy() {
	}

	public RecipeManagerEnergy(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs, Object... craftingModifier) {
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
	public IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeInput[] inputs,
			Object... craftingModifier) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs, craftingModifier);
	}
}
