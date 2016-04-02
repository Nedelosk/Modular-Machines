package de.nedelosk.forestmods.api.modules.integration;

import java.util.List;

import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.NeiStack;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleNEI extends IModule {

	List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe);

	List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe);
}
