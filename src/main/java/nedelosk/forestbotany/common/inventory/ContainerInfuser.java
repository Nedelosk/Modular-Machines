package nedelosk.forestbotany.common.inventory;

import nedelosk.forestbotany.common.blocks.tile.TileInfuser;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.inventory.slots.SlotGender;
import nedelosk.forestbotany.common.inventory.slots.SlotSoil;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInfuser extends ContainerBase {

	public ContainerInfuser(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}
	
	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		if(((TileInfuser)tile).getChamerWithGender(Allele.male) != null)
		addSlotToContainer(new SlotGender(((TileInfuser)tile).getChamerWithGender(Allele.male), 0 , 44, 29, Allele.male));
		
		if(((TileInfuser)tile).getChamerWithGender(Allele.female) != null)
		addSlotToContainer(new SlotGender(((TileInfuser)tile).getChamerWithGender(Allele.female), 0 , 116, 29, Allele.female));
		
		//Plant Output
		addSlotToContainer(new Slot(tile, 0 , 80, 29)
		{
			@Override
			public boolean isItemValid(ItemStack p_75214_1_) {
				return false;
			}
		});
		
		//Soil
		addSlotToContainer(new SlotSoil(tile, 1, 80, 56));
	}

}
