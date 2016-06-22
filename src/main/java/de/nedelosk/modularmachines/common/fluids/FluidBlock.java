package de.nedelosk.modularmachines.common.fluids;

import java.util.Locale;

import de.nedelosk.modularmachines.common.core.Registry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;

public class FluidBlock extends BlockFluidClassic {

	protected Fluid fluid;
	protected String fluidName;
	protected String modName;

	public FluidBlock(Fluid fluid, Material material, String fluidName) {
		super(fluid, material);
		this.fluid = fluid;
		this.fluidName = fluidName;
		this.modName = Loader.instance().activeModContainer().getModId().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock().getMaterial(blockState).isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}
	
	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock().getMaterial(blockState).isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public String getUnlocalizedName() {
		return Registry.setUnlocalizedBlockName(fluidName);
	}
}
