package modularmachines.common.modules.pages;

import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.gui.Widget;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.energy.IModuleBattery;
import modularmachines.client.gui.widgets.WidgetEnergyField;

public class BatteryPage extends MainPage<IModuleBattery> {

	public BatteryPage(IModuleState<IModuleBattery> state) {
		super("battery", state);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
		super.updateGui();
		for (Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
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
