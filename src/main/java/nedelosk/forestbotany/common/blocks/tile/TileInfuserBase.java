package nedelosk.forestbotany.common.blocks.tile;

import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.common.fluid.FluidTankNedelosk;
import nedelosk.forestbotany.common.soil.SoilType;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.common.Loader;

public abstract class TileInfuserBase extends TileBaseInventory implements IEnergyHandler, IFluidHandler, IInfuserChamber {

	protected FluidTankNedelosk tank = new FluidTankNedelosk(16000);
	protected FluidTankNedelosk tankWater = new FluidTankNedelosk(8000);
	protected EnergyStorage storage = new EnergyStorage(32000);
	public ForgeDirection waterInput;
	private int timer;
	protected SoilType soil;
	protected boolean hasSoil;
	
	public TileInfuserBase(int slots) {
		super(slots);
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		tank.writeToNBT(nbt);
		if(waterInput != null)
		{
		nbt.setInteger("WaterInput", waterInput.ordinal());
		}
		NBTTagCompound nbtTank = new NBTTagCompound();
		tankWater.writeToNBT(nbtTank);
		nbt.setTag("TankWater", nbtTank);
		nbt.setBoolean("HasSoil", hasSoil);
		if(hasSoil)
		nbt.setInteger("Soil", soil.ordinal());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		tank.readFromNBT(nbt);
		if(nbt.hasKey("WaterInput"))
		{
		waterInput = ForgeDirection.values()[nbt.getInteger("WaterInput")];
		}
		if(nbt.hasKey("TankWater"))
		{
		NBTTagCompound nbtTank = nbt.getCompoundTag("TankWater");
		tankWater.readFromNBT(nbtTank);
		}
		hasSoil = nbt.getBoolean("HasSoil");
		if(hasSoil)
		soil = SoilType.values()[nbt.getInteger("Soil")];
	}
	
	public IEnergyStorage getStorage() {
		return storage;
	}
	
	@Override
	public void updateServer() {
		updateSoil();
	}
	
	public SoilType getSoil() {
		return soil;
	}
	
	public void updateSoil()
	{
		if(getStackInSlot(1) != null)
		{
			if(Block.getBlockFromItem(getStackInSlot(1).getItem()) != null)
			{
			for(SoilType type : SoilType.values())
			{
				if(type.getMod() == null || Loader.isModLoaded(type.getMod().modID))
				{
				if(Block.getBlockFromItem(getStackInSlot(1).getItem()) == type.getSoil() && getStackInSlot(1).getItemDamage() == type.getSoilMeta())
				{
					soil = type;
					hasSoil = true;
				}
				}
			}
			}
			else
			{
				if(soil != null || hasSoil)
				{
					soil = null;
				hasSoil = false;
				}
			}
		}
		else
		{
			if(soil != null || hasSoil)
			{
				soil = null;
			hasSoil = false;
			}
		}
	}

	public boolean hasSoil()
	{
		return hasSoil;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,boolean simulate) {
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
	
	public FluidTankNedelosk getTank() {
		return tank;
	}
	
	public TileInfuserBase getBaseTile()
	{
		return (TileInfuserBase) this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
	}
		
	public ForgeDirection getNutrientInput() {
		return waterInput;
	}
	
	public FluidTankNedelosk getTankWater() {
		return tankWater;
	}
}
