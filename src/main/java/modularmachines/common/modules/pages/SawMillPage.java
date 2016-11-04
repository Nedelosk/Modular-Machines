package modularmachines.common.modules.pages;

import modularmachines.api.modules.handlers.filters.FilterMachine;
import modularmachines.api.modules.handlers.filters.OutputFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleMachine;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SawMillPage extends MainPage<IModuleMachine> {

	public SawMillPage(IModuleState<IModuleMachine> moduleState) {
		super("sawmill", moduleState);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 56, 35, FilterMachine.INSTANCE);
		invBuilder.addInventorySlot(false, 116, 35, OutputFilter.INSTANCE);
		invBuilder.addInventorySlot(false, 134, 35, OutputFilter.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetProgressBar(82, 36, moduleState));
	}
}
