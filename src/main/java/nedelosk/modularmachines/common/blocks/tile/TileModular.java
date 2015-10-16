package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileModular<M extends IModular> extends TileBaseInventory implements IModularTileEntity {

	public M modular;
	
	public TileModular()
	{
		super(0);
	}
	
	@Override
	public String getMachineTileName() {
		return "modular";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound machineTag = new NBTTagCompound();
		modular.writeToNBT(machineTag);
		nbt.setTag("Machine", machineTag);
		nbt.setString("MachineName", modular.getName());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		modular = MachineBuilder.createMachine(nbt.getString("MachineName"), nbt.getCompoundTag("Machine"));
		modular.setMachine(this);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		//if(!worldObj.isRemote)
			//PacketHandler.INSTANCE.sendTo(new PacketModularMachineNBT(this), (EntityPlayerMP) inventory.player);
		if(modular != null)
			return modular.getGuiManager().getContainer(this, inventory);
		else
			return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		/*if(modular != null){
			if(inventory.player.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord) != null)
					this.page = ((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord).page;
				else
					page = modular.getGuiManager().getModuleWithGuis().get(0).getModule().getName();
		}*/
		if(modular != null)
			return modular.getGuiManager().getGUIContainer(this, inventory);
		else
			return null;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		if(modular != null){
			modular.update();
		}
	}

	public void setMachine(NBTTagCompound tagCompound) {
		modular = MachineBuilder.createMachine(tagCompound.getString("MachineName"), tagCompound.getCompoundTag("Machine"));
		modular.setMachine(this);
		modular.initModular();
		ModuleStack<IModule, IProducerGui> gui = modular.getGuiManager().getModuleWithGui();
		modular.getGuiManager().setPage(gui.getModule().getName(gui));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public int getYCoord() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(modular == null)
			return 0;
		if(modular.getManager() == null)
			return 0;
		if(modular.getManager().getFluidHandler() == null)
			return 0;
		return modular.getManager().getFluidHandler().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(modular == null)
			return null;
		if(modular.getManager() == null)
			return null;
		if(modular.getManager().getFluidHandler() == null)
			return null;
		return modular.getManager().getFluidHandler().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(modular == null)
			return null;
		if(modular.getManager() == null)
			return null;
		if(modular.getManager().getFluidHandler() == null)
			return null;
		return modular.getManager().getFluidHandler().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(modular == null)
			return false;
		if(modular.getManager() == null)
			return false;
		if(modular.getManager().getFluidHandler() == null)
			return false;
		return modular.getManager().getFluidHandler().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(modular == null)
			return false;
		if(modular.getManager() == null)
			return false;
		if(modular.getManager().getFluidHandler() == null)
			return false;
		return modular.getManager().getFluidHandler().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(modular == null)
			return new FluidTankInfo[0];
		if(modular.getManager() == null)
			return new FluidTankInfo[0];
		if(modular.getManager().getFluidHandler() == null)
			return new FluidTankInfo[0];
		return modular.getManager().getFluidHandler().getTankInfo(from);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(modular == null)
			return 0;
		if(modular.getManager() == null)
			return 0;
		if(modular.getManager().getEnergyHandler() == null)
			return 0;
		return modular.getManager().getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(modular == null)
			return 0;
		if(modular.getManager() == null)
			return 0;
		if(modular.getManager().getEnergyHandler() == null)
			return 0;
		return modular.getManager().getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if(modular == null)
			return 0;
		if(modular.getManager() == null)
			return 0;
		if(modular.getManager().getEnergyHandler() == null)
			return 0;
		return modular.getManager().getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if(modular == null)
			return 0;
		if(modular.getManager() == null)
			return 0;
		if(modular.getManager().getEnergyHandler() == null)
			return 0;
		return modular.getManager().getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(modular == null)
			return false;
		if(modular.getManager() == null)
			return false;
		if(modular.getManager().getEnergyHandler() == null)
			return false;
		return modular.getManager().getEnergyHandler().canConnectEnergy(from);
	}

	@Override
	public M getModular() {
		return modular;
	}

}
