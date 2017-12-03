package modularmachines.common.modules.components.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.components.handlers.IEnergyHandlerComponent;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.components.handlers.ISaveHandler;
import modularmachines.common.utils.NBTUtil;

public class SaveHandlers {
	public enum THEnergy implements ISaveHandler<IEnergyHandlerComponent> {
		INSTANCE;
		
		@Override
		public void writeToItem(IEnergyHandlerComponent component, ItemStack itemStack) {
			NBTUtil.setInteger(itemStack, "Energy", component.getEnergyStored());
		}
		
		@Override
		public void readFromItem(IEnergyHandlerComponent component, ItemStack itemStack) {
			int energy = NBTUtil.getInteger(itemStack, "Energy");
			component.setEnergy(energy);
		}
	}
	
	public enum THTank implements ISaveHandler<IFluidHandlerComponent> {
		INSTANCE;
		
		@Override
		public void writeToItem(IFluidHandlerComponent component, ItemStack itemStack) {
			IFluidHandlerComponent.ITank tank = component.getTank(0);
			if (tank == null) {
				return;
			}
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack != null) {
				NBTUtil.setTag(itemStack, "Fluid", fluidStack.writeToNBT(new NBTTagCompound()));
			}
		}
		
		@Override
		public void readFromItem(IFluidHandlerComponent component, ItemStack itemStack) {
			NBTTagCompound fluid = NBTUtil.getTag(itemStack, "Fluid");
			if (!fluid.hasNoTags()) {
				IFluidHandlerComponent.ITank tank = component.getTank(0);
				if (tank != null) {
					tank.setFluid(FluidStack.loadFluidStackFromNBT(fluid));
				}
			}
		}
	}
}
