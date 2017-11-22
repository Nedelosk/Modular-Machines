package modularmachines.common.blocks;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IBoundingBoxComponent;
import modularmachines.api.modules.components.INeighborBlockComponent;
import modularmachines.api.modules.components.IRedstoneComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.blocks.tile.TileEntityModuleContainer;
import modularmachines.common.config.Config;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.RayTraceHelper;

@SuppressWarnings("deprecation")
public class BlockModuleContainer extends Block {
	
	public BlockModuleContainer() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("wrench", 0);
		setUnlocalizedName("module_container");
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
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
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
		RayTraceResult hit = collisionRayTrace(state, world, pos, vectors.getKey(), vectors.getValue());
		if (hit == null) {
			return false;
		}
		return container.onActivated(player, hand, hit);
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
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
		IBoundingBoxComponent component = module.getInterface(IBoundingBoxComponent.class);
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
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side == null) {
			return false;
		}
		IModuleContainer provider = ModuleUtil.getContainer(pos, world);
		if (provider != null) {
			for (IRedstoneComponent listener : ModuleUtil.getModules(provider, IRedstoneComponent.class)) {
				if (listener.canProvidePower(state, world, pos, side)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		super.getSubBlocks(itemIn, items);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
		//dropModules(world, pos, null);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState blockState, EntityPlayer player) {
		//dropModules(world, pos, player);
	}
	
	/*public void dropModules(World world, BlockPos pos, @Nullable EntityPlayer player){
		if (!world.isRemote) {
			IModuleLogic tileLogic = CapabilityUtils.getCapability(world, pos, ModuleRegistry.MODULE_LOGIC,null);
			IAssembler assembler = CapabilityUtils.getCapability(world, pos, ModuleRegistry.ASSEMBLER,null);
			if (tileLogic != null) {
				Random random = world.rand;
				List<ItemStack> drops = Lists.newArrayList();
				if (tileLogic != null) {
					/*ItemStack harvestTool = player.getHeldItemMainhand();
					int level = -1;
					if (harvestTool != null && harvestTool.getItem() != null) {
						level = harvestTool.getItem().getHarvestLevel(harvestTool, "wrench", player, blockState);
					}
					if (level >= 0) {
						drops.add(ModuleHelper.saveModularToItem(new ItemStack(this), tileLogic, player));
					} else {
					for (Module module : tileLogic.getModules()) {
						for (IModuleComponent component : module.getComponents()) {
							if (component instanceof IDropListener) {
								drops.addAll(((IDropListener) component).getDrops(player));
							}
						}
						for(ItemStack itemStack : module.getDrops()) {
							//moduleState.getModule().saveDataToItem(itemStack, moduleState);
							drops.add(getDropStack(itemStack, random));
						}
					}
					if (!Config.destroyModules || random.nextBoolean()) {
						drops.add(new ItemStack(ItemManager.itemChassis));
					}
					//}
				} else if (assembler != null) {
					for (IStoragePosition postion : assembler.getPositions()) {
						IStoragePage page = assembler.getPage(postion);
						ItemStack storageStack = page.getStorageStack();
						drops.add(getDropStack(storageStack, random));
						if (page != null) {
							IItemHandler itemHandler = page.getItemHandler();
							for (int i = 0; i < itemHandler.getSlots(); i++) {
								drops.add(getDropStack(itemHandler.getStackInSlot(i), random));
							}
						}
					}
					if (!Config.destroyModules || random.nextBoolean()) {
						drops.add(new ItemStack(ItemManager.itemChassis));
					}
				}
				WorldUtil.dropItems(world, pos, drops);
			}
		}
	}*/
	
	private ItemStack getDropStack(ItemStack stack, Random random) {
		if (!Config.destroyModules) {
			return stack;
		}
		IModuleDataContainer moduleContainer = ModuleManager.helper.getContainerFromItem(stack);
		if (moduleContainer != null) {
			if (random.nextFloat() < moduleContainer.getData().getDropChance()) {
				return stack;
			}
			return ItemStack.EMPTY;
		}
		return stack;
	}
	
	/*@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);
		IModuleLogic itemLogic = CapabilityUtils.getCapability(stack, ModuleRegistry.MODULE_LOGIC, null);
		IModuleLogic tileLogic = CapabilityUtils.getCapability(world, pos, ModuleRegistry.MODULE_LOGIC,null);
		if(tileLogic != null && itemLogic != null) {
			NBTTagCompound compound = tileLogic.writeToNBT(new NBTTagCompound());
			itemLogic.readFromNBT(compound);
			stack.setTagCompound(compound);
			return stack;
		}
		return stack;
	}*/
	
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
		super.onNeighborChange(world, pos, neighbor);
		IModuleContainer tileLogic = ModuleUtil.getContainer(pos, world);
		if (tileLogic != null) {
			for (INeighborBlockComponent changeListener : ModuleUtil.getModules(tileLogic, INeighborBlockComponent.class)) {
				changeListener.onNeighborChange(world, pos, neighbor);
			}
		}
	}
	
	@Override
	public EnumFacing[] getValidRotations(World world, BlockPos pos) {
		return EnumFacing.HORIZONTALS;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Collections.emptyList();
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
}