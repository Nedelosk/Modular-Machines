package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.integration.IModuleNEI;
import de.nedelosk.modularmachines.api.modules.integration.INEIPage;
import de.nedelosk.modularmachines.api.modules.integration.SlotNEI;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.render.modules.MachineRenderer;
import de.nedelosk.modularmachines.common.modules.ModuleToolEngine;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.NEIPage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleSawMill extends ModuleToolEngine implements IModuleColored{

	public ModuleSawMill(int speed, int engines) {
		super(speed, engines);
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(ItemStack.class)).getInputItems();
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
	public IModulePage[] createPages(IModuleState state) {
		return new IModulePage[]{new SawMillPage(0, state)};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public INEIPage createNEIPage(IModuleNEI module) {
		return new SawMillNEIPage(module);
	}

	public static class SawMillPage extends ModulePage<IModuleTool> {

		public SawMillPage(int pageID, IModuleState<IModuleTool> moduleState) {
			super(pageID, moduleState);
		}
		
		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.setInventoryName("module.inventory.sawmill.name");
			invBuilder.addInventorySlot(true, new ItemFilterMachine());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0, 56, 35));
			modularSlots.add(new SlotModule(state, 1, 116, 35));
			modularSlots.add(new SlotModule(state, 2, 134, 35));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 36, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new MachineRenderer(state.getModuleState().getContainer());
	}

	@SideOnly(Side.CLIENT)
	public static class SawMillNEIPage extends NEIPage {

		public SawMillNEIPage(IModuleNEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotNEI> modularSlots) {
			modularSlots.add(new SlotNEI(56, 24, true));
			modularSlots.add(new SlotNEI(116, 24, false));
			modularSlots.add(new SlotNEI(134, 24, false));
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}
}
