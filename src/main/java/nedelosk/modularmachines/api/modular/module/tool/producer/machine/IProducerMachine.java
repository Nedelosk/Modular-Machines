package nedelosk.modularmachines.api.modular.module.tool.producer.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerController;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerWaila;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerWithItem;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;

public interface IProducerMachine extends IProducerInventory, IProducerGuiWithWidgets, IProducerWithItem, IProducerController, IProducerWaila {

	int getSpeed(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe);
	
	@SideOnly(Side.CLIENT)
	List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe);

}
