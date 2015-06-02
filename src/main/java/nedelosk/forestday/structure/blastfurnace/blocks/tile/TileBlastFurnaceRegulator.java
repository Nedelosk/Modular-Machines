package nedelosk.forestday.structure.blastfurnace.blocks.tile;

import java.util.ArrayList;

import nedelosk.forestday.api.structure.tile.ITileBlastFurnace;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.structure.base.blocks.tile.TileBus;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureMetal;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipe;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileBlastFurnaceRegulator extends TileRegulatorHeat implements ITileBlastFurnace {

	public TileBlastFurnaceRegulator() {
		super(1000, "alloyRegulator");
	}
	
	private int coilMaxHeat;
	private int heatResistanceTotal;
	private ArrayList<TileCoilHeat> coils = new ArrayList<TileCoilHeat>();
	private ArrayList<TileBusFluid> busGas = new ArrayList<TileBusFluid>();
	private ArrayList<TileBusFluid> busGasOut = new ArrayList<TileBusFluid>();
	private ArrayList<TileBusFluid> busFluid = new ArrayList<TileBusFluid>();
	private ArrayList<TileBusItem> busItems = new ArrayList<TileBusItem>();
	private ArrayList<TileStructureBrick> bricks = new ArrayList<TileStructureBrick>();
	private ArrayList<TileStructureMetal> metals = new ArrayList<TileStructureMetal>();
	private ArrayList<TileBlastFurnaceController> controller = new ArrayList<TileBlastFurnaceController>();
	private int updateTime;
	private int updateTimeTotal = 100;
	private FluidStack[] currentOutput = new FluidStack[2];
	private int heatTimer;
	private int progressTime;
	private int progressTimeTotal = 200;
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
		return heatResistanceTotal / bricks.size();
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
			//heat = 2000;
			if(busFluid.get(0) == null || busFluid.get(1) == null)
			{
				isMaster = false;
			}
			if(progressTime < progressTimeTotal)
			{
				progressTime++;
			}
			else
			{
				coilMaxHeat = getCoilMaxHeat();
				//System.out.print("Structure");
				//System.out.print(heat);
				TileBusFluid outputBusFluid0 = busFluid.get(0);
				TileBusFluid outputBusFluid1 = busFluid.get(1);
				TileBusItem inputBus = busItems.get(0);
				TileBusFluid inputBusGas0 = busGas.get(0);
				TileBusFluid inputBusGas1 = busGas.get(1);
				TileBusFluid inputBusGas2 = busGas.get(2);
				TileBusFluid inputBusGas3 = busGas.get(3);
				
				TileBusFluid outputBusGas0 = busGasOut.get(0);
				TileBusFluid outputBusGas1 = busGasOut.get(1);
				
			if(inputBusGas0.getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && inputBusGas1.getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && inputBusGas2.getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && inputBusGas3.getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && !outputBusGas0.getTank().isFull() && !outputBusGas1.getTank().isFull())
			{
			if(!outputBusFluid0.getTank().isFull() && !outputBusFluid1.getTank().isFull())	
			{
			if(currentOutput != null)	
			{
				outputBusFluid0.getTank().fill(currentOutput[0], true);
				outputBusFluid1.getTank().fill(currentOutput[1], true);
				outputBusGas0.getTank().fill(new FluidStack(FluidRegistry.getFluid("gas_blast"), ForestdayConfig.blastFurnaceGasOutput), true);
				outputBusGas1.getTank().fill(new FluidStack(FluidRegistry.getFluid("gas_blast"), ForestdayConfig.blastFurnaceGasOutput), true);
				currentOutput = null;
				heat= heat - 10;
			}
			else
			{
				ItemStack[] stackInput = new ItemStack[]{ inputBus.getStackInSlot(0), inputBus.getStackInSlot(1), inputBus.getStackInSlot(2), inputBus.getStackInSlot(3) };
				if(stackInput[0] != null && stackInput[1] != null && stackInput[2] != null && stackInput[3] != null)
				{
					BlastFurnaceRecipe recipe = BlastFurnaceRecipeManager.getRecipe(stackInput, heat);
					if(recipe != null)
					{
						if(stackInput[0].stackSize >= recipe.getInput()[0].stackSize && stackInput[1].stackSize >= recipe.getInput()[1].stackSize && stackInput[2].stackSize >= recipe.getInput()[2].stackSize && stackInput[3].stackSize >= recipe.getInput()[3].stackSize)
						{
							inputBus.decrStackSize(0, recipe.getInput()[0].stackSize);
							inputBus.decrStackSize(1, recipe.getInput()[1].stackSize);
							inputBus.decrStackSize(2, recipe.getInput()[2].stackSize);
							inputBus.decrStackSize(3, recipe.getInput()[3].stackSize);
							
							inputBusGas0.getTank().drain(new FluidStack(FluidRegistry.getFluid("air_hot"), ForestdayConfig.blastFurnaceGasInput), true);
							inputBusGas1.getTank().drain(new FluidStack(FluidRegistry.getFluid("air_hot"), ForestdayConfig.blastFurnaceGasInput), true);
							inputBusGas2.getTank().drain(new FluidStack(FluidRegistry.getFluid("air_hot"), ForestdayConfig.blastFurnaceGasInput), true);
							inputBusGas3.getTank().drain(new FluidStack(FluidRegistry.getFluid("air_hot"), ForestdayConfig.blastFurnaceGasInput), true);
							
							currentOutput = recipe.getOutput();
							progressTime = 0;
						}
					}
				}
			}
				
			}
			}
			
			}
			if(coilHeat < heatRegulator && coilHeat < coilMaxHeat )
			{
				if(hasGas())
				{
					GasDrainAndFill();
					coilHeat++;
				}
			}
			if(heat != coilHeat)
			{
				if (heatTimer < 50) {
					heatTimer++;
				} 
				else 
				{
					heatTimer = 0;
					heat++;
					
				}
			}
			
		}
		if(!hasCoils())
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
	
	public boolean hasGas()
	{
		return busGas.get(0).getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && busGas.get(1).getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && busGas.get(2).getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput && busGas.get(3).getTank().getFluidAmount() >= ForestdayConfig.blastFurnaceGasInput;
	}
	
	public void GasDrainAndFill()
	{
		busGas.get(0).getTank().drain(10, true);
		busGas.get(1).getTank().drain(10, true);
		busGas.get(2).getTank().drain(10, true);
		busGas.get(3).getTank().drain(10, true);
		
		busGasOut.get(0).getTank().fill(new FluidStack(FluidRegistry.getFluid("gas_blast"), 5), true);
		busGasOut.get(1).getTank().fill(new FluidStack(FluidRegistry.getFluid("gas_blast"), 5), true);
	}
	
	@Override
	public boolean isStructure() {

		controller.clear();
		bricks.clear();
		busFluid.clear();
		busGas.clear();
		busGasOut.clear();
		busItems.clear();
		metals.clear();
		coils.clear();

		for (int i = 0; i < 5; i++) {
			for (int e = 0; e < 5; e++) {
				for (int r = 0; r < 7; r++) {
					TileEntity tile = this.worldObj.getTileEntity(this.xCoord - 2 + i, this.yCoord + r, this.zCoord - 2 + e);
					if (tile != null) {
						if (tile instanceof ITileBlastFurnace || tile instanceof TileStructureBrick || tile instanceof TileStructureMetal) {
							if (tile instanceof TileBlastFurnaceController) {
								if (tile.yCoord == this.yCoord + 1) {
									controller.add((TileBlastFurnaceController) tile);
								}
							} else if (tile instanceof TileStructureBrick) {
								if(tile.getBlockMetadata() == 2)
								{
								bricks.add((TileStructureBrick) tile);
								}
							}
							else if (tile instanceof TileStructureMetal) {
								metals.add((TileStructureMetal) tile);
							}
						} else if (tile instanceof TileBus|| tile instanceof TileCoilHeat) {
							if (tile instanceof TileBusFluid) {
								if(tile.yCoord == this.yCoord + 6 && ((TileBusFluid)tile).getMode() == Mode.Output)
								{
								busGasOut.add((TileBusFluid) tile);
								}
								if(tile.yCoord == this.yCoord + 2 && ((TileBusFluid)tile).getMode() == Mode.Input)
								{
								busGas.add((TileBusFluid) tile);
								}
								if(tile.yCoord == this.yCoord + 1 && ((TileBusFluid)tile).getMode() == Mode.Output)
								{
								busFluid.add((TileBusFluid) tile);
								}
							}
							else if (tile instanceof TileBusItem) {
								if(tile.yCoord == this.yCoord + 6 && ((TileBusItem)tile).getMode() == Mode.Input)
								{
								busItems.add((TileBusItem) tile);
								}
								
							}
							else if (tile instanceof TileCoilHeat) {
								if(tile.yCoord == this.yCoord + 3 || tile.yCoord == this.yCoord + 4 || tile.yCoord == this.yCoord + 5)
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
		if (busFluid.size() == 2 && busGas.size() == 4 && busGasOut.size() == 2 && busItems.size() == 1 && controller.size() == 1 && coils.size() == 6 && metals.size() == 76 && bricks.size() == 37 && this.isAir()) {
			busItems.get(0).setStructure(Structures.Blast_Furnace);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isAir()
	{
		int air = 0;
		for(int i = 0;i <3;i++)
		{
			for(int r = 0;r < 3;r++)
			{
				for(int e = 0;e < 5;e++)
				{
					if(this.worldObj.isAirBlock(xCoord - 1 + r, yCoord + 1 + e, zCoord - 1 + i))
					{
						air++;
					}
					else
					{
						System.out.print((xCoord - 1 + r) + "," +  (yCoord + 1 + e) + "," +  (zCoord - 1 + i) + ", ");
					}
				}
			}
		}
		if(air == 3 * 3 * 5)
		{
			return true;
		}
		return false;
	}
	
	public int getCoilHeat() {
		return coilHeat;
	}
	
	public void setCoilHeat(int coilHeat) {
		this.coilHeat = coilHeat;
	}
	
	public void getMaxWallHeat()
	{
		if(bricks != null && bricks.size() == 20)
		{
			for(int i = 0;i < bricks.size();i++)
			{
				int heat = bricks.get(0).getMaxHeat();
				addHeat(heat);
			}
		}
	}
	
	@Override
	public int getCoilMaxHeat() {
		if(coils != null && coils.size() == 6)
		{
			int heat = coils.get(0).getProducibleHeat() + coils.get(1).getProducibleHeat() + coils.get(2).getProducibleHeat() + coils.get(3).getProducibleHeat() + coils.get(4).getProducibleHeat() + coils.get(5).getProducibleHeat();
			return heat / coils.size();
		}
		else
		{
			return 0;
		}
	}
	
	public boolean hasCoils()
	{
		if(coils.size() == 6)
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
