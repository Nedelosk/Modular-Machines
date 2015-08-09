package nedelosk.modularmachines.common.inventory.machine;

import nedelosk.modularmachines.api.modular.module.IModuleGui;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerModularMachine extends ContainerBase{
	
	public InventoryPlayer inventory;
	
	public ContainerModularMachine(TileModularMachine tileModularMachine, InventoryPlayer inventory) {
		super(tileModularMachine, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		this.inventory = inventoryPlayer;
		if(((IModuleGui)((TileModularMachine)inventoryBase).getModuleGui().getModule()).addSlots(this, ((TileModularMachine)this.inventoryBase).machine) != null)
		{
			for(Slot slot : ((IModuleGui)((TileModularMachine)inventoryBase).getModuleGui().getModule()).addSlots(this, ((TileModularMachine)this.inventoryBase).machine))
			{
				addSlotToContainer(slot);		
			}
		}
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

}
