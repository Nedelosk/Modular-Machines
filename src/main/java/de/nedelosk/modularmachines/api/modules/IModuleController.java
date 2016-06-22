package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import net.minecraft.item.ItemStack;

public interface IModuleController extends IModule {

	boolean canAssembleGroup(IAssemblerGroup group);

	IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID);
}