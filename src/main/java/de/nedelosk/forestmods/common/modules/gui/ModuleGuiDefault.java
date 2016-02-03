package de.nedelosk.forestmods.common.modules.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

@SideOnly(Side.CLIENT)
public class ModuleGuiDefault<M extends IModule, S extends IModuleSaver> extends ModuleGui<M, S> {

	public ModuleGuiDefault(String UID) {
		super(UID);
	}

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}
}
