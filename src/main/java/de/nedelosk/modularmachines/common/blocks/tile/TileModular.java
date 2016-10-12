package de.nedelosk.modularmachines.common.blocks.tile;

import java.util.EnumMap;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.client.model.ModelModular;
import de.nedelosk.modularmachines.common.modular.ModularHandlerTileEntity;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.InterfaceList({
	@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHLib"),
	@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHLib"),
	@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2"),
	@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2")
})
public class TileModular extends TileBaseGui implements IEnergyProvider, IEnergyReceiver, IEnergySink {

	@CapabilityInject(ITeslaConsumer.class)
	public static Capability<ITeslaConsumer> TESLA_CONSUMER = null;
	@CapabilityInject(ITeslaProducer.class)
	public static Capability<ITeslaProducer> TESLA_PRODUCER = null;
	@CapabilityInject(ITeslaHolder.class)
	public static Capability<ITeslaHolder> TESLA_HOLDER = null;

	protected EnumMap<EnumFacing, SideCapability> sides = new EnumMap(EnumFacing.class);
	protected IModularHandlerTileEntity modularHandler;
	protected boolean addedToEnet;

	public TileModular() {
		modularHandler = new ModularHandlerTileEntity(this, ModularManager.DEFAULT_STORAGE_POSITIONS);
		for(EnumFacing face : EnumFacing.VALUES){
			sides.put(face, new SideCapability(face));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (modularHandler != null) {
			nbt.setTag("ModularHandler", modularHandler.serializeNBT());
		}
		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("ModularHandler")) {
			modularHandler.deserializeNBT(nbt.getCompoundTag("ModularHandler"));
		}
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return serializeNBT();
	}

	@Override
	public Container createContainer(InventoryPlayer inventory) {
		if (modularHandler != null) {
			return modularHandler.createContainer(inventory);
		} else {
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		if (modularHandler != null) {
			return modularHandler.createGui(inventory);
		} else {
			return null;
		}
	}

	@Override
	public void updateClient() {
		if (modularHandler != null) {
			modularHandler.updateClient();
		}
	}

	@Override
	public void updateServer() {
		if (modularHandler != null) {
			modularHandler.updateServer();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if (modularHandler != null) {
			modularHandler.invalidate();
		}
		if(worldObj.isRemote){
			removeHandler();
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (modularHandler != null) {
			modularHandler.invalidate();
		}
		if(worldObj.isRemote){
			removeHandler();
		}
	}

	@SideOnly(Side.CLIENT)
	private void removeHandler(){
		ModelModular.modularHandlers.remove(modularHandler);
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		if(modularHandler != null){
			modularHandler.setWorld(world);
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ModularManager.MODULAR_HANDLER_CAPABILITY) {
			return ModularManager.MODULAR_HANDLER_CAPABILITY.cast(modularHandler);
		}
		if(TESLA_CONSUMER != null){
			if(capability == TESLA_CONSUMER){
				SideCapability tesla = sides.get(facing);
				return TESLA_CONSUMER.cast(tesla);
			}else if(capability == TESLA_PRODUCER){
				SideCapability tesla = sides.get(facing);
				return TESLA_PRODUCER.cast(tesla);
			}else if(capability == TESLA_HOLDER){
				SideCapability tesla = sides.get(facing);
				return TESLA_HOLDER.cast(tesla);
			}
		}
		if(capability == CapabilityEnergy.ENERGY){
			return CapabilityEnergy.ENERGY.cast(sides.get(facing));
		}
		if(modularHandler != null){
			if(modularHandler.hasCapability(capability, facing)){
				return modularHandler.getCapability(capability, facing);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == ModularManager.MODULAR_HANDLER_CAPABILITY) {
			return true;
		}
		if(TESLA_CONSUMER != null){
			if(capability == TESLA_CONSUMER || capability == TESLA_PRODUCER || capability == TESLA_HOLDER){
				return true;
			}
		}
		if(capability == CapabilityEnergy.ENERGY){
			return true;
		}
		if(modularHandler != null){
			if(modularHandler.hasCapability(capability, facing)){
				return true;
			}
		}
		return super.hasCapability(capability, facing);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return 0;
		}
		return (int) modularHandler.getModular().getEnergyBuffer().getEnergyStored();
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return 0;
		}
		return (int) modularHandler.getModular().getEnergyBuffer().getCapacity();
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return 	modularHandler != null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyBuffer() != null;
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return 0;
		}
		return (int) modularHandler.getModular().getEnergyBuffer().receiveEnergy(null, from, maxReceive, simulate);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return 0;
		}
		return (int) modularHandler.getModular().getEnergyBuffer().extractEnergy(null, from, maxExtract, simulate);
	}

