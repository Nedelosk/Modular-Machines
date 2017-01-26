package modularmachines.client.gui.widgets;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends WidgetEnergy {

	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");

	public WidgetEnergyField(int posX, int posY, IEnergyStorage storage) {
		super(posX, posY, 66, 66, storage);
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		if (source == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.texture(widget);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 0, 190, pos.width, pos.height);
		int energy = storage.getEnergyStored();
		if (energy > 0) {
			int scaledEnergy = getEnergyStoredScaled(energy);
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y + 66 - scaledEnergy, 66, 190 + 66 - scaledEnergy, pos.width, scaledEnergy);
		}
		GlStateManager.disableAlpha();
	}
}