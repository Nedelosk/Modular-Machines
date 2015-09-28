package nedelosk.modularmachines.common.blocks.tile;

import java.util.ArrayList;
import java.util.Vector;

import nedelosk.modularmachines.api.basic.machine.IModularTileEntity;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.IModuleGui;
import nedelosk.modularmachines.api.basic.machine.module.ModuleStack;
import nedelosk.modularmachines.client.gui.machine.GuiModularMachine;
import nedelosk.modularmachines.common.inventory.machine.ContainerModularMachine;
import nedelosk.modularmachines.common.machines.MachineBuilder;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileModular<M extends IModular> extends TileBaseInventory implements IModularTileEntity {

	public M machine;
	public String page;
	
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
		machine.writeToNBT(machineTag);
		nbt.setTag("Machine", machineTag);
		nbt.setString("MachineName", machine.getName());
		
		nbt.setString("Page", page);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		page = nbt.getString("Page");
		machine = MachineBuilder.createMachine(nbt.getString("MachineName"));
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		//if(!worldObj.isRemote)
			//PacketHandler.INSTANCE.sendTo(new PacketModularMachineNBT(this), (EntityPlayerMP) inventory.player);
		return new ContainerModularMachine(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		if(inventory.player.getExtendedProperties(ModularSaveModule.class.getName()) != null)
			if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord) != null)
				this.page = ((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(xCoord, yCoord, zCoord).page;
			else
				page = getModuleWithGuis().get(0).getModule().getName();
		return new GuiModularMachine(this, inventory);
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
	}

	public void setMachine(NBTTagCompound tagCompound) {
		machine = MachineBuilder.createMachine(tagCompound.getString("MachineName"), tagCompound.getTag("Machine"), this);
		if(page == null)
			page = getModuleWithGuis().get(0).getModule().getName();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public ArrayList<ModuleStack> getModuleWithGuis() {
		ArrayList<ModuleStack> guis = new ArrayList<ModuleStack>();
		for(Vector<ModuleStack> stacks : machine.getModules().values())
			for(ModuleStack module : stacks)
			{
				if(module.getModule() instanceof IModuleGui)
					guis.add(module);
			}
		return guis;
	}
	
	public ModuleStack getModuleWithGui()
	{
		for(ModuleStack module : getModuleWithGuis())
		{
			if(module.getModule().getName().equals(page))
				return module;
		}
		return null;
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
		if(machine == null)
			return 0;
		if(machine.getManager() == null)
			return 0;
		if(machine.getManager().getFluidHandler() == null)
			return 0;
		return machine.getManager().getFluidHandler().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(machine == null)
			return null;
		if(machine.getManager() == null)
			return null;
		if(machine.getManager().getFluidHandler() == null)
			return null;
		return machine.getManager().getFluidHandler().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(machine == null)
			return null;
		if(machine.getManager() == null)
			return null;
		if(machine.getManager().getFluidHandler() == null)
			return null;
		return machine.getManager().getFluidHandler().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(machine == null)
			return false;
		if(machine.getManager() == null)
			return false;
		if(machine.getManager().getFluidHandler() == null)
			return false;
		return machine.getManager().getFluidHandler().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(machine == null)
			return false;
		if(machine.getManager() == null)
			return false;
		if(machine.getManager().getFluidHandler() == null)
			return false;
		return machine.getManager().getFluidHandler().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(machine == null)
			return new FluidTankInfo[0];
		if(machine.getManager() == null)
			return new FluidTankInfo[0];
		if(machine.getManager().getFluidHandler() == null)
			return new FluidTankInfo[0];
		return machine.getManager().getFluidHandler().getTankInfo(from);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(machine == null)
			return 0;
		if(machine.getManager() == null)
			return 0;
		if(machine.getManager().getEnergyHandler() == null)
			return 0;
		return machine.getManager().getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(machine == null)
			return 0;
		if(machine.getManager() == null)
			return 0;
		if(machine.getManager().getEnergyHandler() == null)
			return 0;
		return machine.getManager().getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if(machine == null)
			return 0;
		if(machine.getManager() == null)
			return 0;
		if(machine.getManager().getEnergyHandler() == null)
			return 0;
		return machine.getManager().getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if(machine == null)
			return 0;
		if(machine.getManager() == null)
			return 0;
		if(machine.getManager().getEnergyHandler() == null)
			return 0;
		return machine.getManager().getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(machine == null)
			return false;
		if(machine.getManager() == null)
			return false;
		if(machine.getManager().getEnergyHandler() == null)
			return false;
		return machine.getManager().getEnergyHandler().canConnectEnergy(from);
	}

	@Override
	public M getModular() {
		return machine;
	}

}
