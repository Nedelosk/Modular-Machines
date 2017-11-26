package modularmachines.common.modules.components;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.INeighborBlockComponent;

public class WaterIntakeComponent extends TickableComponent implements INBTWritable, INBTReadable,
		INeighborBlockComponent {
	private static final int TIME_CONSTANT = 40;
	private static final int WATER_AMOUNT = 25 * TIME_CONSTANT;
	
	private int watertSources = -1;
	private boolean inHell;
	
	@Override
	public void update() {
		super.update();
		if (!tickHelper.updateOnInterval(TIME_CONSTANT)) {
			return;
		}
		IFluidHandler fluidHandler = provider.getContainer().getComponent(IFluidHandler.class);
		if (fluidHandler == null) {
			return;
		}
		if (!inHell) {
			ILocatable locatable = provider.getContainer().getLocatable();
			World world = locatable.getWorldObj();
			BlockPos pos = locatable.getCoordinates();
			if (watertSources > 2) {
				fluidHandler.fill(new FluidStack(FluidRegistry.WATER, WATER_AMOUNT * (watertSources - 1)), true);
			} else if (world.isRaining() && world.canSeeSky(pos)) {
				fluidHandler.fill(new FluidStack(FluidRegistry.WATER, WATER_AMOUNT), true);
			}
		}
		
		if (watertSources < 0) {
			updateSources();
		}
	}
	
	@Override
	public void onNeighborBlockChange() {
		updateSources();
	}
	
	private void updateSources() {
		ILocatable locatable = provider.getContainer().getLocatable();
		World world = locatable.getWorldObj();
		BlockPos pos = locatable.getCoordinates();
		watertSources = 0;
		inHell = world.getBiome(pos) == Biomes.HELL;
		
		if (isWater(world.getBlockState(pos.down()))) {
			watertSources++;
		}
		if (isWater(world.getBlockState(pos.up()))) {
			watertSources++;
		}
		if (isWater(world.getBlockState(pos.west()))) {
			watertSources++;
		}
		if (isWater(world.getBlockState(pos.east()))) {
			watertSources++;
		}
		if (isWater(world.getBlockState(pos.north()))) {
			watertSources++;
		}
		if (isWater(world.getBlockState(pos.south()))) {
			watertSources++;
		}
	}
	
	private static boolean isWater(IBlockState state) {
		return (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER) && state.getValue(BlockLiquid.LEVEL) == 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inHell = compound.getBoolean("Hell");
		watertSources = compound.getInteger("Sources");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("Hell", inHell);
		compound.setInteger("Sources", watertSources);
		return compound;
	}
}
