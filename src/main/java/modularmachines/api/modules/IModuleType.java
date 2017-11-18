/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules;

import net.minecraft.item.ItemStack;


/**
 * Types can by used to regulate which modules can by used at the same time.
 *
 * Every {@link ModuleData} can have multiple types.
 * <p>
 * You can get the types of a module with {@link ModuleData#getTypes(ItemStack)}
 */
public interface IModuleType {
	
	/**
 	 * @param count the count of modules of this type in the inventory of this {@link IAssembler}.
	 *
	 * @return true if this type of module can by used by so many modules.
	 */
	//void isValidModuleCount(IAssembler assembler, int count, List<AssemblerError> errors);
}
