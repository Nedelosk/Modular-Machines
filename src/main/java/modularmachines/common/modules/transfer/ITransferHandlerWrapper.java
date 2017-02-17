package modularmachines.common.modules.transfer;

import modularmachines.api.modules.logic.IModuleLogic;
import net.minecraft.item.ItemStack;

public interface ITransferHandlerWrapper<H> {

	ModuleTransfer<H> getTransferModule();
	
	String getTabTooltip();
	
	ItemStack getTabItem();
	
	void init(IModuleLogic logic);
	
	boolean isValid();
}
