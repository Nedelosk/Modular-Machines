package nedelosk.modularmachines.api.modules.storage.tanks;

import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTank extends IModuleAddable {

	int getCapacity();

	void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack);

	FluidStack getStorageFluid(ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack);
}
