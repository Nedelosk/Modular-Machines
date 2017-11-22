package modularmachines.common.modules.container.components;

import javax.annotation.Nullable;
import java.util.List;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.container.IModuleGuiLogic;
import modularmachines.api.modules.pages.PageComponent;

public class ModuleGuiLogic implements IModuleGuiLogic {
	private final IModuleContainer provider;
	@Nullable
	private IModule currentModule;
	@Nullable
	private PageComponent currentComponent;
	
	public ModuleGuiLogic(IModuleContainer provider) {
		this.provider = provider;
		List<IModule> modules = ModuleManager.helper.getModulesWithComponents(provider);
		if (!modules.isEmpty()) {
			setCurrentPage(modules.get(0).getComponent(0), false);
		}
	}
	
	public ModuleGuiLogic(IModuleContainer provider, int moduleIndex, int pageIndex) {
		this.provider = provider;
		List<IModule> modules = ModuleManager.helper.getModulesWithComponents(provider);
		if (!modules.isEmpty()) {
			if (moduleIndex < 0) {
				moduleIndex = modules.get(0).getIndex();
			}
			if (pageIndex < 0) {
				pageIndex = 0;
			}
			setCurrentPage(provider.getModule(moduleIndex).getComponent(pageIndex), false);
		}
	}
	
	public boolean canOpenGui() {
		return currentModule == null || currentComponent == null;
	}
	
	@Override
	public void setCurrentPage(@Nullable PageComponent page, boolean sendToServer) {
		if (page != null) {
			this.currentModule = page.getParent();
			this.currentComponent = page;
			if (sendToServer) {
				//PacketHandler.sendToServer(new PacketSelectModulePage(provider, page));
			}
		} else {
			this.currentModule = null;
			this.currentComponent = null;
		}
	}
	
	@Override
	public IModule getCurrentModule() {
		return currentModule;
	}
	
	@Override
	public PageComponent getCurrentComponent() {
		return currentComponent;
	}
	
	@Override
	public IModuleContainer getProvider() {
		return provider;
	}
}
