package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAssemblerCasing extends Slot {

	private EntityPlayer player;

	public SlotAssemblerCasing(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, EntityPlayer player) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);

		this.player = player;
	}

	@Override
	public void onSlotChanged() {
		((TileModularAssembler)inventory).assembler.updateControllerSlots(player);
		((TileModularAssembler)inventory).assembler.update(player, true);
		super.onSlotChanged();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer container = ModuleManager.getContainerFromItem(stack);
		if(container != null && IModuleCasing.class.isAssignableFrom(container.getModule().getClass())){
			return true;
		}
		return false;
	}
}
