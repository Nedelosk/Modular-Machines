package de.nedelosk.forestmods.common.modules.handlers;

import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import de.nedelosk.forestmods.library.recipes.RecipeRegistry;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class FluidFilterMachine implements IContentFilter<FluidStack, IModuleMachine> {

	@Override
	public boolean isValid(int index, FluidStack content, IModuleMachine module, ForgeDirection facing) {
		return RecipeRegistry.isRecipeInput(module.getRecipeCategory(), new RecipeItem(index, content));
	}
}
