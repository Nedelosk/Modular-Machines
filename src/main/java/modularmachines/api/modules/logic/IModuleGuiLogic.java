package modularmachines.api.modules.logic;

import javax.annotation.Nullable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModuleComponent;

public interface IModuleGuiLogic {

    void setCurrentPage(@Nullable ModuleComponent page, boolean sendToServer);
    
    @Nullable
    Module getCurrentModule();
    
    @Nullable
    ModuleComponent getCurrentComponent();
    
    @Nullable
    IModuleLogic getLogic();
	
}
