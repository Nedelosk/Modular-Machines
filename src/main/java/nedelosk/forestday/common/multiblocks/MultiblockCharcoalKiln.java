package nedelosk.forestday.common.multiblocks;

import java.util.ArrayList;

import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.modules.ModuleCoal;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class MultiblockCharcoalKiln extends MultiblockForestday {

	@Override
	public MultiblockPattern createPattern() {
		char[][] charcoal = { { 'O', 'O', 'O', 'O', 'O', }, { 'O', 'C', 'C', 'C', 'O', }, { 'O', 'C', 'C', 'C', 'O', },
				{ 'O', 'C', 'C', 'C', 'O', }, { 'O', 'O', 'O', 'O', 'O', }, };

		char[][] charcoal_O = { { 'O', 'O', 'O', 'O', 'O', }, { 'O', 'O', 'O', 'O', 'O', },
				{ 'O', 'O', 'O', 'O', 'O', }, { 'O', 'O', 'O', 'O', 'O', }, { 'O', 'O', 'O', 'O', 'O', }, };

		char[][] charcoal_Master = { { 'O', 'O', 'O', 'O', 'O', }, { 'O', 'C', 'C', 'C', 'O', },
				{ 'O', 'C', 'M', 'C', 'O', }, { 'O', 'C', 'C', 'C', 'O', }, { 'O', 'O', 'O', 'O', 'O', }, };

		return new MultiblockPattern(new char[][][] { charcoal_O, charcoal, charcoal_Master, charcoal_O }, 2, 2, 2);
	}

	@Override
	public boolean isPatternBlockValid(int x, int y, int z, char pattern, ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
		Block block = base.getWorldObj().getBlock(x, y, z);
		TileEntity baseTile = base.getWorldObj().getTileEntity(x, y, z);
		switch (pattern) {
		case 'C':
			if (block != ModuleCoal.BlockManager.Multiblock_Charcoal_Kiln.block()
					&& !(baseTile instanceof TileCharcoalKiln)) {
				return false;
			}
			break;
		case 'O':
			if (block == ModuleCoal.BlockManager.Multiblock_Charcoal_Kiln.block()
					&& baseTile instanceof TileCharcoalKiln) {
				return false;
			}
			break;
		}
		return true;
	}

	@Override
	public String getMultiblockName() {
		return "charcoalkiln";
	}

	@Override
	public void updateServer(ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
		if (base.burnTimeTotal == 0)
			base.burnTimeTotal = ForestDayConfig.charcoalKilnBurnTime;
		if (!base.isWorking())
			return;
		if (base.burnTime >= base.burnTimeTotal) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for (int x = 0; x < 3; x++) {
				for (int z = 0; z < 3; z++) {
					for (int y = 0; y < 2; y++) {
						int xP = x + base.xCoord - 1;
						int zP = z + base.zCoord - 1;
						int yP = y + base.yCoord - 1;

						if (base.xCoord == xP && base.yCoord == yP && base.zCoord == zP) {

						} else {
							for (ItemStack stack : ((TileCharcoalKiln) base.getWorldObj().getTileEntity(xP, yP,
									zP)).type.charcoalDropps) {
								items.add(stack);
							}
							((TileCharcoalKiln) base.getWorldObj().getTileEntity(xP, yP, zP)).isConsumed = true;
							base.getWorldObj().setBlock(xP, yP, zP, Blocks.air);
						}
					}
				}
			}
			for (ItemStack stack : ((TileCharcoalKiln) base.getWorldObj().getTileEntity(base.xCoord, base.yCoord,
					base.zCoord)).type.charcoalDropps) {
				items.add(stack);
			}
			base.getWorldObj().setBlock(base.xCoord, base.yCoord - 1, base.zCoord,
					ModuleCoal.BlockManager.Multiblock_Charcoal_Kiln.block(), 1, 2);
			((TileCharcoalAsh) base.getWorldObj().getTileEntity(base.xCoord, base.yCoord - 1, base.zCoord))
					.setDropps(items);
			((TileCharcoalKiln) base.getWorldObj().getTileEntity(base.xCoord, base.yCoord,
					base.zCoord)).isConsumed = true;
			base.getWorldObj().setBlock(base.xCoord, base.yCoord, base.zCoord, Blocks.air);
		} else
			base.burnTime++;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

	}

}
