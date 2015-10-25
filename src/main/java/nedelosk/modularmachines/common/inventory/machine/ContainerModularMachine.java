package nedelosk.modularmachines.common.inventory.machine;

import nedelosk.forestday.common.inventory.ContainerBase;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.IProducerInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
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
		
		IModularGuiManager guiManager = inventoryBase.getModular().getGuiManager();
		ModuleStack stack = guiManager.getModuleWithGui();
		
		if(stack.getProducer() instanceof IProducerInventory){
			if(((IProducerInventory)stack.getProducer()).addSlots(this, this.inventoryBase.modular, stack) != null)
			{
				for(Slot slot : ((IProducerInventory)stack.getProducer()).addSlots(this, this.inventoryBase.modular, stack))
				{
					addSlotToContainer(slot);		
				}
			}
		}
	}
	
	@Override
	protected void addInventory(InventoryPlayer inventory) {
		
		IModularGuiManager guiManager = inventoryBase.getModular().getGuiManager();
		ModuleStack stack = guiManager.getModuleWithGui();
		
		if(stack.getProducer() instanceof IProducerInventory){
			int i = ((IProducerGui)stack.getProducer()).getGuiTop(inventoryBase.modular, stack) - 82;
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
