package nedelosk.modularmachines.api.parts;

import net.minecraft.item.ItemStack;

public interface IMachinePartEngine extends IMachinePart {

	int[] getEngineSpeed(ItemStack stack);
	
}
