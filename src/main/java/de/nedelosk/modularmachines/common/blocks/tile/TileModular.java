package de.nedelosk.modularmachines.common.blocks.tile;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.common.modular.Modular;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileModular extends TileMachineBase implements IModularHandler<IModular> {

	public IModular modular;

	public TileModular() {
		super();
	}

	@Override
	public String getTitle() {
		return "modular";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (modular != null) {
			nbt.setTag("Modular", modular.writeToNBT(new NBTTagCompound()));
		}
		return super.writeToNBT(nbt);
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
	public void setModular(IModular modular) {
		modular.setHandler(this);
		this.modular = modular;
		markDirty();
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		if (modular == null) {
			return 0;
		}
		if (modular.getFluidHandler() == null) {
			return 0;
		}
		return modular.getFluidHandler().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getFluidHandler() == null) {
			return null;
		}
		return modular.getFluidHandler().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		if (modular == null) {
			return null;
		}
		if (modular.getFluidHandler() == null) {
			return null;
		}
		return modular.getFluidHandler().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getFluidHandler() == null) {
			return false;
		}
		return modular.getFluidHandler().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		if (modular == null) {
			return false;
		}
		if (modular.getFluidHandler() == null) {
			return false;
		}
		return modular.getFluidHandler().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		if (modular == null) {
			return new FluidTankInfo[0];
		}
		if (modular.getFluidHandler() == null) {
			return new FluidTankInfo[0];
		}
		return modular.getFluidHandler().getTankInfo(from);
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if (modular == null) {
			return false;
		}
		if (modular.getEnergyHandler() == null) {
			return false;
		}
		return modular.getEnergyHandler().canConnectEnergy(from);
	}
}
