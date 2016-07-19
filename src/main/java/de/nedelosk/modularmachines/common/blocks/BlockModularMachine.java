package de.nedelosk.modularmachines.common.blocks;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.core.ClientProxy;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockPos;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.WorldUtil;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
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
import net.minecraft.nbt.NBTTagCompound;
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

public class BlockModularMachine extends BlockContainerForest implements IItemModelRegister, IStateMapperRegister {

	public BlockModularMachine() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName("modular");
		setCreativeTab(TabModularMachines.tabModules);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ModularMachines.proxy.registerStateMapper(this, new ClientProxy.BlockModeStateMapper(new ModelResourceLocation("modularmachines:modular")));
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileModular){
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			IModuleState<IModuleCasing> casingState = tileModular.getModular().getModules(IModuleCasing.class).get(0);
			return casingState.getModule().getHardness(casingState);
		}
		return super.getBlockHardness(blockState, world, pos);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileModular){
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			IModuleState<IModuleCasing> casingState = tileModular.getModular().getModules(IModuleCasing.class).get(0);
			return casingState.getModule().getResistance(casingState) / 5;
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{ UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState)super.getExtendedState(state, world, pos)).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModular();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (tileModular.getModular() == null || tileModular.getModular().getContainer(tileModular, player.inventory) == null) {
				return false;
			}
			if (!world.isRemote) {
				player.openGui(ModularMachines.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
	}


	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockState) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntity && tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
			IModularHandler modular = tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (modular != null && modular.getModular() != null) {
				List<ItemStack> drops = Lists.newArrayList();
				for(IModuleState state : modular.getModular().getModuleStates()) {
					if (state != null) {
						drops.add(state.getModule().saveDataToItem(state).copy());
						IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
						if(inventory != null){
							for(int i = 0;i < inventory.getSlots();i++){
								ItemStack stack = inventory.getStackInSlot(i);
								if(stack != null){
									drops.add(stack);
								}
							}
						}
					}
				}
				WorldUtil.dropItem(world, pos, drops);
			}
		}
		super.breakBlock(world, pos, blockState);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);
		TileEntity tile = world.getTileEntity(pos);
		if (tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			IModularHandlerItem<IModular, NBTTagCompound> itemHandler = (IModularHandlerItem) stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			itemHandler.setModular(tileModular.getModular().copy(itemHandler));
			itemHandler.setOwner(player.getGameProfile());
			itemHandler.setWorld(world);
			itemHandler.setUID();
			stack.setTagCompound(itemHandler.serializeNBT());
		}
		return stack;
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		IBlockState state = world.getBlockState(pos);
		state = state.getBlock().getActualState(state, world, pos);
		if (state.getMaterial().isToolNotRequired()){
			return true;
		}

		String tool = getHarvestTool(state);
		int harvestLevel = getHarvestLevel(state);

		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileModular){
			IModularHandlerTileEntity tileModular = (IModularHandlerTileEntity) tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			IModuleState<IModuleCasing> casingState = tileModular.getModular().getModules(IModuleCasing.class).get(0);

			tool = casingState.getModule().getHarvestTool(casingState);
			harvestLevel = casingState.getModule().getHarvestLevel(casingState);
		}

		ItemStack stack = player.inventory.getCurrentItem();
		if (stack == null || tool == null){
			return player.canHarvestBlock(state);
		}

		int toolLevel = stack.getItem().getHarvestLevel(stack, tool);
		if (toolLevel < 0){
			return player.canHarvestBlock(state);
		}

		return toolLevel >= harvestLevel;
	}

	@Override
	public boolean isToolEffective(String type, IBlockState state) {
		return type != null && type.equals("pickaxe");
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		float hardness = state.getBlockHardness(world, pos);
		if (hardness < 0.0F)
		{
			return 0.0F;
		}

		if (!canHarvestBlock(world, pos, player))
		{
			return player.getDigSpeed(state, pos) / hardness / 100F;
		}
		else
		{
			return player.getDigSpeed(state, pos) / hardness / 30F;
		}
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
