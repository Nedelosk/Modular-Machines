package de.nedelosk.forestmods.common.multiblocks.charcoal;

import de.nedelosk.forestmods.common.blocks.BlockCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.library.multiblock.IMultiblockPart;
import de.nedelosk.forestmods.library.multiblock.MultiblockControllerBase;
import de.nedelosk.forestmods.library.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.library.multiblock.RectangularMultiblockControllerBase;
import de.nedelosk.forestmods.library.utils.BlockPos;
import de.nedelosk.forestmods.library.utils.Log;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MultiblockCharcoalKiln extends RectangularMultiblockControllerBase {

	private int burnTime;
	private boolean isActive;
	private int timer;

	public MultiblockCharcoalKiln(World world) {
		super(world);
		isActive = false;
		burnTime = 0;
	}

	@Override
	public void setIsActive() {
		isActive = true;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
	}

	@Override
	protected void onMachineRestored() {
	}

	@Override
	protected void onMachinePaused() {
	}

	@Override
	protected void onMachineAssembled() {
	}

	@Override
	protected void onMachineDisassembled() {
		isActive = false;
		burnTime = 0;
		markReferenceCoordForUpdate();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 20;
	}

	@Override
	protected int getMaximumXSize() {
		return 9;
	}

	@Override
	protected int getMaximumYSize() {
		return 6;
	}

	@Override
	protected int getMaximumZSize() {
		return 9;
	}

	@Override
	protected int getMinimumXSize() {
		return 4;
	}

	@Override
	protected int getMinimumYSize() {
		return 2;
	}

	@Override
	protected int getMinimumZSize() {
		return 4;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase otherMachine) {
		if (!(otherMachine instanceof MultiblockCharcoalKiln)) {
			Log.warn("[%s] Charcoal Kiln @ %s is attempting to assimilate a non-Charcoal Kiln machine! That machine's data will be lost!",
					worldObj.isRemote ? "CLIENT" : "SERVER", getReferenceCoord());
			return;
		}
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
	}

	@Override
	protected boolean updateServer() {
		if (!isActive()) {
			return false;
		}
		if (burnTime >= Config.charcoalKilnBurnTime) {
			for(IMultiblockPart part : connectedParts) {
				((TileCharcoalKiln) part).setIsAsh();
			}
		} else {
			burnTime++;
			if (timer >= 50) {
				markReferenceCoordForUpdate();
			}
			timer++;
		}
		return true;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		if (connectedParts.size() < getMinimumNumberOfBlocksForAssembledMachine()) {
			throw new MultiblockValidationException(StatCollector.translateToLocal("multiblock.error.small"));
		}
		BlockPos maximumCoord = getMaximumCoord();
		BlockPos minimumCoord = getMinimumCoord();
		// Quickly check for exceeded dimensions
		int deltaX = maximumCoord.x - minimumCoord.x + 1;
		int deltaY = maximumCoord.y - minimumCoord.y + 1;
		int deltaZ = maximumCoord.z - minimumCoord.z + 1;
		int maxX = getMaximumXSize();
		int maxY = getMaximumYSize();
		int maxZ = getMaximumZSize();
		int minX = getMinimumXSize();
		int minY = getMinimumYSize();
		int minZ = getMinimumZSize();
		if (maxX > 0 && deltaX > maxX) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.large.x", maxX));
		}
		if (maxY > 0 && deltaY > maxY) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.large.y", maxY));
		}
		if (maxZ > 0 && deltaZ > maxZ) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.large.z", maxZ));
		}
		if (deltaX < minX) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.small.x", minX));
		}
		if (deltaY < minY) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.small.y", minY));
		}
		if (deltaZ < minZ) {
			throw new MultiblockValidationException(StatCollector.translateToLocalFormatted("multiblock.error.small.z", minZ));
		}
		testKilnLayer(worldObj, minimumCoord.x, minimumCoord.y, minimumCoord.z, maximumCoord.x, maximumCoord.z);
	}

	protected void testKilnLayer(World world, int x, int y, int z, int maxX, int maxZ) throws MultiblockValidationException {
		int partsOnLayer = 0;
		for(IMultiblockPart part : connectedParts) {
			if (part.yCoord == y) {
				if (part.xCoord < x || part.xCoord > maxX || part.zCoord < z || part.zCoord > maxZ) {
					throw new MultiblockValidationException("To much charcoal kiln block at: " + part.xCoord + ", " + y + ", " + part.zCoord);
				}
				partsOnLayer++;
			}
		}
		if (partsOnLayer < 4) {
			throw new MultiblockValidationException("The charcoal kiln top is to small.");
		}
		for(int posX = x; posX <= maxX; posX++) {
			for(int posZ = z; posZ <= maxZ; posZ++) {
				Block block = world.getBlock(posX, y, posZ);
				if (!(block instanceof BlockCharcoalKiln)) {
					throw new MultiblockValidationException("No charcoal kiln block at: " + posX + ", " + y + ", " + posZ);
				}
			}
		}
		if (y != getMaximumCoord().y) {
			testKilnLayer(worldObj, x + 1, y + 1, z + 1, maxX - 1, maxZ - 1);
		} /*
		 * else{ if(maxX - x != 1){ throw new MultiblockValidationException(
		 * "The charcoal kiln is to small"); } }
		 */
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("BurnTime", burnTime);
		data.setBoolean("IsActive", isActive);
		data.setInteger("Timer", timer);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		burnTime = data.getInteger("BurnTime");
		isActive = data.getBoolean("IsActive");
		timer = data.getInteger("Timer");
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		writeToNBT(data);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		readFromNBT(data);
	}
}
