package nedelosk.modularmachines.api.modular.module.tool.producer.fluids;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IProducerTank extends IProducer {

	int getCapacity();

	void setStorageFluid(FluidStack stack, ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack);
	
	FluidStack getStorageFluid(ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack);

}
