package modularmachines.common.modules.pages;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.handlers.filters.FilterMachine;
import modularmachines.api.modules.handlers.filters.OutputFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleModeMachine;
import modularmachines.client.gui.widgets.WidgetMode;
import modularmachines.client.gui.widgets.WidgetProgressBar;

public class LathePage extends MainPage<IModuleModeMachine> {

	public LathePage(IModuleState<IModuleModeMachine> state) {
		super("lathe", state);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetProgressBar(82, 36, moduleState));
		add(new WidgetMode(86, 16, moduleState));
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 54, 35, FilterMachine.INSTANCE);
		invBuilder.addInventorySlot(false, 116, 35, OutputFilter.INSTANCE);
		invBuilder.addInventorySlot(false, 134, 35, OutputFilter.INSTANCE);
	}
}
