/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.assembler.page;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.SlotModule;
import modularmachines.api.modules.storages.IStoragePosition;

public class BasicStoragePage extends StoragePage {

	public BasicStoragePage(IAssembler assembler, IStoragePosition position, int size) {
		super(assembler, position, size);
	}

	public BasicStoragePage(IAssembler assembler, IStoragePosition position) {
		super(assembler, position);
	}
	
	@Override
	public void createContainerSlots(Container container, EntityPlayer player, IAssembler assembler, List<Slot> slots) {
		if (position != null) {
			slots.add(new SlotModule(this.slots.getStorageSlot(), 44, 35, player));
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
