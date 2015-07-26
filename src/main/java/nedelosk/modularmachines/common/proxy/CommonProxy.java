package nedelosk.modularmachines.common.proxy;

import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssemblerSlot;
import nedelosk.modularmachines.common.inventory.ContainerModularAssemblerSlot;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachineNBT;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.client.gui.Gui;
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
	    
		if (tile == null) return null;
	    if(tile instanceof TileBaseInventory && ID == 1)
	    {
	    	ModuleEntry entry = ((ModularSaveModule)player.getExtendedProperties(ModularSaveModule.class.getName())).entry;
	    	return new ContainerModularAssemblerSlot((TileBaseInventory) tile, player.inventory, entry);
	    }
		else if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getContainer(player.inventory);
	    }
	    return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    TileEntity tile = world.getTileEntity(x, y, z);
	    
		if (tile == null) return null;
		
		if(tile instanceof TileBaseInventory  && ID == 1)
	    {
	    	ModuleEntry entry = ((ModularSaveModule)player.getExtendedProperties(ModularSaveModule.class.getName())).entry;
	    	return new GuiModularAssemblerSlot((TileBaseInventory) tile, player.inventory, entry);
	    }
		else if(tile instanceof TileBaseInventory)
	    {
	    	return ((TileBaseInventory) tile).getGUIContainer(player.inventory);
	    }
	    return null;
	}
	
}
