package modularmachines.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemWrench extends Item implements IItemModelRegister {
	
	public ItemWrench() {
		setCreativeTab(TabModularMachines.tabModularMachines);
		setUnlocalizedName("wrench");
		setHarvestLevel("wrench", 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0);
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block.rotateBlock(worldIn, pos, facing)) {
			playerIn.swingArm(hand);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(super.getUnlocalizedName(stack).replace("item.", ""));
	}
}
