package nedelosk.nedeloskcore.common.blocks.tile;

import nedelosk.nedeloskcore.client.gui.GuiPlan;
import nedelosk.nedeloskcore.common.inventory.ContainerPlan;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TilePlan extends TileMachineBase {

	public NBTTagCompound plan;
	public int stages;
	public ItemStack[] inputs = new ItemStack[4];
	public ItemStack[] input = new ItemStack[4];
	public int stage;
	public boolean closeGui;
	public boolean closedGui;
	
	public TilePlan() {
		super(8);
		burnTimeTotal = 120;
	}

	@Override
	public String getMachineTileName() {
		return "plan";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerPlan(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiPlan(this, inventory);
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		if(plan != null)
		{
		if(stage == stages && stages != 0)
		{
			closeGui = true;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			if(closedGui)
			{
			ItemUtils.dropItems(worldObj, xCoord, yCoord, zCoord);
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Block.getBlockFromItem(getOutput(plan).getItem()), getOutput(plan).getItemDamage(), 3);
			}
		}
		else if(((getStackInSlot(0) != null && ((input[0] != null && inputs[0].stackSize != input[0].stackSize) || input[0] == null)) || (getStackInSlot(1) != null && ((input[1] != null && inputs[1].stackSize != input[1].stackSize) || input[1] == null)) || (getStackInSlot(2) != null  && ((input[2] != null && inputs[2].stackSize != input[2].stackSize) || input[2] == null)) || (getStackInSlot(3) != null &&((input[0] != null && inputs[0].stackSize != input[0].stackSize) || input[0] == null))) || ((inputs[0] == null || input[0] != null && inputs[0].stackSize == input[0].stackSize) && (inputs[1] == null || input[1] != null && inputs[1].stackSize == input[1].stackSize) && (inputs[2] == null || input[2] != null && inputs[2].stackSize == input[2].stackSize) && (inputs[3] == null || input[3] != null && inputs[3].stackSize == input[3].stackSize)))
		{
		if(burnTime > burnTimeTotal)
		{
			if((inputs[0] == null || input[0] != null && inputs[0].stackSize == input[0].stackSize) && (inputs[1] == null || input[1] != null && inputs[1].stackSize == input[1].stackSize) && (inputs[2] == null || input[2] != null && inputs[2].stackSize == input[2].stackSize) && (inputs[3] == null || input[3] != null && inputs[3].stackSize == input[3].stackSize))
			{
				input[0] = null;
				input[1] = null;
				input[2] = null;
				input[3] = null;
				stage++;
				inputs[0] = getInput(plan, stage)[0];
				inputs[1] = getInput(plan, stage)[1];
				inputs[2] = getInput(plan, stage)[2];
				inputs[3] = getInput(plan, stage)[3];
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else
			{
			for(int i = 0;i < 4;i++)
			{
				if(inputs[i] != null)
				{
				ItemStack stack = getStackInSlot(i);
				if(stack != null)
				{
					if(stack.getItem() == inputs[i].getItem() && stack.getItemDamage() == inputs[i].getItemDamage())
					{
					if(input[i] == null)
					{
						decrStackSize(i, 1);
						input[i] = new ItemStack(inputs[i].getItem(), 1, inputs[i].getItemDamage());
					}
					else if(inputs[i].stackSize != input[i].stackSize)
					{
						for(int r = 6;r > 0;r--)
						{
						if((!(inputs[i].stackSize < input[i].stackSize + r)) && stack.stackSize >= r)
						{
							decrStackSize(i, r);
							input[i].stackSize = input[i].stackSize + r;
							break;
						}
						}
					}
					}
				}
				}
			}
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			burnTime = 0;
		}
		else
		{
			burnTime++;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		}
		else
		{
			if(burnTime != 0)
			{
			burnTime = 0;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(plan != null)
		{
			nbt.setTag("plan", plan);
		}
		
		nbt.setInteger("stages", stages);
		nbt.setInteger("stage", stage);
		
		NBTTagList nbtTagList = new NBTTagList();
		for(int i = 0; i< 4; i++){
			if (this.input[i] != null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("item", (byte)i);
				this.input[i].writeToNBT(item);
				nbtTagList.appendTag(item);
			}
		}
		nbt.setTag("input", nbtTagList);
		
		NBTTagList nbtTag = new NBTTagList();
		for(int i = 0; i< 4; i++){
			if (this.inputs[i] != null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("item", (byte)i);
				this.inputs[i].writeToNBT(item);
				nbtTag.appendTag(item);
			}
		}
		nbt.setTag("inputs", nbtTag);
		
		nbt.setBoolean("closedGui", closedGui);
		nbt.setBoolean("closeGui", closeGui);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		plan = nbt.getCompoundTag("plan");
		
		stages = nbt.getInteger("stages");
		stage = nbt.getInteger("stage");
		
		closedGui = nbt.getBoolean("closedGui");
		closeGui = nbt.getBoolean("closeGui");
		
		NBTTagList nbtTagList = nbt.getTagList("input", 10);
		
		for(int i = 0; i < nbtTagList.tagCount(); i++){
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			if (itemLocation >= 0 && itemLocation < this.getSizeInventory()){
				this.input[itemLocation] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		NBTTagList nbtTag = nbt.getTagList("inputs", 10);
		if(nbtTag != null)
		{
			for(int i = 0; i < nbtTag.tagCount(); i++){
			NBTTagCompound item = nbtTag.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			if (itemLocation >= 0 && itemLocation < this.getSizeInventory())
			{
				this.inputs[itemLocation] = ItemStack.loadItemStackFromNBT(item);
			}
		    }
		}
	}
	
	public void setPlan(NBTTagCompound plan)
	{
		this.plan = plan;
		stages = plan.getInteger("stages");
		inputs[0] = getInput(plan, stage)[0];
		inputs[1] = getInput(plan, stage)[1];
		inputs[2] = getInput(plan, stage)[2];
		inputs[3] = getInput(plan, stage)[3];
	}
	
	public ItemStack[] getInput() {
		return input;
	}
	
	public ItemStack[] getInputs() {
		return input;
	}
	
	public ItemStack getOutput(NBTTagCompound plan) {
		if(plan == null)
			return null;
		NBTTagCompound nbt = plan.getCompoundTag("Output");
		return ItemStack.loadItemStackFromNBT(nbt);
	}
	
	public ItemStack[] getInput(NBTTagCompound plan, int stage) {
		if(plan == null)
			return null;
		
		NBTTagList nbtTagList = plan.getTagList("input" + stage, 10);
		ItemStack[] stacks = new ItemStack[4];
		for(int i = 0; i < nbtTagList.tagCount(); i++)
		{
			NBTTagCompound nbt = nbtTagList.getCompoundTagAt(i);
			stacks[i] = ItemStack.loadItemStackFromNBT(nbt);
		}
		return stacks;
	}
	
	public NBTTagCompound getPlan() {
		return plan;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot > 3)
		return false;
		
		if(inputs[slot] == null)
			return false;
		
		if(inputs[slot].getItem() == stack.getItem() && inputs[slot].getItemDamage() == stack.getItemDamage())
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		return false;
	}


	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side != 1)
		{
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7};
		}
		return null;
	}

	@Override
	public String getMachineName() {
		return "plan";
	}

}
