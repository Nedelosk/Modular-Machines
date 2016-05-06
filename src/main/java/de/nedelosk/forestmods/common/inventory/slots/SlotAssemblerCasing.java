package de.nedelosk.forestmods.common.inventory.slots;

import de.nedelosk.forestmods.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
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
		((TileModularAssembler)inventory).assembler.updateControllerSlots();
		((TileModularAssembler)inventory).assembler.updateActivitys(player, true);
		super.onSlotChanged();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(stack);
		if(container != null && IModuleCasing.class.isAssignableFrom(container.getModuleClass())){
			return true;
		}
		return false;
	}
}
