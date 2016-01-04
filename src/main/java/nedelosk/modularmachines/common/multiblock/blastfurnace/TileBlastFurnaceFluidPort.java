package nedelosk.modularmachines.common.multiblock.blastfurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.fluids.TankManager;
import nedelosk.modularmachines.client.gui.multiblock.GuiBlastFurnaceFluidPort;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerBlastFurnaceFluidPort;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBlastFurnaceFluidPort extends TileBlastFurnaceBase implements IFluidHandler {

	public static enum PortType {
		INPUT_AIR_HOT, SLAG, OUTPUT, Gas_Blast_Furnace;
	}

	private PortType type;

	public TileBlastFurnaceFluidPort() {
		super();
		type = PortType.INPUT_AIR_HOT;
	}

	public PortType getType() {
		return type;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!isConnected() || type != PortType.INPUT_AIR_HOT || from != getOutwardsDir()) {
			return 0;
		}

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (!isConnected() || from != getOutwardsDir()) {
			return null;
		}

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (!isConnected() || from != getOutwardsDir()) {
			return null;
		}

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}

		if (!(type == PortType.INPUT_AIR_HOT)) {
			return false;
		} // Prevent pipes from filling up the output tank inadvertently

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (!isConnected() || from != getOutwardsDir()) {
			return false;
		}

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (!isConnected() || from != getOutwardsDir()) {
			return new FluidTankInfo[0];
		}

		TankManager tm = getBlastFurnaceController().getTankManager();
		return tm.getTankInfo(from);
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
