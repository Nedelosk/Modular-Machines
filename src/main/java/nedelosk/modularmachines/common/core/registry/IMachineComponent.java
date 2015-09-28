package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.basic.machine.part.MaterialType;
import net.minecraft.item.ItemStack;

public interface IMachineComponent {
	
	MaterialType getMaterialType();
	
}
