package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.api.ForestdayApi;
import nedelosk.forestday.api.structure.coils.ICoilItem;
import nedelosk.forestday.api.structure.coils.ITileCoilGrinding;
import nedelosk.forestday.api.structure.tile.ITileCoilHeat;
import nedelosk.forestday.common.plugins.PluginManager;
import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import nedelosk.forestday.structure.base.gui.GuiCoilGrinding;
import nedelosk.forestday.structure.base.gui.container.ContainerCoilGrinding;
import nedelosk.forestday.structure.base.items.ItemCoilGrinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileCoilGrinding extends TileStructureInventory implements ITileCoilGrinding {
	
	public TileCoilGrinding() {
		super(1000, 6, "coilGrinding");
	}
	
	public TileCoilGrinding(int max, String uid, int coilSpeed) {
		super(max, 6, "coilGrinding" + uid);
		this.coilSpeedMax = coilSpeed;
		this.coilSpeed = coilSpeedMax;
	}
	
	private int coilSpeedMax;
	private boolean hasCoils;
	private int coilSpeed;
	private int timer;
	private int y = 0;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("MaxCoilSpeed", this.coilSpeedMax);
		nbt.setInteger("CoilSpeed", this.coilSpeed);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.coilSpeedMax = nbt.getInteger("MaxCoilSpeed");
		this.coilSpeed = nbt.getInteger("CoilSpeed");
		
	}
	
	public int getCoilSpeed() {
		return coilSpeed;
	}
	
	public int getCoilSpeedMax() {
		return coilSpeedMax;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerCoilGrinding(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiCoilGrinding(this, inventory);
	}
	
	public boolean isStackInSlotCoil(int slotID)
	{
		if (getStackInSlot(slotID) != null) {
			if (getStackInSlot(slotID).getItem() instanceof ItemCoilGrinding) {
				return true;
			}
		}
		return false;
	}
	
	public int getCoilMeta(int slotID)
	{
		return this.getStackInSlot(slotID).getItemDamage();
	}
	
	public boolean hasCoils()
	{
		return isStackInSlotCoil(2) && isStackInSlotCoil(3) && isStackInSlotCoil(4) && isStackInSlotCoil(5);
	}
	
	public ItemStack getCoil(int coil)
	{
		switch (coil) {
		case 0:
			return getStackInSlot(2);
		case 1:
			return getStackInSlot(3);
		case 2:
			return getStackInSlot(4);
		case 3:
			return getStackInSlot(5);
		default:
			return null;
		}
	}
	
	public int coilWeight()
	{
		return BlockCoilGrinding.coilWeight[getCoilMeta(2)] + BlockCoilGrinding.coilWeight[getCoilMeta(3)] + BlockCoilGrinding.coilWeight[getCoilMeta(4)] + BlockCoilGrinding.coilWeight[getCoilMeta(5)];
	}
	
	public boolean CoilsHasSharpness()
	{
		if(((ICoilItem)getCoil(0).getItem()).getCoilSharpness(getCoil(0)) > 0 && ((ICoilItem)getCoil(1).getItem()).getCoilSharpness(getCoil(1)) > 0 && ((ICoilItem)getCoil(2).getItem()).getCoilSharpness(getCoil(2)) > 0 && ((ICoilItem)getCoil(3).getItem()).getCoilSharpness(getCoil(3)) > 0)
		{
			return true;
		}
		return false;
	}
	
	public void sharpCoil()
	{
		if(this.getStackInSlot(0) != null)
		{
			if(getGrindValue(this.getStackInSlot(0)) != 0)
			{
				int grind = getGrindValue(this.getStackInSlot(0));
				grind = grind / 4;
				for(int i = 0;i < 4;i++)
				{
				int sharp = getCoil(i).getTagCompound().getInteger("Damage");
				if(!(sharp >= BlockCoilGrinding.coilMaxSharpness[getCoil(i).getItemDamage()]))
				{
				getCoil(i).getTagCompound().setInteger("Damage", sharp + grind >= BlockCoilGrinding.coilMaxSharpness[getCoil(i).getItemDamage()] ? BlockCoilGrinding.coilMaxSharpness[getCoil(i).getItemDamage()] : sharp + grind);
				y++;
				}
				}
				if(y == 4)
				{
					this.decrStackSize(0, 1);
					y = 0;
				}
			}
		}
	}
	
	public int getGrindValue(ItemStack stack)
	{
		if(stack.getItem() == Items.flint)
		{
			return 200;
		}
		return 100;
	}

	@Override
	public String getMachineTileName() {
		return "structure.coil.grinding";
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	if(timer < 45)
    	{
    		timer++;
    	}
    	else{
    	if(hasCoils())
    	{
    		hasCoils = true;
    		if(coilWeight() > BlockCoilGrinding.coilMaxWeight[this.getBlockMetadata()])
    		{
    			if(coilSpeed != BlockCoilGrinding.coilSpeed[this.getBlockMetadata()] / 2)
    			{
    			coilSpeed = BlockCoilGrinding.coilSpeed[this.getBlockMetadata()] / 2;
    			}
    		}
    		
    		sharpCoil();
    	}
    	else
    	{
    		hasCoils = false;
    	}
    	timer = 0;
    	}
		
	}

}
