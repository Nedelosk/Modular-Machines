package nedelosk.forestday.common.machines.base.furnace.alloysmelter;

import nedelosk.forestday.client.machines.base.gui.GuiAlloySmelter;
import nedelosk.forestday.common.machines.base.tile.TileHeatBase;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAlloySmelter extends TileHeatBase implements IEnergyHandler, IFluidHandler {

	protected EnergyStorage storage;
	public FluidTankNedelosk tank;
	public ItemStack[] output = new ItemStack[3];
	
	public TileAlloySmelter() {
		super(7);
		storage = new EnergyStorage(getCapacityFromTier());
		tank = new FluidTankNedelosk(1);
	}
	
	private int getCapacityFromTier()
	{
		if(tier > 2)
		{
			return 160000 * tier;
		}
		return 0;
	}

	@Override
	public String getMachineName() {
		return "alloy_smelter";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerAlloySmelter(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiAlloySmelter(this, inventory);
	}
	
	public int getHeat() {
		return heat;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		if(burnTime >= burnTimeTotal / ((tier - 2 > 0) ? tier - 2 : 1))
		{
			if(heat >= 250)
			{
				ItemStack input = getStackInSlot(0);
			}
			burnTime = 0;
		}
		else
		{
			burnTime++;
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(tier == 1 || tier == 2)
			return false;
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList nbtTagList = new NBTTagList();
		for(int i = 0; i< this.getSizeInventory(); i++){
			if (this.output[i] != null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("item", (byte)i);
				this.output[i].writeToNBT(item);
				nbtTagList.appendTag(item);
			}
		}
		nbt.setTag("output", nbtTagList);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList nbtTagList = nbt.getTagList("output", 10);
		this.output = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < nbtTagList.tagCount(); i++){
			NBTTagCompound item = nbtTagList.getCompoundTagAt(i);
			byte itemLocation = item.getByte("item");
			if (itemLocation >= 0 && itemLocation < this.getSizeInventory()){
				this.output[itemLocation] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return null;
	}

}
