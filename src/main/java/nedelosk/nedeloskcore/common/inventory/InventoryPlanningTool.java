package nedelosk.nedeloskcore.common.inventory;

import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryPlanningTool extends ExternalInventory {

	public ItemStack parent;

	public InventoryPlanningTool(ItemStack parent)
	{
		super(1);
		this.parent = parent;
		if (parent.getTagCompound() == null)
			parent.setTagCompound(new NBTTagCompound());
		readFromNBT(parent.getTagCompound());
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}
	
	public ItemStack findParentInInventory(EntityPlayer player) {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (ItemUtils.isIdenticalItem(stack, parent)) {
				return stack;
			}
		}
		return parent;
	}
	
	@Override
	public void onGuiSaved(EntityPlayer player) {
		parent = findParentInInventory(player);
		if (parent != null) {
			save();
		}
	}
	
	@Override
	public void save()
	{
		NBTTagCompound nbt = parent.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		writeToNBT(nbt);
		parent.setTagCompound(nbt);
	}

}
