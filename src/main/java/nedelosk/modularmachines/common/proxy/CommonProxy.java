package nedelosk.modularmachines.common.proxy;

import nedelosk.modularmachines.api.basic.modular.module.ModuleEntry;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssemblerSlot;
import nedelosk.modularmachines.client.techtree.gui.GuiTechTree;
import nedelosk.modularmachines.client.techtree.gui.GuiTechTreeEditor;
import nedelosk.modularmachines.common.inventory.ContainerDummy;
import nedelosk.modularmachines.common.inventory.ContainerModularAssemblerSlot;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
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
			if(tile != null && tile instanceof TileBaseInventory)
		    {
				return ((TileBaseInventory) tile).getContainer(player.inventory);
		    }
		case 1:
			if(tile != null && tile instanceof TileBaseInventory)
		    {
		    	ModuleEntry entry = ((ModularSaveModule)player.getExtendedProperties(ModularSaveModule.class.getName())).entry;
		    	return new ContainerModularAssemblerSlot((TileBaseInventory) tile, player.inventory, entry);
		    }
		case 3:
	    	return new ContainerDummy();
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
			if(tile instanceof TileBaseInventory)
		    {
		    	return ((TileBaseInventory) tile).getGUIContainer(player.inventory);
		    }
		case 1:
			if(tile instanceof TileBaseInventory)
		    {
		    	ModuleEntry entry = ((ModularSaveModule)player.getExtendedProperties(ModularSaveModule.class.getName())).entry;
		    	return new GuiModularAssemblerSlot((TileBaseInventory) tile, player.inventory, entry);
		    }
		case 2:
		    return new GuiTechTree();
		case 3:
	    	return new GuiTechTreeEditor();
		default:
			return null;
	    }
	    }
	    return null;
	}
	
}
