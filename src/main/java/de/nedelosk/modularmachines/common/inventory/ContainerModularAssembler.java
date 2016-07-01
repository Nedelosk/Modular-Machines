package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerOutput;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerModularAssembler extends ContainerBase<TileModularAssembler> {

	public ContainerModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 10 + l1 * 18, 175 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 10 + j1 * 18, 233));
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {

		ModularManager.assembleModular(handler.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), player, new ItemStack(BlockManager.blockModular));

		super.onCraftMatrixChanged(inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotAssembler(this, 0, 10, 82, inventory.player, IModuleCasing.class));
		addSlotToContainer(new SlotAssemblerOutput(handler, 1, 154, 82));
		addSlotToContainer(new SlotAssembler(this, 2, 46, 10, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 3, 82, 10, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 4, 118, 10, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 5, 28, 46, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 6, 64, 46, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 7, 100, 46, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 8, 136, 46, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 9, 46, 82, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 10, 82, 82, inventory.player, IModuleController.class));
		addSlotToContainer(new SlotAssembler(this, 11, 118, 82, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 12, 28, 118, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 13, 64, 118, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 14, 100, 118, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 15, 136, 118, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 16, 46, 154, inventory.player));
		addSlotToContainer(new SlotAssembler(this, 17, 82, 154, inventory.player, IModuleTool.class));
		addSlotToContainer(new SlotAssembler(this, 18, 118, 154, inventory.player));
	}
}
