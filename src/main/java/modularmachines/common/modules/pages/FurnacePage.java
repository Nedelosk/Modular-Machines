package modularmachines.common.modules.pages;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.handlers.filters.FilterMachine;
import modularmachines.api.modules.handlers.filters.OutputFilter;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleMachine;
import modularmachines.client.gui.widgets.WidgetProgressBar;

public class FurnacePage extends MainPage<IModuleMachine> {

	public FurnacePage(IModuleState<IModuleMachine> module) {
		super("furnace", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 55, 35, FilterMachine.INSTANCE);
		invBuilder.addInventorySlot(false, 116, 35, OutputFilter.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetProgressBar(82, 35, moduleState));
	}
}
