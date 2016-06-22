package de.nedelosk.modularmachines.common.transport.node;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.transport.node.IContentHandler;
import de.nedelosk.modularmachines.api.transport.node.INodeSide;
import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemHandler implements IContentHandler<IItemHandler> {

	private final List<Inventory> inventorys;
	private final ITransportNode node;
	private int itemPerTick;

	public ItemHandler(ITransportNode node) {
		this.node = node;
		this.inventorys = Lists.newArrayList();
	}

	@Override
	public boolean insertItem(ItemStack stack) {
		for(INodeSide side : node.getSides()) {
			if (side.isConnected() && getHandler(side) != null) {
				IItemHandler itemHandler = getHandler(side);
				for(int slotIndex = 0; slotIndex < itemHandler.getSlots(); slotIndex++) {
					ItemStack existing = itemHandler.getStackInSlot(slotIndex);
					ItemStack inserted = itemHandler.insertItem(slotIndex, stack, true);
					if(inserted != null &&(existing == null || inserted != existing)){
						itemHandler.insertItem(slotIndex, stack, false);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean extractItem() {
		for(INodeSide side : node.getSides()) {
			if (side.isConnected() && getHandler(side) != null) {
				IItemHandler itemHandler = getHandler(side);
				for(int slotIndex = 0; slotIndex < itemHandler.getSlots(); slotIndex++) {
					if (itemHandler.getStackInSlot(slotIndex) != null) {
						ItemStack extraced = itemHandler.extractItem(slotIndex, itemPerTick, true);
						if (extraced != null && extraced.stackSize > 0) {
							for(ITransportNode otherNode : node.getSystem().getNodes()){
								for(INodeSide nodeSide : otherNode.getSides()){
									if(nodeSide != null && nodeSide != side && side.isConnected() && side.getHandler(getHandlerClass()) != null){
										IContentHandler<IItemHandler> handler = side.getHandler(getHandlerClass());
										if(handler.insertItem(extraced)){
											itemHandler.extractItem(slotIndex, itemPerTick, false);
											return true;
										}
									}
								}
							}
									
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
		if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getSide().getOpposite())){
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
	}

	@Override
	public void update() {
		for(ITransportNode node : node.getSystem().getNodes()) {
			for(INodeSide side : node.getSides()) {
				if (canConnectToSide(side)) {
					inventorys.add(new Inventory(side, side.getPart().getWorldLocation().getDistSq(node.getWorldLocation())));
				}
			}
		}
		if (!inventorys.isEmpty()) {
			Collections.sort(inventorys);
		}
	}

	@Override
	public IItemHandler getHandler(INodeSide side) {
		TileEntity tile = side.getSideTile();
		return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getSide().getOpposite());
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

	@Override
	public Class<IItemHandler> getHandlerClass() {
		return IItemHandler.class;
	}
}
