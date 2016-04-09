package de.nedelosk.forestmods.client.render;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.Optional;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.core.BlockManager;
import forestry.arboriculture.WoodType;
import forestry.arboriculture.gadgets.TileWood;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class CharcoalKilnBlockAccessWrapper implements IBlockAccess {

	public IBlockAccess wrapped;
	public ItemStack woodStack;

	public CharcoalKilnBlockAccessWrapper(IBlockAccess wrapped, ItemStack woodStack) {
		this.wrapped = wrapped;
		this.woodStack = woodStack;
	}

	public CharcoalKilnBlockAccessWrapper(IBlockAccess wrapped) {
		this(wrapped, null);
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		Block res = wrapped.getBlock(x, y, z);
		TileEntity te = getTileEntity(x, y, z);
		if (te instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) te;
			if (kiln.isAsh()) {
				res = BlockManager.blockGravel;
			} else if (woodStack != null) {
				res = Block.getBlockFromItem(woodStack.getItem());
			}
		}
		return res;
	}

	@Override
	public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
		if (Loader.isModLoaded("Forestry")) {
			return getTileWood(p_147438_1_, p_147438_2_, p_147438_3_);
		}
		return wrapped.getTileEntity(p_147438_1_, p_147438_2_, p_147438_3_);
	}

	@Optional.Method(modid = "Forestry")
	private TileEntity getTileWood(int x, int y, int z) {
		if (Block.getBlockFromItem(woodStack.getItem()) instanceof forestry.arboriculture.gadgets.BlockLog) {
			if (wrapped.getTileEntity(x, y, z) instanceof TileCharcoalKiln) {
				TileWood tile = new TileWood();
				ObfuscationReflectionHelper.setPrivateValue(TileWood.class, tile, WoodType.getFromCompound(woodStack.getTagCompound()), 0);
				return tile;
			}
		}
		return wrapped.getTileEntity(x, y, z);
	}

	@Override
	public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
		return wrapped.getLightBrightnessForSkyBlocks(p_72802_1_, p_72802_2_, p_72802_3_, p_72802_4_);
	}

	@Override
	public int getBlockMetadata(int x, int y, int z) {
		TileEntity te = wrapped.getTileEntity(x, y, z);
		if (te instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) te;
			if (kiln.isAsh()) {
				return 1;
			} else if (woodStack != null) {
				return woodStack.getItemDamage();
			}
		}
		return wrapped.getBlockMetadata(x, y, z);
	}

	@Override
	public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
		return wrapped.isBlockProvidingPowerTo(p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
	}

	@Override
	public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
		return wrapped.isAirBlock(p_147437_1_, p_147437_2_, p_147437_3_);
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
		return wrapped.getBiomeGenForCoords(p_72807_1_, p_72807_2_);
	}

	@Override
	public int getHeight() {
		return wrapped.getHeight();
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		return wrapped.extendedLevelsInChunkCache();
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
		return wrapped.isSideSolid(x, y, z, side, _default);
	}
}
