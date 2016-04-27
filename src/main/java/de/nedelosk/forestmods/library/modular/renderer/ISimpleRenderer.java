package de.nedelosk.forestmods.library.modular.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ISimpleRenderer {

	void render(IRenderState state);
}
