package de.nedelosk.modularmachines.common.blocks.tile;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;

public abstract class TileMachineBase extends TileBaseInventory {

	public EnumFacing facing;
	protected int timer;
	protected int timerMax = 50;
	protected GameProfile owner;

	public TileMachineBase(int slots) {
		super(slots);
	}

	public TileMachineBase() {
		super();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("facing", facing.ordinal());
		if (this.owner != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbtTag, owner);
			nbt.setTag("owner", nbtTag);
		}
		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		facing = EnumFacing.VALUES[nbt.getInteger("facing")];
		if (nbt.hasKey("owner")) {
			owner = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("owner"));
		}
	}

	public EnumFacing getFacing() {
		return facing;
	}

	public GameProfile getOwner() {
		return owner;
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}
}
