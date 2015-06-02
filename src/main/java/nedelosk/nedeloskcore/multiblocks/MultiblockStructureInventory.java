package nedelosk.nedeloskcore.multiblocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public abstract class MultiblockStructureInventory extends MultiblockStructureBase implements ISidedInventory {

	private ItemStack[] slots;
	
	public MultiblockStructureInventory(int height, int length, int width, String structureID, int slot, char[][][] pattern, World world) {
		super(height, length, width, structureID, pattern, world);
		slots = new ItemStack[slot];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.slots[slot] != null){
			ItemStack itemstack;
				
		    if(this.slots[slot].stackSize <= amount){
		    	itemstack = this.slots[slot];
		    	this.slots[slot] = null;
		    	return itemstack;
		    }
		    else{
		    	itemstack = this.slots[slot].splitStack(amount);
		    	
		    	if(this.slots[slot].stackSize == 0){
		    		this.slots[slot] = null;
		    	}
		    }
		}
		
		return null;
		
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null){
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		};
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}
	
	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entitiPlayer) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		return true;
	}


	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return null;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		
		NBTTagList nbtTagList = new NBTTagList();
		for(int i = 0; i< this.getSizeInventory(); i++){
			if (this.slots[i] != null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("item", (byte)i);
				this.slots[i].writeToNBT(item);
				nbtTagList.appendTag(item);
			}
		}
		nbt.setTag("slots", nbtTagList);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
		NBTTagList nbtTagList = nbt.getTagList("slots", 10);
		this.slots = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < nbtTagList.tagCount(); i++){
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			if (itemLocation >= 0 && itemLocation < this.getSizeInventory()){
				this.slots[itemLocation] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}
	
	
	protected boolean addToOutput(ItemStack output, int slotMin, int slotMax) {
		if (output == null) return true;
		
		for(int i = slotMin; i < slotMax; i++){
			ItemStack itemStack = getStackInSlot(i);
			if (itemStack == null){
				setInventorySlotContents(i, output); 
				return true;
			}
			else{
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()){
					if (itemStack.stackSize < itemStack.getMaxStackSize()){
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize){
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							setInventorySlotContents(i, itemStack);
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
		return false;		
	}
	
	public abstract Container getContainer(InventoryPlayer inventory);

	public abstract Object getGUIContainer(InventoryPlayer inventory);

	@Override
	public void markDirty() {
		
	}

}
