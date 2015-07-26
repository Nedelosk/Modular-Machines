package nedelosk.nedeloskcore.common.network;

import nedelosk.forestbotany.client.book.GuiPlantBookIndex;
import nedelosk.nedeloskcore.client.gui.GuiBlueprint;
import nedelosk.nedeloskcore.client.gui.GuiPlanningTool;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerPlanningTool;
import nedelosk.nedeloskcore.common.inventory.InventoryPlanningTool;
import nedelosk.nedeloskcore.common.items.ItemBook;
import nedelosk.nedeloskcore.common.items.ItemPlan;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(x, y, z);
		if((ID == 0 && tile == null) || (ID == 1 && tile == null))
		{
			return null;
		}
		else if(ID == 2)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPlan && player.getCurrentEquippedItem().getItemDamage() == 2)
			{
			return new ContainerPlanningTool(new InventoryPlanningTool(player.getCurrentEquippedItem()), player.inventory);
			}
		}
	    
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
		/*if(ID == 0)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBook)
			{
			return new GuiPlantBookIndex(null, "plants", ((ItemBook)player.getCurrentEquippedItem().getItem()).getBookData(), player.getGameProfile(), world);
			}
		}
		else*/if(ID == 1)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPlan && player.getCurrentEquippedItem().getItemDamage() == 1)
			{
				return new GuiBlueprint(PlanRecipeManager.getBlocks(), player);
			}
		}
		else if(ID == 2)
		{
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPlan && player.getCurrentEquippedItem().getItemDamage() == 2)
			{
				return new GuiPlanningTool(new ContainerPlanningTool(new InventoryPlanningTool(player.getCurrentEquippedItem()), player.inventory), player.inventory);
			}
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
