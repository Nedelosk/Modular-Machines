package de.nedelosk.forestmods.library.modules.handlers;

import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraftforge.common.util.ForgeDirection;

public interface IContentFilter<C, M extends IModule> {

	boolean isValid(int index, C content, M module, ForgeDirection facing);
}
