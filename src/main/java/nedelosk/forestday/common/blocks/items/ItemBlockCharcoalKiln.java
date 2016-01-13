package nedelosk.forestday.common.blocks.items;

import java.util.List;

import nedelosk.forestday.common.multiblock.TileCharcoalKiln;
import nedelosk.forestday.modules.ModuleCoal;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBlockCharcoalKiln extends ItemBlock {

	public ItemBlockCharcoalKiln(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ,
			int metadata) {
		if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
			return false;
		}
		if (!(world.getTileEntity(x, y, z) instanceof TileCharcoalKiln)) {
			return false;
		}
		TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
		kiln.setWoodType(ModuleCoal.readFromStack(stack));
		if (world.getBlock(x, y, z) == field_150939_a) {
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean p_77624_4_) {
		if (ModuleCoal.readFromStack(stack) != null) {
			info.add(StatCollector.translateToLocal(ModuleCoal.readFromStack(stack).getWood().getUnlocalizedName() + ".name"));
		}
	}
}
