package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnHelper;
import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition;
import de.nedelosk.forestmods.library.ForestModsApi;
import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.forestmods.library.utils.BlockPos;
import de.nedelosk.forestmods.library.utils.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockContainer {

	public BlockCharcoalKiln() {
		super(Material.gourd);
		setHardness(1.0F);
		setBlockName("kiln.charcoal");
		setStepSound(soundTypeWood);
		setCreativeTab(Tabs.tabForestMods);
		setHarvestLevel("axe", 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCharcoalKiln();
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			if (kiln.isConnected() && kiln.getController().isAssembled() && kiln.getController().isActive()) {
				return 10;
			}
		}
		return super.getLightValue(world, x, y, z);
	}

	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("forestmods:ash");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
		if (!kiln.isAsh() && kiln.isConnected() && kiln.getController().isAssembled() && kiln.getController().isActive()) {
			if (kiln.getKilnPosition() != CharcoalKilnPosition.INTERIOR
					|| kiln.getController().getMaximumCoord().y == y && kiln.getKilnPosition() == CharcoalKilnPosition.INTERIOR) {
				float f = x + 0.5F;
				float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
				float f2 = z + 0.5F;
				float f3 = 0.52F;
				float f4 = random.nextFloat() * 0.6F - 0.3F;
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		subItems.addAll(CharcoalKilnHelper.createKilnItems());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		if (!(world.getTileEntity(x, y, z) instanceof TileCharcoalKiln)) {
			return false;
		}
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && (stack.getItem() == Items.flint_and_steel || stack.getItem() == Item.getItemFromBlock(Blocks.torch))) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
			if (kiln.isConnected() && kiln.getController().isAssembled() && !kiln.getController().isActive()) {
				kiln.getController().setIsActive();
				if (stack.getItem() == Items.flint_and_steel) {
					stack.damageItem(1, player);
				}
				BlockPos min = kiln.getController().getMinimumCoord();
				BlockPos max = kiln.getController().getMaximumCoord();
				for(int xPos = min.x; xPos < max.x; xPos++) {
					for(int zPos = min.z; zPos < max.z; zPos++) {
						for(int yPos = min.y; yPos < max.y; yPos++) {
							world.markBlockForUpdate(xPos, yPos, zPos);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			if (kiln.isAsh()) {
				list.add(new ItemStack(Items.coal, 1, 8));
				list.addAll(ForestModsApi.getCharcoalDrops(kiln.getWoodStack()));
			} else {
				list.add(CharcoalKilnHelper.createKiln(kiln.getWoodStack()));
			}
			WorldUtil.dropItem(world, x, y, z, list);
		} else {
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			return !kiln.isConnected() || kiln.isConnected() && !kiln.getController().isAssembled();
		}
		return true;
	}

	@Override
	public int getRenderType() {
		return ClientProxy.charcoalKilnRenderID;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			return CharcoalKilnHelper.createKiln(((TileCharcoalKiln) tile).getWoodStack());
		}
		return super.getPickBlock(target, world, x, y, z);
	}
}
