package de.nedelosk.forestmods.common.inventory.slots;

import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotModularInput extends SlotModular {

	private String recipeName;

	public <M extends IModuleMachineRecipe, S extends IModuleMachineSaver> SlotModularInput(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_,
			int p_i1824_4_, ModuleStack<M, S> stack) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_, stack);
		this.recipeName = stack.getModule().getRecipeCategory(stack);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return RecipeRegistry.getRecipeInput(recipeName, new RecipeItem(getSlotIndex(), stack)) != null;
	}
}
