package modularmachines.common.modules.logic;

import java.util.List;

import javax.annotation.Nullable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSelectModulePage;

public class ModuleGuiLogic implements IModuleGuiLogic {
	private final IModuleLogic logic;
	@Nullable
	private Module currentModule;
	@Nullable
	private ModulePage currentPage;
	
	public ModuleGuiLogic(IModuleLogic logic) {
		this.logic = logic;
		List<Module> modules = ModuleHelper.getPageModules(logic);
		if(!modules.isEmpty()){
			setCurrentPage(modules.get(0).getPage(0), false);
		}
	}
    
    public boolean canOpenGui(){
    	return currentModule == null || currentPage == null;
    }
    
    @Override
	public void setCurrentPage(@Nullable ModulePage page, boolean sendToServer){
    	if(page != null){
    		this.currentModule = page.getParent();
    		this.currentPage = page;
    		if(sendToServer){
    			PacketHandler.sendToServer(new PacketSelectModulePage(logic, page.getIndex()));
    		}
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
