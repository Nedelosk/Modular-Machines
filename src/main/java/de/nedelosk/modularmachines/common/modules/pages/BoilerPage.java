package de.nedelosk.modularmachines.common.modules.pages;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleTool;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BoilerPage extends ModulePage<IModuleTool> {

	public BoilerPage(String pageID, String title, IModuleState<IModuleTool> moduleState) {
		super(pageID, title, moduleState);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, new ItemFluidFilter(true));
		invBuilder.addInventorySlot(false, 15, 48, new OutputAllFilter());

		invBuilder.addInventorySlot(true, 147, 28, new ItemFluidFilter(false));
		invBuilder.addInventorySlot(false, 147, 48, new OutputAllFilter());
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 55, 15, new FluidFilter(FluidRegistry.WATER));
		tankBuilder.addFluidTank(16000, false, 105, 15, new OutputAllFilter());
	}

	@Override
	public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
		modularSlots.add(new SlotModule(state, 0).setBackgroundTexture("liquid"));
		modularSlots.add(new SlotModule(state, 1).setBackgroundTexture("container"));

		modularSlots.add(new SlotModule(state, 2).setBackgroundTexture("container"));
		modularSlots.add(new SlotModule(state, 3).setBackgroundTexture("liquid"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(0)));
		add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(1)));
	}
}
