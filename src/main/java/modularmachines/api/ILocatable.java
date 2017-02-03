package modularmachines.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILocatable {

	BlockPos getCoordinates();

	/** Must not be named "getWorldObj" to avoid SpecialSource issue https://github.com/md-5/SpecialSource/issues/12 */
	World getWorldObj();
	
	void markLocatableDirty();
	
}
