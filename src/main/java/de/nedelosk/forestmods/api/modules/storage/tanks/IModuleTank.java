package de.nedelosk.forestmods.api.modules.storage.tanks;

import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IModuleTank extends IModuleAddable {

	int getCapacity();

	void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack);

	FluidStack getStorageFluid(ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack);
}
