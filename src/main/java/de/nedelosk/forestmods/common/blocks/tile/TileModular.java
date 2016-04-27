package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.common.modular.Modular;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularException;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileModular extends TileMachineBase implements IModularTileEntity<IModular> {

	public IModular modular;

	public TileModular() {
		super();
	}

	@Override
	public String getTitle() {
		return "modular";
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (modular != null) {
			NBTTagCompound machineTag = new NBTTagCompound();
			modular.writeToNBT(machineTag);
			nbt.setTag("Modular", machineTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("Modular")) {
			modular = new Modular(nbt.getCompoundTag("Modular"), this);
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.getContainer(this, inventory);
		} else {
			return null;
		}
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.getGUIContainer(this, inventory);
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

	@Override
	public void assembleModular() {
		try {
			modular.assemble();
		} catch (ModularException e) {
			modular.setLastException(e);
		}
		modular.initModular();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void setModular(IModular modular) {
		modular.setTile(this);
		this.modular = modular;
	}

	@Override
	public IModular getModular() {
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
		if (modular.getFluidHandler() == null) {
			return 0;
		}
		return modular.getFluidHandler().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getFluidHandler() == null) {
			return null;
		}
		return modular.getFluidHandler().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getFluidHandler() == null) {
			return null;
		}
		return modular.getFluidHandler().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getFluidHandler() == null) {
			return false;
		}
		return modular.getFluidHandler().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getFluidHandler() == null) {
			return false;
		}
		return modular.getFluidHandler().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (modular == null) {
			return new FluidTankInfo[0];
		}
		if (modular.getFluidHandler() == null) {
			return new FluidTankInfo[0];
		}
		return modular.getFluidHandler().getTankInfo(from);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (modular == null) {
			return false;
		}
		if (modular.getEnergyHandler() == null) {
			return false;
		}
		return modular.getEnergyHandler().canConnectEnergy(from);
	}

	@Override
	public World getWorld() {
		return worldObj;
	}
}
