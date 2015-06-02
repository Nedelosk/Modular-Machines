package nedelosk.forestbotany.common.inventory.slots;

import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.items.ItemSeed;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGender extends Slot {

	private IAlleleGender gender;
	
	public SlotGender(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, IAlleleGender gender) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.gender = gender;
	}
	
	public SlotGender(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(gender == null && stack.getItem() instanceof ItemSeed)
		{
			return true;
		}
		if(gender == null)
		{
			return false;
		}
		if(PlantManager.getPlantManager(stack) != null && PlantManager.getPlantManager(stack).hasGender(gender, stack))
		{
			return true;
		}
		return false;
	}

}
