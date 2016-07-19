package de.nedelosk.modularmachines.common.blocks.tile;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandlerTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.InterfaceList({
	@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHLib"),
	@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHLib")
})
public class TileModular extends TileBaseGui implements IEnergyProvider, IEnergyReceiver {

	public IModularHandlerTileEntity modularHandler;

	public TileModular() {
		modularHandler = new ModularHandlerTileEntity(this);
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
			modularHandler = new ModularHandlerTileEntity(this);
			modularHandler.deserializeNBT(nbt.getCompoundTag("ModularHandler"));
		}
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (modularHandler != null) {
			return modularHandler.getContainer(inventory);
		} else {
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (modularHandler != null) {
			return modularHandler.getGUIContainer(inventory);
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
		if(modularHandler == null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyInterface() != null){
			return 0;
		}
		return ((IEnergyHandler)modularHandler.getModular().getEnergyInterface()).getEnergyStored(from);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if(modularHandler == null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyInterface() != null){
			return 0;
		}
		return ((IEnergyHandler)modularHandler.getModular().getEnergyInterface()).getMaxEnergyStored(from);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if(modularHandler == null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyInterface() != null){
			return false;
		}
		return ((IEnergyConnection)modularHandler.getModular().getEnergyInterface()).canConnectEnergy(from);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(modularHandler == null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyInterface() != null){
			return 0;
		}
		return ((IEnergyReceiver)modularHandler.getModular().getEnergyInterface()).receiveEnergy(from, maxReceive, simulate);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if(modularHandler == null && modularHandler.getModular() != null && modularHandler.getModular().getEnergyInterface() != null){
			return 0;
		}
		return ((IEnergyProvider)modularHandler.getModular().getEnergyInterface()).extractEnergy(from, maxExtract, simulate);
	}
}
