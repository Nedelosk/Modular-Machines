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

public class ModuleAlloySmelter extends ModuleToolEngine implements IModuleColored {

	public ModuleAlloySmelter(int speed, int engines) {
		super(speed, engines);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "AlloySmelter";
	}
	
	@Override
	public byte getSize() {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new MachineRenderer(state.getModuleState().getContainer());
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(ItemStack.class)).getInputItems();
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		IModulePage[] pages = new IModulePage[] { new AlloySmelterPage(0, state) };
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public INEIPage createNEIPage(IModuleNEI module) {
		return new AlloySmelterNEIPage(module);
	}

	public static class AlloySmelterPage extends ModulePage<IModuleTool> {

		public AlloySmelterPage(int pageID, IModuleState<IModuleTool> module) {
			super(pageID, module);
		}
		
		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.setInventoryName("module.inventory.alloysmleter.name");
			invBuilder.addInventorySlot(true, new ItemFilterMachine());
			invBuilder.addInventorySlot(true, new ItemFilterMachine());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0, 36, 35));
			modularSlots.add(new SlotModule(state, 1, 54, 35));
			modularSlots.add(new SlotModule(state, 2, 116, 35));
			modularSlots.add(new SlotModule(state, 3, 134, 35));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class AlloySmelterNEIPage extends NEIPage {

		public AlloySmelterNEIPage(IModuleNEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotNEI> modularSlots) {
			modularSlots.add(new SlotNEI(36, 24, true));
			modularSlots.add(new SlotNEI(54, 24, true));
			modularSlots.add(new SlotNEI(116, 24, false));
			modularSlots.add(new SlotNEI(134, 24, false));
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}

	@Override
	public int getColor() {
		return 0xB22222;
	}
}
