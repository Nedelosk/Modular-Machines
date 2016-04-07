package de.nedelosk.forestmods.common.transport.node;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import cofh.api.inventory.IInventoryConnection;
import de.nedelosk.forestcore.utils.InventoryUtil;
import de.nedelosk.forestmods.api.transport.node.IContentHandler;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;

public class ItemHandler implements IContentHandler<IInventory> {

	private final List<Inventory> inventorys;
	private final ITransportNode node;

	public ItemHandler(ITransportNode node) {
		this.node = node;
		this.inventorys = Lists.newArrayList();
	}

	public boolean insertItems() {
		return false;
	}

	public boolean extractItems() {
		for(INodeSide side : node.getSides()) {
			if (side.isConnected() && getHandler(side) != null) {
				IInventory inventory = getHandler(side);
				for(int slotIndex = 0; slotIndex < inventory.getSizeInventory(); slotIndex++) {
					if (inventory.getStackInSlot(slotIndex) != null) {
						if (((ISidedInventory) inventory).canExtractItem(slotIndex, inventory.getStackInSlot(slotIndex), side.getSide().ordinal())) {
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canConnectToSide(INodeSide side) {
		TileEntity tile = side.getSideTile();
		if (!(tile instanceof IInventory)) {
			return false;
		}
		IInventory inv = (IInventory) tile;
		if (inv instanceof IInventoryConnection) {
			return ((IInventoryConnection) inv).canConnectInventory(side.getSide()).canConnect;
		} else if (inv instanceof ISidedInventory) {
			int[] slots = ((ISidedInventory) inv).getAccessibleSlotsFromSide(side.getSide().ordinal());
			return slots != null && slots.length > 0;
		} else {
			return true;
		}
	}

	@Override
	public void tick() {
	}

	@Override
	public void update() {
		for(ITransportNode node : node.getSystem().getNodes()) {
			for(INodeSide side : node.getSides()) {
				if (side.getSideTile() instanceof IInventory) {
					inventorys.add(new Inventory(side, side.getPart().getWorldLocation().getDistSq(node.getWorldLocation())));
				}
			}
		}
		if (!inventorys.isEmpty()) {
			Collections.sort(inventorys);
		}
	}

	@Override
	public IInventory getHandler(INodeSide side) {
		IInventory inv = (IInventory) side.getSideTile();
		return InventoryUtil.getInventory(inv);
	}

	static class Inventory implements Comparable<Inventory> {

		INodeSide side;
		int distance;

		Inventory(INodeSide side, int distance) {
			this.side = side;
			this.distance = distance;
		}

		@Override
		public int compareTo(Inventory o) {
			if (side.getPriority() != o.side.getPriority()) {
				return compare(o.side.getPriority(), side.getPriority());
			}
			return compare(distance, o.distance);
		}

		static int compare(int x, int y) {
			return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}
	}
}
