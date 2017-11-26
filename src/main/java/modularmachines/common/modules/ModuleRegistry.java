package modularmachines.common.modules;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleRegistry;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.client.model.module.ModelDataEmpty;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ModBlocks;
import modularmachines.common.modules.container.EmptyModuleContainer;
import modularmachines.common.utils.capabilitys.DefaultStorage;

public enum ModuleRegistry implements IModuleRegistry {
	INSTANCE;
	
	private final Multimap<Item, IModuleDataContainer> containers = HashMultimap.create();
	private final IModuleData defaultData;
	private final IForgeRegistry<IModuleData> registry;
	
	ModuleRegistry() {
		ResourceLocation defaultKey = new ResourceLocation(Constants.MOD_ID, "empty");
		defaultData = ModuleManager.factory.createData().setRegistryName(defaultKey);
		ModelDataEmpty.addModelData(defaultData);
		registry = new RegistryBuilder<IModuleData>()
				.setMaxID(4095)
				.setName(new ResourceLocation("modularmachines:moduledatas"))
				.setType(IModuleData.class)
				.setDefaultKey(defaultKey)
				.create();
		registry.register(defaultData);
		
		CapabilityManager.INSTANCE.register(IModuleContainer.class, new DefaultStorage(), () -> EmptyModuleContainer.INSTANCE);
	}
	
	public IForgeRegistry<IModuleData> getRegistry() {
		return registry;
	}
	
	/**
	 * @return All modules of an IModular that have a page.
	 */
	public List<IModule> getModulesWithComponents(@Nullable IModuleContainer provider) {
		if (provider == null) {
			return Collections.emptyList();
		}
		List<IModule> validModules = Lists.newArrayList();
		for (IModule module : provider.getModules()) {
			if (module != null && !module.getComponents().isEmpty()) {
				validModules.add(module);
			}
		}
		return validModules;
	}
	
	/**
	 * @return The matching module container for the stack.
	 */
	@Nullable
	public IModuleDataContainer getContainerFromItem(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		for (IModuleDataContainer container : containers.get(stack.getItem())) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
	public void registerContainer(IModuleDataContainer container) {
		ItemStack itemStack = container.getParent();
		if (itemStack.isEmpty()) {
			return;
		}
		containers.put(itemStack.getItem(), container);
	}
	
	public Collection<IModuleDataContainer> getContainers() {
		return Collections.unmodifiableCollection(containers.values());
	}
	
	@Override
	public IModuleData getDefaultData() {
		return defaultData;
	}
	
	@Override
	public boolean placeModule(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (heldItem.isEmpty()) {
			return false;
		}
		IModuleDataContainer dataContainer = getContainerFromItem(heldItem);
		if (dataContainer == null) {
			return false;
		}
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		
		if (!block.isReplaceable(world, pos)) {
			pos = pos.offset(facing);
		}
		
		if (!heldItem.isEmpty() && player.canPlayerEdit(pos, facing, heldItem) && world.mayPlace(ModBlocks.moduleStorage, pos, false, facing, null)) {
			world.setBlockState(pos, ModBlocks.moduleStorage.getDefaultState());
			
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity == null || !tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite())) {
				return false;
			}
			IModuleContainer container = tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite());
			if (container == null) {
				return false;
			}
			IModuleHandler handler = container.getHandler();
			if (handler.insertModule(EnumCasingPositions.CENTER, dataContainer, heldItem, world.isRemote)) {
				IBlockState blockState = world.getBlockState(pos);
				blockState.getBlock().onBlockPlacedBy(world, pos, blockState, player, heldItem);
				SoundType soundtype = blockState.getBlock().getSoundType(blockState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				heldItem.shrink(1);
			}
			
			return true;
		}
		return false;
	}
}
