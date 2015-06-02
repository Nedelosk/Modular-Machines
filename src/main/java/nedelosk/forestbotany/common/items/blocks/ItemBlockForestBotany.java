package nedelosk.forestbotany.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockForestBotany extends ItemBlock {

	public ItemBlockForestBotany(Block p_i45328_1_) {
		super(p_i45328_1_);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int i) {
		return i;
	}
	
	public Block getBlock()
	{
		return field_150939_a;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getBlock().getUnlocalizedName() + "." + stack.getItemDamage();
	}

}
