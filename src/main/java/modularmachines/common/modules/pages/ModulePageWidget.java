package modularmachines.common.modules.pages;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.client.gui.GuiModuleLogic;
import modularmachines.client.gui.WidgetManager;
import modularmachines.client.gui.widgets.Widget;

public class ModulePageWidget<P extends Module> extends ModulePage<P> {

	public ModulePageWidget(P parent) {
		super(parent);
	}
	
	public void addWidget(Widget widget){
		if(gui instanceof GuiModuleLogic){
			WidgetManager widgetManager = ((GuiModuleLogic) gui).getWidgetManager();
			widgetManager.add(widget);
		}
	}

}
