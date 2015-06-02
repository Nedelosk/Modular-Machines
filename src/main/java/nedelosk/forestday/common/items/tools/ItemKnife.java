package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKnife extends ItemToolCrafting {

	public ItemKnife(String name, int maxDamage, int tier, Material material) {
		super(name, maxDamage, tier, material, name, 5);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int z, int y, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
        {
            return false;
        }
        else
        {
            Block block = world.getBlock(x, z, y);
            int damage = block.getDamageValue(world, x, z, y);
            
            if (block == Blocks.log)
            {
            	if(damage == 1)
            	{
                Block var12 = ForestdayBlockRegistry.logNature;
                if (!world.isRemote)
                {
                    world.setBlock(x, z, y, var12);
                    stack.damageItem(getDamage() , player);
                    	if(player.capabilities.isCreativeMode)
                    	{
                            if(ForestdayConfig.activeCreativeDrop)
                            {
                         EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 3));
                         world.spawnEntityInWorld(bark);
                            }
                    	}
                    	else
                    	{
                            EntityItem bark = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ForestdayItemRegistry.nature, 2, 3));
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
            else
            {
            	return false;
            }
        }

        
    }

}
