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
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
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
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.listeners.INeighborBlockListener;
import modularmachines.api.modules.listeners.IRedstoneListener;
import modularmachines.client.core.ClientProxy;
import modularmachines.client.model.ModelManager;
import modularmachines.common.ModularMachines;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.blocks.tile.TileModuleStorage;
import modularmachines.common.config.Config;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.RayTraceHelper;
import modularmachines.common.utils.content.IClientContentHandler;
import modularmachines.common.utils.content.IItemModelRegister;

@SuppressWarnings("deprecation")
public class BlockModuleStorage extends Block implements IItemModelRegister, IClientContentHandler {
	
	public BlockModuleStorage() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("wrench", 0);
		setRegistryName("module_storage");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientContent() {
		ModularMachines.proxy.registerStateMapper(this, new ClientProxy.BlockModeStateMapper(new ModelResourceLocation("modularmachines:module_storage")));
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
		return new TileModuleStorage();
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
		if (hit != null) {
			if (player.isSneaking()) {
				ItemStack itemStack = container.extractModule(hit, world.isRemote);
				if (itemStack.isEmpty()) {
					return false;
				}
				ItemHandlerHelper.giveItemToPlayer(player, itemStack);
			} else {
				if (container.insertModule(player.getHeldItem(hand), hit, world.isRemote)) {
					ItemStack itemStack = player.getHeldItem(hand);
					itemStack.shrink(1);
					if (itemStack.isEmpty()) {
						player.setHeldItem(hand, ItemStack.EMPTY);
					}
					if (world.isRemote) {
						world.playSound(player, player.posX, player.posY, player.posZ,
								SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.PLAYERS, 0.6F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					}
					return true;
				}
				return false;
			}
		}
		return true;
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
		container.setFacing(placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		IModuleContainer container = ModuleUtil.getContainer(pos, worldIn);
		if (container == null) {
			return FULL_BLOCK_AABB.offset(pos);
		}
		return container.getBoundingBox().offset(pos);
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
			for (IRedstoneListener listener : ModuleUtil.getModules(provider, IRedstoneListener.class)) {
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
		IModuleDataContainer moduleContainer = ModuleHelper.getContainerFromItem(stack);
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
		EnumFacing facing = container.getFacing();
		if (facing != axis) {
			container.setFacing(axis);
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
			for (INeighborBlockListener changeListener : ModuleUtil.getModules(tileLogic, INeighborBlockListener.class)) {
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
	
}