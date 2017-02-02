package modularmachines.common.utils;

import java.util.List;

import modularmachines.api.ILocatable;
import modularmachines.api.ILocatableSource;
import modularmachines.common.containers.ContainerAssembler;
import modularmachines.common.core.ModularMachines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class ContainerUtil {
	
	private static final int SIZE_PLAYER_INV = 9 * 4;
	private static final int SIVE_HOTBAR = 9;

	public static ItemStack transferStackInSlot(List inventorySlots, EntityPlayer player, int slotIndex) {
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		if (slot == null || !slot.getHasStack()) {
			return null;
		}
		int numSlots = inventorySlots.size();
		ItemStack stackInSlot = slot.getStack();
		ItemStack originalStack = stackInSlot.copy();
		if (!shiftItemStack(inventorySlots, stackInSlot, slotIndex, numSlots)) {
			return null;
		}
		slot.onSlotChange(stackInSlot, originalStack);
		if (stackInSlot.getCount() <= 0) {
			slot.putStack(ItemUtil.empty());
		} else {
			slot.onSlotChanged();
		}
		if (stackInSlot.getCount() == originalStack.getCount()) {
			return null;
		}
		slot.onTake(player, stackInSlot);
		return originalStack;
	}

	private static boolean shiftItemStack(List inventorySlots, ItemStack stackInSlot, int slotIndex, int numSlots) {
		if (isInPlayerInventory(slotIndex)) {
			if (shiftToInventory(inventorySlots, stackInSlot, numSlots)) {
				return true;
			}
			if (isInPlayerHotbar(slotIndex)) {
				return shiftToPlayerInventoryNoHotbar(inventorySlots, stackInSlot);
			} else {
				return shiftToHotbar(inventorySlots, stackInSlot);
			}
		} else {
			return shiftToPlayerInventory(inventorySlots, stackInSlot);
		}
	}

	private static boolean shiftToPlayerInventory(List inventorySlots, ItemStack stackInSlot) {
		int playerHotbarStart = SIZE_PLAYER_INV - SIVE_HOTBAR;
		// try to merge with existing stacks, hotbar first
		boolean shifted = shiftItemStackToRangeMerge(inventorySlots, stackInSlot, playerHotbarStart, SIVE_HOTBAR);
		shifted |= shiftItemStackToRangeMerge(inventorySlots, stackInSlot, 0, playerHotbarStart);
		// shift to open slots, hotbar first
		shifted |= shiftItemStackToRangeOpenSlots(inventorySlots, stackInSlot, playerHotbarStart, SIVE_HOTBAR);
		shifted |= shiftItemStackToRangeOpenSlots(inventorySlots, stackInSlot, 0, playerHotbarStart);
		return shifted;
	}

	private static boolean shiftToPlayerInventoryNoHotbar(List inventorySlots, ItemStack stackInSlot) {
		return shiftItemStackToRange(inventorySlots, stackInSlot, 0, SIZE_PLAYER_INV - SIVE_HOTBAR);
	}

	private static boolean shiftToHotbar(List inventorySlots, ItemStack stackInSlot) {
		return shiftItemStackToRange(inventorySlots, stackInSlot, SIZE_PLAYER_INV - SIVE_HOTBAR, SIVE_HOTBAR);
	}

	private static boolean shiftToInventory(List inventorySlots, ItemStack stackToShift, int numSlots) {
		boolean success = false;
		if (stackToShift.isStackable()) {
			success = shiftToMachineInventory(inventorySlots, stackToShift, numSlots, true);
		}
		if (stackToShift.getCount() > 0) {
			success |= shiftToMachineInventory(inventorySlots, stackToShift, numSlots, false);
		}
		return success;
	}

	// if mergeOnly = true, don't shift into empty slots.
	private static boolean shiftToMachineInventory(List inventorySlots, ItemStack stackToShift, int numSlots, boolean mergeOnly) {
		for (int machineIndex = SIZE_PLAYER_INV; machineIndex < numSlots; machineIndex++) {
			Slot slot = (Slot) inventorySlots.get(machineIndex);
			if (mergeOnly && slot.getStack() == null) {
				continue;
			}
			if (!slot.isItemValid(stackToShift)) {
				continue;
			}
			if (shiftItemStackToRange(inventorySlots, stackToShift, machineIndex, 1)) {
				return true;
			}
		}
		return false;
	}

	private static boolean shiftItemStackToRange(List inventorySlots, ItemStack stackToShift, int start, int count) {
		boolean changed = shiftItemStackToRangeMerge(inventorySlots, stackToShift, start, count);
		changed |= shiftItemStackToRangeOpenSlots(inventorySlots, stackToShift, start, count);
		return changed;
	}

	private static boolean shiftItemStackToRangeMerge(List inventorySlots, ItemStack stackToShift, int start, int count) {
		if (stackToShift == null || !stackToShift.isStackable() || stackToShift.getCount() <= 0) {
			return false;
		}
		boolean changed = false;
		for (int index = start; stackToShift.getCount() > 0 && index < start + count; index++) {
			Slot slot = (Slot) inventorySlots.get(index);
			ItemStack stackInSlot = slot.getStack();
			if (stackInSlot != null && ItemUtil.isIdenticalItem(stackInSlot, stackToShift)) {
				int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
				int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
				if (resultingStackSize <= max) {
					ItemUtil.setCount(stackToShift, 0);
					ItemUtil.setCount(stackInSlot, resultingStackSize);
					slot.onSlotChanged();
					changed = true;
				} else if (stackInSlot.getCount() < max) {
					ItemUtil.shrink(stackToShift, max - stackInSlot.getCount());
					ItemUtil.setCount(stackInSlot, max);
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}

	private static boolean shiftItemStackToRangeOpenSlots(List inventorySlots, ItemStack stackToShift, int start, int count) {
		if (stackToShift == null || stackToShift.getCount() <= 0) {
			return false;
		}
		boolean changed = false;
		for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < start + count; slotIndex++) {
			Slot slot = (Slot) inventorySlots.get(slotIndex);
			ItemStack stackInSlot = slot.getStack();
			if (stackInSlot == null) {
				int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
				stackInSlot = stackToShift.copy();
				ItemUtil.setCount(stackInSlot, Math.min(stackToShift.getCount(), max));
				ItemUtil.shrink(stackToShift, stackInSlot.getCount());
				slot.putStack(stackInSlot);
				slot.onSlotChanged();
				changed = true;
			}
		}
		return changed;
	}

	private static boolean isInPlayerInventory(int slotIndex) {
		return slotIndex < SIZE_PLAYER_INV;
	}

	public static boolean isSlotInRange(int slotIndex, int start, int count) {
		return slotIndex >= start && slotIndex < start + count;
	}

	private static boolean isInPlayerHotbar(int slotIndex) {
		return isSlotInRange(slotIndex, SIZE_PLAYER_INV - SIVE_HOTBAR, SIZE_PLAYER_INV);
	}
	
	/**
	 * Open a gui and transfer the held item of the players.
	 */
	public static void openGuiSave(ILocatableSource source){
		openOrCloseGuiSave(source, true);
	}
	
	public static EntityPlayer getPlayer(Container container){
		Slot slot = container.getSlot(0);
		if(slot != null && slot.inventory instanceof InventoryPlayer){
			InventoryPlayer inv = (InventoryPlayer) slot.inventory;
			return inv.player;
		}
		return null;
	}
	
	public static void openOrCloseGuiSave(ILocatableSource source, boolean openGui){
		ILocatable locatable = source.getLocatable();
		if(locatable != null){
			World world = locatable.getWorldObj();
			BlockPos position = locatable.getCoordinates();
			if(world instanceof WorldServer){
				WorldServer server = (WorldServer) world;
				for (EntityPlayer player : server.playerEntities) {
					if(!(player instanceof EntityPlayerMP) || player instanceof FakePlayer){
						continue;
					}
					EntityPlayerMP playerMP = (EntityPlayerMP) player;
					if (player.openContainer instanceof ContainerAssembler) {
						ContainerAssembler container = (ContainerAssembler) player.openContainer;
						if (container.getSource() == source) {
							if(!openGui){
								player.closeScreen();
							}else{
								ItemStack itemStack = ItemUtil.empty();
								InventoryPlayer inv = player.inventory;
								if (!inv.getItemStack().isEmpty()) {
									itemStack = inv.getItemStack();
									inv.setItemStack(ItemUtil.empty());
								}
								player.openGui(ModularMachines.instance, 0, world, position.getX(), position.getY(), position.getZ());
								if (!itemStack.isEmpty()) {
									inv.setItemStack(itemStack);
									playerMP.connection.sendPacket(new SPacketSetSlot(-1, -1, itemStack));
								}
							}
						}
					}
				}
			}
		}
	}

}
