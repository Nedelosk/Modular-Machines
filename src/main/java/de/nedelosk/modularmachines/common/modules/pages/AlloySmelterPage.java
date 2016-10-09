package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.handlers.filters.FilterMachine;
import de.nedelosk.modularmachines.api.modules.handlers.filters.OutputFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleMachine;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlloySmelterPage extends MainPage<IModuleMachine> {

	public AlloySmelterPage(IModuleState<IModuleMachine> module) {
		super("alloysmelter", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 36, 35, FilterMachine.INSTANCE);
		invBuilder.addInventorySlot(true, 54, 35, FilterMachine.INSTANCE);
		invBuilder.addInventorySlot(false, 116, 35, OutputFilter.INSTANCE);
		invBuilder.addInventorySlot(false, 134, 35, OutputFilter.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetProgressBar(82, 35, moduleState));
	}
}
