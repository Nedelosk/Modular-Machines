package de.nedelosk.forestmods.library.modules;

import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import net.minecraft.item.ItemStack;

public interface IModuleController extends IModule {

	boolean canAssembleGroup(IAssemblerGroup group);

	IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID);
}