/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.utils.capabilitys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityUtils {
	
	@Nullable
	public static <T> T getCapability(IBlockAccess world, BlockPos pos, @Nonnull Capability<T> capability, @Nullable EnumFacing facing){
		TileEntity tile = world.getTileEntity(pos);
		if(tile == null){
			return null;
		}
		return getCapability(tile, capability, facing);
	}
	
	@Nullable
	public static <T> T getCapability(ICapabilityProvider provider, @Nonnull Capability<T> capability, @Nullable EnumFacing facing){
		if(provider == null || !provider. hasCapability(capability, facing)){
			return null;
		}
		return provider.getCapability(capability, facing);
	}
}
