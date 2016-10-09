package de.nedelosk.modularmachines.api.modules.handlers.filters;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FilterMachine implements IContentFilter<Object, IModuleMachine> {

	public static final FilterMachine INSTANCE = new FilterMachine();

	private FilterMachine() {
	}

	@Override
	public boolean isValid(int index, Object content, IModuleState<IModuleMachine> state) {
		if(content == null){
			return false;
		}
		RecipeItem recipeItem = null;
		if(content instanceof ItemStack){
			recipeItem = new RecipeItem(index, (ItemStack)content);
		}else if(content instanceof FluidStack){
			recipeItem = new RecipeItem(index, (FluidStack)content);
		}else{
			return false;
		}
		return state.getModule().isRecipeInput(state, recipeItem);
	}
}
