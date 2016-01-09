package nedelosk.modularmachines.api.client.gui;

import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.client.IProducerGui;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class WidgetManagerModular extends WidgetManager<GuiModular> {

	public WidgetManagerModular(GuiModular gui) {
		super(gui);
	}

	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget widget = getAtPosition(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (widget != null) {
			widget.handleMouseClick(mouseX, mouseY, mouseButton, gui);
			ModuleStack<IModule, IProducerGui> gui = ((IModularTileEntity) getGui().getTile()).getModular()
					.getGuiManager().getModuleWithGui();

			gui.getProducer().handleMouseClicked((IModularTileEntity) getGui().getTile(), widget, mouseX, mouseY,
					mouseButton, gui);
		}
	}

}
