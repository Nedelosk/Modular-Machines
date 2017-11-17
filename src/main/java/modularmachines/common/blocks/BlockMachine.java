package modularmachines.common.blocks;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.items.IItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.listeners.IDropListener;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.core.ClientProxy;
import modularmachines.client.model.ModelManager;
import modularmachines.common.ModularMachines;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.blocks.tile.TileEntityMachine;
import modularmachines.common.config.Config;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.utils.WorldUtil;
import modularmachines.common.utils.capabilitys.CapabilityUtils;
import modularmachines.common.utils.content.IClientContentHandler;
import modularmachines.common.utils.content.IItemModelRegister;

public class BlockMachine extends Block implements IItemModelRegister, IClientContentHandler {

	public BlockMachine() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("wrench", 0);
		setUnlocalizedName("machine");
		setRegistryName("machine");
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
		ModularMachines.proxy.registerStateMapper(this, new ClientProxy.BlockModeStateMapper(new ModelResourceLocation("modularmachines:modular")));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] { UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS });
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
		return new TileEntityMachine();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	/*@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityMachine tile = WorldUtil.getTile(world, pos, TileEntityMachine.class);
		if (tile != null) {
			if(tile.isAssembled()){
				IModuleLogic logic = tile.logic;
				if(ModuleHelper.getModulesWithComponents(logic).isEmpty()){
					return false;
				}
			}else{
				EnumFacing facing = tile.facing;
				EnumStoragePosition position = EnumStoragePosition.getPositionFromFacing(side, facing);
				if(position != EnumStoragePosition.NONE) {
					IStoragePage page = tile.assembler.getPage(position);
					if(page != null){
					}
				}
			}
			if (!world.isRemote) {
				player.openGui(ModularMachines.instance, tile.isAssembled() ? 1: 0, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}*/

	/*@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if(side == null){
			return false;
		}
		IModuleLogic tileLogic = CapabilityUtils.getCapability(world, pos, ModuleRegistry.MODULE_LOGIC,null);
		if(tileLogic != null){
			for(IRedstoneListener listener : ModuleUtil.getModules(tileLogic, IRedstoneListener.class)){
				if(listener.canProvidePower(state, world, pos, side)){
					return true;
				}
			}
		}
		return false;
	}*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
		dropModules(world, pos, null);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState blockState, EntityPlayer player) {
		dropModules(world, pos, player);
	}
	
	public void dropModules(World world, BlockPos pos, @Nullable EntityPlayer player){
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
					} else {*/
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
	}
	
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

	@Override
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
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		TileEntityMachine tile = WorldUtil.getTile(world, pos, TileEntityMachine.class);
		if (tile != null) {
			if (tile.getFacing() != axis) {
				tile.setFacing(axis);
				tile.markLocatableDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
				return true;
			}
		}
		return false;
	}
	
	/*@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);
		IModuleLogic tileLogic = CapabilityUtils.getCapability(world, pos, ModuleRegistry.MODULE_LOGIC,null);
		if(tileLogic != null){
			for(INeighborBlockListener changeListener : ModuleUtil.getModules(tileLogic, INeighborBlockListener.class)){
				changeListener.onNeighborChange(world, pos, neighbor);
			}
		}
	}*/
	
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
