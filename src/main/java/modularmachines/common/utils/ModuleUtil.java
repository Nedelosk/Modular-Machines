package modularmachines.common.utils;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.ModuleCapabilities;

public class ModuleUtil {
	
	public static void tryEmptyContainer(int inputSlot, int outputSlot, IItemHandler inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			ItemStack stack = inventory.getStackInSlot(inputSlot);
			if (ItemUtil.isNotEmpty(stack)) {
				FluidActionResult result = FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if (result.isSuccess()) {
					ItemStack containerStack = result.getResult();
					if (ItemUtil.isNotEmpty(inventory.extractItem(inputSlot, 1, true))) {
						if (ItemUtil.isEmpty(inventory.insertItem(outputSlot, containerStack, true))) {
							FluidActionResult emptyContainer = FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, true);
							inventory.insertItem(outputSlot, emptyContainer.getResult(), false);
							inventory.extractItem(inputSlot, 1, false);
						}
					}
				}
			}
		}
	}
	
	public static void tryFillContainer(int inputSlot, int outputSlot, IItemHandler inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			ItemStack stack = inventory.getStackInSlot(inputSlot);
			if (ItemUtil.isNotEmpty(stack)) {
				FluidActionResult result = FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if (result.isSuccess()) {
					ItemStack containerStack = result.getResult();
					if (ItemUtil.isNotEmpty(containerStack)) {
						if (ItemUtil.isNotEmpty(inventory.extractItem(inputSlot, 1, true))) {
							if (ItemUtil.isEmpty(inventory.insertItem(outputSlot, containerStack, true))) {
								FluidActionResult filledContainer = FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, true);
								inventory.insertItem(outputSlot, filledContainer.getResult(), false);
								inventory.extractItem(inputSlot, 1, false);
							}
						}
					}
				}
			}
		}
	}
	
	@Nullable
	public static IModuleContainer getContainer(ILocatable locatable) {
		return getContainer(locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	public static void markDirty(IModule module) {
		ILocatable locatable = module.getContainer().getLocatable();
		locatable.markLocatableDirty();
		//markForRenderUpdate(module);
	}
	
	public static void markForModelUpdate(IModule module) {
		ModularMachines.proxy.markForModelUpdate(module);
		markForRenderUpdate(module);
	}
	
	public static void markForRenderUpdate(IModule module) {
		ILocatable locatable = module.getContainer().getLocatable();
		BlockPos pos = locatable.getCoordinates();
		World world = locatable.getWorldObj();
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Nullable
	public static <C> C getComponent(@Nullable IModule module, Class<C> componentClass) {
		if (module == null) {
			return null;
		}
		return module.getComponent(componentClass);
	}
	
	@Nullable
	public static IModuleContainer getContainer(BlockPos pos, IBlockAccess world) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, null)) {
			return tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, null);
		}
		return null;
	}
	
}
