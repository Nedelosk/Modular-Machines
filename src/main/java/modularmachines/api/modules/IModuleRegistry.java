package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;

/**
 * Can be used to add {@link IModuleDataContainer}s and to place a module in the world.
 */
public interface IModuleRegistry {
	
	boolean placeModule(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing);
	
	void registerContainer(IModuleDataContainer container);
	
	@Nullable
	IModuleDataContainer getContainerFromItem(ItemStack stack);
	
	Collection<IModuleDataContainer> getContainers();
	
	IModuleData getDefaultData();
}
