package modularmachines.common.modules;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleHelper;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.common.core.managers.ModBlocks;

public enum ModuleHelper implements IModuleHelper {
	INSTANCE;
	
	private final List<IModuleDataContainer> containers = new ArrayList<>();
	
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
		for (IModuleDataContainer container : containers) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
	public void registerContainer(IModuleDataContainer container) {
		containers.add(container);
	}
	
	public List<IModuleDataContainer> getContainers() {
		return Collections.unmodifiableList(containers);
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
