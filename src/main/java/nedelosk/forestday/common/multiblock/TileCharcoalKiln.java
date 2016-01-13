package nedelosk.forestday.common.multiblock;

import nedelosk.forestcore.library.BlockPos;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.TileMultiblockBase;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.WoodType;
import net.minecraft.nbt.NBTTagCompound;

public class TileCharcoalKiln extends TileMultiblockBase<MultiblockCharcoalKiln> {

	private WoodType woodType;
	private CharcoalKilnPosition kilnPosition;
	private byte level;
	private boolean isAsh;

	public TileCharcoalKiln() {
		super();
		this.kilnPosition = CharcoalKilnPosition.UNKNOWN;
		this.isAsh = false;
		this.level = 0;
		this.woodType = null;
	}

	public void setWoodType(WoodType type) {
		this.woodType = type;
	}

	public WoodType getWoodType() {
		return woodType;
	}

	public boolean isAsh() {
		return isAsh;
	}

	public void setIsAsh() {
		isAsh = true;
	}

	public byte getLevel() {
		return level;
	}

	public CharcoalKilnPosition getKilnPosition() {
		return kilnPosition;
	}

	@Override
	protected void encodeDescriptionPacket(NBTTagCompound packetData) {
		super.encodeDescriptionPacket(packetData);
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		packetData.setTag("Part", nbtTag);
	}

	@Override
	protected void decodeDescriptionPacket(NBTTagCompound packetData) {
		super.decodeDescriptionPacket(packetData);
		NBTTagCompound nbtTag = packetData.getCompoundTag("Part");
		readFromNBT(nbtTag);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (woodType != null) {
			nbt.setString("WoodType", woodType.getName());
		}
		nbt.setBoolean("IsAsh", isAsh);
		nbt.setByte("Level", level);
		nbt.setInteger("KilnPosition", kilnPosition.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("WoodType")) {
			woodType = ForestDayCrafting.woodManager.get(nbt.getString("WoodType"));
		}
		isAsh = nbt.getBoolean("IsAsh");
		level = nbt.getByte("Level");
		kilnPosition = CharcoalKilnPosition.values()[nbt.getInteger("KilnPosition")];
	}

	@Override
	public void recalculateOutwardsDirection(BlockPos minCoord, BlockPos maxCoord) {
		super.recalculateOutwardsDirection(minCoord, maxCoord);
		int facesMatching = 0;
		if (maxCoord.x == this.xCoord || minCoord.x == this.xCoord) {
			facesMatching++;
		}
		if (maxCoord.z == this.zCoord || minCoord.z == this.zCoord) {
			facesMatching++;
		}
		if (facesMatching <= 0) {
			kilnPosition = CharcoalKilnPosition.INTERIOR;
		} else {
			if (maxCoord.x == this.xCoord) {
				if (maxCoord.z == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_RIGHT;
				} else if (minCoord.z == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_RIGHT;
				} else {
					kilnPosition = CharcoalKilnPosition.RIGHT;
				}
			} else if (minCoord.x == this.xCoord) {
				if (maxCoord.z == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_LEFT;
				} else if (minCoord.z == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.LEFT;
				}
			} else if (maxCoord.z == this.zCoord) {
				if (maxCoord.x == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_RIGHT;
				} else if (minCoord.x == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.FRONT;
				}
			} else if (minCoord.z == this.zCoord) {
				if (maxCoord.x == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_RIGHT;
				} else if (minCoord.x == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.BACK;
				}
			}
		}
		if (maxCoord.y == this.yCoord) {
			level = 1;
		} else {
			level = 0;
		}
	}

	@Override
	public MultiblockCharcoalKiln createNewMultiblock() {
		return new MultiblockCharcoalKiln(worldObj);
	}

	@Override
	public Class<? extends MultiblockCharcoalKiln> getMultiblockControllerType() {
		return MultiblockCharcoalKiln.class;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {
	}
}
