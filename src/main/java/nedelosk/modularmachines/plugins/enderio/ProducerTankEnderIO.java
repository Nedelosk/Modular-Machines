package nedelosk.modularmachines.plugins.enderio;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.fluids.IModuleTank;
import nedelosk.modularmachines.api.modules.fluids.ModuleTank;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;

public class ProducerTankEnderIO extends ModuleTank {

	public ProducerTankEnderIO(String modifier, int capacity) {
		super(modifier, capacity);
	}

	@Override
	public void setStorageFluid(FluidStack stack, ModuleStack<IModule, IModuleTank> moduleStack, ItemStack itemStack) {
		if (stack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();
			stack.writeToNBT(fluidTag);
			nbtTag.setTag("tankContents", fluidTag);
			nbtTag.setInteger("tankType", itemStack.getItemDamage());
			nbtTag.setInteger("voidMode", 2);
			nbtTag.setByte("eio.abstractMachine", (byte) 1);
			NBTTagCompound displayTag = new NBTTagCompound();
			displayTag.setString("Name", itemStack.getItemDamage() == 0 ? "Fluid Tank (Configured)" : "Pressurized Fluid Tank (Configured)");
			nbtTag.setTag("display", displayTag);
			nbtTag.setTag("Items", new NBTTagList());
			nbtTag.setInteger("slotLayoutVersion", 1);
			nbtTag.setInteger("redstoneControlMode", 0);
			itemStack.setTagCompound(nbtTag);
		}
	}

	@Override
	public FluidStack getStorageFluid(ModuleStack<IModule, IModuleTank> moduleStack, ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) {
			return null;
		}
		if (!itemStack.getTagCompound().hasKey("tankContents")) {
			return null;
		}
		NBTTagCompound tag = itemStack.getTagCompound();
		NBTTagCompound fluidTag = tag.getCompoundTag("tankContents");
		return FluidStack.loadFluidStackFromNBT(fluidTag);
	}
}