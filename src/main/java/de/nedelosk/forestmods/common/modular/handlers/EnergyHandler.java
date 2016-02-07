package de.nedelosk.forestmods.common.modular.handlers;

import cofh.api.energy.IEnergyHandler;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSyncEnergy;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyHandler implements IEnergyHandler {

	public IModular modular;

	public EnergyHandler(IModular machine) {
		this.modular = machine;
	}

	public void setMachine(IModular machine) {
		this.modular = machine;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return ModularUtils.getBatteryStack(modular).getSaver() != null;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int e = ModularUtils.getBatteryStack(modular).getSaver().getStorage().receiveEnergy(maxReceive, simulate);
		if (e > 0) {
			PacketHandler.INSTANCE.sendToAll(new PacketSyncEnergy((TileEntity & IModularTileEntity) modular.getMachine()));
		}
		return e;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		int e = ModularUtils.getBatteryStack(modular).getSaver().getStorage().extractEnergy(maxExtract, simulate);
		if (e > 0) {
			PacketHandler.INSTANCE.sendToAll(new PacketSyncEnergy((TileEntity & IModularTileEntity) modular.getMachine()));
		}
		return e;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return ModularUtils.getBatteryStack(modular).getSaver().getStorage().getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ModularUtils.getBatteryStack(modular).getSaver().getStorage().getMaxEnergyStored();
	}
}
