package de.nedelosk.modularmachines.common.modules.pages;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBattery;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetEnergyField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BatteryPage extends MainPage<IModuleBattery> {

	public BatteryPage(IModuleState<IModuleBattery> state) {
		super("battery", state);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
		super.updateGui();
		for(Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
			if (widget instanceof WidgetEnergyField) {
				((WidgetEnergyField) widget).setProvider(moduleState.getContentHandler(IEnergyBuffer.class));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		gui.getWidgetManager().add(new WidgetEnergyField(55, 15, moduleState.getContentHandler(IEnergyBuffer.class)));
	}
}
