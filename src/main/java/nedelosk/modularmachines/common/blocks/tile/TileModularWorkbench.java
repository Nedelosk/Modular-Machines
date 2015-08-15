package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.modularmachines.api.basic.modular.IModularWorkbench;
import nedelosk.modularmachines.client.gui.GuiModularWorkbench;
import nedelosk.modularmachines.common.inventory.ContainerModularWorkbench;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileModularWorkbench extends TileBaseInventory implements IModularWorkbench {

	public TileModularWorkbench() {
		super(26);
	}
	
	public int tier;
	
	public ContainerModularWorkbench container;
	
	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		testWorkbench();
	}

	@Override
	public String getMachineTileName() {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularWorkbench(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularWorkbench(inventory, this);
	}
	
	public void setInventorySlotContentsSoftly(int par1, ItemStack par2ItemStack)
	{
		this.slots[par1] = par2ItemStack;
	}
	
	public void testWorkbench()
	{
	}

	@Override
	public ItemStack getStackInRowAndColumn(int par1, int par2) {
		if ((par1 >= 0) && (par1 < 5))
		{
		       int var3 = par1 + par2 * 5;
		       return getStackInSlot(var3);
		}
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		ItemStack stack = super.getStackInSlotOnClosing(par1);
		markDirty();
		return stack;
	}
	
	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		ItemStack stack = super.decrStackSize(par1, par2);
		if (this.container != null) {
			this.container.onCraftMatrixChanged(this);
		}
		markDirty();
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		super.setInventorySlotContents(par1, par2ItemStack);
	     markDirty();
	     this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	     if (this.container != null) {
	       this.container.onCraftMatrixChanged(this);
	     }
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tier = nbt.getInteger("Tier");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tier = nbt.getInteger("Tier");
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public boolean canDrain(int tier) {
		return false;
	}

	@Override
	public boolean drainMaterials(int tier) {
		return false;
	}

}
