package de.nedelosk.modularmachines.common.utils;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ModuleUtil {

	public static void tryEmptyContainer(int inputSlot, int outputSlot, IModuleInventory inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			if (inventory.getStackInSlot(inputSlot) != null) {
				ItemStack stack = inventory.getStackInSlot(inputSlot);
				IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if (containerStack != null) {
					if (inventory.extractItemInternal(inputSlot, 1, true) != null) {
						if (inventory.insertItemInternal(outputSlot, containerStack, true) == null) {
							inventory.insertItemInternal(outputSlot, FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, true), false);
							inventory.extractItemInternal(inputSlot, 1, false);
						}
					}
				}
			}
		}
	}

	public static void tryFillContainer(int inputSlot, int outputSlot, IModuleInventory inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			if (inventory.getStackInSlot(inputSlot) != null) {
				ItemStack stack = inventory.getStackInSlot(inputSlot);
				ItemStack containerStack = FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if (containerStack != null) {
					if (inventory.extractItemInternal(inputSlot, 1, true) != null) {
						if (inventory.insertItemInternal(outputSlot, containerStack, true) == null) {
							inventory.insertItemInternal(outputSlot, FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, true), false);
							inventory.extractItemInternal(inputSlot, 1, false);
						}
					}
				}
			}
		}
	}
}
