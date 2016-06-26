package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.render.modules.MachineRenderer;
import de.nedelosk.modularmachines.common.modules.ModuleToolEngine;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleAlloySmelter extends ModuleToolEngine implements IModuleColored {

	public ModuleAlloySmelter(int speed, int size) {
		super(speed, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "AlloySmelter";
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

	public static class AlloySmelterPage extends ModulePage<IModuleTool> {

		public AlloySmelterPage(int pageID, IModuleState<IModuleTool> module) {
			super(pageID, module);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.setInventoryName("module.inventory.alloysmelter.name");
			invBuilder.addInventorySlot(true, new ItemFilterMachine());
			invBuilder.addInventorySlot(true, new ItemFilterMachine());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
			invBuilder.addInventorySlot(false, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
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

	@Override
	public int getColor() {
		return 0xB22222;
	}
}
