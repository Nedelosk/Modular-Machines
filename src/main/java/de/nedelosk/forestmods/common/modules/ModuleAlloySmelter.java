package de.nedelosk.forestmods.common.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.modules.integration.IModuleNEI;
import de.nedelosk.forestmods.api.modules.integration.INEIPage;
import de.nedelosk.forestmods.api.modules.integration.SlotNEI;
import de.nedelosk.forestmods.api.modules.recipes.RecipeAlloySmelter;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.client.render.modules.MachineRenderer;
import de.nedelosk.forestmods.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.common.modules.handlers.NEIPage;
import de.nedelosk.forestmods.common.modules.handlers.OutputAllFilter;

public class ModuleAlloySmelter extends ModuleAdvanced {

	public ModuleAlloySmelter(String name) {
		super(name);
	}

	@Override
	public String getRecipeCategory() {
		return "AlloySmelter";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return new MachineRenderer(moduleStack);
	}

	@Override
	public void addRequiredModules(List<Class<? extends IModule>> requiredModules) {
		requiredModules.add(IModuleBattery.class);
		requiredModules.add(IModuleEngine.class);
	}

	@Override
	public List<Class<? extends IModule>> getRequiredModules() {
		List<Class<? extends IModule>> requiredModules = new ArrayList();
		requiredModules.add(IModuleBattery.class);
		requiredModules.add(IModuleEngine.class);
		requiredModules.add(IModuleCasing.class);
		return requiredModules;
	}

	@Override
	public List<Class<? extends IModule>> getAllowedModules() {
		List<Class<? extends IModule>> requiredModules = new ArrayList();
		requiredModules.add(IModuleBattery.class);
		requiredModules.add(IModuleCasing.class);
		requiredModules.add(IModuleEngine.class);
		return requiredModules;
	}

	@Override
	public RecipeItem[] getInputs() {
		if (inventory == null) {
			return null;
		}
		return inventory.getInputItems();
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return RecipeAlloySmelter.class;
	}

	@Override
	protected IModulePage[] createPages() {
		IModulePage[] pages = new IModulePage[] { new AlloySmelterPage(0, modular, moduleStack) };
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public INEIPage createNEIPage(ModuleStack<IModuleNEI> stack) {
		return new AlloySmelterNEIPage(stack);
	}

	public static class AlloySmelterPage extends ModulePage {

		public AlloySmelterPage(int pageID, IModular modular, ModuleStack moduleStack) {
			super(pageID, modular, moduleStack);
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
			invBuilder.setInventoryName("module.inventory.alloysmleter.name");
			invBuilder.initSlot(0, true, new ItemFilterMachine());
			invBuilder.initSlot(1, true, new ItemFilterMachine());
			invBuilder.initSlot(2, false, new OutputAllFilter());
			invBuilder.initSlot(3, false, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(moduleStack, 0, 36, 35));
			modularSlots.add(new SlotModule(moduleStack, 1, 54, 35));
			modularSlots.add(new SlotModule(moduleStack, 2, 116, 35));
			modularSlots.add(new SlotModule(moduleStack, 3, 134, 35));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			IModuleEngine engine = ModularUtils.getEngine(modular).getModule();
			int burnTime = 0;
			int burnTimeTotal = 0;
			if (engine != null) {
				burnTime = engine.getBurnTime();
				burnTimeTotal = engine.getBurnTimeTotal();
			}
			widgets.add(new WidgetProgressBar(82, 35, burnTime, burnTimeTotal));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class AlloySmelterNEIPage extends NEIPage {

		public AlloySmelterNEIPage(ModuleStack module) {
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
			widgets.add(new WidgetProgressBar(82, 24, 0, 0));
		}
	}

	@Override
	public int getSpeed() {
		return 150;
	}
}
