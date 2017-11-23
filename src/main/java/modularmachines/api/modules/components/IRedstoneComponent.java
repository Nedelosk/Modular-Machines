/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.components;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * This component can be used to provide redstone power.
 */
public interface IRedstoneComponent extends IModuleComponent {
	
	boolean canProvidePower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side);
}
