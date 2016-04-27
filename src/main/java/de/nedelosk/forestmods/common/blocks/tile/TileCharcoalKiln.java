package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition;
import de.nedelosk.forestmods.common.multiblocks.charcoal.MultiblockCharcoalKiln;
import de.nedelosk.forestmods.library.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.library.multiblock.TileMultiblockBase;
import de.nedelosk.forestmods.library.utils.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileCharcoalKiln extends TileMultiblockBase<MultiblockCharcoalKiln> {

	private ItemStack woodStack;
	private CharcoalKilnPosition kilnPosition;
	private boolean isAsh;

	public TileCharcoalKiln() {
		super();
		this.kilnPosition = CharcoalKilnPosition.UNKNOWN;
		this.woodStack = null;
	}

	public void setWoodStack(ItemStack woodStack) {
		this.woodStack = woodStack;
	}

	public ItemStack getWoodStack() {
		return woodStack;
	}

	public CharcoalKilnPosition getKilnPosition() {
		return kilnPosition;
	}

	public boolean isAsh() {
		return isAsh;
	}

	public void setIsAsh() {
		isAsh = true;
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
		if (woodStack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			woodStack.writeToNBT(nbtTag);
			nbt.setTag("WoodStack", nbtTag);
		}
		nbt.setBoolean("IsAsh", isAsh);
		nbt.setInteger("KilnPosition", kilnPosition.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("WoodStack")) {
			woodStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WoodStack"));
		}
		isAsh = nbt.getBoolean("IsAsh");
		kilnPosition = CharcoalKilnPosition.values()[nbt.getInteger("KilnPosition")];
	}

	@Override
	public void recalculateOutwardsDirection(BlockPos minCoord, BlockPos maxCoord) {
		super.recalculateOutwardsDirection(minCoord, maxCoord);
		int level = yCoord - minCoord.y;
		int facesMatching = 0;
		if (maxCoord.x - level == this.xCoord || minCoord.x + level == this.xCoord) {
			facesMatching++;
		}
		if (maxCoord.z - level == this.zCoord || minCoord.z + level == this.zCoord) {
			facesMatching++;
		}
		if (facesMatching <= 0) {
			kilnPosition = CharcoalKilnPosition.INTERIOR;
		} else {
			if (maxCoord.x - level == this.xCoord) {
				if (maxCoord.z - level == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_RIGHT;
				} else if (minCoord.z + level == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_RIGHT;
				} else {
					kilnPosition = CharcoalKilnPosition.RIGHT;
				}
			} else if (minCoord.x + level == this.xCoord) {
				if (maxCoord.z - level == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_LEFT;
				} else if (minCoord.z + level == this.zCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.LEFT;
				}
			} else if (maxCoord.z - level == this.zCoord) {
				if (maxCoord.x - level == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_RIGHT;
				} else if (minCoord.x + level == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.FRONT_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.FRONT;
				}
			} else if (minCoord.z + level == this.zCoord) {
				if (maxCoord.x - level == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_RIGHT;
				} else if (minCoord.x + level == this.xCoord) {
					kilnPosition = CharcoalKilnPosition.BACK_LEFT;
				} else {
					kilnPosition = CharcoalKilnPosition.BACK;
				}
			}
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
