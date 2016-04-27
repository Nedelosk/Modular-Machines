package de.nedelosk.forestmods.common.modular.handlers;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyHandler implements IEnergyProvider, IEnergyReceiver {

	public IModular modular;

	public EnergyHandler(IModular machine) {
		this.modular = machine;
	}

	public void setMachine(IModular machine) {
		this.modular = machine;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return !modular.getModules(IModuleBattery.class).isEmpty();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		for(IModuleBattery battery : modular.getModules(IModuleBattery.class)){
			int energy = battery.getStorage().receiveEnergy(maxReceive, true);
			if (energy > 0) {
				PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), battery));
				battery.getStorage().extractEnergy(maxReceive, simulate);
				return energy;
			}
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		for(IModuleBattery battery : modular.getModules(IModuleBattery.class)){
			int energy = battery.getStorage().extractEnergy(maxExtract, true);
			if (energy > 0) {
				PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), battery));
				battery.getStorage().extractEnergy(maxExtract, simulate);
				return energy;
			}
		}
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		int energy = 0;
		for(IModuleBattery battery : modular.getModules(IModuleBattery.class)){
			energy+=battery.getStorage().getEnergyStored();
		}
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		int maxEnergy = 0;
		for(IModuleBattery battery : modular.getModules(IModuleBattery.class)){
			maxEnergy+=battery.getStorage().getMaxEnergyStored();
		}
		return maxEnergy;
	}
}
