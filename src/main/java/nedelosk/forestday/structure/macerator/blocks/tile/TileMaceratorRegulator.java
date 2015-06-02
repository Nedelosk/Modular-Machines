package nedelosk.forestday.structure.macerator.blocks.tile;

import java.util.ArrayList;

import nedelosk.forestday.api.structure.tile.ITileMacerator;
import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipe;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileMaceratorRegulator extends TileRegulatorGrinding implements ITileMacerator {

	public TileMaceratorRegulator() {
		super(1000, "maceratorController");
	}
	
	private int heatResistanceTotal;
	private ArrayList<TileCoilGrinding> coils = new ArrayList<TileCoilGrinding>();
	private ArrayList<TileBusItem> bus = new ArrayList<TileBusItem>();
	private ArrayList<TileStructureBrick> walls = new ArrayList<TileStructureBrick>();
	private ArrayList<TileMaceratorController> controller = new ArrayList<TileMaceratorController>();
	private int updateTime;
	private int updateTimeTotal = 100;
	private ItemStack currentOutput;
	private boolean isMaster;
	private int progressTime;
	private int progressTimeTotal;
	private int sharpness;
	
	public void addRoughness(int heat)
	{
		this.heatResistanceTotal = this.heatResistanceTotal + heat;
	}
	
	public int getRoughnessResistance() {
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
		if(isMaster && coils != null && coils.get(0) != null && coils.get(1) != null)
		{
			if(coils.get(0).hasCoils() && coils.get(1).hasCoils() && coils.get(0).CoilsHasSharpness() && coils.get(1).CoilsHasSharpness())
			{
			if(bus.get(0) == null || bus.get(1) == null)
			{
				isMaster = false;
			}
			this.sharpness = setSharpness();
			TileBusItem inputBus = (bus.get(0).getMode() == Mode.Input) ? bus.get(0) : bus.get(1);
			TileBusItem outputBus = (bus.get(1).getMode() == Mode.Output) ? bus.get(1) : bus.get(0);
			inputBus.setStructure(Structures.Macerator);
			if(progressTime < progressTimeTotal)
			{
				progressTime++;
			}
			else
			{
			if(currentOutput != null)
			{
				if(addToOutput(currentOutput, outputBus))
				{
					currentOutput = null;
				}
			}else{
		    ItemStack itemStackInputSlot1 = inputBus.getStackInSlot(0);
    		if(itemStackInputSlot1 != null)
    		{
				//System.out.print(sharpness + ", ;");
    			MaceratorRecipe recipe = MaceratorRecipeManager.getRecipe(itemStackInputSlot1, sharpness);
    			if(recipe != null)
    			{
    				if(recipe.getInput() != null)
    				{
    				if (itemStackInputSlot1.stackSize >= recipe.getInput().stackSize){
    					inputBus.decrStackSize(0, recipe.getInput().stackSize);
        				this.currentOutput = recipe.getOutput1();
        				progressTime = 0;
        				progressTimeTotal = recipe.getBurnTime() * 100 / (coils.get(0).getCoilSpeed() + coils.get(1).getCoilSpeed() / 2);
        				setCoilSharpness(5);
    				}
    				}
    				if(recipe.getOredictInput() != null)
    				{
    					if(itemStackInputSlot1.stackSize >= 1)
    					{
    						inputBus.decrStackSize(0, 1);
            				this.currentOutput = recipe.getOutput1();
            				progressTime = 0;
            				progressTimeTotal = recipe.getBurnTime() * 100 / (coils.get(0).getCoilSpeed() + coils.get(1).getCoilSpeed() / 2);
            				setCoilSharpness(5);
    					}
    				}
    			}
    		}
			}
			
		}
		}
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
						if (tile instanceof ITileMacerator || tile instanceof TileStructureBrick) {
							if (tile instanceof TileMaceratorController) {
								if (tile.yCoord == this.yCoord + 1) {
									controller.add((TileMaceratorController) tile);
								}
							} else if (tile instanceof TileStructureBrick) {
								walls.add((TileStructureBrick) tile);
							}
						} else if (tile instanceof TileBusItem|| tile instanceof TileCoilGrinding) {
							if (tile instanceof TileBusItem) {
								if(tile.yCoord == this.yCoord + 1 && ((TileBusItem)tile).getMode() == Mode.Output)
								{
								bus.add((TileBusItem) tile);
								}
								if(tile.yCoord == this.yCoord + 2 && ((TileBusItem)tile).getMode() == Mode.Input)
								{
								bus.add((TileBusItem) tile);
								}
							} else if (tile instanceof TileCoilGrinding) {
								if(tile.yCoord == this.yCoord + 1)
								{
								coils.add((TileCoilGrinding) tile);
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
	
	public void getMaxWall()
	{
		if(walls != null && walls.size() == 20)
		{
			for(int i = 0;i < walls.size();i++)
			{
				int heat = walls.get(0).getMaxHeat();
				addRoughness(heat);
			}
		}
	}
	
	public boolean hasCoils()
	{
		if(this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord) instanceof BlockCoilGrinding && this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord) instanceof BlockCoilGrinding || this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1) instanceof BlockCoilGrinding && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1) instanceof BlockCoilGrinding)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		
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
	
	public void setCoilSharpness(int sharpness)
	{
		for(int r = 0;r < 2;r++)
		{
		for(int i = 0;i < 4;i++)
		{
			TileCoilGrinding coil = this.coils.get(r);
			int sharpnessI = coil.getCoil(i).getTagCompound().getInteger("Damage");
			coil.getCoil(i).getTagCompound().setInteger("Damage", sharpnessI - sharpness);
		}
		}
	}
	
	public int setSharpness()
	{
		int sharpness = 0;
		for(int r = 0;r < 2;r++)
		{
		for(int i = 0;i < 4;i++)
		{
			TileCoilGrinding coil = this.coils.get(r);
			int sharpnessI = coil.getCoil(i).getTagCompound().getInteger("Damage");
			sharpness = sharpness + sharpnessI;
		}
		}
		return sharpness / 8;
	}
	
	public int getSharpness() {
		return sharpness;
	}
	
	public int getProgressTime() {
		return progressTime;
	}
	
	public int getProgressTimeTotal() {
		return progressTimeTotal;
	}
}
