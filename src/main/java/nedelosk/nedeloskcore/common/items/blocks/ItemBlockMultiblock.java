package nedelosk.nedeloskcore.common.items.blocks;

import java.util.List;

import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockMultiblock extends ItemBlockForestday {

	public ItemBlockMultiblock(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add(EnumChatFormatting.ITALIC + NCoreApi.getMaterials().get(stack.getItemDamage()).block.getDisplayName());
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return NRegistry.setUnlocalizedItemName("multiblock" + (Block.getBlockFromItem(itemstack.getItem()) == NCBlocks.Multiblock_Valve.block() ? "_valve" : ""), "nc");
	}

}
