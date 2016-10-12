package de.nedelosk.modularmachines.common.inventory;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.common.utils.ContainerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public abstract class BaseContainer<H extends IGuiProvider> extends Container implements IContainerBase<H> {

	protected H handler;
	protected EntityPlayer player;

	public BaseContainer(H tile, InventoryPlayer inventory) {
		this.handler = tile;
		this.player = inventory.player;
		addInventory(inventory);
		addSlots(inventory);
	}

	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 142));
		}
	}

	@Override
	public List<IContainerListener> getListeners(){
		return listeners;
	}

	@Override
	public H getHandler() {
		return handler;
	}

	protected abstract void addSlots(InventoryPlayer inventory);

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		if(handler instanceof TileEntity){
			TileEntity tile = (TileEntity) handler;
			BlockPos pos = tile.getPos();
			Block block = tile.getWorld().getBlockState(pos).getBlock();
			// does the block we interacted with still exist?
			if(block == Blocks.AIR || block != tile.getBlockType()) {
				return false;
			}

			// too far away from block?
			return player.getDistanceSq(pos.getX() + 0.5d,
					pos.getY() + 0.5d,
					pos.getZ() + 0.5d) <= 64;
		}
		return true;
	}

	public boolean sameGui(BaseContainer otherContainer) {
		return this.handler == otherContainer.handler;
	}

	public void syncOnOpen(EntityPlayerMP playerOpened) {
		// find another player that already has the gui for this tile open
		WorldServer server = playerOpened.getServerWorld();
		for(EntityPlayer player : server.playerEntities) {
			if(player == playerOpened) {
				continue;
			}
			if(player.openContainer instanceof BaseContainer) {
				if(this.sameGui((BaseContainer<H>) player.openContainer)) {
					syncWithOtherContainer((BaseContainer<H>) player.openContainer, playerOpened);
					return;
				}
			}
		}

		// no player has a container open for the tile
		syncNewContainer(playerOpened);
	}

	protected void syncWithOtherContainer(BaseContainer<H> otherContainer, EntityPlayerMP player) {
	}

	/**
	 * Called when the container is opened and no other player has it open.
	 * Set the default state here.
	 */
	protected void syncNewContainer(EntityPlayerMP player) {
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return ContainerUtil.transferStackInSlot(inventorySlots, player, slotIndex);
	}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public List<Slot> getSlots() {
		return inventorySlots;
	}
}
