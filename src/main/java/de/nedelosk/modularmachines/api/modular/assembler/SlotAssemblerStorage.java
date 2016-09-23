package de.nedelosk.modularmachines.api.modular.assembler;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAssemblerStorage extends SlotItemHandler {

	protected final IStoragePage page;
	protected final IStoragePosition position;
	protected final IAssemblerContainer container;

	public SlotAssemblerStorage(IItemHandler inventory, int index, int xPosition, int yPosition, IStoragePage page, IStoragePosition position, IAssemblerContainer container) {
		super(inventory, index, xPosition, yPosition);

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
		if(page != null){
			page.onSlotChanged(container);
		}
		if(!container.transferStack()){
			container.getHandler().getAssembler().onStorageChange();
		}else{
			container.setHasStorageChange(true);
		}
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer container = ModuleManager.getContainerFromItem(stack);
		if(container == null){
			return false;
		}
		IModule module = container.getModule();
		if(module instanceof IStorageModule){
			if(((IStorageModule)module).isValidForPosition(position, container)){
				return true;
			}
		}
		return false;
	}
}
