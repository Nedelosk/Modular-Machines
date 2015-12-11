package nedelosk.forestday.common.blocks.tiles;

import com.mojang.authlib.GameProfile;

import nedelosk.forestcore.api.tile.TileBaseInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

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

	public abstract String getMachineName();

	@Override
	public String getMachineTileName() {
		return getMachineName();
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

	public short getFacing() {
		return facing;
	}

	public GameProfile getOwner() {
		return owner;
	}

	public void setFacing(short facing) {
		this.facing = facing;
	}

	public void setOwner(GameProfile owner) {
		this.owner = owner;
	}

}
