package modularmachines.common.core;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.ModuleCapabilities;

public class GuiHandler implements IGuiHandler {
	
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		IGuiProvider provider = getProvider(ID, world, new BlockPos(x, y, z));
		if (provider == null) {
			return null;
		}
		return provider.createContainer(player.inventory);
	}
	
	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		IGuiProvider provider = getProvider(ID, world, new BlockPos(x, y, z));
		if (provider == null) {
			return null;
		}
		return provider.createGui(player.inventory);
	}
	
	@Nullable
	private IGuiProvider getProvider(int ID, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile == null || !tile.hasCapability(ModuleCapabilities.MODULE_CONTAINER, null)) {
			return null;
		}
		IModuleContainer container = tile.getCapability(ModuleCapabilities.MODULE_CONTAINER, null);
		if (container == null) {
			return null;
		}
		IModule module = container.getModule(ID);
		if (module == null) {
			return null;
		}
		return module.getInterface(IGuiProvider.class);
	}
	
}
