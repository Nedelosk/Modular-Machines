package modularmachines.api.modules;

import javax.annotation.Nullable;

import modularmachines.api.modules.pages.ModulePage;

public class ModuleGuiLogic implements IModuleGuiLogic {
	private final IModuleLogic logic;
	@Nullable
	private Module currentModule;
	@Nullable
	private ModulePage currentPage;
	
	public ModuleGuiLogic(IModuleLogic logic) {
		this.logic = logic;
	}
    
    public boolean canOpenGui(){
    	return currentModule == null || currentPage == null;
    }
    
    @Override
	public void setCurrentPage(@Nullable ModulePage page){
    	if(page != null){
    		this.currentModule = page.getParent();
    		this.currentPage = page;
    	}else{
    		this.currentModule = null;
    		this.currentPage = null;
    	}
    }
    
    @Override
	public Module getCurrentModule() {
		return currentModule;
	}
    
    @Override
	public ModulePage getCurrentPage() {
		return currentPage;
	}
    
	@Override
	public IModuleLogic getLogic() {
		return logic;
	}
	
}
