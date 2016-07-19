package de.nedelosk.modularmachines.common.modules.storaged.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleSawMill extends ModuleMachine implements IModuleColored{

	public ModuleSawMill(int complexity, int speed, EnumModuleSize size) {
		super("sawmill", complexity, speed, size);
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "SawMill";
	}

	@Override
	public int getColor() {
		return 0xA65005;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new SawMillPage("Basic", state));
		return pages;
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}

	public static class SawMillPage extends ModulePage<IModuleMachine> {

		public SawMillPage(String pageID, IModuleState<IModuleMachine> moduleState) {
			super(pageID, "sawmill", moduleState);
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
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 36, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
		}
	}

	/*@SideOnly(Side.CLIENT)
	public static class SawMillNEIPage extends NEIPage {

		public SawMillNEIPage(IModuleJEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotJEI> modularSlots) {
			modularSlots.add(new SlotJEI(56, 24, true));
			modularSlots.add(new SlotJEI(116, 24, false));
			modularSlots.add(new SlotJEI(134, 24, false));
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}*/
}
