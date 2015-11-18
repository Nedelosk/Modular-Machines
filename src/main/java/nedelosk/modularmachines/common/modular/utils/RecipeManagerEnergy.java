package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import net.minecraftforge.common.util.ForgeDirection;

public class RecipeManagerEnergy extends RecipeManager {
	
	public RecipeManagerEnergy() {
	}
	
	public RecipeManagerEnergy(IModular modular, String recipeName, RecipeInput[] inputs) {
		super(modular, recipeName, inputs);
	}
	
	public RecipeManagerEnergy(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs) {
		super(modular, recipeName, materialModifier, inputs);
	}

	@Override
	public boolean removeMaterial() {
		if (modular == null || modular.getManager() == null || modular.getManager().getEnergyHandler() == null)
			return false;
		if (modular.getManager().getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, materialModifier, false) > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeInput[] inputs) {
		return new RecipeManagerEnergy(modular, recipeName, materialModifier, inputs);
	}
}
