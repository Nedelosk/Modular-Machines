package modularmachines.common.containers;

import javax.annotation.Nullable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;

public class ContainerChestModule extends BaseContainer {
	
	@Nullable
	private final IItemHandlerComponent itemHandler;
	
	public ContainerChestModule(IModule module, InventoryPlayer inventory) {
		super(module.getComponent(IGuiProvider.class), inventory);
		IItemHandlerComponent itemHandler = module.getComponent(IItemHandlerComponent.class);
		if (itemHandler == null) {
			throw new IllegalStateException();
		}
		this.itemHandler = itemHandler;
		addSlots(inventory);
	}
	
	@Override
	protected void addSlots(InventoryPlayer inventory) {
		if (itemHandler == null) {
			return;
		}
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotModule(itemHandler, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		for (int i1 = 0; i1 < 3; i1++) {
			for (int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 85 + i1 * 18));
			}
		}
		for (int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 143));
		}
	}
}
