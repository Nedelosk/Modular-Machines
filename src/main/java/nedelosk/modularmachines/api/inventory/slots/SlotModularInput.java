package nedelosk.modularmachines.api.inventory.slots;

import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.IModuleMachineRecipe;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotModularInput extends SlotModular {

	private String recipeName;

	public <M extends IModuleMachineRecipe<S>, S extends IModuleMachineSaver> SlotModularInput(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_,
			int p_i1824_4_, ModuleStack<M, S> stack) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
		this.recipeName = stack.getModule().getRecipeName(stack);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return RecipeRegistry.getRecipeInput(recipeName, new RecipeItem(getSlotIndex(), stack)) != null;
	}
}
