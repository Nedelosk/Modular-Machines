package de.nedelosk.forestmods.api.producers.handlers;

import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IContentFilter<C> {

	boolean isValid(int index, C content, ModuleStack moduleStack, ForgeDirection facing);
}
