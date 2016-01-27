package nedelosk.forestday.common.multiblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.utils.WorldUtil;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.WoodType;
import nedelosk.forestday.modules.ModuleCoal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockContainer {

	public BlockCharcoalKiln() {
		super(Material.wood);
		setHardness(1.0F);
		setBlockName("kiln.charcoal");
		setStepSound(soundTypeWood);
		setCreativeTab(Tabs.tabForestday);
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
		blockIcon = IIconRegister.registerIcon("forestday:ash");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
		if (!kiln.isAsh() && kiln.isConnected() && kiln.getController().isAssembled() && kiln.getController().isActive()) {
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

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for ( WoodType type : ForestDayCrafting.woodManager.getWoodTypes().values() ) {
			subItems.add(ModuleCoal.writeWoodType(type));
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln && !((TileCharcoalKiln) tile).isAsh()) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			list.add(ModuleCoal.writeWoodType(kiln.getWoodType()));
			WorldUtil.dropItem(world, x, y, z, list);
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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			return ModuleCoal.writeWoodType(((TileCharcoalKiln) tile).getWoodType());
		}
		return super.getPickBlock(target, world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (player.isSneaking()) {
			return false;
		}
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		IMultiblockPart part = null;
		MultiblockControllerBase controller = null;
		if (te instanceof IMultiblockPart) {
			part = (IMultiblockPart) te;
			controller = part.getMultiblockController();
		}
		if (player.getCurrentEquippedItem() == null) {
			if (controller != null) {
				Exception e = controller.getLastValidationException();
				if (e != null) {
					player.addChatMessage(new ChatComponentText(e.getMessage()));
					return true;
				}
			} else {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("multiblock.error.notConnected", "charcoalkiln")));
				return true;
			}
		}
		// If nonempty, or there was no error, just fall through
		return false;
	}
}
