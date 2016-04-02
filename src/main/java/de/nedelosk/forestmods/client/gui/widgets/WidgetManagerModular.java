package de.nedelosk.forestmods.client.gui.widgets;

import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.gui.WidgetManager;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.client.gui.GuiModularMachines;

public class WidgetManagerModular extends WidgetManager<GuiModularMachines> {

	private final IModulePage page;

	public WidgetManagerModular(GuiModularMachines gui, IModulePage page) {
		super(gui);
		this.page = page;
	}

	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget widget = getAtPosition(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (widget != null) {
			widget.handleMouseClick(mouseX, mouseY, mouseButton, gui);
			page.handleMouseClicked(widget, mouseX, mouseY, mouseButton);
		}
	}
}
