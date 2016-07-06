package de.nedelosk.modularmachines.api.modules.tool;

import de.nedelosk.modularmachines.api.modules.IModule;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleTool extends IModule {

	/**
	 * The size of the tool. A number between 1 and 3.
	 */
	int getSize();


	@SideOnly(Side.CLIENT)
	boolean renderWall();

}
