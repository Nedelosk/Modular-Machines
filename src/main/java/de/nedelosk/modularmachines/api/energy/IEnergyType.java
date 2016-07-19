package de.nedelosk.modularmachines.api.energy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IEnergyType<I extends Number> {

	@Nullable
	IEnergyInterface getInterface(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing facing);

	String getFullName();

	/**
	 * @return the short name of the energy type, like rf or eu.
	 */
	String getShortName();
}
