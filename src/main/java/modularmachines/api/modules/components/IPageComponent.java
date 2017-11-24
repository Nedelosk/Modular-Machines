package modularmachines.api.modules.components;

import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.pages.IPage;

public interface IPageComponent extends IModuleProvider {
	
	IPage createPage();
}
