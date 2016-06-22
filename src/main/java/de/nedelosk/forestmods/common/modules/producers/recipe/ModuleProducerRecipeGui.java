package de.nedelosk.forestmods.common.modules.producers.recipe;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;

@SideOnly(Side.CLIENT)
@Optional.Interface(modid = "NotEnoughItems", iface = "codechicken.nei.recipe.GuiCraftingRecipe")
public class ModuleProducerRecipeGui<M extends IModuleProducerRecipe, S extends IModuleSaver, T extends IModuleProducerRecipeType>
extends ProducerGuiDefault<M, S, T> {

	public ModuleProducerRecipeGui(ModuleUID UID) {
		super(UID);
	}

	@Override
	public void handleMouseClicked(IModularState tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S, T> stack) {
		if (widget instanceof WidgetProgressBar) {
			if (Loader.isModLoaded("NotEnoughItems")) {
				openNEI(stack);
			}
		}
	}

	@Optional.Method(modid = "NotEnoughItems")
	protected void openNEI(ModuleStack<M, S, T> stack) {
		GuiCraftingRecipe.openRecipeGui("ModularMachines" + stack.getModuleContainer().getRecipeCategory(stack));
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S, T> stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for(Widget widget : widgets) {
			if (widget instanceof WidgetProgressBar) {
				ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularHelper.getEngine(modular).getItemStack();
				if (engine != null) {
					int burnTime = engine.getSaver().getWorkTime(engine);
					int burnTimeTotal = engine.getSaver().getWorkTimeTotal(engine);
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}
}
