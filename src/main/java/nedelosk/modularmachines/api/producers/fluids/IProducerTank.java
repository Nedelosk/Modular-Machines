package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IProducerTank extends IProducer {

	int getCapacity();

	void setStorageFluid(FluidStack stack, ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack);

	FluidStack getStorageFluid(ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack);

}
