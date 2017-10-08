package modularmachines.common.modules.logic;

import javax.annotation.Nullable;
import java.util.List;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSelectModulePage;

public class ModuleGuiLogic implements IModuleGuiLogic {
	private final IModuleLogic logic;
	@Nullable
	private Module currentModule;
	@Nullable
	private ModuleComponent currentComponent;
	
	public ModuleGuiLogic(IModuleLogic logic) {
		this.logic = logic;
		List<Module> modules = ModuleHelper.getModulesWithComponents(logic);
		if(!modules.isEmpty()){
			setCurrentPage(modules.get(0).getComponent(0), false);
		}
	}
	
	public ModuleGuiLogic(IModuleLogic logic, int moduleIndex, int pageIndex) {
		this.logic = logic;
		List<Module> modules = ModuleHelper.getModulesWithComponents(logic);
		if(!modules.isEmpty()){
			if(moduleIndex < 0){
				moduleIndex = modules.get(0).getIndex();
			}
			if(pageIndex < 0){
				pageIndex = 0;
			}
			setCurrentPage(logic.getModule(moduleIndex).getComponent(pageIndex), false);
		}
	}
    
    public boolean canOpenGui(){
    	return currentModule == null || currentComponent == null;
    }
    
    @Override
	public void setCurrentPage(@Nullable ModuleComponent page, boolean sendToServer){
    	if(page != null){
    		this.currentModule = page.getParent();
    		this.currentComponent = page;
    		if(sendToServer){
    			PacketHandler.sendToServer(new PacketSelectModulePage(logic, page));
    		}
    	}else{
    		this.currentModule = null;
    		this.currentComponent = null;
    	}
    }
    
    @Override
	public Module getCurrentModule() {
		return currentModule;
	}
    
    @Override
	public ModuleComponent getCurrentComponent() {
		return currentComponent;
	}
    
	@Override
	public IModuleLogic getLogic() {
		return logic;
	}
	
}
