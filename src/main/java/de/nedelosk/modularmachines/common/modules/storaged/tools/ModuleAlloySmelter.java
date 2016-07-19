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

public class ModuleAlloySmelter extends ModuleMachine implements IModuleColored, IModuleJEI{

	public ModuleAlloySmelter(int complexity, int speed, EnumModuleSize size) {
		super("alloysmelter", complexity,speed, size);
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "AlloySmelter";
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{ModuleCategoryUIDs.ALLOYSMELTER};
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.HEAT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerStatus(new ResourceLocation[]{
				new ResourceLocation("modularmachines:module/alloysmelters/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + "_on"),
				new ResourceLocation("modularmachines:module/alloysmelters/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + "_off")
		});
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List handlers = new ArrayList<>();
		handlers.add(new ModelHandlerStatus(new ResourceLocation[]{
				new ResourceLocation("modularmachines:module/alloysmelters/" + container.getMaterial().getName() + "_" + size.getName() + "_on"),
				new ResourceLocation("modularmachines:module/alloysmelters/" + container.getMaterial().getName() + "_" + size.getName() + "_off")
		}));
		return handlers;
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
