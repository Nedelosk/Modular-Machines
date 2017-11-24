/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.components;

import net.minecraft.util.math.BlockPos;

public interface INeighborBlockComponent extends IModuleComponent {
	
	default void onNeighborTileChange(BlockPos neighbor) {
	}
	
	default void onNeighborBlockChange() {
	}
	
}
