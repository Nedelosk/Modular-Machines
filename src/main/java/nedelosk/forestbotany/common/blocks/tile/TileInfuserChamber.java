package nedelosk.forestbotany.common.blocks.tile;

import nedelosk.forestbotany.api.botany.IChamberLogic;
import nedelosk.forestbotany.client.core.gui.GuiInfuserChamber;
import nedelosk.forestbotany.common.botany.ChamberLogic;
import nedelosk.forestbotany.common.inventory.ContainerInfuserChamber;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileInfuserChamber extends TileInfuserBase {
	
	public IChamberLogic logic;
	
	public TileInfuserChamber() {
		super(7);
		logic = new ChamberLogic(this);
	}

	@Override
	public String getMachineTileName() {
		return "infuser";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if(getBlockMetadata() == 2)
		{
			return new ContainerInfuserChamber(this, inventory);
		}
		if(getBlockMetadata() == 3)
		{
			return ((TileInfuserChamber)this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getContainer(inventory);
		}
		return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		if(getBlockMetadata() == 2)
		{
			return new GuiInfuserChamber(this, inventory);
		}
		if(getBlockMetadata() == 3)
		{
			return ((TileInfuserChamber)this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord)).getGUIContainer(inventory);
		}
		return null;
	}

	@Override
	public void updateClient() {
		if(getBlockMetadata() == 2)
		{
			
		}
	}

	@Override
	public void updateServer() {

		if(getBlockMetadata() == 2)
		{
			super.updateServer();
			logic.update();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(getBlockMetadata() == 3)
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
		if(getBlockMetadata() == 3)
			return null;
		if(from == waterInput)
		{
			return tankWater.drain(resource, doDrain);
		}
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(getBlockMetadata() == 3)
			return null;
		if(from == waterInput)
		{
			return tankWater.drain(maxDrain, doDrain);
		}
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(getBlockMetadata() == 3)
			return false;
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(getBlockMetadata() == 3)
			return false;
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(getBlockMetadata() == 3)
			return null;
		return new FluidTankInfo[]{ tank.getInfo(), tankWater.getInfo() };
	}

	@Override
	public ItemStack getPlant() {
		return getStackInSlot(0);
	}

	@Override
	public void setPlant(ItemStack stack) {
		setInventorySlotContents(0, stack);
	}

	@Override
	public boolean addProduct(ItemStack stack) {
		return addToOutput(stack, 2, 8);
	}
	
	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public PlantStatus getPlantStatus() {
		return logic.getPlantStatus();
	}

	@Override
	public ItemStack getFruit(int fruit) {
		return getStackInSlot(fruit + 2);
	}

	@Override
	public void setFruit(int fruitID, ItemStack fruit) {
		setInventorySlotContents(fruitID + 2, fruit);
	}
}
