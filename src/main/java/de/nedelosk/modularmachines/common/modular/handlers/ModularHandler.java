package de.nedelosk.modularmachines.common.modular.handlers;

import com.mojang.authlib.GameProfile;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.common.modular.Modular;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public abstract class ModularHandler implements IModularHandler<IModular, NBTTagCompound>{

	protected IModular modular;
	protected World world;
	protected GameProfile owner;

	public ModularHandler(World world) {
		this.world = world;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (modular != null) {
			nbt.setTag("Modular", modular.writeToNBT(new NBTTagCompound()));
		}
		if (this.owner != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbtTag, owner);
			nbt.setTag("owner", nbtTag);
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Modular")) {
			modular = new Modular(nbt.getCompoundTag("Modular"), this);
		}
		if (nbt.hasKey("owner")) {
			owner = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("owner"));
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.getContainer(this, inventory);
		} else {
			return null;
		}
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.getGUIContainer(this, inventory);
		} else {
			return null;
		}
	}

	@Override
	public void updateClient() {
		if (modular != null) {
			modular.update(false);
		}
	}

	@Override
	public void updateServer() {
		if (modular != null) {
			modular.update(true);
		}
	}

	@Override
	public void setModular(IModular modular) {
		modular.setHandler(this);
		this.modular = modular;
		markDirty();
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModularManager.MODULAR_HANDLER_CAPABILITY){
			return ModularManager.MODULAR_HANDLER_CAPABILITY.cast(this);
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ModularManager.MODULAR_HANDLER_CAPABILITY){
			return true;
		}
		return false;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if (modular == null) {
			return 0;
		}
		if (modular.getEnergyHandler() == null) {
			return 0;
		}
		return modular.getEnergyHandler().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		if (modular == null) {
			return false;
		}
		if (modular.getEnergyHandler() == null) {
			return false;
		}
		return modular.getEnergyHandler().canConnectEnergy(from);
	}

	@Override
	public GameProfile getOwner() {
		return owner;
	}

	@Override
	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
	}

	protected EntityPlayer getPlayer(){
		return world.getPlayerEntityByName(getOwner().getName());
	}
}
