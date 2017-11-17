package modularmachines.common.modules.transfer;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleContainer;

public interface ITransferHandlerWrapper<H> {

	ModuleTransfer<H> getTransferModule();
	
	String getTabTooltip();
	
	ItemStack getTabItem();
	
	void init(IModuleContainer provider);
	
	boolean isValid();
}
