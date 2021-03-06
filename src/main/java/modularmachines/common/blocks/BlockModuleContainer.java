package modularmachines.common.blocks;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.components.block.INeighborBlockComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.blocks.tile.TileEntityModuleContainer;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.ParticleHelper;
import modularmachines.common.utils.RayTraceHelper;

@SuppressWarnings("deprecation")
public class BlockModuleContainer extends Block {
	
	public BlockModuleContainer() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setTranslationKey("module_container");
		setRegistryName("module_container");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) super.getExtendedState(state, world, pos)).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityModuleContainer();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return false;
		}
		Pair<Vec3d, Vec3d> vectors = RayTraceHelper.getRayTraceVectors(player);
		RayTraceResult hit = container.collisionRayTrace(pos, vectors.getKey(), vectors.getValue());
		if (hit == null) {
			return false;
		}
		return container.onActivated(player, hand, hit);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		return !removeModule(world, pos, player, !player.isCreative()).isEmpty() || super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	private List<ItemStack> removeModule(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean simulate) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return Collections.emptyList();
		}
		Pair<Vec3d, Vec3d> vectors = RayTraceHelper.getRayTraceVectors(player);
		RayTraceResult hit = container.collisionRayTrace(pos, vectors.getKey(), vectors.getValue());
		if (hit == null) {
			return Collections.emptyList();
		}
		return container.extractModule(hit, simulate);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.addAll(removeModule(world, pos, harvesters.get(), false));
		if (drops.isEmpty()) {
			super.getDrops(drops, world, pos, state, fortune);
		}
	}
	
	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return;
		}
		Pair<Vec3d, Vec3d> vectors = RayTraceHelper.getRayTraceVectors(player);
		RayTraceResult hit = container.collisionRayTrace(pos, vectors.getKey(), vectors.getValue());
		if (hit == null) {
			return;
		}
		container.onClick(player, hit);
	}
	
	@Nullable
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		IModuleContainer container = ModuleUtil.getContainer(pos, worldIn);
		if (container == null) {
			return null;
		}
		return container.collisionRayTrace(pos, start, end);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		IModuleContainer container = ModuleUtil.getContainer(pos, worldIn);
		if (container == null) {
			return;
		}
		container.getLocatable().setFacing(placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return FULL_BLOCK_AABB.offset(pos);
		}
		Pair<Vec3d, Vec3d> vectors = RayTraceHelper.getRayTraceVectors(Minecraft.getMinecraft().player);
		RayTraceResult hit = collisionRayTrace(state, world, pos, vectors.getKey(), vectors.getValue());
		if (hit == null) {
			return FULL_BLOCK_AABB.offset(pos);
		}
		IModule module = container.getModule(hit.subHit);
		if (module == null) {
			return FULL_BLOCK_AABB.offset(pos);
		}
		IBoundingBoxComponent component = module.getComponent(IBoundingBoxComponent.class);
		if (component == null) {
			return FULL_BLOCK_AABB.offset(pos);
		}
		return component.getCollisionBox().offset(pos);
	}
	
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		IModuleContainer container = ModuleUtil.getContainer(pos, worldIn);
		if (container == null) {
			return FULL_BLOCK_AABB;
		}
		return container.getBoundingBox();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return ItemStack.EMPTY;
		}
		IModule module = container.getModule(target.subHit);
		if (module == null) {
			return ItemStack.EMPTY;
		}
		return module.getItemStack().copy();
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		IModuleContainer container = ModuleUtil.getContainer(pos, world);
		if (container == null) {
			return false;
		}
		EnumFacing facing = container.getLocatable().getFacing();
		if (facing != axis) {
			container.getLocatable().setFacing(axis);
			container.getLocatable().markLocatableDirty();
			world.markBlockRangeForRenderUpdate(pos, pos);
			return true;
		}
		return false;
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		IModuleContainer moduleContainer = ModuleUtil.getContainer(pos, world);
		if (moduleContainer != null) {
			moduleContainer.getModules().stream().map(m -> m.getComponents(INeighborBlockComponent.class)).flatMap(Collection::stream).forEach(c -> c.onNeighborTileChange(neighbor));
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IModuleContainer moduleContainer = ModuleUtil.getContainer(pos, world);
		if (moduleContainer != null) {
			moduleContainer.getModules().stream().map(m -> m.getComponents(INeighborBlockComponent.class)).flatMap(Collection::stream).forEach(INeighborBlockComponent::onNeighborBlockChange);
		}
	}
	
	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos) {
		return EnumFacing.HORIZONTALS;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
		return ParticleHelper.addHitEffects(world, target, manager, "modularmachines:blocks/casing/bronze_side");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		return ParticleHelper.addDestroyEffects(world, pos, manager, "modularmachines:blocks/casing/bronze_side");
	}
}