package de.nedelosk.modularmachines.common.inventory;

import java.util.Iterator;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerOutput;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerModularAssembler extends ContainerBase<TileModularAssembler> {

	private SlotAssembler[] slots;

	public ContainerModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addInventory(InventoryPlayer inventory) {
		for(int i1 = 0; i1 < 3; i1++) {
			for(int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 10 + l1 * 18, 175 + i1 * 18));
			}
		}
		for(int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 10 + j1 * 18, 233));
		}
	}

	public void updateSlots(){
		updateSlot(slots[8]);
		for(SlotAssembler slot : slots){
			slot.isUpdated = false;
		}

		ModularManager.assembleModular(handler.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), player, new ItemStack(BlockManager.blockModular));
	}

	public void updateSlot(IAssemblerSlot slot){
		slot.setUpdated(true);
		if(slot.getSlot().getStack() != null){
			IAssemblerSlot[] siblings = slot.getSiblings();
			if(siblings != null && siblings.length > 0){
				for(IAssemblerSlot sibling : siblings){
					if(!sibling.isActive()){
						sibling.addParent(slot);
						sibling.changeStatus(true);
					}
				}
			}
		}else if(slot.isActive()){
			List<IAssemblerSlot> parents = slot.getParents();
			if(!parents.isEmpty()){
				Iterator<IAssemblerSlot> parentIterator = parents.iterator();
				while(parentIterator.hasNext()){
					IAssemblerSlot parent = parentIterator.next();
					if(!parent.isActive() || parent.getSlot().getStack() == null){
						parentIterator.remove();
					}
				}
				if(!slot.isController() && parents.isEmpty()){
					slot.changeStatus(false);
				}
			}
			IAssemblerSlot[] siblings = slot.getSiblings();
			if(siblings != null && siblings.length > 0){
				for(IAssemblerSlot sibling : siblings){
					if(sibling != null && !sibling.isUpdated() && !parents.contains(sibling)){
						updateSlot(sibling);
					}
				}
			}
		}
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		slots = new SlotAssembler[17];
		addSlotToContainer(new SlotAssembler(this, 0, 10, 82, inventory.player, IModuleCasing.class, true));
		addSlotToContainer(new SlotAssemblerOutput(handler, 1, 154, 82));
		slots[0] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 2, 46, 10, inventory.player));
		slots[1] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 3, 82, 10, inventory.player, IModuleTool.class));
		slots[2] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 4, 118, 10, inventory.player));

		slots[3] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 5, 28, 46, inventory.player));
		slots[4] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 6, 64, 46, inventory.player, IModuleTool.class));
		slots[5] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 7, 100, 46, inventory.player, IModuleTool.class));
		slots[6] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 8, 136, 46, inventory.player));

		slots[7] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 9, 46, 82, inventory.player));
		slots[8] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 10, 82, 82, inventory.player, IModuleController.class, true).setController(true));
		slots[9] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 11, 118, 82, inventory.player));

		slots[10] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 12, 28, 118, inventory.player));
		slots[11] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 13, 64, 118, inventory.player, IModuleTool.class));
		slots[12] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 14, 100, 118, inventory.player, IModuleTool.class));
		slots[13] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 15, 136, 118, inventory.player));

		slots[14] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 16, 46, 154, inventory.player));
		slots[15] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 17, 82, 154, inventory.player, IModuleTool.class));
		slots[16] = (SlotAssembler) addSlotToContainer(new SlotAssembler(this, 18, 118, 154, inventory.player));

		slots[0].setSiblings(new SlotAssembler[]{slots[1], slots[3], slots[4]});
		slots[1].setSiblings(new SlotAssembler[]{slots[0], slots[4], slots[5], slots[2]});
		slots[2].setSiblings(new SlotAssembler[]{slots[3], slots[5], slots[8]});
		slots[3].setSiblings(new SlotAssembler[]{slots[0], slots[4], slots[7]});
		slots[4].setSiblings(new SlotAssembler[]{slots[7], slots[8], slots[3], slots[0], slots[1], slots[5]});
		slots[5].setSiblings(new SlotAssembler[]{slots[1], slots[2], slots[4], slots[8], slots[9], slots[6]});
		slots[6].setSiblings(new SlotAssembler[]{slots[2], slots[5], slots[9]});
		slots[7].setSiblings(new SlotAssembler[]{slots[2], slots[4], slots[8], slots[11], slots[10]});
		slots[8].setSiblings(new SlotAssembler[]{slots[4], slots[5], slots[9], slots[11], slots[12], slots[7]});
		slots[9].setSiblings(new SlotAssembler[]{slots[5], slots[6], slots[13], slots[12], slots[8]});
		slots[10].setSiblings(new SlotAssembler[]{slots[7], slots[11], slots[14]});
		slots[11].setSiblings(new SlotAssembler[]{slots[7], slots[8], slots[12], slots[15], slots[14], slots[10]});
		slots[12].setSiblings(new SlotAssembler[]{slots[8], slots[9], slots[13], slots[16], slots[15], slots[11]});
		slots[13].setSiblings(new SlotAssembler[]{slots[6], slots[9], slots[16]});
		slots[14].setSiblings(new SlotAssembler[]{slots[10], slots[11], slots[15]});
		slots[15].setSiblings(new SlotAssembler[]{slots[11], slots[12], slots[16], slots[14]});
		slots[16].setSiblings(new SlotAssembler[]{slots[15], slots[12], slots[13]});
	}
}
