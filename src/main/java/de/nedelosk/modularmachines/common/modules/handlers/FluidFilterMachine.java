package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraftforge.fluids.FluidStack;

public class FluidFilterMachine implements IContentFilter<FluidStack, IModuleMachine> {

	@Override
	public boolean isValid(int index, FluidStack content, IModuleState<IModuleMachine> state) {
		return state.getModule().isRecipeInput(state, new RecipeItem(index, content));
	}
}
