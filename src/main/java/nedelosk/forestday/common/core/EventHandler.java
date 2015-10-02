package nedelosk.forestday.common.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodType;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodTypeManager;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodTypeManager.WoodTypeStack;
import nedelosk.forestday.common.registrys.FBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EventHandler {

	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent event){
	    EntityPlayer player = event.entityPlayer;
	    World world = event.world;
	    int x = event.x;
	    int y = event.y;
	    int z = event.z;
	 
	    if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel){
	    	ItemStack stack = player.getCurrentEquippedItem();
			for(int xPos = 0;xPos < 3;xPos++){
				for(int zPos = 0;zPos < 3;zPos++){
					for(int yPos = 0;yPos < 2;yPos++){
						Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						if(!block.isWood(world, x + xPos - 1, y + yPos - 1, z + zPos - 1))
							return;
					}
				}
			}
			for(int xPos = 0;xPos < 3;xPos++){
				for(int zPos = 0;zPos < 3;zPos++){
					for(int yPos = 0;yPos < 2;yPos++){
						Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						int damage = world.getBlockMetadata(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						if(world.setBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1, FBlocks.Multiblock_Charcoal_Kiln.block())){
							TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x + xPos - 1, y + yPos - 1, z + zPos - 1);
							WoodType type;
							if(WoodTypeManager.woodTypes.equals(new WoodTypeStack(block, damage)))
								type = WoodTypeManager.woodTypes.get(new WoodTypeStack(block, damage));
							else
								type = new WoodType(new ItemStack(block, 1, damage), new ItemStack(Items.coal, 1, 1));
						kiln.setType(type);
						}
					}
				}
			}
			TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
			kiln.setWorking(true);
			stack.damageItem(1, player);
			kiln.testMultiblock();
	    }
	}
	
}
