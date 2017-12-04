package modularmachines.common.modules;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

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

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleRegistry;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ModBlocks;
import modularmachines.common.modules.container.EmptyModuleContainer;
import modularmachines.common.modules.data.ModuleType;
import modularmachines.common.utils.capabilitys.DefaultStorage;

public enum ModuleRegistry implements IModuleRegistry {
	INSTANCE;
	
	private final IModuleKeyGenerator defaultGenerator = m -> {
		IModuleData moduleData = m.getData();
		ResourceLocation registryName = moduleData.getRegistryName();
		return registryName == null ? "null" : registryName.getResourcePath();
	};
	
	private final Multimap<Item, IModuleType> types = HashMultimap.create();
	private final IModuleData defaultData;
	private final IForgeRegistry<IModuleData> registry;
	
	ModuleRegistry() {
		ResourceLocation defaultKey = new ResourceLocation(Constants.MOD_ID, "empty");
		defaultData = ModuleManager.factory.createData().setRegistryName(defaultKey);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			//	defaultData.setGenerator(defaultGenerator);
			//	defaultData.setBakery(new BakeryBase());
		}
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
	 * @return The matching module container for the stack.
	 */
	@Nullable
	public IModuleType getTypeFromItem(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		for (IModuleType type : types.get(stack.getItem())) {
			if (type.matches(stack)) {
				return type;
			}
		}
		return null;
	}
	
	public void registerType(IModuleType type) {
		ItemStack itemStack = type.getItem();
		if (itemStack.isEmpty()) {
			return;
		}
		types.put(itemStack.getItem(), type);
	}
	
	@Override
	public void registerType(IModuleData data, ItemStack parent) {
		registerType(new ModuleType(parent, data));
	}
	
	@Override
	public void registerType(ItemStack parent, IModuleData data) {
		registerType(new ModuleType(parent, data));
	}
	
	public Collection<IModuleType> getTypes() {
		return Collections.unmodifiableCollection(types.values());
	}
	
	@Override
	public IModuleData getEmpty() {
		return defaultData;
	}
	
	@Override
	public IModuleKeyGenerator getDefaultGenerator() {
		return defaultGenerator;
	}
	
	@Override
	public boolean placeModule(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (heldItem.isEmpty()) {
			return false;
		}
		IModuleType type = getTypeFromItem(heldItem);
		if (type == null) {
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
			if (handler.insertModule(CasingPosition.CENTER, type, heldItem, world.isRemote)) {
				IBlockState blockState = world.getBlockState(pos);
				blockState.getBlock().onBlockPlacedBy(world, pos, blockState, player, heldItem);
				SoundType soundtype = blockState.getBlock().getSoundType(blockState, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				if (!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
				}
			}
			
			return true;
		}
		return false;
	}
}
