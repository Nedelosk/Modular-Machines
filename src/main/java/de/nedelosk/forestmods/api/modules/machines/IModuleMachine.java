package de.nedelosk.forestmods.api.modules.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.basic.IModuleUpdatable;
import de.nedelosk.forestmods.api.modules.basic.IModuleWithRenderer;
import de.nedelosk.forestmods.api.modules.integration.IModuleNEI;
import de.nedelosk.forestmods.api.modules.integration.IModuleWaila;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.modules.special.IModuleWithItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleMachine extends IModuleNEI, IModuleUpdatable, IModuleWithItem, IModuleController, IModuleWaila, IModuleWithRenderer, IModuleAddable {

	@SideOnly(Side.CLIENT)
	String getFilePath(ModuleStack stack);
}
