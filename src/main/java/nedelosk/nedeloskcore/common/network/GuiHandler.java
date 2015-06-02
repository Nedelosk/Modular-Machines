package nedelosk.nedeloskcore.common.network;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
	    
	    if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getContainer(player.inventory);
	    }
	    return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
		
	    if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getGUIContainer(player.inventory);
	    }
	    return null;
	}
}
