package nedelosk.modularmachines.client.gui.machine;

import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.client.gui.WidgetManager;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
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
			ModuleStack<IModule, IProducerGui> gui = getGui().getTile().getModular().getGuiManager().getModuleWithGui(Minecraft.getMinecraft().thePlayer, this.gui.getTile());

			gui.getProducer().handleMouseClicked(getGui().getTile(), widget, mouseX, mouseY, mouseButton, gui);
		}
	}

}
