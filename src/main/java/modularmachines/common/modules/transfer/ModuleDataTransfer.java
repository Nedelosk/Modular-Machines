/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.transfer;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleType;
import modularmachines.common.modules.ModuleDataCasingPosition;
import modularmachines.common.modules.ModuleType;

public class ModuleDataTransfer extends ModuleDataCasingPosition {
	public ModuleDataTransfer() {
	}
	
	@Override
	public IModuleType[] getTypes(ItemStack itemStack) {
		return new IModuleType[]{ModuleType.TRANSFER};
	}
}
