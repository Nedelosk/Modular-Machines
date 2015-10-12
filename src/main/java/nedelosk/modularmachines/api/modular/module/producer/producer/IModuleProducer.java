package nedelosk.modularmachines.api.modular.module.producer.producer;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithWidgets;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modular.module.producer.tool.IModuleTool;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.recipes.NeiStack;
import net.minecraft.item.ItemStack;

public interface IModuleProducer extends IModuleTool, IModuleInventory, IModuleGuiWithWidgets, IModuleWithItem {

	int getBurnTime();
	
	int getBurnTimeTotal();
	
	ArrayList<NeiStack> addNEIStacks();
	
}
