package de.nedelosk.modularmachines.common.modules.engine;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilterSteam;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fluids.FluidStack;

public class ModuleEngineSteam extends ModuleEngine {

	public ModuleEngineSteam(int complexity, int burnTimeModifier, int materialPerTick) {
		super("engine.steam", complexity, burnTimeModifier, materialPerTick);
	}

	@Override
	public boolean removeMaterial(IModuleState state, IModuleState<IModuleMachine> machineState) {
		IModuleTank tank = (IModuleTank) state.getContentHandler(IModuleTank.class);
		if(tank == null){
			return false;
		}
		if (tank.drain(new FluidStack(FluidManager.Steam, materialPerTick), false).amount >= materialPerTick) {
			return tank.drain(new FluidStack(FluidManager.Steam, materialPerTick), true).amount >= materialPerTick;
		} else {
			return false;
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new EnginePage("Basic", state));
		return pages;
	}

	public class EnginePage extends ModulePage<IModuleEngine>{

		public EnginePage(String pageID, IModuleState<IModuleEngine> module) {
			super(pageID, "engine.steam", module);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 15, 28, new ItemFluidFilter(true));
			invBuilder.addInventorySlot(false, 15, 48, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0).setBackgroundTexture("liquid"));
			modularSlots.add(new SlotModule(state, 1).setBackgroundTexture("container"));
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			tankBuilder.addFluidTank(16000, true, 80, 18, new FluidFilterSteam());
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(0)));
		}

	}
}