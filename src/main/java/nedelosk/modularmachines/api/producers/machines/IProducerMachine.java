package nedelosk.modularmachines.api.producers.machines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.api.gui.IGuiBase;
import nedelosk.forestcore.api.gui.Widget;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.producers.integration.IProducerWaila;
import nedelosk.modularmachines.api.producers.inventory.IProducerInventory;
import nedelosk.modularmachines.api.producers.special.IProducerController;
import nedelosk.modularmachines.api.producers.special.IProducerWithItem;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerMachine extends IProducerInventory, IProducerGuiWithWidgets, IProducerWithItem, IProducerController, IProducerWaila {

	int getSpeed(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe);
	
	@SideOnly(Side.CLIENT)
	List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe);

}
