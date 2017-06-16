package modularmachines.common.utils;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.modules.logic.EnergyStorageComponent;
import modularmachines.common.modules.logic.HeatComponent;
import modularmachines.common.modules.logic.ModelComponent;
import modularmachines.common.modules.logic.UpdateComponent;

public class ModuleUtil {

	public static void tryEmptyContainer(int inputSlot, int outputSlot, IItemHandler inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			ItemStack stack = inventory.getStackInSlot(inputSlot);
			if(ItemUtil.isNotEmpty(stack)){
				FluidActionResult result = FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if(result.isSuccess()){
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
			if(ItemUtil.isNotEmpty(stack)){
				FluidActionResult result =  FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false);
				if(result.isSuccess()){
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
	public static <M> List<M> getModules(IModuleLogic logic, Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<M> modules = Lists.newArrayList();
		for (IStorage storage : logic.getStorages()) {
			for (Module module : storage.getStorage().getModules()) {
				if (moduleClass.isAssignableFrom(module.getClass())) {
					modules.add((M) module);
				}
			}
		}
		return modules;
	}

	@Nullable
	public static <M> M getModule(IModuleLogic logic, Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		for (IStorage storage : logic.getStorages()) {
			for (Module module : storage.getStorage().getModules()) {
				if (moduleClass.isAssignableFrom(module.getClass())) {
					return (M) module;
				}
			}
		}
		return null;
	}
	
	@Nullable
	public static IModuleLogic getLogic(ILocatable locatable){
		return getLogic(locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Nullable
	public static IAssembler getAssembler(ILocatable locatable){
		return getAssembler(locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	public static IModuleGuiLogic getGuiLogic(IModuleLogic logic, EntityPlayer player){
		return getGuiLogic(player.world, logic.getLocatable().getCoordinates(), player.getGameProfile());
	}
	
	public static IModuleGuiLogic getGuiLogic(BlockPos pos, EntityPlayer player){
		return getGuiLogic(player.world, pos, player.getGameProfile());
	}
	
	public static IModuleGuiLogic getGuiLogic(World world, BlockPos pos, @Nullable GameProfile player){
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
	public static ModelComponent getModel(IModuleLogic logic){
		return logic.getComponent(LogicComponent.MODEL);
	}
	
	@Nullable
	public static HeatComponent getHeat(IModuleLogic logic){
		return logic.getComponent(LogicComponent.HEAT);
	}
	
	@Nullable
	public static EnergyStorageComponent getEnergy(IModuleLogic logic){
		return logic.getComponent(LogicComponent.ENERGY);
	}
	
	@Nullable
	public static UpdateComponent getUpdate(IModuleLogic logic){
		return logic.getComponent(LogicComponent.UPDATE);
	}
	
	@Nullable
	public static IModuleLogic getLogic(BlockPos pos, World world){
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity.hasCapability(ModuleRegistry.MODULE_LOGIC, null)){
			return tileEntity.getCapability(ModuleRegistry.MODULE_LOGIC, null);
		}
		return null;
	}
	
	@Nullable
	public static IAssembler getAssembler(BlockPos pos, World world){
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity.hasCapability(ModuleRegistry.ASSEMBLER, null)){
			return tileEntity.getCapability(ModuleRegistry.ASSEMBLER, null);
		}
		return null;
	}
	
}
