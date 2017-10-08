package modularmachines.api;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILocatable {
	/** Must not be named "getPos to avoid SpecialSource issue https://github.com/md-5/SpecialSource/issues/12 */
	BlockPos getCoordinates();

	/** Must not be named "getWorldObj" to avoid SpecialSource issue https://github.com/md-5/SpecialSource/issues/12 */
	World getWorldObj();
	
	/** Must not be named "canInteractWith" to avoid SpecialSource issue https://github.com/md-5/SpecialSource/issues/12 */
	boolean isUsableByPlayer(EntityPlayer player);
	
	/** Must not be named "markLocatableDirty" to avoid SpecialSource issue https://github.com/md-5/SpecialSource/issues/12 */
	void markLocatableDirty();
	
	/**
	 * @return the facing of this locatable
	 */
	@Nullable
	EnumFacing getFacing();
	
}
