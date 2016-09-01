package de.nedelosk.modularmachines.common.modules.pages;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleMachine;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PulverizerPage extends ModulePage<IModuleMachine> {

	public PulverizerPage(String pageID, IModuleState<IModuleMachine> moduleState) {
		super(pageID, "pulverizer", moduleState);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 56, 35, new ItemFilterMachine());
		invBuilder.addInventorySlot(false, 116, 35, new OutputAllFilter());
		invBuilder.addInventorySlot(false, 134, 35, new OutputAllFilter());
	}

	@Override
	public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
		modularSlots.add(new SlotModule(state, 0));
		modularSlots.add(new SlotModule(state, 1));
		modularSlots.add(new SlotModule(state, 2));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		add(new WidgetProgressBar(82, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
	}
}
