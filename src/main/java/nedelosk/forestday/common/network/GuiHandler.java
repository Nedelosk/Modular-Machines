package nedelosk.forestday.common.network;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null) {
			return null;
		}
		if (tile instanceof nedelosk.forestcore.library.inventory.IGuiHandler) {
			return ((nedelosk.forestcore.library.inventory.IGuiHandler) tile).getContainer(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null) {
			return null;
		}
		if (tile instanceof nedelosk.forestcore.library.inventory.IGuiHandler) {
			return ((nedelosk.forestcore.library.inventory.IGuiHandler) tile).getGUIContainer(player.inventory);
		}
		return null;
	}
}
