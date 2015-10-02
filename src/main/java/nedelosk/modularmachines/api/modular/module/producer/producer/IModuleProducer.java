package nedelosk.modularmachines.api.modular.module.producer.producer;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modular.module.producer.tool.IModuleTool;
import nedelosk.modularmachines.api.recipes.NeiStack;
import net.minecraft.item.ItemStack;

public interface IModuleProducer extends IModuleTool, IModuleInventory {

	int getBurnTime();
	
	int getBurnTimeTotal();
	
	ArrayList<NeiStack> addNEIStacks();
	
	IModule buildModule(ItemStack[] stacks);
	
}
