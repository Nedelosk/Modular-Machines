package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.crafting.ITool;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import net.minecraft.item.ItemStack;

public class ItemCutter extends ItemToolCrafting
{
	public static ForestdayBlockRegistry coreBlocks;
	
	public ItemCutter(int damagemax, int damage, String uln, String nameTexture, int tier, Material material){
		super(uln, damagemax, tier, material, nameTexture, damage);
		this.setNoRepair();
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
	{
		return false;
	}
	
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		ItemStack itemstack = stack.copy();
		itemstack.setItemDamage(itemstack.getItemDamage() + getDamage());
		itemstack.stackSize = 1;
		return itemstack;
	}
}
