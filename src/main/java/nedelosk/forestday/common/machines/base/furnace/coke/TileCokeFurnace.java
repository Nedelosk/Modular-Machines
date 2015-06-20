package nedelosk.forestday.common.machines.base.furnace.coke;

import nedelosk.forestday.client.machines.base.gui.GuiCokeFurnace;
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

public class TileCokeFurnace extends TileHeatBase implements IFluidHandler {

	private FluidTankNedelosk tank = new FluidTankNedelosk(16000);
	
	private ItemStack[] output = new ItemStack[3];
	private FluidStack outputFluid;
	
	public TileCokeFurnace() {
		super(4);
	}
	
	private int burnTime;
	
	@Override
	public void updateServer() {
		super.updateServer();
    	
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	if(this.heat > 150)
    	{
		if(burnTime < 1600)
		{
			burnTime++;
		}
		else
		{
			if(output[0] != null)
			{
				if(addToOutput(output[0]))
				{
					output[0] = null;
				}
			}
			if(output[1] != null)
			{
				if(addToOutput(output[1]))
				{
					output[1] = null;
				}
			}
			if(output[2] != null)
			{
				if(addToOutput(output[2]))
				{
					output[2] = null;
				}
			}
			if(outputFluid != null)
			{
				if(!tank.isFull())
				{
					tank.fill(outputFluid, true);
					outputFluid = null;
				}
			}
			if(output[0] == null && output[1] == null && output[2] == null && outputFluid == null)
			{
			ItemStack input = this.getStackInSlot(0);
			if(input != null)
			{
				CokeFurnaceRecipe recipe = CokeFurnaceRecipeManager.getRecipe(input);
				if(recipe != null)
				{
					if(input.stackSize >= recipe.getInput().stackSize)
					{
						this.decrStackSize(0, recipe.getInput().stackSize);
						if(recipe.getOutput() != null)
						{
							output[0] = recipe.getOutput();
						}
						if(recipe.getOutput1() != null)
						{
							output[1] = recipe.getOutput();
						}
						if(recipe.getOutput2() != null)
						{
							output[2] = recipe.getOutput();
						}
						if(recipe.getOutputFluid() != null)
						{
							outputFluid = recipe.getOutputFluid();
						}
						burnTime = 0;
					}
				}
			}
			}
		}
    	}
	}
	
	private boolean addToOutput(ItemStack output) {
		if (output == null) return true;
		
		for(int i = 1; i < this.getSizeInventory(); i++){
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

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerCokeFurnace(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiCokeFurnace(inventory, this);
	}
	
	public FluidTankNedelosk getTank() {
		return tank;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
	      if(tank.getFluidAmount() > 0) {
		        NBTTagCompound tankRoot = new NBTTagCompound();
		        tank.writeToNBT(tankRoot);
		        nbt.setTag("tank", tankRoot);
		      }
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt); 
	    if(nbt.hasKey("tank")) {
	        NBTTagCompound tankRoot = (NBTTagCompound) nbt.getTag("tank");
	        if(tankRoot != null) {
	          tank.readFromNBT(tankRoot);
	        } else {
	        	tank.setFluid(null);
	        }
	      } else {
	    	  tank.setFluid(null);
	      }
	}

	@Override
	public String getMachineName() {
		return "furnace.coke";
	}

	@Override
	public void updateClient() {
		
	}

}
