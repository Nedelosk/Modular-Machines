package modularmachines.api.modules.container;

import javax.annotation.Nullable;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.pages.PageComponent;

public interface IModuleGuiLogic {
	
	IModuleContainer getProvider();
	
	void setCurrentPage(@Nullable PageComponent page, boolean sendToServer);
	
	@Nullable
	IModule getCurrentModule();
	
	@Nullable
	PageComponent getCurrentComponent();
	
}
