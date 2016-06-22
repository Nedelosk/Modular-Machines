package de.nedelosk.modularmachines.api.modules.integration;

import de.nedelosk.modularmachines.api.modules.IModule;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleNEI extends IModule {

	@SideOnly(Side.CLIENT)
	INEIPage createNEIPage(IModuleNEI module);
}
