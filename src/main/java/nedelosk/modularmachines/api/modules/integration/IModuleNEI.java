package nedelosk.modularmachines.api.modules.integration;

import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleNEI extends IModule {

	List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe);

	List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe);
}
