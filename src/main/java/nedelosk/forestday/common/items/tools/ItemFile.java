package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.crafting.ITool;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.BlockRegistry;
import nedelosk.forestday.common.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFile extends ItemToolCrafting
{
	
	public ItemFile(int damagemax, int damage, String uln, String nameTexture, int tier, Material material){
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
