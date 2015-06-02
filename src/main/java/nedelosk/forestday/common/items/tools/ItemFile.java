package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.crafting.ITool;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFile extends ItemToolCrafting
{
	
	public static ForestdayBlockRegistry coreBlocks;
	
	public ItemFile(int damagemax, int damage, String uln, String nameTexture, int tier, Material material){
		super(uln, damagemax, tier, material, nameTexture, damage);
		this.setNoRepair();
		this.setTextureName("forestday:tools/" + nameTexture);
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
	{
		return false;
	}
	
   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int z, int y, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
    	{
        if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
        {
            return false;
        }
        else
        {
            Block block = world.getBlock(x, z, y);
            int damage = block.getDamageValue(world, x, z, y);
            
            if (block == Blocks.log || block == Blocks.log2)
            {
                Block var12 = ForestdayBlockRegistry.trunkBig;
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                    	if(player.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 4, 0));
                         world.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 4, 0));
                            world.spawnEntityInWorld(bark);
                    	}
                    return true;
                }
                else
                {
                	return false;
                }
            }
            else if (block == ForestdayBlockRegistry.trunkBig)
            {
                Block var12 = ForestdayBlockRegistry.trunkMedium;        
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                    if(ForestdayConfig.activeCreativeDrop)
                    {
                    	if(player.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                         world.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                            world.spawnEntityInWorld(bark);
                    	}
                    }
                    return true;
                }
                else
                {
                	return false;
                }
            }
            else if (block == ForestdayBlockRegistry.trunkMedium)
            {
                Block var12 = ForestdayBlockRegistry.trunkSmall;
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                	if(player.capabilities.isCreativeMode)
                	{
                        if(ForestdayConfig.activeCreativeDrop)
                        {
                     EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                     world.spawnEntityInWorld(bark);
                        }
                	}
                	else
                	{
                        EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                        world.spawnEntityInWorld(bark);
                	}
                    return true;
                }
                else
                {
                	return false;
                }
            }
            else if (block == ForestdayBlockRegistry.trunkSmall)
            {
                Block var12 = ForestdayBlockRegistry.trunkTiny;
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                	if(player.capabilities.isCreativeMode)
                	{
                        if(ForestdayConfig.activeCreativeDrop)
                        {
                     EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                     world.spawnEntityInWorld(bark);
                        }
                	}
                	else
                	{
                        EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                        world.spawnEntityInWorld(bark);
                	}
                    return true;
                }
                else
                {
                	return false;
                }
            }
            else if (block == ForestdayBlockRegistry.trunkTiny)
            {
                Block var12 = Blocks.air;
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                	if(player.capabilities.isCreativeMode)
                	{
                        if(ForestdayConfig.activeCreativeDrop)
                        {
                     EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                     world.spawnEntityInWorld(bark);
                        }
                	}
                	else
                	{
                        EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 1));
                        world.spawnEntityInWorld(bark);
                	}
                    return true;
                }
                else
                {
                	return false;
                }
    		} 
            else
            {
                return false;
            }
        }
    	}
        
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
