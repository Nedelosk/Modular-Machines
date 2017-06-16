package modularmachines.client.gui.widgets;

import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.energy.IEnergyStorage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends WidgetEnergy {
	
	public WidgetEnergyBar(int posX, int posY, IEnergyStorage storage) {
		super(posX, posY, 12, 69, storage);
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		if (storage == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 180, 187, 12, 69);
		int energy = storage.getEnergyStored();
		if (energy > 0) {
			int scaledEnergy = getEnergyStoredScaled(energy);
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y + 69 - scaledEnergy, 192, 256 - scaledEnergy, pos.width, scaledEnergy);
		}
		GlStateManager.disableAlpha();
	}
}