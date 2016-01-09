package nedelosk.forestday.common.multiblocks;

import java.util.ArrayList;
import java.util.Map;

import nedelosk.forestday.api.ForestDayApi;
import nedelosk.forestday.api.multiblocks.IMultiblock;
import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveTypeString;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import nedelosk.forestday.common.blocks.tiles.TileMachineBase;
import nedelosk.forestday.utils.TileCache;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMultiblockBase<M extends IMultiblock> extends TileMachineBase implements ITileMultiblock<M> {

	public MultiblockModifierValveTypeString modifier;
	public MultiblockPattern pattern;
	public M multiblock;
	public TileCache cache = new TileCache(this);
	public boolean tested;
	public boolean isMaster;
	public boolean isMultiblock;
	public boolean isMultiblockSolid;
	private byte patternX;
	private byte patternY;
	private byte patternZ;

	public TileMultiblockBase(int slots) {
		super(slots);
		this.cache = new TileCache(this);
		timerMax = 100;
		modifier = getModifier();
	}

	public TileMultiblockBase() {
		super();
		timerMax = 100;
		modifier = getModifier();
	}

	@Override
	public MultiblockModifierValveTypeString getModifier() {
		if (modifier == null)
			return new MultiblockModifierValveTypeString();
		return modifier;
	}

	@Override
	public final char getPatternMarker() {
		if (isMaster) {
			if (pattern == null || multiblock == null || !isMultiblock)
				return 'O';
		} else if (master == null || master.getPattern() == null || master.getMultiblock() == null
				|| !master.isMultiblock())
			return 'O';
		if (master != null)
			return master.getPattern().getPatternMarker(patternX, patternY, patternZ);
		else
			return pattern.getPatternMarker(patternX, patternY, patternZ);
	}

	public ITileMultiblock<M> master;

	@Override
	public String getMultiblockName() {
		if (master != null && master.getMultiblock() != null)
			return master.getMultiblock().getMultiblockName();
		return null;
	}

	@Override
	public String getMachineName() {
		return "multiblock." + getMultiblockName();
	}

	public float[] getBlockBounds() {
		if (isMaster) {
			if (!isMultiblock || !tested) {
				return new float[] { 0, 0, 0, 1, 1, 1 };
			}
		} else if (!isMaster)
			if (master == null || master.getMultiblock() == null || !master.isMultiblock() || !master.isTested())
				return new float[] { 0, 0, 0, 1, 1, 1 };
		if (!isMaster)
			return master.getMultiblock().getBlockBounds();
		else
			return multiblock.getBlockBounds();
	}

	@Override
	public void updateServer() {
		updateMultiblock();
		if (isMaster && isMultiblock && multiblock != null)
			multiblock.updateServer(this);
	}

	@Override
	public boolean isStructureTile(TileEntity tile) {
		return tile != null
				&& (tile.getClass() == TileMultiblockBase.class || tile.getClass() == TileMultiblockValve.class);
	}

	@Override
	public void setPatternPosition(byte x, byte y, byte z) {
		patternX = x;
		patternY = y;
		patternZ = z;
	}

	@Override
	public void updateMultiblock() {
		if (timer >= timerMax) {
			testMultiblock();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			timer = 0;
		} else
			timer++;
	}

	@Override
	public void testMultiblock() {
		if (!tested) {
			if (testPatterns()) {
				isMaster = true;
				int xWidth = pattern.getPatternWidthX();
				int zWidth = pattern.getPatternWidthZ();
				int height = pattern.getPatternHeight();

				int xOffset = xCoord - pattern.getMasterOffsetX();
				int yOffset = yCoord - pattern.getMasterOffsetY();
				int zOffset = zCoord - pattern.getMasterOffsetZ();

				for (byte patX = 0; patX < xWidth; patX++) {
					for (byte patY = 0; patY < height; patY++) {
						for (byte patZ = 0; patZ < zWidth; patZ++) {
							int x = patX + xOffset;
							int y = patY + yOffset;
							int z = patZ + zOffset;
							TileEntity tile = this.worldObj.getTileEntity(x, y, z);
							char c = pattern.getPatternMarker(patX, patY, patZ);
							if (c != 'O' && c != 'H') {
								if (tile instanceof TileMultiblockBase) {
									if (multiblock.testBlock()) {
										tested = false;
										isMultiblock = false;
										return;
									}
									((TileMultiblockBase) tile).setMaster(this);
									((TileMultiblockBase) tile).tested = true;
									((TileMultiblockBase) tile).setPatternPosition(patX, patY, patZ);
									worldObj.markBlockForUpdate(x, y, z);
								} else {
									tested = false;
									return;
								}
							}
						}
					}
				}
				isMultiblock = true;
			}
		}
	}

	@Override
	public boolean testPatterns() {
		for (Map.Entry<String, ArrayList<MultiblockPattern>> entry : ForestDayApi.getMutiblockPatterns().entrySet()) {
			for (MultiblockPattern pattern : entry.getValue()) {
				M m;
				boolean isPattern = testPattern(pattern, m = ForestDayApi.getMutiblock(entry.getKey()));
				if (isPattern) {
					this.pattern = pattern;
					multiblock = ForestDayApi.getMutiblock(entry.getKey());
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean testPattern(MultiblockPattern pattern, M multiblock) {
		int xWidth = pattern.getPatternWidthX();
		int zWidth = pattern.getPatternWidthZ();
		int height = pattern.getPatternHeight();

		int xOffset = xCoord - pattern.getMasterOffsetX();
		int yOffset = yCoord - pattern.getMasterOffsetY();
		int zOffset = zCoord - pattern.getMasterOffsetZ();

		for (int patX = 0; patX < xWidth; patX++) {
			for (int patY = 0; patY < height; patY++) {
				for (int patZ = 0; patZ < zWidth; patZ++) {
					int x = patX + xOffset;
					int y = patY + yOffset;
					int z = patZ + zOffset;
					if (!multiblock.isPatternBlockValid(x, y, z, pattern.getPatternMarker(patX, patY, patZ), this)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public IIcon getIcon(int side) {
		if (master != null && master.isMultiblock() && master.getMultiblock() != null) {
			return master.getMultiblock().getIcon(side, this);
		} else if (isMaster && multiblock != null && isMultiblock) {
			return multiblock.getIcon(side, this);
		}
		return null;
	}

	public void onBlockAdded() {
		if (worldObj.isRemote)
			return;
		onBlockChange();
	}

	public void onBlockRemoval() {
		if (worldObj.isRemote)
			return;
		onBlockChange();
		isMaster = false;
		if (master != null)
			if (!master.isMultiblockSolid())
				master.setIsMultiblock(false);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (worldObj != null) {

			nbt.setBoolean("isMaster", isMaster);
			nbt.setBoolean("isMultiblock", isMultiblock);
			nbt.setBoolean("isMultiblockSolid", isMultiblockSolid);
			nbt.setByte("patternX", patternX);
			nbt.setByte("patternY", patternY);
			nbt.setByte("patternZ", patternZ);
			if (modifier != null) {
				NBTTagCompound nbtModifier = new NBTTagCompound();
				modifier.writeToNBT(nbtModifier);
				nbt.setTag("Modifier", nbtModifier);
			}
			if (master != null
					&& worldObj.getTileEntity(master.getXCoord(), master.getYCoord(), master.getZCoord()) != null
					&& worldObj.getTileEntity(master.getXCoord(), master.getYCoord(), master.getZCoord()) == master) {
				nbt.setInteger("masterXCoord", master.getXCoord());
				nbt.setInteger("masterYCoord", master.getYCoord());
				nbt.setInteger("masterZCoord", master.getZCoord());
				if (isMaster)
					if (multiblock != null) {
						nbt.setString("MultiblockName", multiblock.getMultiblockName());
						nbt.setInteger("Pattern",
								ForestDayApi.getMutiblockPatterns(multiblock.getMultiblockName()).indexOf(pattern));
						NBTTagCompound nbtMultiblock = new NBTTagCompound();
						multiblock.writeToNBT(nbtMultiblock);
						nbt.setTag("Multiblock", nbtMultiblock);
					}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		patternX = nbt.getByte("patternX");
		patternY = nbt.getByte("patternY");
		patternZ = nbt.getByte("patternZ");

		this.isMaster = nbt.getBoolean("isMaster");
		this.isMultiblock = nbt.getBoolean("isMultiblock");
		this.isMultiblockSolid = nbt.getBoolean("isMultiblockSolid");
		if (nbt.hasKey("Modifier")) {
			NBTTagCompound nbtModifier = nbt.getCompoundTag("Modifier");
			modifier.readFromNBT(nbtModifier);
		}
		if (nbt.hasKey("masterXCoord") || nbt.hasKey("masterYCoord") || nbt.hasKey("masterZCoord")) {
			if (worldObj == null)
				return;
			if (worldObj.getTileEntity(nbt.getInteger("masterXCoord"), nbt.getInteger("masterYCoord"),
					nbt.getInteger("masterZCoord")) != null
					&& worldObj.getTileEntity(nbt.getInteger("masterXCoord"), nbt.getInteger("masterYCoord"),
							nbt.getInteger("masterZCoord")) instanceof TileMachineBase)
				this.master = (TileMultiblockBase) worldObj.getTileEntity(nbt.getInteger("masterXCoord"),
						nbt.getInteger("masterYCoord"), nbt.getInteger("masterZCoord"));
			if (isMaster) {
				if (nbt.hasKey("Multiblock")) {
					multiblock = ForestDayApi.getMutiblock(nbt.getString("MultiblockName"));
					multiblock.readFromNBT(nbt.getCompoundTag("Multiblock"));
					pattern = ForestDayApi.getMutiblockPatterns(multiblock.getMultiblockName())
							.get(nbt.getInteger("Pattern"));
				}
			}
		} else if (master != null) {
			master = null;
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (worldObj.isRemote)
			return;
		tested = false;
	}

	private void onBlockChange() {
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = cache.getTileOnSide(side);
			if (isStructureTile(tile))
				((TileMultiblockBase) tile).onBlockChange(12);
		}
	}

	@Override
	public void onBlockChange(int depth) {
		depth--;
		if (depth < 0)
			return;
		if (tested || isMultiblock) {
			tested = false;
			if (isMaster)
				isMultiblock = false;

			ITileMultiblock mBlock = getMasterBlock();
			if (mBlock != null) {
				mBlock.onBlockChange(12);
				return;
			}

			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
				TileEntity tile = cache.getTileOnSide(side);
				if (isStructureTile(tile))
					((TileMultiblockBase) tile).onBlockChange(depth);
			}
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (!isMaster) {
			ITileMultiblock mBlock = getMasterBlock();
			if (mBlock != null)
				mBlock.markDirty();
		}
	}

	@Override
	public ITileMultiblock getMasterBlock() {
		if (master != null && !master.isTested()) {
			master = null;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return master;
	}

	@Override
	public void setMaster(ITileMultiblock tile) {
		master = tile;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (isMaster)
			super.setInventorySlotContents(i, itemstack);
		else if (master != null)
			master.setInventorySlotContents(i, itemstack);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (isMaster)
			return super.decrStackSize(slot, amount);
		else if (master != null)
			return master.decrStackSize(slot, amount);
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (isMaster)
			return super.getStackInSlotOnClosing(i);
		else if (master != null)
			return master.getStackInSlotOnClosing(i);
		return null;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (isMaster)
			return super.getStackInSlot(i);
		else if (master != null)
			return master.getStackInSlot(i);
		return null;
	}

	@Override
	public int getSizeInventory() {
		if (isMaster)
			return super.getSizeInventory();
		else if (master != null)
			return master.getSizeInventory();
		return 0;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return master.getMultiblock().getContainer(this, inventory);
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return master.getMultiblock().getGUIContainer(this, inventory);
	}

	@Override
	public void updateClient() {
		if (isMaster && isMultiblock && multiblock != null)
			multiblock.updateClient(this);
	}

	@Override
	public M getMultiblock() {
		return multiblock;
	}

	@Override
	public MultiblockPattern getPattern() {
		return pattern;
	}

	@Override
	public boolean isMultiblock() {
		return isMultiblock;
	}

	@Override
	public boolean isMultiblockSolid() {
		return isMultiblockSolid;
	}

	@Override
	public boolean isTested() {
		return tested;
	}

	@Override
	public void setIsMultiblock(boolean isMultiblock) {
		this.isMultiblock = isMultiblock;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public int getYCoord() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public boolean isWorking() {
		return isWorking;
	}

	@Override
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	@Override
	public void onCreateMultiBlock() {

	}

}
