package nedelosk.modularmachines.api.modular.parts;

import nedelosk.modularmachines.api.modular.material.IMachine;
import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.material.MaterialType;
import net.minecraft.item.ItemStack;

public interface IMachineComponent extends IMachine {
	
	MaterialType getMaterialType();
	
	Material getMaterial(ItemStack stack);
	
}
