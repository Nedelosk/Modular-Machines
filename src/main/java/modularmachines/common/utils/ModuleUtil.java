package modularmachines.common.utils;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleGuiLogic;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.containers.ContainerModular;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleUtil {

	@Nullable
	public <M extends Module> List<M> getModules(IModuleLogic logic, Class<? extends M> moduleClass) {
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
	public <M extends Module> M getModule(IModuleLogic logic, Class<? extends M> moduleClass) {
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
		if(container instanceof ContainerModular){
			return ((ContainerModular) container).getGuiLogic();
		}
		return null;
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
