package modularmachines.common.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import modularmachines.api.material.EnumVanillaMaterials;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.ModularManager;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModuleCasing;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleColoredBlock;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.handlers.IAdvancedModuleContentHandler;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.handlers.block.IBlockModificator;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStoragePage;
import modularmachines.client.core.ClientProxy;
import modularmachines.client.model.ModelManager;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.blocks.tile.TileModular;
import modularmachines.common.config.Config;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.utils.WorldUtil;
import modularmachines.common.utils.content.IClientContentHandler;
import modularmachines.common.utils.content.IColoredBlock;
import modularmachines.common.utils.content.IItemModelRegister;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockModular extends BlockForest implements IItemModelRegister, IColoredBlock, IClientContentHandler {

	public BlockModular() {
		super(Material.IRON, TabModularMachines.tabModules);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("wrench", 0);
		setUnlocalizedName("modular");
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
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (tileModular != null && tileModular.getModular() != null) {
				IModuleState<IModuleCasing> casingState = tileModular.getModular().getModule(IModuleCasing.class);
				if (casingState != null && casingState.getContainer() != null && casingState.getContainer().getItemContainer() != null) {
					if (casingState.getContainer().getItemContainer().getMaterial() == EnumVanillaMaterials.WOOD) {
						return SoundType.WOOD;
					}
				}
			}
		}
		return super.getSoundType(state, world, pos, entity);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (tileModular.isAssembled()) {
				IBlockModificator blockModificator = tileModular.getModular().getBlockModificator();
				if (blockModificator != null) {
					return blockModificator.getHardness();
				}
			}
		}
		return super.getBlockHardness(blockState, world, pos);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (tileModular.isAssembled()) {
				IBlockModificator blockModificator = tileModular.getModular().getBlockModificator();
				if (blockModificator != null) {
					return blockModificator.getResistance() / 5;
				}
			}
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileModular();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity modularHandler = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (modularHandler.getModular() != null && !ModuleManager.getModulesWithPages(modularHandler.getModular()).isEmpty() || modularHandler.getAssembler() != null) {
				if (!world.isRemote) {
					player.openGui(ModularMachines.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity modularHandler = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (modularHandler.isAssembled() && modularHandler.getModular() != null) {
				return !modularHandler.getModular().getModules(IModuleController.class).isEmpty();
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState blockState, EntityPlayer player) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
				IModularHandler modularHandler = tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				Random random = world.rand;
				List<ItemStack> drops = Lists.newArrayList();
				if (modularHandler != null) {
					if (modularHandler.getModular() != null) {
						ItemStack harvestTool = player.getHeldItemMainhand();
						int level = -1;
						if (harvestTool != null && harvestTool.getItem() != null) {
							level = harvestTool.getItem().getHarvestLevel(harvestTool, "wrench", player, blockState);
						}
						if (level >= 0) {
							drops.add(ModularManager.saveModularToItem(new ItemStack(this), modularHandler, player));
						} else {
							for(IModuleProvider provider : modularHandler.getModular().getProviders()) {
								ItemStack itemStack = provider.getItemStack().copy();
								for(IModuleState moduleState : provider.getModuleStates()) {
									if (moduleState != null) {
										for(IModuleContentHandler handler : moduleState.getAllContentHandlers()) {
											if (handler instanceof IAdvancedModuleContentHandler) {
												drops.addAll(((IAdvancedModuleContentHandler) handler).getDrops());
											}
										}
										moduleState.getModule().saveDataToItem(itemStack, moduleState);
									}
								}
								drops.add(testStack(itemStack, random));
							}
							if (!Config.destroyItemsAfterDestroyModular || random.nextBoolean()) {
								drops.add(new ItemStack(ItemManager.itemChassis));
							}
						}
					} else if (modularHandler.getAssembler() != null) {
						IModularAssembler assembler = modularHandler.getAssembler();
						for(IStoragePosition postion : assembler.getStoragePositions()) {
							IStoragePage page = assembler.getStoragePage(postion);
							ItemStack storageStack = assembler.getItemHandler().getStackInSlot(assembler.getIndex(postion));
							drops.add(testStack(storageStack, random));
							if (page != null) {
								IItemHandler itemHandler = page.getItemHandler();
								for(int i = 0; i < itemHandler.getSlots(); i++) {
									drops.add(testStack(itemHandler.getStackInSlot(i), random));
								}
							}
						}
						if (!Config.destroyItemsAfterDestroyModular || random.nextBoolean()) {
							drops.add(new ItemStack(ItemManager.itemChassis));
						}
					}
				}
				WorldUtil.dropItems(world, pos, drops);
			}
		}
	}

	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
				IModularHandler modularHandler = tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				Random random = world.rand;
				List<ItemStack> drops = Lists.newArrayList();
				if (modularHandler != null) {
					if (modularHandler.getModular() != null) {
						for(IModuleProvider provider : modularHandler.getModular().getProviders()) {
							ItemStack itemStack = provider.getItemStack().copy();
							for(IModuleState moduleState : provider.getModuleStates()) {
								if (moduleState != null) {
									for(IModuleContentHandler handler : moduleState.getAllContentHandlers()) {
										if (handler instanceof IAdvancedModuleContentHandler) {
											drops.addAll(((IAdvancedModuleContentHandler) handler).getDrops());
										}
									}
									moduleState.getModule().saveDataToItem(itemStack, moduleState);
								}
							}
							drops.add(testStack(itemStack, random));
						}
					} else if (modularHandler.getAssembler() != null) {
						IModularAssembler assembler = modularHandler.getAssembler();
						for(IStoragePosition postion : assembler.getStoragePositions()) {
							IStoragePage page = assembler.getStoragePage(postion);
							ItemStack storageStack = assembler.getItemHandler().getStackInSlot(assembler.getIndex(postion));
							drops.add(testStack(storageStack, random));
							if (page != null) {
								IItemHandler itemHandler = page.getItemHandler();
								for(int i = 0; i < itemHandler.getSlots(); i++) {
									drops.add(testStack(itemHandler.getStackInSlot(i), random));
								}
							}
						}
					}
				}
				if (!Config.destroyItemsAfterDestroyModular || random.nextBoolean()) {
					drops.add(new ItemStack(ItemManager.itemChassis));
				}
				WorldUtil.dropItems(world, pos, drops);
			}
		}
		super.onBlockExploded(world, pos, explosion);
	}

	private ItemStack testStack(ItemStack stack, Random random) {
		if (!Config.destroyItemsAfterDestroyModular) {
			return stack;
		}
		IModuleItemContainer moduleContainer = ModuleManager.getContainerFromItem(stack);
		if (moduleContainer != null) {
			if (random.nextInt(moduleContainer.getMaterial().getTier()) == 0) {
				return stack;
			}
			return null;
		}
		return stack;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);
		TileEntity tile = world.getTileEntity(pos);
		if (tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
			return ModularManager.saveModularToItem(stack, tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null), player);
		}
		return stack;
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
			IModularHandlerTileEntity modularHandler = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (modularHandler.getFacing() != axis) {
				modularHandler.setFacing(axis);
				modularHandler.markDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
				return true;
			}
		}
		return false;
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
	public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
		if (pos != null) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileModular) {
				IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				if (tileModular.isAssembled()) {
					for(IModuleState<IModuleColoredBlock> moduleState : tileModular.getModular().getModules(IModuleColoredBlock.class)) {
						if (moduleState != null) {
							int color = moduleState.getModule().getColor(moduleState, tintIndex);
							if (color > 0) {
								return color;
							}
						}
					}
				}
			}
		}
		return 0;
	}
}
