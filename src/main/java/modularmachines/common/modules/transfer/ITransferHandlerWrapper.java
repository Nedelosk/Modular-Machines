package modularmachines.common.modules.transfer;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.logic.IModuleLogic;

public interface ITransferHandlerWrapper<H> {

	ModuleTransfer<H> getTransferModule();
	
	String getTabTooltip();
	
	ItemStack getTabItem();
	
	void init(IModuleLogic logic);
	
	boolean isValid();
}
