/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.components.block;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import modularmachines.api.modules.components.IModuleComponent;

/**
 * This component can be used to drop items if the module is extracted or a player breaks the block that
 * contains the {@link modularmachines.api.modules.container.IModuleContainer}.
 */
public interface IDropComponent extends IModuleComponent {
	
	void addDrops(NonNullList<ItemStack> drops);
}
