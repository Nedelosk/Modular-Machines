package nedelosk.modularmachines.common.inventory.machine;

import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;
import nedelosk.modularmachines.api.modular.module.basic.inventory.IModuleInventory;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerModularMachine extends ContainerBase<TileModular>{
	
	public InventoryPlayer inventory;
	
	public ContainerModularMachine(TileModular tileModularMachine, InventoryPlayer inventory) {
		super(tileModularMachine, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventoryPlayer) {
		this.inventory = inventoryPlayer;
		if(inventoryBase.getModular().getGuiManager().getModuleWithGui().getModule() instanceof IModuleInventory){
			if(((IModuleInventory)inventoryBase.getModular().getGuiManager().getModuleWithGui().getModule()).addSlots(this, this.inventoryBase.modular) != null)
			{
				for(Slot slot : ((IModuleInventory)inventoryBase.getModular().getGuiManager().getModuleWithGui().getModule()).addSlots(this, this.inventoryBase.modular))
				{
					addSlotToContainer(slot);		
				}
			}
		}
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		if(inventoryBase.getModular().getGuiManager().getModuleWithGui().getModule() instanceof IModuleInventory){
			int i = ((IModuleGui)inventoryBase.getModular().getGuiManager().getModuleWithGui().getModule()).getGuiTop(inventoryBase.modular) - 82;
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

}
