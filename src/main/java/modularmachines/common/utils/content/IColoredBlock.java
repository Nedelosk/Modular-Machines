package modularmachines.common.utils.content;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IColoredBlock {

	@SideOnly(Side.CLIENT)
	int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex);
}
