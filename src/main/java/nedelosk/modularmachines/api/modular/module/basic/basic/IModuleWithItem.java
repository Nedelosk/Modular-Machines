package nedelosk.modularmachines.api.modular.module.basic.basic;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import net.minecraft.item.ItemStack;

public interface IModuleWithItem extends IModule {

	IModule buildModule(ItemStack[] stacks);
	
	PartType[] getRequiredComponents();
	
	ModuleStack creatModule(ItemStack stack);
	
	int getColor();
	
}
