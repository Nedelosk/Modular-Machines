package de.nedelosk.modularmachines.api.modular.renderer;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public interface ISimpleRenderer {

	void render(IRenderState state);
}
