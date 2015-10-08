package nedelosk.nedeloskcore.common.items.blocks;

import java.util.List;

import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.core.registry.NCBlockManager;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import nedelosk.nedeloskcore.common.items.ItemBlockForest;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockMultiblock extends ItemBlockForest {

	public ItemBlockMultiblock(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add(EnumChatFormatting.ITALIC + NCoreApi.getMaterials().get(stack.getItemDamage()).block.getDisplayName());
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return NCRegistry.setUnlocalizedItemName("multiblock" + (Block.getBlockFromItem(itemstack.getItem()) == NCBlockManager.Multiblock_Valve.block() ? "_valve" : ""), "nc");
	}

}
