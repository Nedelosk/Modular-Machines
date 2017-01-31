package modularmachines.common.modules.pages;

import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.widgets.WidgetEnergyField;

public class BatteryPage extends MainPage<IModuleBattery> {

	public BatteryPage(IModuleState<IModuleBattery> state) {
		super("battery", state);
	}

	@Override
	protected void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 26, 24, ItemFilterEnergy.INSTANCE);
		invBuilder.addInventorySlot(true, 26, 54, ItemFilterEnergy.INSTANCE);
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
