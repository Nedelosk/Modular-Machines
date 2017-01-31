package modularmachines.common.utils;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.containers.ContainerModuleLogic;
import modularmachines.common.modules.logic.EnergyStorageComponent;
import modularmachines.common.modules.logic.HeatComponent;
import modularmachines.common.modules.logic.UpdateComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ModuleUtil {

	public static void tryEmptyContainer(int inputSlot, int outputSlot, IItemHandler inventory, IFluidHandler handler) {
		if (inventory != null && handler != null) {
			ItemStack stack = inventory.getStackInSlot(inputSlot);
			if(!stack.isEmpty()){
				ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false).getResult();
				if (!containerStack.isEmpty()) {
					if (!inventory.extractItem(inputSlot, 1, true).isEmpty()) {
						if (inventory.insertItem(outputSlot, containerStack, true).isEmpty()) {
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
			if(!stack.isEmpty()){
				ItemStack containerStack = FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, false).getResult();
				if (!containerStack.isEmpty()) {
					if (!inventory.extractItem(inputSlot, 1, true).isEmpty()) {
						if (inventory.insertItem(outputSlot, containerStack, true).isEmpty()) {
							FluidActionResult filledContainer = FluidUtil.tryFillContainer(stack, handler, Fluid.BUCKET_VOLUME, null, true);
							inventory.insertItem(outputSlot, filledContainer.getResult(), false);
							inventory.extractItem(inputSlot, 1, false);
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
	
	@SideOnly(Side.CLIENT)
	public static IModuleGuiLogic getClientGuiLogic(){
		Minecraft mc = Minecraft.getMinecraft();
		return getGuiLogic(mc.player);
	}
	
	public static IModuleGuiLogic getGuiLogic(EntityPlayer player){
		Container container = player.openContainer;
		if(container instanceof ContainerModuleLogic){
			return ((ContainerModuleLogic) container).getGuiLogic();
		}
		return null;
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
		if(tileEntity.hasCapability(ModuleManager.MODULE_LOGIC, null)){
			return tileEntity.getCapability(ModuleManager.MODULE_LOGIC, null);
		}
		return null;
	}
	
	@Nullable
	public static IAssembler getAssembler(BlockPos pos, World world){
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity.hasCapability(ModuleManager.ASSEMBLER, null)){
			return tileEntity.getCapability(ModuleManager.ASSEMBLER, null);
		}
		return null;
	}
	
}
