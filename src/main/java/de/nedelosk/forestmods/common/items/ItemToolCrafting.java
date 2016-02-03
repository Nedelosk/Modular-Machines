package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestmods.api.crafting.ITool;
import net.minecraft.item.ItemStack;

public class ItemToolCrafting extends ItemToolForestday implements ITool {

	protected int damage;

	public ItemToolCrafting(String name, int maxDamage, int tier, Material material, String nameTexture, int damage) {
		super(name, maxDamage, tier, material);
		this.setTextureName("forestday:tools/" + nameTexture);
		this.damage = damage;
		this.setNoRepair();
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack itemstack = stack.copy();
		itemstack.setItemDamage(itemstack.getItemDamage() + 5);
		itemstack.stackSize = 1;
		return itemstack;
	}

	@Override
	public int getDamage() {
		return damage;
	}
}
