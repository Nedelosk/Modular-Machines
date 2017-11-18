package modularmachines.common.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.common.modules.logic.EnergyStorageComponent;
import modularmachines.common.modules.logic.HeatComponent;
import modularmachines.common.modules.logic.UpdateComponent;

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
	
	public static <M> List<M> getModules(IModuleContainer provider, Class<? extends M> moduleClass) {
		Preconditions.checkNotNull(moduleClass);
		List<M> modules = Lists.newArrayList();
		for (Module module : provider.getModules()) {
			if (moduleClass.isAssignableFrom(module.getClass())) {
				modules.add((M) module);
			}
		}
		return modules;
	}
	
	@Nullable
	public static <M> M getModule(IModuleContainer provider, Class<? extends M> moduleClass) {
		Preconditions.checkNotNull(moduleClass);
		for (Module module : provider.getModules()) {
			if (moduleClass.isAssignableFrom(module.getClass())) {
				return (M) module;
			}
		}
		return null;
	}
	
	@Nullable
	public static IModuleContainer getContainer(ILocatable locatable) {
		return getContainer(locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Nullable
	public static IModuleGuiLogic getGuiLogic(IModuleContainer provider, EntityPlayer player) {
		return getGuiLogic(player.world, provider.getLocatable().getCoordinates(), player.getGameProfile());
	}
	
	@Nullable
	public static IModuleGuiLogic getGuiLogic(BlockPos pos, EntityPlayer player) {
		return getGuiLogic(player.world, pos, player.getGameProfile());
	}
	
	@Nullable
	public static IModuleGuiLogic getGuiLogic(World world, BlockPos pos, @Nullable GameProfile player) {
		String filename = "guiLogic." + (player == null ? "common" : player.getId());
		GuiLogicCache cache = (GuiLogicCache) world.loadData(GuiLogicCache.class, filename);
		
		// Create a cache if there is none yet.
		if (cache == null) {
			cache = new GuiLogicCache(filename);
			world.setData(filename, cache);
		}
		
		return cache.getLogic(world, pos);
	}
	
	@Nullable
	public static HeatComponent getHeat(IModuleContainer provider) {
		return provider.getComponent(LogicComponent.HEAT);
	}
	
	@Nullable
	public static EnergyStorageComponent getEnergy(IModuleContainer provider) {
		return provider.getComponent(LogicComponent.ENERGY);
	}
	
	@Nullable
	public static UpdateComponent getUpdate(IModuleContainer provider) {
		return provider.getComponent(LogicComponent.UPDATE);
	}
	
	@Nullable
	public static IModuleContainer getContainer(BlockPos pos, IBlockAccess world) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.hasCapability(ModuleRegistry.MODULE_CONTAINER, null)) {
			return tileEntity.getCapability(ModuleRegistry.MODULE_CONTAINER, null);
		}
		return null;
	}
	
}
