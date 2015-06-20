package nedelosk.forestday.common.machines.base.fluid.heater;

import nedelosk.forestday.client.machines.base.gui.GuiFluidHeater;
import nedelosk.forestday.common.machines.base.tile.TileHeatBase;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileFluidHeater extends TileHeatBase implements IFluidHandler {

	private FluidTankNedelosk tankOutput = new FluidTankNedelosk(16000);
	private FluidTankNedelosk tankInput = new FluidTankNedelosk(16000);
	private FluidTankNedelosk tankInput2 = new FluidTankNedelosk(16000);
	
	private FluidStack output;
	private ItemStack outputItem;
	
	public TileFluidHeater() {
		super(2);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	if(this.heat > 200)
    	{
    	if(burnTime < burnTimeTotal)
    	{
    		burnTime++;
    	}
    	else
    	{
    		if(!tankOutput.isFull())
    		{
    			if(output != null)
    			{
    				tankOutput.fill(output, true);
    				output = null;
    				if(outputItem != null)
    					if(addToOutput(outputItem, 1, 2))
    						outputItem = null;
    			}
    			else if(outputItem != null)
    			{
    				if(addToOutput(outputItem, 1, 2)){
    					outputItem = null;
    				}
    			}
    			else
    			{
    				ItemStack inputItem = getStackInSlot(0);
    				FluidStack fluidTank0 = tankInput.getFluid();
    				FluidStack fluidTank1 = tankInput2.getFluid();
    				
    				if(fluidTank0 != null)
    				{
    					FluidHeaterRecipe recipe = FluidHeaterRecipeManager.getRecipe(fluidTank0, fluidTank1, inputItem);
    					if(recipe != null)
    					{
    						if(fluidTank0.amount >=  recipe.getInput().amount)
    						{
    							if(recipe.getInput2() == null || fluidTank1.amount >= recipe.getInput2().amount)
    							{
    							tankInput.drain(recipe.getInput(), true);
    							if(recipe.getInputItem() != null)
    							{
    								decrStackSize(0, recipe.getInputItem().stackSize);
    							}
    							if(recipe.getInput2() != null)
    							{
    							tankInput2.drain(recipe.getInput2(), true);
    							}
    							if(recipe.getOutput() != null)
    							{
    							output = recipe.getOutput();
    							}
     							if(recipe.getOutput() != null)
    							{
    							outputItem = recipe.getOutputItem();
    							}
    							burnTime = 0;
    							burnTimeTotal = recipe.getBurnTime() / (tier - 1);
    							}
    						}
    					}
    				}
    				else if(inputItem != null)
    				{
    					FluidHeaterRecipe recipe = FluidHeaterRecipeManager.getRecipe(fluidTank0, fluidTank1, inputItem);
    					if(recipe != null)
    					{
    						if(inputItem.stackSize >=  recipe.getInputItem().stackSize)
    						{
    							if(recipe.getInput2() == null || fluidTank1.amount >= recipe.getInput2().amount)
    							{
    							decrStackSize(0, recipe.getInputItem().stackSize);
    							if(recipe.getInput2() != null)
    							{
    							tankInput2.drain(recipe.getInput2(), true);
    							}
    							if(recipe.getOutput() != null)
    							{
    							output = recipe.getOutput();
    							}
     							if(recipe.getOutput() != null)
    							{
    							outputItem = recipe.getOutputItem();
    							}
    							burnTime = 0;
    							burnTimeTotal = recipe.getBurnTime();
    							}
    						}
    					}
    				}
    			}
    		}
    		
    	}
    	}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerFluidHeater(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiFluidHeater(inventory, this);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		for(FluidHeaterRecipe recipe : FluidHeaterRecipeManager.instance.getRecipes())
		{
			if(recipe.getInput().getFluid() == resource.getFluid() && !tankInput.isFull())
			{
				return tankInput.fill(resource, doFill);
			}
			if(recipe.getInput2().getFluid() == resource.getFluid() && !tankInput.isFull())
			{
				return tankInput2.fill(resource, doFill);
			}
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tankOutput.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tankOutput.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tankInput.getInfo(), tankInput2.getInfo(), tankOutput.getInfo() };
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
	      if(tankInput.getFluidAmount() > 0) {
		        NBTTagCompound tankRoot = new NBTTagCompound();
		        tankInput.writeToNBT(tankRoot);
		        nbt.setTag("tankInput", tankRoot);
		      }
	      
	      if(tankInput2.getFluidAmount() > 0) {
		        NBTTagCompound tankRoot = new NBTTagCompound();
		        tankInput2.writeToNBT(tankRoot);
		        nbt.setTag("tankInput2", tankRoot);
		      }
	      
	      if(tankOutput.getFluidAmount() > 0) {
		        NBTTagCompound tankRoot = new NBTTagCompound();
		        tankOutput.writeToNBT(tankRoot);
		        nbt.setTag("tankOutput", tankRoot);
		      }
	      
	      nbt.setInteger("BurnTime", this.burnTime);
	      nbt.setInteger("BurnTimeTotal", this.burnTimeTotal);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt); 
	    if(nbt.hasKey("tankInput")) {
	        NBTTagCompound tankRoot = (NBTTagCompound) nbt.getTag("tankInput");
	        if(tankRoot != null) {
	          tankInput.readFromNBT(tankRoot);
	        } else {
	        	tankInput.setFluid(null);
	        }
	      } else {
	    	  tankInput.setFluid(null);
	      }
	    
	    if(nbt.hasKey("tankInput2")) {
	        NBTTagCompound tankRoot = (NBTTagCompound) nbt.getTag("tankInput2");
	        if(tankRoot != null) {
	          tankInput2.readFromNBT(tankRoot);
	        } else {
	        	tankInput2.setFluid(null);
	        }
	      } else {
	    	  tankInput2.setFluid(null);
	      }
	    
	    if(nbt.hasKey("tankOutput")) {
	        NBTTagCompound tankRoot = (NBTTagCompound) nbt.getTag("tankOutput");
	        if(tankRoot != null) {
	          tankOutput.readFromNBT(tankRoot);
	        } else {
	        	tankOutput.setFluid(null);
	        }
	      } else {
	    	  tankOutput.setFluid(null);
	      }
	    
	    this.burnTime = nbt.getInteger("BurnTime");
	    this.burnTimeTotal = nbt.getInteger("BurnTimeTotal");
	}
	
	public FluidTankNedelosk getTankInput() {
		return tankInput;
	}
	
	public FluidTankNedelosk getTankInput2() {
		return tankInput2;
	}
	
	public FluidTankNedelosk getTankOutput() {
		return tankOutput;
	}
	
	public int getBurnTime() {
		return burnTime;
	}
	
	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}

	@Override
	public String getMachineName() {
		return "heater.fluid";
	}

	@Override
	public void updateClient() {
		
	}

}
