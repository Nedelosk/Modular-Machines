package de.nedelosk.forestmods.common.crafting;

import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.utils.CharcoalKilnUtil;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CraftingRecipeKiln implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				ItemStack stack = inv.getStackInRowAndColumn(x, y);
				if (stack == null || stack.getItem() == null) {
					return false;
				}
				Block block = Block.getBlockFromItem(stack.getItem());
				if (block == null) {
					return false;
				}
				if (x == 1 && y == 1) {
					if (Block.getBlockFromItem(stack.getItem()) != BlockManager.blockGravel || stack.getItemDamage() != 0) {
						return false;
					}
				} else {
					if (!CharcoalKilnUtil.isWood(stack)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack itemStack = inv.getStackInRowAndColumn(0, 0);
		return CharcoalKilnUtil.createKiln(itemStack);
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(BlockManager.blockCharcoalKiln);
	}
}
