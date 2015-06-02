package nedelosk.forestday.structure.alloysmelter.blocks.tile;

import java.util.ArrayList;

import nedelosk.forestday.api.structure.tile.ITileAlloySmelter;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.base.blocks.BlockCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileAlloySmelterRegulator extends TileRegulatorHeat implements ITileAlloySmelter {

	public TileAlloySmelterRegulator() {
		super(1000, "alloyRegulator");
	}
	
	private int coilMaxHeat;
	private int heatResistanceTotal;
	private ArrayList<TileCoilHeat> coils = new ArrayList<TileCoilHeat>();
	private ArrayList<TileBusItem> bus = new ArrayList<TileBusItem>();
	private ArrayList<TileStructureBrick> walls = new ArrayList<TileStructureBrick>();
	private ArrayList<TileAlloySmelterController> controller = new ArrayList<TileAlloySmelterController>();
	private int updateTime;
	private int updateTimeTotal = 100;
	private ItemStack currentOutput;
	private int heatTimer;
	private int progressTime;
	private int progressTimeTotal;
	private boolean isMaster;
	
	public int getHeat()
	{
		return heat;
	}
	
	public void setHeat(int heat) {
		this.heat = heat;
	}
	
	public void addHeat(int heat)
	{
		this.heatResistanceTotal = this.heatResistanceTotal + heat;
	}
	
	public int getHeatResistance() {
		return heatResistanceTotal / walls.size();
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		if(updateTime==updateTimeTotal)
		{
			if(isStructure())
			{
				isMaster = true;
			}
			else
			{
				isMaster = false;
			}
			updateTime = 0;
		}
		else
		{
		updateTime++;
		}
		if(isMaster)
		{
			if(progressTime < progressTimeTotal)
			{
				progressTime++;
			}
			else
			{
				coilMaxHeat = getCoilMaxHeat();
				if(bus == null ||bus.get(0) == null || bus.get(1) == null)
				{
					isMaster = false;
				}
				TileBusItem inputBus = (bus.get(0).getMode() == Mode.Input) ? bus.get(0) : bus.get(1);
				TileBusItem outputBus = (bus.get(1).getMode() == Mode.Output) ? bus.get(1) : bus.get(0);
				inputBus.setStructure(Structures.Alloy_Smelter);
				if(currentOutput != null)
				{
					if(addToOutput(currentOutput, outputBus))
					{
						currentOutput = null;
					}
				}else{
			    ItemStack itemStackInputSlot1 = inputBus.getStackInSlot(0);
	    		ItemStack itemStackInputSlot2 = inputBus.getStackInSlot(1);
	    		if(itemStackInputSlot1 != null && itemStackInputSlot2 != null)
	    		{
	    			AlloySmelterRecipe recipe = AlloySmelterRecipeManager.getRecipe(itemStackInputSlot1, itemStackInputSlot2, heat);
	    			if(recipe != null)
	    			{
	    				if (itemStackInputSlot1.stackSize >= recipe.getInput1().stackSize && itemStackInputSlot2.stackSize >= recipe.getInput2().stackSize){
	    					inputBus.decrStackSize(0, recipe.getInput1().stackSize);
	    					inputBus.decrStackSize(1, recipe.getInput2().stackSize);
	        				this.currentOutput = recipe.getOutput1();
	        				progressTime = 0;
	    				}
	    			}
	    		}
				
			}
			}
			
		}
		if(hasCoils())
		{
		if(coilHeat < heatRegulator && coilHeat < coilMaxHeat)
		{
			coilHeat++;
		}
		if (heat != coilHeat) {
			if (heatTimer < 100) {
				heatTimer++;
			} else {
				heatTimer = 0;
				heat++;
			}
		}
		}
		else
		{
			coilHeat = 0;
		}
		if(heat > coilHeat || heat > heatRegulator)
		{
			heat--;
		}
		if(coilHeat > heatRegulator)
		{
			coilHeat--;
		}
	}
	
	private boolean addToOutput(ItemStack output, TileBusItem tile) {
		if (output == null) return true;
		
		for(int i = 0; i < 4; i++){
			ItemStack itemStack = tile.getStackInSlot(i);
			if (itemStack == null){
				tile.setInventorySlotContents(i, output); 
				return true;
			}
			else{
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()){
					if (itemStack.stackSize < itemStack.getMaxStackSize()){
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize){
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							tile.setInventorySlotContents(i, itemStack);
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
	
	@Override
	public boolean isStructure() {

		controller.clear();
		walls.clear();
		bus.clear();
		coils.clear();

		for (int i = 0; i < 3; i++) {
			for (int e = 0; e < 3; e++) {
				for (int r = 0; r < 3; r++) {
					TileEntity tile = this.worldObj.getTileEntity(this.xCoord - 1 + i, this.yCoord + r, this.zCoord - 1 + e);
					if (tile != null) {
						if (tile instanceof ITileAlloySmelter || tile instanceof TileStructureBrick) {
							if (tile instanceof TileAlloySmelterController) {
								if (tile.yCoord == this.yCoord + 1) {
									controller.add((TileAlloySmelterController) tile);
								}
							} else if (tile instanceof TileStructureBrick) {
								walls.add((TileStructureBrick) tile);
							}
						} else if (tile instanceof TileBusItem|| tile instanceof TileCoilHeat) {
							if (tile instanceof TileBusItem) {
								if(tile.yCoord == this.yCoord + 1 && ((TileBusItem)tile).getMode() == Mode.Output)
								{
								bus.add((TileBusItem) tile);
								}
								if(tile.yCoord == this.yCoord + 2 && ((TileBusItem)tile).getMode() == Mode.Input)
								{
								bus.add((TileBusItem) tile);
								}
							} else if (tile instanceof TileCoilHeat) {
								if(tile.yCoord == this.yCoord + 1)
								{
								coils.add((TileCoilHeat) tile);
								}
							}
						} else {

						}
					}
				}
			}
		}
		if (bus.size() == 2 && controller.size() == 1 && coils.size() == 2 && walls.size() == 20 && this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCoilHeat(int coilHeat) {
		this.coilHeat = coilHeat;
	}
	
	public void getMaxWallHeat()
	{
		if(walls != null && walls.size() == 20)
		{
			for(int i = 0;i < walls.size();i++)
			{
				int heat = walls.get(0).getMaxHeat();
				addHeat(heat);
			}
		}
	}
	
	@Override
	public int getCoilMaxHeat() {
		if(coils != null && coils.size() == 2)
		{
			int heat = coils.get(0).getProducibleHeat() + coils.get(1).getProducibleHeat();
			return heat / coils.size();
		}
		else
		{
			return 0;
		}
	}
	
	public boolean hasCoils()
	{
		if(this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord) instanceof BlockCoilHeat && this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord) instanceof BlockCoilHeat || this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1) instanceof BlockCoilHeat && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1) instanceof BlockCoilHeat)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void setHeatRegulator(int heatRegulator) {
		this.heatRegulator = heatRegulator;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heatRegulator", this.heatRegulator);
		nbt.setInteger("heat", this.heat);
		nbt.setInteger("heatCoil", this.coilHeat);
		
		
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heatRegulator = nbt.getInteger("heatRegulator");
		this.heat = nbt.getInteger("heat");
		this.coilHeat = nbt.getInteger("heatCoil");
		
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

}
