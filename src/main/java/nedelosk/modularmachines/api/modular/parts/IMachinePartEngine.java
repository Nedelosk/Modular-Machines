package nedelosk.modularmachines.api.modular.parts;

import net.minecraft.item.ItemStack;

public interface IMachinePartEngine extends IMachinePart {

	int[] getEngineSpeed(ItemStack stack);
	
}
