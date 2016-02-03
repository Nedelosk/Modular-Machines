package de.nedelosk.forestmods.common.blocks.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.fluids.TankManager;
import de.nedelosk.forestcore.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.client.gui.GuiCokeOvenFluidPort;
import de.nedelosk.forestmods.common.inventory.ContainerCokeOvenFluidPort;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCokeOvenFluidPort extends TileCokeOvenBase implements IFluidHandler {

	private PortType portType;

	public static enum PortType {
		OUTPUT, FUEL;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke ovene part may not be placed in the coke oven's frame", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the coke oven's top", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This coke oven part may not be placed in the bcoke oven's bottom", xCoord, yCoord, zCoord));
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isConnected() && from == getOutwardsDir() && portType == PortType.FUEL) {
			return getController().getTankManager().getTank(0).fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (portType == PortType.FUEL) {
				return getController().getTankManager().getTank(0).drain(resource, doDrain);
			} else if (portType == PortType.OUTPUT) {
				return getController().getTankManager().getTank(1).drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (portType == PortType.FUEL) {
				return getController().getTankManager().getTank(0).drain(maxDrain, doDrain);
			} else if (portType == PortType.OUTPUT) {
				return getController().getTankManager().getTank(1).drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}
		if (!(portType == PortType.FUEL)) {
			return false;
		}
		TankManager tm = getController().getTankManager();
		return tm.canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}
		TankManager tm = getController().getTankManager();
		return tm.canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (!isConnected() || from != getOutwardsDir()) {
			return new FluidTankInfo[0];
		}
		TankManager tm = getController().getTankManager();
		return tm.getTankInfo(from);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("Type", portType.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		portType = PortType.values()[data.getInteger("Type")];
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new ContainerCokeOvenFluidPort(this, inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new GuiCokeOvenFluidPort(this, inventory);
	}

	public PortType getPortType() {
		return portType;
	}
}
