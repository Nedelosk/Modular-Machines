package nedelosk.forestday.common.network;

import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.common.core.registry.EntryRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
	    
	    if(tile instanceof TileMachineBase)
	    {
	    	return ((TileMachineBase) tile).getContainer(player.inventory);
	    }
	    return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == 11)
		{
			return new GuiBook(new ResourceLocation("forestday", "textures/gui/book_lumberjack.png"), EntryRegistry.lumberjackData, player.getGameProfile(), world);
		}
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
		
	    if(tile instanceof TileMachineBase)
	    {
	    	return ((TileMachineBase) tile).getGUIContainer(player.inventory);
	    }
	    return null;
	}
}
