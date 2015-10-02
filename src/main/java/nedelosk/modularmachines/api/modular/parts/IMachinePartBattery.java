package nedelosk.modularmachines.api.modular.parts;

import net.minecraft.item.ItemStack;

public interface IMachinePartBattery extends IMachinePart {

	int getEnergyReceiveRate(ItemStack stack);
	
	int getEnergyExtractionRate(ItemStack stack);
	
	int getEnergyMax(ItemStack stack);
	
	int getEnergy(ItemStack stack);
	
}
