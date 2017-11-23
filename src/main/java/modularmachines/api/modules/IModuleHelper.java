package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;

/**
 * Can be used to add {@link IModuleDataContainer}s and to place a module in the world.
 */
public interface IModuleHelper {
	
	boolean placeModule(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing);
	
	List<IModule> getModulesWithComponents(@Nullable IModuleContainer provider);
	
	void registerContainer(IModuleDataContainer container);
	
	@Nullable
	IModuleDataContainer getContainerFromItem(ItemStack stack);
	
	Collection<IModuleDataContainer> getContainers();
}
