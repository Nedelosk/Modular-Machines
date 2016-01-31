package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.forestcore.library.Log;
import nedelosk.forestday.common.blocks.tiles.TileMachineBase;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.common.modular.ModularMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileModularMachine extends TileMachineBase implements IModularTileEntity<ModularMachine> {

	public ModularMachine modular;

	public TileModularMachine() {
		super(0);
	}

	@Override
	public String getMachineTileName() {
		return "modular";
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		try {
			NBTTagCompound machineTag = new NBTTagCompound();
			modular.writeToNBT(machineTag);
			nbt.setTag("Machine", machineTag);
		} catch (Exception e) {
			e.printStackTrace();
			if (!worldObj.isRemote) {
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
			Log.err("Error To Write Data From Modular Machine on Position " + xCoord + ", " + yCoord + ", " + zCoord);
			e.printStackTrace();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		modular = new ModularMachine(nbt.getCompoundTag("Machine"));
		if (modular == null || modular.getModuleContainers() == null) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			Log.err("Error To Load Data From Modular Machine on Position  " + xCoord + ", " + yCoord + ", " + zCoord);
		} else {
			modular.setMachine(this);
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (modular != null && modular instanceof IModularInventory) {
			return ((IModularInventory) modular).getGuiManager().getContainer(this, inventory);
		} else {
			return null;
		}
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (modular != null && modular instanceof IModularInventory) {
			return ((IModularInventory) modular).getGuiManager().getGUIContainer(this, inventory);
		} else {
			return null;
		}
	}

	@Override
	public void updateClient() {
		if (modular != null) {
			modular.update(false);
		}
	}

	@Override
	public void updateServer() {
		if (modular != null) {
			modular.update(true);
		}
	}

	public void assembleModular() throws ModularException {
		modular.assemble();
		modular.initModular();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void setModular(ModularMachine modular) {
		this.modular = modular;
	}

	@Override
	public ModularMachine getModular() {
		return modular;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public int getYCoord() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (modular == null) {
			return 0;
		}
		if (modular.getUtilsManager() == null) {
			return 0;
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return 0;
		}
		return modular.getUtilsManager().getFluidHandler().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getUtilsManager() == null) {
			return null;
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return null;
		}
		return modular.getUtilsManager().getFluidHandler().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getUtilsManager() == null) {
			return null;
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return null;
		}
		return modular.getUtilsManager().getFluidHandler().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getUtilsManager() == null) {
			return false;
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return false;
		}
		return modular.getUtilsManager().getFluidHandler().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getUtilsManager() == null) {
			return false;
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return false;
		}
		return modular.getUtilsManager().getFluidHandler().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (modular == null) {
			return new FluidTankInfo[0];
		}
		if (modular.getUtilsManager() == null) {
			return new FluidTankInfo[0];
		}
		if (modular.getUtilsManager().getFluidHandler() == null) {
			return new FluidTankInfo[0];
		}
		return modular.getUtilsManager().getFluidHandler().getTankInfo(from);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getUtilsManager() == null) {
			return 0;
		}
		if (modular.getUtilsManager().getEnergyHandler() == null) {
			return 0;
		}
		return modular.getUtilsManager().getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getUtilsManager() == null) {
			return 0;
		}
		if (modular.getUtilsManager().getEnergyHandler() == null) {
			return 0;
		}
		return modular.getUtilsManager().getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getUtilsManager() == null) {
			return 0;
		}
		if (modular.getUtilsManager().getEnergyHandler() == null) {
			return 0;
		}
		return modular.getUtilsManager().getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getUtilsManager() == null) {
			return 0;
		}
		if (modular.getUtilsManager().getEnergyHandler() == null) {
			return 0;
		}
		return modular.getUtilsManager().getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (modular == null) {
			return false;
		}
		if (modular.getUtilsManager() == null) {
			return false;
		}
		if (modular.getUtilsManager().getEnergyHandler() == null) {
			return false;
		}
		return modular.getUtilsManager().getEnergyHandler().canConnectEnergy(from);
	}

	@Override
	public String getMachineName() {
		return null;
	}
}
