package nedelosk.modularmachines.api.client.gui;

import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.guis.WidgetManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.gui.IProducerGui;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;

public class WidgetManagerModular extends WidgetManager<GuiModularMachine> {

	public WidgetManagerModular(GuiModularMachine gui) {
		super(gui);
	}

	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget widget = getAtPosition(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (widget != null) {
			widget.handleMouseClick(mouseX, mouseY, mouseButton, gui);
			ModuleStack<IModule, IProducerGui> gui = ((IModularTileEntity) getGui().getTile()).getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, this.gui.getTile());

			gui.getProducer().handleMouseClicked((IModularTileEntity) getGui().getTile(), widget, mouseX, mouseY, mouseButton, gui);
		}
	}

}
