package modularmachines.api.modules.logic;

import javax.annotation.Nullable;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModuleComponent;

public interface IModuleGuiLogic {
	
	IModuleContainer getProvider();
	
	void setCurrentPage(@Nullable ModuleComponent page, boolean sendToServer);
	
	@Nullable
	Module getCurrentModule();
	
	@Nullable
	ModuleComponent getCurrentComponent();
	
}
