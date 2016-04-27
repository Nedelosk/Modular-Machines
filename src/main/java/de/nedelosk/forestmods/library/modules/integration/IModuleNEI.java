package de.nedelosk.forestmods.library.modules.integration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleNEI extends IModule {

	@SideOnly(Side.CLIENT)
	INEIPage createNEIPage(IModuleNEI module);
}
