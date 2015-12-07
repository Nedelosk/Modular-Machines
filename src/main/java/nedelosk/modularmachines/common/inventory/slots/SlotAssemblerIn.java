package nedelosk.modularmachines.common.inventory.slots;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotAssemblerIn extends Slot {

	public boolean isActivated;
	public Container parent;

	public SlotAssemblerIn(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, Container parent) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.parent = parent;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void activate() {
		isActivated = true;
	}

	public void deactivate() {
		isActivated = false;
	}

	@Override
	public void onSlotChanged() {
		parent.onCraftMatrixChanged(inventory);
	}

	@Override
	public boolean func_111238_b() {
		return isActivated;
	}

}
