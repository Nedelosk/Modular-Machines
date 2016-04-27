package de.nedelosk.forestmods.common.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import de.nedelosk.forestmods.library.multiblock.MultiblockServerTickHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

	public void registerRenderers() {
	}

	public void registerTickHandlers() {
		FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
		// FMLCommonHandler.instance().bus().register(new
		// TransportServerTickHandler());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case 0:
				if (tile != null && tile instanceof de.nedelosk.forestmods.library.inventory.IGuiHandler) {
					return ((de.nedelosk.forestmods.library.inventory.IGuiHandler) tile).getContainer(player.inventory);
				}
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if ((world instanceof WorldClient)) {
			switch (ID) {
				case 0:
					if (tile instanceof de.nedelosk.forestmods.library.inventory.IGuiHandler) {
						return ((de.nedelosk.forestmods.library.inventory.IGuiHandler) tile).getGUIContainer(player.inventory);
					}
				default:
					return null;
			}
		}
		return null;
	}
}
