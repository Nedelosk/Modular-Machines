package de.nedelosk.forestmods.common.blocks;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.blocks.BlockContainerForest;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.Tabs;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileMachineBase;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.core.modules.ModuleForestDay;
import de.nedelosk.forestmods.common.items.ItemCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachinesWood extends BlockContainerForest {

	private final Class<? extends TileMachineBase>[] tiles;

	public BlockMachinesWood(String blockName, Class<? extends TileMachineBase>... tiles) {
		super(Material.wood);
		setStepSound(soundTypeWood);
		setCreativeTab(Tabs.tabForestMods);
		setBlockName(blockName);
		this.tiles = tiles;
	}

	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1 || md == 2) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.625D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1 || md == 2) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.625D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
		int md = iblockaccess.getBlockMetadata(x, y, z);
		if (md == 1 || md == 2) {
			setBlockBounds(0F, 0F, 0F, 1F, 0.625F, 1F);
		} else {
			setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		}
	}

	@Override
	public IIcon getIcon(int blockSide, int blockMeta) {
		return Blocks.planks.getIcon(0, 0);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
		return Blocks.planks.getIcon(0, 0);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(ModuleForestDay.ItemManager.Curb.item(), 1, 0);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCampfire) {
			TileCampfire campfire = (TileCampfire) tile;
			ret.add(campfire.getStackInSlot(4));
		} else {
			ret = super.getDrops(world, x, y, z, metadata, fortune);
		}
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileCampfire
				&& ((TileCampfire) world.getTileEntity(x, y, z)).isWorking) {
			int l = world.getBlockMetadata(x, y, z);
			float f = x + 0.5F;
			float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (player.getCurrentEquippedItem() != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileCampfire) {
				TileCampfire campfile = (TileCampfire) tile;
				if (player.getCurrentEquippedItem().getItem() instanceof ItemCampfire) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, campfile.setCampfireItem(player.getCurrentEquippedItem()));
					return true;
				}
			}
		}
		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileMachineBase) {
			player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		if (!world.isRemote) {
			WorldUtil.dropItems(world, x, y, z);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		try {
			TileMachineBase i = null;
			if (tiles[meta] != null) {
				Constructor<? extends TileMachineBase> itemCtor = tiles[meta].getConstructor();
				i = itemCtor.newInstance();
			}
			if (i != null) {
				return i;
			}
			return null;
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "Caught an exception during TileEntity registration in Forestday:BlockMachines " + tiles[meta].getName());
			throw new LoaderException(e);
		}
	}

	public String getNameFromSide(int side) {
		switch (side) {
			case 0:
				return "down";
			case 1:
				return "top";
			case 2:
				return "back";
			case 3:
				return "front";
			case 4:
				return "side";
			case 5:
				return "side";
			default:
				return null;
		}
	}

	public String getFileFromTier(int tier) {
		switch (tier) {
			case 0:
				return "bricks";
			case 1:
				return "iron";
			case 2:
				return "base";
			case 3:
				return "steel";
			case 4:
				return "forest_steel";
			default:
				return "base";
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for ( int i = 1; i < tiles.length; i++ ) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		if (world.isRemote) {
			return;
		}
		int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		TileMachineBase tile = (TileMachineBase) world.getTileEntity(x, y, z);
		if (player instanceof EntityPlayer) {
			tile.setOwner(((EntityPlayer) player).getGameProfile());
		}
		tile.facing = getFacingForHeading(heading);
		if (world.isRemote) {
			return;
		}
		world.markBlockForUpdate(x, y, z);
	}

	protected short getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return 2;
			case 1:
				return 5;
			case 2:
				return 3;
			case 3:
			default:
				return 4;
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
	}
}
