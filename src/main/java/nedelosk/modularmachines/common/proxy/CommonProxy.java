package nedelosk.modularmachines.common.proxy;

import nedelosk.modularmachines.client.techtree.gui.GuiTechTree;
import nedelosk.modularmachines.client.techtree.gui.GuiTechTreeEditor;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseGui;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	
	public void registerRenderer()
	{
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
	    switch (ID) {
		case 0:
			if(tile != null && tile instanceof TileBaseGui)
		    {
				return ((TileBaseInventory) tile).getContainer(player.inventory);
		    }
		default:
			return null;
	    }
	}
	
	public void init(){
	}
	
	public void postInit(){
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(x, y, z);
	    if ((world instanceof WorldClient)) {
	    switch (ID) {
		case 0:
			if(tile instanceof TileBaseGui)
		    {
		    	return ((TileBaseInventory) tile).getGUIContainer(player.inventory);
		    }
		case 1:
	    	return new GuiTechTreeEditor();
		case 2:
		    return new GuiTechTree();
		default:
			return null;
	    }
	    }
	    return null;
	}
	
}
