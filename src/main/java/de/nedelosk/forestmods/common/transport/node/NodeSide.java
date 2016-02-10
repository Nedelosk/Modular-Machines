package de.nedelosk.forestmods.common.transport.node;

import cofh.api.energy.IEnergyHandler;
import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestmods.api.transport.ITransportPart;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.common.transport.PartSide;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public class NodeSide extends PartSide implements INodeSide {

	public NodeSide(ForgeDirection side, ITransportPart part) {
		super(side, part);
	}

	@Override
	public ISidedInventory getInventory() {
		TileEntity tile = getSideTile();
		if (tile instanceof ISidedInventory) {
			return (ISidedInventory) tile;
		}
		return null;
	}

	@Override
	public IFluidHandler getFluidHandler() {
		TileEntity tile = getSideTile();
		if (tile instanceof IFluidHandler) {
			return (IFluidHandler) tile;
		}
		return null;
	}

	@Override
	public IEnergyHandler getEnergyHandler() {
		TileEntity tile = getSideTile();
		if (tile instanceof IEnergyHandler) {
			return (IEnergyHandler) tile;
		}
		return null;
	}

	private BlockPos getSidePos() {
		return new BlockPos(part.getTileEntity().xCoord + side.offsetX, part.getTileEntity().yCoord + side.offsetY, part.getTileEntity().zCoord + side.offsetZ);
	}

	private TileEntity getSideTile() {
		BlockPos pos = getSidePos();
		return part.getTileEntity().getWorldObj().getTileEntity(pos.x, pos.y, pos.z);
	}
}
