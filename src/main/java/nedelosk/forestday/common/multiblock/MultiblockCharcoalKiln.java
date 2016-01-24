package nedelosk.forestday.common.multiblock;

import nedelosk.forestcore.library.BlockPos;
import nedelosk.forestcore.library.Log;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.RectangularMultiblockControllerBase;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.WoodType;
import nedelosk.forestday.common.blocks.tiles.TileAsh;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.modules.ModuleCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MultiblockCharcoalKiln extends RectangularMultiblockControllerBase {

	private int burnTime;
	private int burnTimeTotal = ForestDayConfig.charcoalKilnBurnTime;
	private boolean isActive;
	private WoodType woodType;
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
	
	public int getBurnTimeTotal() {
		return burnTimeTotal;
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
					for ( int y = min.y; y < max.y + 1;y++) {
						TileCharcoalKiln kiln = (TileCharcoalKiln) worldObj.getTileEntity(x, y, z);
						if(kiln.getLevel() == 0){
							TileCharcoalKiln kiln1 = (TileCharcoalKiln) worldObj.getTileEntity(x, y + 1, z);
							worldObj.setBlock(x, y, z, ModuleCore.BlockManager.Gravel.block(), 1, 3);
							TileEntity tile = worldObj.getTileEntity(x, y, z);
							if(tile instanceof TileAsh){
								((TileAsh) tile).setWoodTypes(new WoodType[]{kiln.getWoodType(), kiln1.getWoodType()});
							}
							worldObj.markBlockForUpdate(x, y, z);
						}else{
							worldObj.setBlockToAir(x, y, z);
						}
					}
				}
			}
		} else {
			burnTime++;
			if(timer >= 50){
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
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("BurnTime", burnTime);
		data.setInteger("BurnTimeTotal", burnTimeTotal);
		data.setBoolean("IsActive", isActive);
		data.setInteger("Timer", timer);
		if (woodType != null) {
			data.setString("WoodType", woodType.getName());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		burnTime = data.getInteger("BurnTime");
		burnTimeTotal = data.getInteger("BurnTimeTotal");
		isActive = data.getBoolean("IsActive");
		timer = data.getInteger("Timer");
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
