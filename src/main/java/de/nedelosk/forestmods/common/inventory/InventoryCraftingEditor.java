package de.nedelosk.forestmods.common.inventory;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingEditor extends InventoryCrafting {

	public InventoryCraftingEditor(ItemStack[] stackList) {
		super(null, 3, 3);
		ItemStack[] list = ObfuscationReflectionHelper.getPrivateValue(InventoryCrafting.class, this, 0);
		list[0] = stackList[0];
		list[1] = stackList[1];
		list[2] = stackList[2];
		list[3] = stackList[3];
		list[4] = stackList[4];
		list[5] = stackList[5];
		list[6] = stackList[6];
		list[7] = stackList[7];
		list[8] = stackList[8];
	}
}
