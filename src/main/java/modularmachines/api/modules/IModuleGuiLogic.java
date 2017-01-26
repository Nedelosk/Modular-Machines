package modularmachines.api.modules;

import javax.annotation.Nullable;

import modularmachines.api.modules.pages.ModulePage;

public interface IModuleGuiLogic {

    void setCurrentPage(@Nullable ModulePage page, boolean sendToServer);
    
    @Nullable
    Module getCurrentModule();
    
    @Nullable
    ModulePage getCurrentPage();
    
    @Nullable
    IModuleLogic getLogic();
	
}
