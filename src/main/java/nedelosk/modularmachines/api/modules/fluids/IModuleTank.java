package nedelosk.modularmachines.api.modules.fluids;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTank extends IModule {

	int getCapacity();

	void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank> moduleStack, ItemStack itemStack);

	FluidStack getStorageFluid(ModuleStack<IModuleTank> moduleStack, ItemStack itemStack);
}
