package de.nedelosk.modularmachines.api.modular.renderer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ISimpleRenderer {

	void render(IRenderState state);
}
