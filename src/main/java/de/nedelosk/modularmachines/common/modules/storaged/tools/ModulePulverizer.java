package de.nedelosk.modularmachines.common.modules.storaged.tools;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.modules.ModelHandlerStatus;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.ModuleCategoryUIDs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePulverizer extends ModuleMachine implements IModuleColored, IModuleJEI{

	public ModulePulverizer(int complexity, int speed, EnumModuleSize size) {
		super("pulverizer", complexity, speed, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Pulverizer";
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{ModuleCategoryUIDs.PULVERIZER};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerStatus(new ResourceLocation[]{
				new ResourceLocation("modularmachines:module/pulverizers/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + "_on"),
				new ResourceLocation("modularmachines:module/pulverizers/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + "_off")
		});
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List handlers = new ArrayList<>();
		handlers.add(new ModelHandlerStatus(new ResourceLocation[]{
				new ResourceLocation("modularmachines:module/pulverizers/" + container.getMaterial().getName() + "_" + size.getName() + "_on"),
				new ResourceLocation("modularmachines:module/pulverizers/" + container.getMaterial().getName() + "_" + size.getName() + "_off")
		}));
		return handlers;
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if(handler instanceof ModelHandlerStatus){
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if(getWorkTime(state) > 0){
				if(!status.status){
					status.status = true;
					return true;
				}
			}else{
				if(status.status){
					status.status = false;
					return true;
				}
			}
		}
		return false;
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
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
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
