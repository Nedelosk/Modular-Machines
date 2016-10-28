package de.nedelosk.modularmachines.api.modular.assembler;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected final IStoragePage page;
	protected final IStoragePosition position;
	protected final IAssemblerContainer container;

	public SlotAssemblerStorage(IModularAssembler assembler, int xPosition, int yPosition, IStoragePage page, IStoragePosition position, IAssemblerContainer container) {
		super(assembler.getItemHandler(), assembler.getIndex(position), xPosition, yPosition);
		this.container = container;
		this.position = position;
		this.page = page;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (page != null) {
			page.onSlotChanged(container);
		}
		container.getHandler().getAssembler().onStorageSlotChange();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
		if (itemContainer == null) {
			return false;
		}
		Boolean isValid = null;
		for(IModuleContainer container : itemContainer.getContainers()) {
			IModule module = container.getModule();
			if (module instanceof IStorageModule) {
				if (((IStorageModule) module).isValidForPosition(position, container)) {
					IModularAssembler assembler = this.container.getHandler().getAssembler();
					if (container.getModule().isValid(this.container.getHandler().getAssembler(), position, stack, null, this)) {
						IStoragePosition second = ((IStorageModule) module).getSecondPosition(container, position);
						if (second != null) {
							if (this.container.getHandler().getAssembler().getStoragePage(second) == null && second != position) {
								if (isValid == null) {
									if (itemContainer.needOnlyOnePosition(container)) {
										isValid = true;
										break;
									}
								}
							} else {
								isValid = false;
							}
						}
						if (isValid == null) {
							isValid = true;
						}
					} else {
						isValid = false;
					}
				}
			}
		}
		if (isValid == null) {
			isValid = false;
		}
		return isValid;
	}
}
