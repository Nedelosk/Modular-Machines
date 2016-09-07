package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleMachine;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FurnacePage extends MainPage<IModuleMachine> {
	public FurnacePage(IModuleState<IModuleMachine> module) {
		super("furnace", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 55, 35, new ItemFilterMachine());
		invBuilder.addInventorySlot(false, 116, 35, new OutputAllFilter());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		add(new WidgetProgressBar(82, 35, moduleState.getModule().getWorkTime(moduleState), moduleState.getModule().getWorkTimeTotal(moduleState)));
	}
}
