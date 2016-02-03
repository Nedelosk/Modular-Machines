package de.nedelosk.forestmods.common.blocks.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.fluids.TankManager;
import de.nedelosk.forestcore.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.client.gui.GuiCowperFluidPort;
import de.nedelosk.forestmods.common.inventory.ContainerCowperFluidPort;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCowperFluidPort extends TileCowperBase implements IFluidHandler {

	public static enum PortType {
		INPUT, FUEL, OUTPUT, STEAM;
	}

	private PortType type;

	public TileCowperFluidPort() {
		super();
		type = PortType.INPUT;
	}

	public TileCowperFluidPort(PortType type) {
		super();
		this.type = type;
	}

	public PortType getType() {
		return type;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - This cowper part may not be placed in the cowper's frame", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - This cowper may not be placed in the cowper's top", xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This cowper part may not be placed in theair cowper's bottom", xCoord, yCoord, zCoord));
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isConnected() && from == getOutwardsDir()) {
			if (type == PortType.INPUT) {
				return getController().getTankManager().getTank(0).fill(resource, doFill);
			} else if (type == PortType.FUEL) {
				return getController().getTankManager().getTank(1).fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (type == PortType.INPUT) {
				return getController().getTankManager().getTank(0).drain(resource, doDrain);
			} else if (type == PortType.FUEL) {
				return getController().getTankManager().getTank(1).drain(resource, doDrain);
			} else if (type == PortType.OUTPUT) {
				return getController().getTankManager().getTank(2).drain(resource, doDrain);
			} else if (type == PortType.STEAM) {
				return getController().getTankManager().getTank(2).drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (type == PortType.INPUT) {
				return getController().getTankManager().getTank(0).drain(maxDrain, doDrain);
			} else if (type == PortType.FUEL) {
				return getController().getTankManager().getTank(1).drain(maxDrain, doDrain);
			} else if (type == PortType.OUTPUT) {
				return getController().getTankManager().getTank(2).drain(maxDrain, doDrain);
			} else if (type == PortType.STEAM) {
				return getController().getTankManager().getTank(2).drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}
		if (type == PortType.OUTPUT || type == PortType.STEAM) {
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
		data.setInteger("Type", type.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		type = PortType.values()[data.getInteger("Type")];
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new ContainerCowperFluidPort(this, inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (!isConnected() || !getController().isAssembled()) {
			return null;
		}
		return new GuiCowperFluidPort(this, inventory);
	}
}
