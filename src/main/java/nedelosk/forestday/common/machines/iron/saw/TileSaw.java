package nedelosk.forestday.common.machines.iron.saw;

import nedelosk.forestday.client.machines.iron.gui.GuiSaw;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSaw extends TileBaseInventory implements IFluidHandler{
	
	private FluidTankNedelosk tank = new FluidTankNedelosk(16000);
	
	public TileSaw() {
		super(6);
		this.burnTimeTotal = ForestdayConfig.sawBurnTime;
	}
	
	private int timer;
	
	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerSaw(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiSaw(inventory, this);
	}
	
    private ItemStack currentOutput;
    
    private int sawdustPile;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if (this.currentOutput != null){
			NBTTagList nbtTagList = new NBTTagList();
			NBTTagCompound item = new NBTTagCompound();
			this.currentOutput.writeToNBT(item);
			nbtTagList.appendTag(item);
			nbt.setTag("currentOutput", nbtTagList);
		}
		
		tank.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("currentOutput")){
			NBTTagList nbtTagList = nbt.getTagList("currentOutput", 10);
			NBTTagCompound item = nbtTagList.getCompoundTagAt(0);
			this.currentOutput = ItemStack.loadItemStackFromNBT(item);
		}else{
			this.currentOutput = null;
		}
		
		tank.readFromNBT(nbt);
		
	}

	@Override
	public int getScaledProcess(int maxWidth) {
		return (this.burnTime > 0) ? (this.burnTime * maxWidth) / this.burnTimeTotal : 0;
	}

	@Override
	public String getMachineTileName() {
		return "machine.saw";
	}
	
	public FluidTankNedelosk getTank() {
		return tank;
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
    	
		TileEntity tile = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if(timer == 35)
		{
		if(tile != null)
		{
			if(tile instanceof TileBusFluid)
			{
				FluidTankNedelosk busTank = ((TileBusFluid)tile).getTank();
				if(busTank.getFluid() != null)
				{
				if(busTank.getFluid().getFluid() == FluidRegistry.getFluid("lubricant"))
				{
					if(!busTank.isEmpty() && busTank.getFluidAmount() >= 100 && !tank.isFull() && tank.getFluidAmount() <= 15000)
					{
			    		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						busTank.drain(100, true);
						tank.fill(new FluidStack(FluidRegistry.getFluid("lubricant"), 100), true);
					}
				}
				}
			}
		}
		timer = 0;
		}
		else
		{
			timer++;
		}
		
    	if (this.burnTime > 0){
    		this.burnTime--;
    		this.sawdustPile++;
    		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		if(this.sawdustPile >= 35)
    		{
    			addToOutput(new ItemStack(ForestdayItemRegistry.nature, 1, 1), 1, 3);
    			sawdustPile = 0;
    		}
    	}else{
    		if (this.currentOutput != null){
    			if (this.addToOutput(this.currentOutput, 3, 5)){
    				this.currentOutput = null;
    			}
    	}else{
    		    ItemStack itemStackInputSlot = this.getStackInSlot(0);
	    		if (itemStackInputSlot != null && tank.getFluid() != null && tank.getFluid().getFluid() == FluidRegistry.getFluid("lubricant")){
	    			SawRecipe recipe = SawRecipeManager.getRecipe(itemStackInputSlot);
	    			if (recipe != null){
	    				if (itemStackInputSlot.stackSize >= recipe.getInput().stackSize){
	    					this.decrStackSize(0, recipe.getInput().stackSize);
	    					this.currentOutput = recipe.getOutput();
	    					tank.drain(200, true);
	    					this.burnTime = burnTimeTotal;
	    				}
	    			}
	    		}
    		}
    	}
	}

	@Override
	public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
		return true;
	}

	@Override
	public boolean canFill(ForgeDirection arg0, Fluid arg1) {
		return true;
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection arg0, int resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public int fill(ForgeDirection arg0, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
		return new FluidTankInfo[] { tank.getInfo() };
	}
}