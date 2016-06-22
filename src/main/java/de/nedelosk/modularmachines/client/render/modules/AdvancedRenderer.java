package de.nedelosk.modularmachines.client.render.modules;

import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;

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