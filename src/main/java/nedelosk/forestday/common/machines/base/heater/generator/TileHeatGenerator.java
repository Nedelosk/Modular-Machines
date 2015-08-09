package nedelosk.forestday.common.machines.base.heater.generator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.crafting.BurningMode;
import nedelosk.forestday.client.machines.base.gui.GuiHeatGenerator;
import nedelosk.forestday.common.config.ForestdayConfig;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileHeatGenerator extends nedelosk.forestday.common.machines.base.tile.TileHeatGenerator {
	
	public TileHeatGenerator() {
		super(6);
		burnTimeTotal = ForestdayConfig.generatorHeatBurnTime;
	}
	
	private ItemStack[] produceStacks = new ItemStack[3];
	private BurningMode[] modes = new BurningMode[3];
	private int[] burnTimesTotal = new int[3];
	private int[] burnTimes = new int[3];
	private int[] heatModifiers = new int[3];
	private boolean[] isActives = new boolean[3];
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("Active0", this.isActives[0]);
		nbt.setBoolean("Active1", this.isActives[1]);
		nbt.setBoolean("Active2", this.isActives[2]);
		
		if(modes[0] != null)
		{
		nbt.setInteger("Mode0", modes[0].ordinal());
		}
		if(modes[1] != null)
		{
		nbt.setInteger("Mode1", modes[1].ordinal());
		}
		if(modes[2] != null)
		{
		nbt.setInteger("Mode2", modes[2].ordinal());
		}
		
		nbt.setInteger("BurnTime0", this.burnTimes[0]);
		nbt.setInteger("BurnTime1", this.burnTimes[1]);
		nbt.setInteger("BurnTime2", this.burnTimes[2]);
		
		nbt.setInteger("BurningTimeTotal0", this.burnTimesTotal[0]);
		nbt.setInteger("BurningTimeTotal1", this.burnTimesTotal[1]);
		nbt.setInteger("BurningTimeTotal2", this.burnTimesTotal[2]);
		
		nbt.setInteger("HeatM0", heatModifiers[0]);
		nbt.setInteger("HeatM1", heatModifiers[1]);
		nbt.setInteger("HeatM2", heatModifiers[2]);
		
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.burnTimes[0] = nbt.getInteger("BurnTime0");
		this.burnTimes[1] = nbt.getInteger("BurnTime1");
		this.burnTimes[2] = nbt.getInteger("BurnTime2");
		
		this.heatModifiers[0] = nbt.getInteger("HeatM0");
		this.heatModifiers[1] = nbt.getInteger("HeatM1");
		this.heatModifiers[2] = nbt.getInteger("HeatM2");
		
		this.modes[0] = BurningMode.values()[nbt.getInteger("Mode0")]; 
		this.modes[1] = BurningMode.values()[nbt.getInteger("Mode1")];
		this.modes[2] = BurningMode.values()[nbt.getInteger("Mode2")];
		
		this.burnTimesTotal[0] = nbt.getInteger("BurningTimeTotal0");
		this.burnTimesTotal[1] = nbt.getInteger("BurningTimeTotal1");
		this.burnTimesTotal[2] = nbt.getInteger("BurningTimeTotal2");
		
		this.isActives[0] = nbt.getBoolean("Active0");
		this.isActives[1] = nbt.getBoolean("Active1");
		this.isActives[2] = nbt.getBoolean("Active2");
		
	}
	
	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerHeatGenerator(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiHeatGenerator(inventory, this);
	}
	
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled0(int p_145955_1_)
    {
        return this.burnTimes[0] * p_145955_1_ / this.burnTimesTotal[0];
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled1(int p_145955_1_)
    {
        return this.burnTimes[1] * p_145955_1_ / this.burnTimesTotal[1];
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled2(int p_145955_1_)
    {
        return this.burnTimes[2] * p_145955_1_ / this.burnTimesTotal[2];
    }
    
    public boolean isActive0() {
		return this.isActives[0];
	}
    
    public boolean isActive1() {
		return this.isActives[1];
	}
    
    public boolean isActive2() {
		return this.isActives[2];
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		super.updateServer();
		if(burnTime < burnTimeTotal)
		{
			burnTime++;
		}
	}


	@Override
	public String getMachineName() {
		return "generator.heat";
	}


	@Override
	public void generateHeat() {
    	ItemStack stackSlot0 = this.getStackInSlot(0);
    	ItemStack stackSlot1 = this.getStackInSlot(1);
    	ItemStack stackSlot2 = this.getStackInSlot(2);
    	
    	if(isActives[0] || isActives[1]|| isActives[2])
    		isWorking = true;
    	else
    		isWorking = false;
    	
    	if(isActives[0] != true)
    	{
    	if(stackSlot0 != null)
    	{
    		HeatGeneratorRecipe recipe = HeatGeneratorRecipeManager.getRecipe(stackSlot0);
    		if(recipe != null)
    		{
    			if(stackSlot0.stackSize >= 1)
    			{
    				this.decrStackSize(0, 1);
    				burnTimesTotal[0] = recipe.getBurnTime();
    				produceStacks[0] = recipe.getOutput();
    				modes[0] = recipe.getMode();
    				isActives[0] = true;
    			}
    		}
    	}
    	}
    	else
    	{
    		if(burnTimes[0] < burnTimesTotal[0])
    		{
    			burnTimes[0]++;
    		}
    		else
    		{
    			addToOutput(produceStacks[0], 3, 6);
    			burnTimes[0] = 0;
    			burnTimesTotal[0] = 0;
    			modes[0] = null;
    			isActives[0] = false;
    		}
    	}
    	
    	if(isActives[1] != true)
    	{
    	if(stackSlot1 != null)
    	{
    		HeatGeneratorRecipe recipe = HeatGeneratorRecipeManager.getRecipe(stackSlot1);
    		if(recipe != null)
    		{
    			if(stackSlot1.stackSize >= 1)
    			{
    				this.decrStackSize(1, 1);
    				burnTimesTotal[1] = recipe.getBurnTime();
    				produceStacks[1] = recipe.getOutput();
    				modes[1] = recipe.getMode();
    				isActives[1] = true;
    			}
    		}
    	}
    	}
    	else
    	{
    		if(burnTimes[1] < burnTimesTotal[1])
    		{
    			burnTimes[1]++;
    		}
    		else
    		{
    			addToOutput(produceStacks[1], 3, 6);
    			burnTimes[1] = 0;
    			burnTimesTotal[1] = 0;
    			modes[1] = null;
    			isActives[1] = false;
    		}
    	}
    	
    	if(isActives[2] != true)
    	{
    	if(stackSlot2 != null)
    	{
    		HeatGeneratorRecipe recipe = HeatGeneratorRecipeManager.getRecipe(stackSlot2);
    		if(recipe != null)
    		{
    			if(stackSlot2.stackSize >= 1)
    			{
    				this.decrStackSize(2, 1);
    				burnTimesTotal[2] = recipe.getBurnTime();
    				produceStacks[2] = recipe.getOutput();
    				modes[2] = recipe.getMode();
    				isActives[2] = true;
    			}
    		}
    	}
    	}
    	else
    	{
    		if(burnTimes[2] < burnTimesTotal[2])
    		{
    			burnTimes[2]++;
    		}
    		else
    		{
    			addToOutput(produceStacks[2], 3, 6);
    			burnTimes[2] = 0;
    			burnTimesTotal[2] = 0;
    			isActives[2] = false;
    			modes[2] = null;
    		}
    	}
    	
    	for(int i = 0; i < 3;i++)
    	{
    		BurningMode mod = modes[i];
    		int modifier;
    		if(mod == null)
    		{
    			modifier = 1;
    		}
    		else if(mod == BurningMode.Wood)
    		{
    			modifier = 4;
    		}
    		else if(mod == BurningMode.Charcoal)
    		{
    			modifier = 5;
    		}
    		else if(mod == BurningMode.Coal)
    		{
    			modifier = 6;
    		}
    		else if(mod == BurningMode.Peat)
    		{
    			modifier = 7;
    		}
    		else if(mod == BurningMode.Coke)
    		{
    			modifier = 8;
    		}
    		else if(mod == BurningMode.Other)
    		{
    			modifier = 3;
    		}
    		else
    		{
    			modifier = 3;
    		}
    		heatModifiers[i] = modifier;
    		int a = 0;
    	}
		heatMax = heatModifiers[0] * heatModifiers[1] * heatModifiers[2];
		if(heat < heatMax)
		{
			heat++;
		}
		else if(heat > heatMax)
		{
			heat-=3;
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		burnTime = 0;
	}

}
