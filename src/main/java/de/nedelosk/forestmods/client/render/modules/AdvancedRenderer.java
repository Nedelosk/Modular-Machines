package de.nedelosk.forestmods.client.render.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;

@SideOnly(Side.CLIENT)
public abstract class AdvancedRenderer implements ISimpleRenderer {

	@Override
	public void render(IRenderState state) {
		if (state.getItem() != null) {
			renderItem(state);
		} else {
			renderBlock(state);
		}
	}

	protected void renderBlock(IRenderState state) {
	}

	protected void renderItem(IRenderState state) {
	}
}