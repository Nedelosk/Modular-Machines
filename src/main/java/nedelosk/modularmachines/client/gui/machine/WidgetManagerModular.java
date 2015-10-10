package nedelosk.modularmachines.client.gui.machine;

import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;
import nedelosk.nedeloskcore.api.machines.Widget;
import nedelosk.nedeloskcore.client.gui.WidgetManager;

public class WidgetManagerModular extends WidgetManager<GuiModularMachine> {

	public WidgetManagerModular(GuiModularMachine gui) {
		super(gui);
	}
	
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget widget = getAtPosition(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (widget != null) {
			widget.handleMouseClick(mouseX, mouseY, mouseButton, gui);
			((IModuleGui)getGui().getTile().getModular().getGuiManager().getModuleWithGui().getModule()).handleMouseClicked(getGui().getTile(), widget, mouseX, mouseY, mouseButton);
		}
	}

}
