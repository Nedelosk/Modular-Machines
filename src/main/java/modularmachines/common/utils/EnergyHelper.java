package modularmachines.common.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.energy.CapabilityEnergy;

import forestry.core.tiles.TileUtil;

public class EnergyHelper {
	
	public static int extractEnergyFromAdjacentEnergyProvider(TileEntity tile, EnumFacing side, int energy, boolean simulate) {
		TileEntity handler = TileUtil.getTile(tile.getWorld(), tile.getPos().offset(side));
		
		if (handler != null && handler.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
			return handler.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).extractEnergy(energy, simulate);
		}
		return 0;
	}
	
	public static int insertEnergyIntoAdjacentEnergyReceiver(TileEntity tile, EnumFacing side, int energy, boolean simulate) {
		TileEntity handler = TileUtil.getTile(tile.getWorld(), tile.getPos().offset(side));
		
		if (handler != null && handler.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
			return handler.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(energy, simulate);
		}
		return 0;
	}
}
