package modularmachines.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.client.model.ModelManager;
import modularmachines.common.blocks.tile.TileModuleCrafter;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.WorldUtil;
import modularmachines.common.utils.content.IItemModelRegister;

public class BlockModuleCrafter extends BlockForest implements IItemModelRegister, IBlockWithMeta {

	public BlockModuleCrafter() {
		super(Material.GROUND, TabModularMachines.tabModules);
		setUnlocalizedName("module_crafter");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModuleCrafter) {
			if (!world.isRemote) {
				player.openGui(ModularMachines.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileModuleCrafter();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockState) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModuleCrafter) {
			WorldUtil.dropItems(world, pos);
		}
		super.breakBlock(world, pos, blockState);
	}

	@Override
	public String getNameFromMeta(int meta) {
		return "default";
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
}
