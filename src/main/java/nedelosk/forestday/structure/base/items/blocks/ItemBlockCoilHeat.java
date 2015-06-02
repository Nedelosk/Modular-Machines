package nedelosk.forestday.structure.base.items.blocks;

import java.util.List;

import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.structure.base.blocks.BlockCoilHeat;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockCoilHeat extends ItemBlockForestday {

	public ItemBlockCoilHeat(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		int meta = stack.getItemDamage();
		
			list.add(StatCollector.translateToLocal("forestday.tooltip.heat") + BlockCoilHeat.coilHeat[meta]);
	}

}