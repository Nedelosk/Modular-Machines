package nedelosk.modularmachines.common.multiblock.blastfurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.fluids.TankManager;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.modularmachines.client.gui.multiblock.GuiBlastFurnaceFluidPort;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerBlastFurnaceFluidPort;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBlastFurnaceFluidPort extends TileBlastFurnaceBase implements IFluidHandler {

	public static enum PortType {
		AIR, SLAG, OUTPUT, GAS;
	}

	private PortType type;

	public TileBlastFurnaceFluidPort() {
		super();
		type = PortType.AIR;
	}

	public TileBlastFurnaceFluidPort(PortType type) {
		super();
		this.type = type;
	}

	public PortType getType() {
		return type;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(
				String.format("%d, %d, %d - This blast furnace part may not be placed in the blast furnace's frame",
						xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		if (type != PortType.AIR && type != PortType.OUTPUT)
			throw new MultiblockValidationException(
					String.format("%d, %d, %d - This blast furnace part may not be placed in the blast furnace's side",
							xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		if (type != PortType.GAS)
			throw new MultiblockValidationException(
					String.format("%d, %d, %d - This blast furnace part may not be placed in the blast furnace's top",
							xCoord, yCoord, zCoord));
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		if (type != PortType.SLAG)
			throw new MultiblockValidationException(String.format(
					"%d, %d, %d - This blast furnace part may not be placed in the blast furnace's bottom", xCoord,
					yCoord, zCoord));
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isConnected() && from == getOutwardsDir() && type == PortType.AIR) {
			return getController().getTankManager().getTank(0).fill(resource, doFill);
		}

		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (type == PortType.AIR)
				return getController().getTankManager().getTank(0).drain(resource, doDrain);
			else if (type == PortType.SLAG)
				return getController().getTankManager().getTank(1).drain(resource, doDrain);
			else if (type == PortType.OUTPUT)
				return getController().getTankManager().getTank(2).drain(resource, doDrain);
			else if (type == PortType.GAS)
				return getController().getTankManager().getTank(3).drain(resource, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isConnected() && from == getOutwardsDir()) {
			if (type == PortType.AIR)
				return getController().getTankManager().getTank(0).drain(maxDrain, doDrain);
			else if (type == PortType.SLAG)
				return getController().getTankManager().getTank(1).drain(maxDrain, doDrain);
			else if (type == PortType.OUTPUT)
				return getController().getTankManager().getTank(2).drain(maxDrain, doDrain);
			else if (type == PortType.GAS)
				return getController().getTankManager().getTank(3).drain(maxDrain, doDrain);
		}

		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}

		if (!(type == PortType.AIR)) {
			return false;
		} // Prevent pipes from filling up the output tank inadvertently

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
		if (getMultiblockController() == null)
			return null;
		return new ContainerBlastFurnaceFluidPort(this, inventory);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (getMultiblockController() == null)
			return null;
		return new GuiBlastFurnaceFluidPort(this, inventory);
	}

}
