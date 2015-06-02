package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.airheater.crafting.AirHeaterRecipe;
import nedelosk.forestday.structure.airheater.crafting.AirHeaterRecipeManager;
import nedelosk.forestday.structure.base.blocks.BlockMetal;
import nedelosk.forestday.structure.base.gui.GuiBusFluid;
import nedelosk.forestday.structure.base.gui.GuiBusItem;
import nedelosk.forestday.structure.base.gui.container.ContainerBusFluid;
import nedelosk.forestday.structure.base.gui.container.ContainerBusItem;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipe;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipeManager;
import nedelosk.nedeloskcore.common.fluids.FluidHelper;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBusFluid extends TileBus implements IFluidHandler{
	
	private FluidTankNedelosk tank = new FluidTankNedelosk(16000);
	
	public TileBusFluid() {
		super(5000, "busFluid");
	}
	
	public TileBusFluid(int heat, String uid, IIcon icon) {
		super(heat, "busFluid" + uid);
		this.overayIcon = icon;
	}

	private IIcon overayIcon;
	private int timer;
	
	public IIcon getOverlayTexture()
	{
		return overayIcon;
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(mode == Mode.Input)
		{
			/*if(structure == Structures.Air_Heater)
			{
				for(AirHeaterRecipe recipe : AirHeaterRecipeManager.getRecipe())
				{
					if(recipe.getInput().getFluid() == resource.getFluid())
					{*/
					return tank.fill(resource, doFill);
					/*}
				}
			}
			if(structure == Structures.Blast_Furnace)
			{
					if(FluidRegistry.getFluid("air_hot") == resource.getFluid())
					{*/
					//return tank.fill(resource, doFill);
					/*}
			}*/
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		if(timer == 90)
		{
			if(tank.getFluid() != null)
			{
		        FluidHelper.fillContainers(this, this, 2, 3, tank.getFluid().getFluid());
			}
	        FluidHelper.drainContainers(this, this, 0, 1);
        timer = 0;
		}
		else
		{
			timer++;
		}
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(mode == Mode.Input)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(mode == Mode.Output)
		{
			return true;
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerBusFluid(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiBusFluid(inventory, this);
	}
	
	public FluidTankNedelosk getTank() {
		return tank;
	}

	@Override
	public String getMachineTileName() {
		return "structure.bus.fluid";
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}
	
}
