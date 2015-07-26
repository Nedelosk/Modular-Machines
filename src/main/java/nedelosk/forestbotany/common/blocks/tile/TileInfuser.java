package nedelosk.forestbotany.common.blocks.tile;

import nedelosk.forestbotany.api.botany.IInfuser;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.botany.IInfuserLogic;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.client.core.gui.GuiInfuser;
import nedelosk.forestbotany.common.botany.InfuserLogic;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.inventory.ContainerInfuser;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileInfuser extends TileInfuserBase implements IInfuser {

	public TileInfuserChamber[] chambers = new TileInfuserChamber[2];
	public IInfuserLogic logic;
	
	public TileInfuser() {
		super(4);
		logic = new InfuserLogic(this);
	}

	@Override
	public String getMachineTileName() {
		return "infuser";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if(getBlockMetadata() == 0)
		{
			return new ContainerInfuser(this, inventory);
		}
		if(getBlockMetadata() == 1)
		{
			return ((TileInfuser)this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getContainer(inventory);
		}
		return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		if(getBlockMetadata() == 0)
		{
			return new GuiInfuser(this, inventory);
		}
		if(getBlockMetadata() == 1)
		{
			return ((TileInfuser)this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getGUIContainer(inventory);
		}
		return null;
	}
	
	public TileInfuserBase getChamberWithGender(IAlleleGender gender)
	{
		if(chambers[0] != null && chambers[1] != null)
		{
		if(chambers[0].getStackInSlot(0) != null && PlantManager.getPlantManager(chambers[0].getStackInSlot(0)) != null && PlantManager.getPlantManager(chambers[0].getStackInSlot(0)).hasGender(gender, chambers[0].getStackInSlot(0)))
		{
			return chambers[0];
		}
		if(chambers[1].getStackInSlot(0) != null && PlantManager.getPlantManager(chambers[1].getStackInSlot(0)) != null && PlantManager.getPlantManager(chambers[1].getStackInSlot(0)).hasGender(gender, chambers[1].getStackInSlot(0)))
		{
			return chambers[1];
		}
		}
		return null;
	}

	@Override
	public void updateClient() {
		if(getBlockMetadata() == 0)
		{
			
		}
	}

	@Override
	public void updateServer() {

		if(getBlockMetadata() == 0)
		{
			super.updateServer();
			logic.update();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			
			onNeighborBlockChange(worldObj, xCoord, yCoord, zCoord, getBlockType());
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(getBlockMetadata() == 0)
		{
			
			if(chambers[0] != null)
			{
				for(TileInfuserChamber chamber: chambers)
				{
					if(chamber == null)
					{
						testChamber1(world, x, y, z);
						return;
					}
					if(worldObj.getTileEntity(chamber.xCoord, chamber.yCoord, chamber.zCoord) != null && worldObj.getTileEntity(chamber.xCoord, chamber.yCoord, chamber.zCoord) instanceof TileInfuserChamber && worldObj.getTileEntity(chamber.xCoord, chamber.yCoord, chamber.zCoord) == chamber)
					{
					}
					else
					{
						chamber = null;
					}
				}
				
			}
			else
			{
				testChamber1(world, x, y, z);
			}
		}
	}
	
	public void testChamber1(World world, int x, int y, int z)
	{
		if(world.getTileEntity(x + 1, y, z) != null && world.getTileEntity(x + 1, y, z) instanceof TileInfuserChamber)
		{
			if(chambers[0] == null)
			{
			chambers[0] = (TileInfuserChamber)world.getTileEntity(x + 1, y, z);
			}
			else if(chambers[1] == null && (TileInfuserChamber)world.getTileEntity(x + 1, y, z) != chambers[0])
			{
			chambers[1] = (TileInfuserChamber)world.getTileEntity(x + 1, y, z);
			}
			else
			{
				testChamber3(world, x, y, z);
			}
		}
		else
		{
			testChamber2(world, x, y, z);
		}
	}
	
	public void testChamber2(World world, int x, int y, int z)
	{
		if(world.getTileEntity(x - 1, y, z) != null && world.getTileEntity(x - 1, y, z) instanceof TileInfuserChamber)
		{
			if(chambers[0] == null)
			{
			chambers[0] = (TileInfuserChamber)world.getTileEntity(x - 1, y, z);
			}
			else if(chambers[1] == null && (TileInfuserChamber)world.getTileEntity(x - 1, y, z) != chambers[0])
			{
			chambers[1] = (TileInfuserChamber)world.getTileEntity(x - 1, y, z);
			}
			else
			{
				testChamber3(world, x, y, z);
			}
		}
		else
		{
			testChamber3(world, x, y, z);
		}
	}
	
	public void testChamber3(World world, int x, int y, int z)
	{
		if(world.getTileEntity(x, y, z + 1) != null && world.getTileEntity(x, y, z + 1) instanceof TileInfuserChamber)
		{
			if(chambers[0] == null)
			{
			chambers[0] = (TileInfuserChamber)world.getTileEntity(x, y, z + 1);
			}
			else if(chambers[1] == null && (TileInfuserChamber)world.getTileEntity(x, y, z + 1) != chambers[0])
			{
			chambers[1] = (TileInfuserChamber)world.getTileEntity(x, y, z + 1);
			}
			else
			{
				testChamber4(world, x, y, z);
			}
		}
		else
		{
			testChamber4(world, x, y, z);
		}
	}
	
	public void testChamber4(World world, int x, int y, int z)
	{
		if(world.getTileEntity(x, y, z - 1) != null && world.getTileEntity(x, y, z - 1) instanceof TileInfuserChamber)
		{
			if(chambers[0] == null)
			{
			chambers[0] = (TileInfuserChamber)world.getTileEntity(x, y, z - 1);
			}
			else if(chambers[1] == null && (TileInfuserChamber)world.getTileEntity(x, y, z - 1) != chambers[0])
			{
			chambers[1] = (TileInfuserChamber)world.getTileEntity(x, y, z - 1);
			}
		}
	}

	@Override
	public ItemStack getMale() {
		if(getChamberWithGender(Allele.male) == null)
			return null;
		return getChamberWithGender(Allele.male).getStackInSlot(0);
	}

	@Override
	public ItemStack getFemale() {
		if(getChamberWithGender(Allele.female) == null)
			return null;
		return getChamberWithGender(Allele.female).getStackInSlot(0);
	}

	
	
	@Override
	public void setOutputPlant(ItemStack stack) {
		setInventorySlotContents(0, stack);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(getBlockMetadata() == 1)
			return 0;
		if(from == waterInput)
		{
			return tankWater.fill(resource, doFill);
		}
		return tank.fill(resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(getBlockMetadata() == 1)
			return null;
		if(from == waterInput)
		{
			return tankWater.drain(resource, doDrain);
		}
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(getBlockMetadata() == 1)
			return null;
		if(from == waterInput)
		{
			return tankWater.drain(maxDrain, doDrain);
		}
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(getBlockMetadata() == 1)
			return false;
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(getBlockMetadata() == 1)
			return false;
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(getBlockMetadata() == 1)
			return null;
		return new FluidTankInfo[]{ tank.getInfo() };
	}

	@Override
	public ItemStack getPlant() {
		return getStackInSlot(0);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		logic.writeToNBT(nbt);
		
		if(chambers[0] != null)
		{
			NBTTagCompound nbtChamber = new NBTTagCompound();
			nbtChamber.setInteger("x", chambers[0].xCoord);
			nbtChamber.setInteger("y", chambers[0].yCoord);
			nbtChamber.setInteger("z", chambers[0].zCoord);
			nbt.setTag("Chamber0", nbtChamber);
		}
		
		if(chambers[1] != null)
		{
			NBTTagCompound nbtChamber = new NBTTagCompound();
			nbtChamber.setInteger("x", chambers[1].xCoord);
			nbtChamber.setInteger("y", chambers[1].yCoord);
			nbtChamber.setInteger("z", chambers[1].zCoord);
			nbt.setTag("Chamber1", nbtChamber);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		logic.readFromNBT(nbt);
		
		if(worldObj == null)
			return;
		if(nbt.hasKey("Chamber0"))
		{
			NBTTagCompound nbtChamber = nbt.getCompoundTag("Chamber0");
			if(this.worldObj.getTileEntity(nbtChamber.getInteger("x"), nbtChamber.getInteger("y"), nbtChamber.getInteger("z")) != null)
			chambers[0] = (TileInfuserChamber) this.worldObj.getTileEntity(nbtChamber.getInteger("x"), nbtChamber.getInteger("y"), nbtChamber.getInteger("z"));
		}
		if(nbt.hasKey("Chamber1"))
		{
			NBTTagCompound nbtChamber = nbt.getCompoundTag("Chamber1");
			if(this.worldObj.getTileEntity(nbtChamber.getInteger("x"), nbtChamber.getInteger("y"), nbtChamber.getInteger("z")) != null)
			chambers[1] = (TileInfuserChamber) this.worldObj.getTileEntity(nbtChamber.getInteger("x"), nbtChamber.getInteger("y"), nbtChamber.getInteger("z"));
		}
	}

	@Override
	public void setPlant(ItemStack stack) {
	}

	@Override
	public boolean addProduct(ItemStack stack) {
		return false;
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public IInfuserChamber[] getChambers() {
		return chambers;
	}

	@Override
	public PlantStatus getPlantStatus() {
		return null;
	}

	@Override
	public ItemStack getFruit(int fruit) {
		return null;
	}

	@Override
	public void setFruit(int fruitID, ItemStack fruit) {
		
	}
	
}
