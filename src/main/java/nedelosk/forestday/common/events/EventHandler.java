package nedelosk.forestday.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.types.WoodType;
import nedelosk.forestday.common.types.WoodTypeManager;
import nedelosk.forestday.common.types.WoodTypeManager.WoodTypeStack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public class EventHandler {

	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;

		if (player.getCurrentEquippedItem() != null && (player.getCurrentEquippedItem().getItem() == Items.flint_and_steel || player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.torch))) {
			ItemStack stack = player.getCurrentEquippedItem();
			for (int xPos = 0; xPos < 3; xPos++) {
				for (int zPos = 0; zPos < 3; zPos++) {
					for (int yPos = 0; yPos < 2; yPos++) {
						Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						if (!block.isWood(world, x + xPos - 1, y + yPos - 1, z + zPos - 1))
							return;
					}
				}
			}
			for (int xPos = 0; xPos < 3; xPos++) {
				for (int zPos = 0; zPos < 3; zPos++) {
					for (int yPos = 0; yPos < 2; yPos++) {
						Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						int damage = world.getBlockMetadata(x + xPos - 1, y + yPos - 1, z + zPos - 1);
						if (world.setBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1,
								FBlockManager.Multiblock_Charcoal_Kiln.block())) {
							TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x + xPos - 1, y + yPos - 1,
									z + zPos - 1);
							WoodType type;
							if (WoodTypeManager.woodTypes.equals(new WoodTypeStack(block, damage)))
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
			if(player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.torch)){
				player.getCurrentEquippedItem().stackSize--;
				if(player.getCurrentEquippedItem().stackSize == 0)
					player.setCurrentItemOrArmor(0, null);
			}
		}
	}
	
	@SubscribeEvent
	public void stickDrop(BlockEvent.HarvestDropsEvent event) {
		Random r = new Random();
		if (event.block == Blocks.leaves || event.block == Blocks.leaves2) {
			if (r.nextInt(16) == 0) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

}
