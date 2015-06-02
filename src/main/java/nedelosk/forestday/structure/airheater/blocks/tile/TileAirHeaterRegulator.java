package nedelosk.forestday.structure.airheater.blocks.tile;

import java.util.ArrayList;

import nedelosk.forestday.api.structure.tile.ITileAirHeater;
import nedelosk.forestday.api.structure.tile.ITileAlloySmelter;
import nedelosk.forestday.structure.airheater.crafting.AirHeaterRecipe;
import nedelosk.forestday.structure.airheater.crafting.AirHeaterRecipeManager;
import nedelosk.forestday.structure.base.blocks.BlockCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class TileAirHeaterRegulator extends TileRegulatorHeat implements ITileAlloySmelter {

	public TileAirHeaterRegulator() {
		super(1000, "alloyRegulator");
	}
	
	private int coilMaxHeat;
	private int heatResistanceTotal;
	private ArrayList<TileCoilHeat> coils = new ArrayList<TileCoilHeat>();
	private ArrayList<TileBusFluid> bus = new ArrayList<TileBusFluid>();
	private ArrayList<TileStructureBrick> walls = new ArrayList<TileStructureBrick>();
	private ArrayList<TileAirHeaterController> controller = new ArrayList<TileAirHeaterController>();
	private int updateTime;
	private int updateTimeTotal = 100;
	private FluidStack currentOutput;
	private int heatTimer;
	private int progressTime;
	private int progressTimeTotal = 50;
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
				if(bus.get(0) == null || bus.get(1) == null)
				{
					isMaster = false;
				}
				if(controller.get(0) == null)
				{
					isMaster = false;
				}
				TileBusFluid inputBus = (bus.get(0).getMode() == Mode.Input) ? bus.get(0) : bus.get(1);
				TileBusFluid outputBus = (bus.get(1).getMode() == Mode.Output) ? bus.get(1) : bus.get(0);
				if(currentOutput != null)
				{
					outputBus.getTank().fill(currentOutput, true);
					currentOutput = null;
				}else{
			    ItemStack itemStackController = controller.get(0).getStackInSlot(0);
	    		FluidStack fludiStackInput = inputBus.getTank().getFluid();
	    		if(fludiStackInput != null)
	    		{
	    			AirHeaterRecipe recipe = AirHeaterRecipeManager.getRecipe(fludiStackInput, heat);
	    			if(recipe != null)
	    			{
	    				if (inputBus.getTank().getFluidAmount() >= recipe.getInput().amount && !outputBus.getTank().isFull()){
	        				inputBus.getTank().drain(recipe.getInput().amount, true);
	        				this.currentOutput = recipe.getOutput();
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
	
	@Override
	public boolean isStructure() {

		controller.clear();
		walls.clear();
		bus.clear();
		coils.clear();

		for (int i = 0; i < 3; i++) {
			for (int e = 0; e < 3; e++) {
				for (int r = 0; r < 5; r++) {
					TileEntity tile = this.worldObj.getTileEntity(this.xCoord - 1 + i, this.yCoord + r, this.zCoord - 1 + e);
					if (tile != null) {
						if (tile instanceof ITileAirHeater || tile instanceof TileStructureBrick) {
							if (tile instanceof TileAirHeaterController) {
								if (tile.yCoord == this.yCoord + 1) {
									controller.add((TileAirHeaterController) tile);
								}
							} else if (tile instanceof TileStructureBrick) {
								if(tile.getBlockMetadata() == 1)
								{
								walls.add((TileStructureBrick) tile);
								}
							}
						} else if (tile instanceof TileBusFluid|| tile instanceof TileCoilHeat) {
							if (tile instanceof TileBusFluid) {
								if(tile.yCoord == this.yCoord + 3 && ((TileBusFluid)tile).getMode() == Mode.Output)
								{
								bus.add((TileBusFluid) tile);
								}
								if(tile.yCoord == this.yCoord + 1 && ((TileBusFluid)tile).getMode() == Mode.Input)
								{
								bus.add((TileBusFluid) tile);
								}
							} else if (tile instanceof TileCoilHeat) {
								if(tile.yCoord == this.yCoord + 2)
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
		if (bus.size() == 2 && controller.size() == 1 && coils.size() == 2 && walls.size() == 36 && this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord) && this.worldObj.isAirBlock(this.xCoord, this.yCoord + 2, this.zCoord) && this.worldObj.isAirBlock(this.xCoord, this.yCoord + 3, this.zCoord)) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getCoilHeat() {
		return coilHeat;
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
		if(this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord) instanceof BlockCoilHeat && this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord) instanceof BlockCoilHeat || this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord - 1) instanceof BlockCoilHeat && this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord + 1) instanceof BlockCoilHeat)
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
