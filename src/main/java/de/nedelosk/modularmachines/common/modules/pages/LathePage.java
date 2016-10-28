package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.handlers.filters.FilterMachine;
import de.nedelosk.modularmachines.api.modules.handlers.filters.OutputFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleModeMachine;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetMode;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
