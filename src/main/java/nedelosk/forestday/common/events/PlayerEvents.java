package nedelosk.forestday.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

public class PlayerEvents {

	@SubscribeEvent
	public void stickDrop(BlockEvent.HarvestDropsEvent event) {
		Random r = new Random();
		if (event.block == Blocks.leaves || event.block == Blocks.leaves2) {
			if (r.nextInt(16) == 1) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

}
