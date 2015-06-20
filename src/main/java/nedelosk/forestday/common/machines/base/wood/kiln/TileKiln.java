package nedelosk.forestday.common.machines.base.wood.kiln;

import nedelosk.forestday.client.machines.base.gui.GuiKiln;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.core.Forestday;
import nedelosk.forestday.common.machines.base.tile.TileHeatBase;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseFluid;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;


public class TileKiln extends TileHeatBase implements IFluidHandler {
	
	private int heatTimer;
	
    private ItemStack currentOutput1;
    private ItemStack currentOutput2;
    
    public FluidTankNedelosk tankTar = new FluidTankNedelosk(16000);
    public FluidTankNedelosk tankResin = new FluidTankNedelosk(16000);
	
	public TileKiln() {
		super(4);
		burnTimeTotal = ForestdayConfig.kilnBurnTime;
	}
	
	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerKiln(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiKiln(inventory, this);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
			return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		switch (from) {
		case UP:
			return tankTar.drain(resource, doDrain);
		case DOWN:
			return tankResin.drain(resource, doDrain);
		default:
			return null;
		}
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		switch (from) {
		case UP:
			return tankTar.drain(maxDrain, doDrain);
		case DOWN:
			return tankResin.drain(maxDrain, doDrain);
		default:
			return null;
		}
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
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (this.currentOutput1 != null){
			NBTTagList nbtTagList = new NBTTagList();
			NBTTagCompound item = new NBTTagCompound();
			this.currentOutput1.writeToNBT(item);
			nbtTagList.appendTag(item);
			nbt.setTag("currentOutput1", nbtTagList);
		}
		if (this.currentOutput2 != null){
			NBTTagList nbtTagList = new NBTTagList();
			NBTTagCompound item = new NBTTagCompound();
			this.currentOutput2.writeToNBT(item);
			nbtTagList.appendTag(item);
			nbt.setTag("currentOutput2", nbtTagList);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("currentOutput1")){
			NBTTagList nbtTagList = nbt.getTagList("currentOutput1", 10);
			NBTTagCompound item = nbtTagList.getCompoundTagAt(0);
			this.currentOutput1 = ItemStack.loadItemStackFromNBT(item);
		}else{
			this.currentOutput1 = null;
		}
		if (nbt.hasKey("currentOutput2")){
			NBTTagList nbtTagList = nbt.getTagList("currentOutput2", 10);
			NBTTagCompound item = nbtTagList.getCompoundTagAt(0);
			this.currentOutput2 = ItemStack.loadItemStackFromNBT(item);
		}else{
			this.currentOutput2 = null;
		}
	      
	}
	
	@Override
	public int getScaledProcess(int maxWidth) {
		return (this.burnTime > 0) ? (this.burnTime * maxWidth) / this.burnTimeTotal : 0 ;
	}
	
	public int getScaledHeat(int maxWidth) {
		return (this.heat > 0) ? (this.heat * maxWidth) / ForestdayConfig.kilnMinHeat : 0 ;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		super.updateServer();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		int lavaSources = 0;
    	if(!this.tankTar.isFull() && !this.tankResin.isFull())
    	{
    		
    	if(heat >= ForestdayConfig.kilnMinHeat)
    	{
    	if(burnTime < burnTimeTotal)
    	{
    		burnTime++;
	}else{
    		if (this.currentOutput1 != null){
    			if (this.addToOutput(this.currentOutput1, 2, 4) && this.addToOutput(this.currentOutput2, 2, 4)){
    				this.currentOutput1 = null;
    				this.currentOutput2 = null;
    				tankTar.fill(new FluidStack(FluidRegistry.getFluid("tar"), 300), true);
    				tankResin.fill(new FluidStack(FluidRegistry.getFluid("resin"), 150), true);
    			}
    			}else{
		    ItemStack itemStackInputSlot1 = this.getStackInSlot(0);
    		ItemStack itemStackInputSlot2 = this.getStackInSlot(1);
    		if(itemStackInputSlot1 != null && itemStackInputSlot2 != null)
    		{
    			KilnRecipe recipe = KilnRecipeManager.getRecipe(itemStackInputSlot1, itemStackInputSlot2);
    			if(recipe != null)
    			{
    				if (itemStackInputSlot1.stackSize >= recipe.getInput1().stackSize && itemStackInputSlot2.stackSize >= recipe.getInput2().stackSize){
    					this.decrStackSize(0, recipe.getInput1().stackSize);
    					this.decrStackSize(1, recipe.getInput2().stackSize);
        				this.currentOutput1 = recipe.getOutput1();
        				this.currentOutput2 = recipe.getOutput2();
        				this.burnTime = 0;
    				}
    			}
    		}
    	}
    		
    	}
	}
	}
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{ tankTar.getInfo(), tankResin.getInfo()};
	}

	@Override
	public String getMachineName() {
		return "kiln";
	}

}
