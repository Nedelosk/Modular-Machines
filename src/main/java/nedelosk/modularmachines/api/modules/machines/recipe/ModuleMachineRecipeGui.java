package nedelosk.modularmachines.api.modules.machines.recipe;

import java.util.List;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import nedelosk.modularmachines.api.modules.engine.IModuleEngineSaver;
import nedelosk.modularmachines.api.modules.gui.ModuleGuiDefault;
import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
@Optional.Interface(modid = "NotEnoughItems", iface = "codechicken.nei.recipe.GuiCraftingRecipe")
public class ModuleMachineRecipeGui<M extends IModuleMachineRecipe, S extends IModuleMachineSaver> extends ModuleGuiDefault<M, S> {

	public ModuleMachineRecipeGui(String UID) {
		super(UID);
	}

	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack) {
		if (widget instanceof WidgetProgressBar) {
			if (Loader.isModLoaded("NotEnoughItems")) {
				openNEI(stack);
			}
		}
	}

	@Optional.Method(modid = "NotEnoughItems")
	protected void openNEI(ModuleStack<M, S> stack) {
		GuiCraftingRecipe.openRecipeGui("ModularMachines" + stack.getModule().getRecipeName(stack));
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack) {
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetProgressBar) {
				ModuleStack<IModuleEngine, IModuleEngineSaver> engine = ModularUtils.getEngine(modular).getStack();
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
