package de.nedelosk.forestmods.api.producers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IProducerWithRenderer extends IModule {

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);
}
