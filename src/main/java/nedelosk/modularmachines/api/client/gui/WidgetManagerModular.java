package nedelosk.modularmachines.api.client.gui;

import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetManager;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.utils.ModularUtils;
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
			IModularInventory modular = ((IModularTileEntity<IModularInventory>) getGui().getTile()).getModular();
			IModuleGui gui = modular.getGuiManager().getCurrentGui();
			ModuleStack stack = ModularUtils.getModuleStackFromGui(modular, gui);
			gui.handleMouseClicked((IModularTileEntity) getGui().getTile(), widget, mouseX, mouseY, mouseButton, stack);
		}
	}
}
