package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.modules.positions.CasingPosition;

/**
 * Can be used to add {@link IModuleType}s and to place a module in the world.
 */
public interface IModuleRegistry {
	
	/**
	 * Tries to place to module that is represented by the item that is held by given player.
	 * Currently only works with modules that can be placed at the {@link CasingPosition#CENTER} position.
	 *
	 * @return If the module was played in the world.
	 */
	boolean placeModule(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing);
	
	void registerType(ItemStack parent, IModuleData data);
	
	/**
	 * Registers the given module type.
	 */
	void registerType(IModuleType type);
	
	@Nullable
	IModuleType getTypeFromItem(ItemStack stack);
	
	Collection<IModuleType> getTypes();
	
	/**
	 * @return The module data that is used for empty modules.
	 */
	IModuleData getEmpty();
}
