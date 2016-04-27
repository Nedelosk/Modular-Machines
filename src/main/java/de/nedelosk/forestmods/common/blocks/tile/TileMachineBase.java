package de.nedelosk.forestmods.common.blocks.tile;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileMachineBase extends TileBaseInventory {

	public short facing;
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
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("facing", facing);
		if (this.owner != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTUtil.func_152460_a(nbtTag, owner);
			nbt.setTag("owner", nbtTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		facing = nbt.getShort("facing");
		if (nbt.hasKey("owner")) {
			owner = NBTUtil.func_152459_a(nbt.getCompoundTag("owner"));
		}
	}

	public ForgeDirection getFacing() {
		return ForgeDirection.VALID_DIRECTIONS[facing];
	}

	public GameProfile getOwner() {
		return owner;
	}

	public void setFacing(ForgeDirection facing) {
		Integer inte = new Integer(facing.ordinal());
		this.facing = inte.shortValue();
	}

	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}
}
