package de.nedelosk.forestmods.api.modules.integration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleNEI extends IModule {

	@SideOnly(Side.CLIENT)
	INEIPage createNEIPage(ModuleStack<IModuleNEI> stack);
}
