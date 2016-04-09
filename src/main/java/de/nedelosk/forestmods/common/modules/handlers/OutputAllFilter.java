package de.nedelosk.forestmods.common.modules.handlers;

import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;

public class OutputAllFilter implements IContentFilter {

	@Override
	public boolean isValid(int index, Object content, ModuleStack moduleStack, ForgeDirection facing) {
		return true;
	}
}
