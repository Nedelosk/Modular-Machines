package de.nedelosk.modularmachines.client.gui.buttons;

import de.nedelosk.modularmachines.client.gui.Button;
import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import net.minecraft.client.Minecraft;

public class ButtonNodeSide extends Button<TileEntityTransportNode> {

	public ButtonNodeSide(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
	}
}
