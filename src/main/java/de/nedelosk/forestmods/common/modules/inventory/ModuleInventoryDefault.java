package de.nedelosk.forestmods.common.modules.inventory;

import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ModuleInventoryDefault extends ModuleInventory {

	public ModuleInventoryDefault(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List addSlots(IContainerBase container, IModularDefault modular, ModuleStack stack) {
		return null;
	}

	@Override
	public boolean transferInput(ModuleStack stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}
}
