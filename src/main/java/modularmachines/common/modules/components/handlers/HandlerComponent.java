package modularmachines.common.modules.components.handlers;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.components.IItemCreationListener;
import modularmachines.api.modules.components.handlers.IHandlerComponent;
import modularmachines.api.modules.components.handlers.ISaveHandler;
import modularmachines.common.modules.components.ModuleComponent;

public abstract class HandlerComponent extends ModuleComponent implements IHandlerComponent, IItemCreationListener {
	@Nullable
	private ISaveHandler saveHandler = null;
	
	@Override
	public ISaveHandler getSaveHandler() {
		return saveHandler;
	}
	
	@Override
	public void setSaveHandler(ISaveHandler saveHandler) {
		this.saveHandler = saveHandler;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ItemStack createItem(ItemStack itemStack) {
		if (saveHandler != null) {
			saveHandler.writeToItem(this, itemStack);
		}
		return itemStack;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onModuleAdded() {
		if (saveHandler != null) {
			saveHandler.readFromItem(this, provider.getItemStack());
		}
	}
}
