package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipeManagerSteam extends RecipeManager {
	
	public RecipeManagerSteam() {
	}
	
	public RecipeManagerSteam(IModular modular, String recipeName, RecipeInput[] inputs) {
		super(modular, recipeName, inputs);
	}
	
	public RecipeManagerSteam(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs) {
		super(modular, recipeName, materialModifier, inputs);
	}

	@Override
	public boolean removeMaterial() {
		if (modular == null || modular.getManager() == null || modular.getManager().getFluidHandler() == null)
			return false;
		FluidStack drain = modular.getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.getFluid("steam"), materialModifier), true);
		if (drain != null && drain.amount > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeInput[] inputs) {
		return new RecipeManagerSteam(modular, recipeName, materialModifier, inputs);
	}
}
