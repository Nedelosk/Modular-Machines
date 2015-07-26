package nedelosk.modularmachines.common.inventory;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import nedelosk.forestday.common.machines.mutiblock.core.tile.TileMultiblockBase;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.modular.module.ModuleItem;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.inventory.slots.SlotModule;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssembler;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import nedelosk.nedeloskcore.common.inventory.slots.SlotOutput;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerModularAssembler extends ContainerBase{
	
	public ContainerModularAssembler(TileModularAssenbler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		
	}

}
