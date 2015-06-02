package nedelosk.forestday.common.network;

import nedelosk.forestbotany.client.book.GuiPlantBook;
import nedelosk.forestbotany.client.book.GuiPlantBookIndex;
import nedelosk.forestday.common.registrys.ForestdayEntryRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import nedelosk.forestday.module.lumberjack.items.ItemCharconia;
import nedelosk.forestday.module.lumberjack.items.ItemNoteofLumberjack;
import nedelosk.nedeloskcore.client.gui.book.GuiBook;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.items.ItemBook;
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
	    
	    if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getContainer(player.inventory);
	    }
	    return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == 0)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBook)
			{
			return new GuiPlantBookIndex(null, "plants", ((ItemBook)player.getCurrentEquippedItem().getItem()).getBookData(), player.getGameProfile(), world);
			}
		}
		/*if(ID == 10)
		{
			return new GuiBook(player.getCurrentEquippedItem().getItem() instanceof ItemCharconia ? player.getCurrentEquippedItem() : null, new ResourceLocation("forestday", "textures/gui/book_charconia.png"), ForestdayItemRegistry.charconia.getBookData());
		}*/
		else if(ID == 11)
		{
			return new GuiBook(new ResourceLocation("forestday", "textures/gui/book_lumberjack.png"), ForestdayEntryRegistry.lumberjackData, player.getGameProfile(), world);
		}
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
		
	    if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getGUIContainer(player.inventory);
	    }
	    return null;
	}
}