	@Optional.Method(modid = "IC2")
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
		return modularHandler != null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyBuffer() != null;
	}

	@Optional.Method(modid = "IC2")
	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return amount;
		}
		long receivedEnergy = modularHandler.getModular().getEnergyBuffer().receiveEnergy(null, directionFrom, (long) amount * 2, false);
		return amount - receivedEnergy / 2;
	}

	@Optional.Method(modid = "IC2")
	@Override
	public double getDemandedEnergy() {
		if(modularHandler == null 
				|| modularHandler.getModular() == null
				|| modularHandler.getModular().getEnergyBuffer() == null){
			return 0;
		}
		IEnergyBuffer buffer = modularHandler.getModular().getEnergyBuffer();
		return (buffer.getCapacity() - buffer.getEnergyStored()) / 2;
	}

	@Optional.Method(modid = "IC2")
	@Override
	public int getSinkTier() {
		return 4;
	}

	/*@Optional.Method(modid = "IC2")
	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
		return false;
	}

	@Optional.Method(modid = "IC2")
	@Override
	public double getOfferedEnergy() {
		double energy = 0;
		for(IEnergySource energyInterface : getEnergyInterfaces(EnergyRegistry.energyUnit, null, IEnergySource.class)){
			energy +=energyInterface.getOfferedEnergy();
		}
		return energy;
	}

	@Optional.Method(modid = "IC2")
	@Override
	public void drawEnergy(double amount) {
		for(IEnergySource energyInterface : getEnergyInterfaces(EnergyRegistry.energyUnit, null, IEnergySource.class)){
			energyInterface.drawEnergy(amount);
		}
	}

	@Optional.Method(modid = "IC2")
	@Override
	public int getSourceTier() {
		int tier = 1;
		for(IEnergySource energyInterface : getEnergyInterfaces(EnergyRegistry.energyUnit, null, IEnergySource.class)){
			if(energyInterface.getSourceTier() > tier){
				tier = energyInterface.getSourceTier();
			}
		}
		return tier;
	}*/

	@Optional.InterfaceList({
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = "tesla"),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = "tesla"),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = "tesla")
	})
	private class SideCapability implements ITeslaConsumer, ITeslaHolder, ITeslaProducer, IEnergyStorage{

		private EnumFacing facing;

		public SideCapability(EnumFacing facing) {
			this.facing = facing;
		}

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return (int) modularHandler.getModular().getEnergyBuffer().receiveEnergy(null, facing, maxReceive, simulate);
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return (int) modularHandler.getModular().getEnergyBuffer().extractEnergy(null, facing, maxExtract, simulate);
		}

		@Override
		public int getEnergyStored() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return (int) modularHandler.getModular().getEnergyBuffer().getEnergyStored();
		}

		@Override
		public int getMaxEnergyStored() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return (int) modularHandler.getModular().getEnergyBuffer().getCapacity();
		}

		@Override
		public boolean canExtract() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return false;
			}
			return modularHandler.getModular().getEnergyBuffer().canExtract();
		}

		@Override
		public boolean canReceive() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return false;
			}
			return modularHandler.getModular().getEnergyBuffer().canReceive();
		}

		@Optional.Method(modid = "tesla")
		@Override
		public long takePower(long power, boolean simulated) {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return modularHandler.getModular().getEnergyBuffer().extractEnergy(null, facing, power, simulated);
		}

		@Optional.Method(modid = "tesla")
		@Override
		public long givePower(long power, boolean simulated) {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return modularHandler.getModular().getEnergyBuffer().receiveEnergy(null, facing, power, simulated);
		}

		@Optional.Method(modid = "tesla")
		@Override
		public long getStoredPower() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return  modularHandler.getModular().getEnergyBuffer().getEnergyStored();
		}

		@Optional.Method(modid = "tesla")
		@Override
		public long getCapacity() {
			if(modularHandler == null 
					|| modularHandler.getModular() == null
					|| modularHandler.getModular().getEnergyBuffer() == null){
				return 0;
			}
			return  modularHandler.getModular().getEnergyBuffer().getCapacity();
		}

	}

}
