package nedelosk.modularmachines.common.inventory.machine;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import nedelosk.forestday.common.machines.mutiblock.core.tile.TileMultiblockBase;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleGui;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.modular.module.ModuleItem;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
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

public class ContainerModularMachine extends ContainerBase{
	
	public InventoryPlayer inventory;
	
	public ContainerModularMachine(TileModularMachine tileModularMachine, InventoryPlayer inventory) {
		super(tileModularMachine, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		this.inventory = inventoryPlayer;
		//addSlot(new Slot(this.inventoryBase, 0, 56, 35));
		((IModuleGui)((TileModularMachine)inventoryBase).getModuleGui().getModule()).addSlots(this, ((TileModularMachine)this.inventoryBase).machine);
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		int i = ((Module)((TileModularMachine)inventoryBase).getModuleGui().getModule()).getGuiTop(((TileModularMachine)inventoryBase).machine) - 82;
        for (int i1 = 0; i1 < 3; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
                addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, i + i1 * 18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, i + 58));
        }
	}
	
	public void addSlot(Slot slot)
	{
		addSlotToContainer(slot);
	}

}
