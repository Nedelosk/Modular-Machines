package modularmachines.common.inventory.slots;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import modularmachines.api.ItemUtil;
import modularmachines.api.recipes.IModuleCrafterRecipe;

public class SlotModuleCrafterHolder extends SlotModuleCrafter {

	public SlotModuleCrafterHolder(IInventory inventoryIn, int index, int xPosition, int yPosition, Container container) {
		super(inventoryIn, index, xPosition, yPosition, container);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
			if (recipe instanceof IModuleCrafterRecipe) {
				IModuleCrafterRecipe crafterRecipe = (IModuleCrafterRecipe) recipe;
				if (crafterRecipe.getHolder() == null) {
					continue;
				}
				if (ItemUtil.isCraftingEquivalent(stack, crafterRecipe.getHolder())) {
					return true;
				}
			}
		}
		return false;
	}
}
