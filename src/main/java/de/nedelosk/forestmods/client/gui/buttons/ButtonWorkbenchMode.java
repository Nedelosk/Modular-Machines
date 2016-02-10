package de.nedelosk.forestmods.client.gui.buttons;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench.Mode;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSwitchWorktableMode;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ButtonWorkbenchMode extends Button<TileWorkbench> {

	private Mode mode;
	private final ResourceLocation texture = new ResourceLocation("forestmods", "textures/gui/button/workbench_mode.png");

	public ButtonWorkbenchMode(int x, int y, Mode mode) {
		super(0, x, y, 20, 20, "");
		this.mode = mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int mx, int my) {
		GL11.glPushMatrix();
		RenderUtil.bindTexture(texture);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, zLevel * 2, 0 + ((mode == Mode.stop_processing) ? 20 : 0), 0, 20, 20, 1F / 40F, 1F / 20F);
		GL11.glPopMatrix();
	}

	@Override
	public List getTooltip(IGuiBase gui) {
		if (mode != null) {
			ArrayList tooltip = new ArrayList();
			tooltip.add(StatCollector.translateToLocal("forestday.tooltip.workbanch.mode." + mode.ordinal()));
			return tooltip;
		}
		return null;
	}

	@Override
	public void onButtonClick(IGuiBase<TileWorkbench> gui) {
		TileWorkbench bench = gui.getTile();
		bench.setMode(bench.getMode() == Mode.further_processing ? Mode.stop_processing : Mode.further_processing);
		setMode(bench.getMode());
		PacketHandler.INSTANCE.sendToServer(new PacketSwitchWorktableMode(gui.getTile()));
	}
}
