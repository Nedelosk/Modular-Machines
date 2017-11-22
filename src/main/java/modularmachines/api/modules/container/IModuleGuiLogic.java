package modularmachines.api.modules.container;

import javax.annotation.Nullable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.PageComponent;

public interface IModuleGuiLogic {
	
	IModuleContainer getProvider();
	
	void setCurrentPage(@Nullable PageComponent page, boolean sendToServer);
	
	@Nullable
	Module getCurrentModule();
	
	@Nullable
	PageComponent getCurrentComponent();
	
}
