package de.nedelosk.forestmods.common.producers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.WidgetProgressBar;
import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.producers.recipes.RecipeAlloySmelter;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGuiBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.MachineRenderer;
import de.nedelosk.forestmods.common.producers.handlers.ProducerPage;

public class ProducerAlloySmelter extends ProducerAdvanced {

	public ProducerAlloySmelter() {
	}

	@Override
	public String getRecipeCategory() {
		return "AlloySmleter";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return new MachineRenderer(moduleStack);
	}

	@Override
	public RecipeItem[] getInputs() {
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

	public static class AlloySmelterPage extends ProducerPage {

		public AlloySmelterPage(int pageID, IModular modular, ModuleStack moduleStack) {
			super(pageID, modular, moduleStack);
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
			invBuilder.initSlot(0, true);
			invBuilder.initSlot(1, true);
			invBuilder.initSlot(2, false);
			invBuilder.initSlot(3, false);
		}

		@Override
		public void createSlots(IContainerBase container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(modular.getTile(), 0, 36, 35, moduleStack));
			modularSlots.add(new SlotModule(modular.getTile(), 1, 54, 35, moduleStack));
			modularSlots.add(new SlotModule(modular.getTile(), 2, 116, 35, moduleStack));
			modularSlots.add(new SlotModule(modular.getTile(), 3, 134, 35, moduleStack));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void createGui(IModuleGuiBuilder guiBuilder) {
			IModuleEngine engine = ModularUtils.getEngine(modular).getModule();
			int burnTime = 0;
			int burnTimeTotal = 0;
			if (engine != null) {
				burnTime = engine.getBurnTime();
				burnTimeTotal = engine.getBurnTimeTotal();
			}
			guiBuilder.addWidget(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
		}
	}

	@Override
	public int getSpeed() {
		return 0;
	}
}
