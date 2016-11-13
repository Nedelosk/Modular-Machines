package modularmachines.common.utils;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.energy.IEnergyBuffer;

public class EnergyUtil {

	public static IEnergyStorage getStorage(World world, BlockPos pos) {
		return getStorage(world, pos, null);
	}

	public static IEnergyStorage getStorage(World world, BlockPos pos, @Nullable EnumFacing facing) {
		if (facing != null) {
			pos = pos.offset(facing);
		}
		TileEntity tile = world.getTileEntity(pos);
		EnumFacing from = facing != null ? facing.getOpposite() : null;
		if (tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, from)) {
			return tile.getCapability(CapabilityEnergy.ENERGY, from);
		}
		return null;
	}

	public static long transferEnergy(World world, BlockPos pos, IEnergyBuffer buffer, int maxEnergyToTransfer, boolean simulate) {
		return buffer.extractEnergy(null, null, transferEnergy(world, pos, Math.min((int) buffer.getEnergyStored(), maxEnergyToTransfer), simulate), simulate);
	}

	public static int transferEnergy(World world, BlockPos pos, IEnergyStorage storage, int maxEnergyToTransfer, boolean simulate) {
		return storage.extractEnergy(transferEnergy(world, pos, Math.min(storage.getEnergyStored(), maxEnergyToTransfer), simulate), simulate);
	}

	public static int transferEnergy(World world, BlockPos pos, int storagedEnergy, int maxEnergyToTransfer, boolean simulate) {
		return transferEnergy(world, pos, Math.min(storagedEnergy, maxEnergyToTransfer), simulate);
	}

	public static int transferEnergy(World world, BlockPos pos, int maxEnergyToTransfer, boolean simulate) {
		int totalTransfered = 0;
		for (EnumFacing facing : EnumFacing.VALUES) {
			IEnergyStorage storageTransfer = getStorage(world, pos, facing);
			if (storageTransfer != null) {
				int transfer = storageTransfer.receiveEnergy(maxEnergyToTransfer, simulate);
				if (transfer > 0) {
					totalTransfered += transfer;
					maxEnergyToTransfer -= transfer;
					if (maxEnergyToTransfer <= 0) {
						break;
					}
				}
			}
		}
		return totalTransfered;
	}
}
