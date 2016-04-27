package de.nedelosk.forestmods.common.modules.producers.recipe;

import java.util.List;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;

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
					int burnTime = engine.getSaver().getBurnTime(engine);
					int burnTimeTotal = engine.getSaver().getBurnTimeTotal(engine);
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}
}
