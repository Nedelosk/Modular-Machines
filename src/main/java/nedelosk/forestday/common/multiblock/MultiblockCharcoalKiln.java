package nedelosk.forestday.common.multiblock;

import nedelosk.forestcore.library.BlockPos;
import nedelosk.forestcore.library.Log;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.RectangularMultiblockControllerBase;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.WoodType;
import nedelosk.forestday.common.configs.ForestDayConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MultiblockCharcoalKiln extends RectangularMultiblockControllerBase {

	private int burnTime;
	private int burnTimeTotal = ForestDayConfig.charcoalKilnBurnTime;
	private boolean isActive;
	private WoodType woodType;

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
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 18;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 2;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 2;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
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
		if (burnTime >= burnTimeTotal) {
			BlockPos min = getMinimumCoord();
			BlockPos max = getMaximumCoord();
			for ( int x = min.x; x < max.x + 1; x++ ) {
				for ( int z = min.z; z < max.z + 1; z++ ) {
					for ( int y = min.y; y < max.y + 1; y++ ) {
						((TileCharcoalKiln) worldObj.getTileEntity(x, y, z)).setIsAsh();
						worldObj.markBlockForUpdate(x, y, z);
					}
				}
			}
		} else {
			burnTime++;
		}
		return false;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		super.isMachineWhole();
		WoodType type = null;
		for ( IMultiblockPart part : connectedParts ) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) part;
			if (type == null) {
				type = kiln.getWoodType();
			} else if (type.equals(kiln.getWoodType())) {
				continue;
			}
			throw new MultiblockValidationException(StatCollector.translateToLocal("multiblock.error.charcoalkiln.notWoodType"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("BurnTime", burnTime);
		data.setInteger("BurnTimeTotal", burnTimeTotal);
		data.setBoolean("IsActive", isActive);
		if (woodType != null) {
			data.setString("WoodType", woodType.getName());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		burnTime = data.getInteger("BurnTime");
		burnTimeTotal = data.getInteger("BurnTimeTotal");
		isActive = data.getBoolean("IsActive");
		if (data.hasKey("WoodType")) {
			woodType = ForestDayCrafting.woodManager.get(data.getString("WoodType"));
		}
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
