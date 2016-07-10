package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.client.modules.ModelHandlerInit;
import de.nedelosk.modularmachines.common.modules.ModuleMachineEngine;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePulverizer extends ModuleMachineEngine implements IModuleColored{

	public ModulePulverizer(int complexity, int speed, EnumModuleSize size) {
		super("pulverizer", complexity, speed, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Pulverizer";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getModelHandler(IModuleState state) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/pulverizer/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + (getWorkTime(state) > 0 ? "_on" : "_off")));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = new ResourceLocation("modularmachines:module/pulverizer/" + container.getMaterial().getName() + "_" + size.getName() + "_off");
		locations[1] = new ResourceLocation("modularmachines:module/pulverizer/" + container.getMaterial().getName() + "_" + size.getName() + "_on");
		return new ModelHandlerInit(locations);
	}

	@Override
	public int getColor() {
		return 0x88A7D1;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new PulverizerPage("Basic", state));
		return pages;
	}

	/*@Override
	public IJEIPage createNEIPage(IModuleJEI module) {
		return new PulverizerNEIPage(module);
	}*/

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getContentHandler(IModuleInventory.class).getInputItems();
	}

	public static class PulverizerPage extends ModulePage<IModuleMachine> {

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
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
		}
	}

	/*@SideOnly(Side.CLIENT)
	public static class PulverizerNEIPage extends NEIPage {

		public PulverizerNEIPage(IModuleJEI module) {
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
