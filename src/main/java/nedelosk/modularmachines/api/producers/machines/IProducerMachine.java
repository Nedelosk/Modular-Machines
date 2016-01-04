package nedelosk.modularmachines.api.producers.machines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.producers.client.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.client.IProducerWithRenderer;
import nedelosk.modularmachines.api.producers.integration.IProducerWaila;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.special.IProducerController;
import nedelosk.modularmachines.api.producers.special.IProducerWithItem;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerMachine extends IProducerInventory, IProducerGuiWithWidgets, IProducerWithItem,
		IProducerController, IProducerWaila, IProducerWithRenderer {

	int getSpeed(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe);

	@SideOnly(Side.CLIENT)
	List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe);

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);

}
