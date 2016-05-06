package de.nedelosk.forestmods.library.modules;

import java.util.List;

import de.nedelosk.forestmods.library.modular.ModularException;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import net.minecraft.item.ItemStack;

public interface IModuleController extends IModule {

	boolean canAssembleModular(IModuleContainer conatiner, IAssembler assembler, IAssemblerGroup group);

	/**
	 * @return The required modules for that type of modular controller
	 */
	List<Class<? extends IModule>> getRequiredModules();

	IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID);

	boolean isAdvanced();
}