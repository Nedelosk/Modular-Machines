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
import de.nedelosk.modularmachines.common.modules.ModuleMachineHeat;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleAlloySmelter extends ModuleMachineHeat implements IModuleColored {

	public ModuleAlloySmelter(int complexity, int speed, EnumModuleSize size) {
		super("alloysmelter", complexity,speed, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "AlloySmelter";
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getContentHandler(IModuleInventory.class).getInputItems();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getModelHandler(IModuleState state) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/alloysmelter/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + (getWorkTime(state) > 0 ? "_on" : "_off")));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = new ResourceLocation("modularmachines:module/alloysmelter/" + container.getMaterial().getName() + "_" + size.getName() + "_off");
		locations[1] = new ResourceLocation("modularmachines:module/alloysmelter/" + container.getMaterial().getName() + "_" + size.getName() + "_on");
		return new ModelHandlerInit(locations);
	}

	@Override
	protected int getConsumeHeat(IModuleState state) {
		return 15;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new AlloySmelterPage("Basic", state));
		return pages;
	}

	public static class AlloySmelterPage extends ModulePage<IModuleMachine> {

		public AlloySmelterPage(String pageID, IModuleState<IModuleMachine> module) {
			super(pageID, "alloysmelter", module);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 36, 35, new ItemFilterMachine());
			invBuilder.addInventorySlot(true, 54, 35, new ItemFilterMachine());
			invBuilder.addInventorySlot(false, 116, 35, new OutputAllFilter());
			invBuilder.addInventorySlot(false, 134, 35, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0));
			modularSlots.add(new SlotModule(state, 1));
			modularSlots.add(new SlotModule(state, 2));
			modularSlots.add(new SlotModule(state, 3));
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
