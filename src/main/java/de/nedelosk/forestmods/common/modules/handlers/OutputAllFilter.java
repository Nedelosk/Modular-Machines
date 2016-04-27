package de.nedelosk.forestmods.common.modules.handlers;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import net.minecraftforge.common.util.ForgeDirection;

public class OutputAllFilter implements IContentFilter {

	@Override
	public boolean isValid(int index, Object content, IModule module, ForgeDirection facing) {
		return true;
	}
}
