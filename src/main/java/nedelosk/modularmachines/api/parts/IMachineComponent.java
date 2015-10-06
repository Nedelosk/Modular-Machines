package nedelosk.modularmachines.api.parts;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import net.minecraft.item.ItemStack;

public interface IMachineComponent extends IMachine {
	
	MaterialType getMaterialType();
	
	Material getMaterial(ItemStack stack);
	
}
