/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.components;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface INeighborBlockComponent extends IModuleComponent {
	
	void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor);
	
}
