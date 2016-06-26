package de.nedelosk.modularmachines.common.items;

import net.minecraft.item.ItemStack;

public class ItemToolCrafting extends ItemTool {

	protected int damage;

	public ItemToolCrafting(String name, int maxDamage, int tier, Material material, int damage) {
		super(name, maxDamage, tier, material);
		this.damage = damage;
		this.setNoRepair();
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack itemstack = stack.copy();
		itemstack.setItemDamage(itemstack.getItemDamage() + damage);
		itemstack.stackSize = 1;
		return itemstack;
	}
}
