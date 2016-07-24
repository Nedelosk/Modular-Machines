package de.nedelosk.modularmachines.common.modular.handlers;

import com.mojang.authlib.GameProfile;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.modular.assembler.ModularAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModularHandler implements IModularHandler<IModular, NBTTagCompound>{

	protected IModular modular;
	protected IModularAssembler assembler;
	protected World world;
	protected GameProfile owner;
	protected boolean isAssembled;

	public ModularHandler(World world) {
		this.world = world;
		this.isAssembled = false;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (modular != null) {
			nbt.setTag("Modular", modular.serializeNBT());
		}
		if (this.owner != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbtTag, owner);
			nbt.setTag("owner", nbtTag);
		}
		if(assembler != null){
			nbt.setTag("Assembler", assembler.serializeNBT());
		}
		nbt.setBoolean("isAssembled", isAssembled);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Modular")) {
			modular = new Modular(nbt.getCompoundTag("Modular"), this);
		}
		if(nbt.hasKey("Assembler")){
			assembler = new ModularAssembler(this, nbt.getCompoundTag("Assembler"));
		}
		if (nbt.hasKey("owner")) {
			owner = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("owner"));
		}
		isAssembled = nbt.getBoolean("isAssembled");
	}

	@Override
	public Container createContainer(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.createContainer(this, inventory);
		}else if (assembler != null) {
			return assembler.createContainer(this, inventory);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		if (modular != null) {
			return modular.createGui(this, inventory);
		}else if (assembler != null) {
			return assembler.createGui(this, inventory);
		}
		return null;
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
	public void setAssembler(IModularAssembler assembler) {
		this.assembler = assembler;
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
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
	public boolean isAssembled() {
		return isAssembled;
	}

	@Override
	public void setAssembled(boolean isAssembled) {
		this.isAssembled = isAssembled;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModularManager.MODULAR_HANDLER_CAPABILITY){
			return ModularManager.MODULAR_HANDLER_CAPABILITY.cast(this);
		}
		if(modular != null){
			if(modular.hasCapability(capability, facing)){
				return modular.getCapability(capability, facing);
			}
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ModularManager.MODULAR_HANDLER_CAPABILITY){
			return true;
		}
		if(modular != null){
			if(modular.hasCapability(capability, facing)){
				return true;
			}
		}
		return false;
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
