package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.gui.IGuiBase;
import modularmachines.api.gui.Widget;
import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends Widget<IEnergyBuffer> {

	public WidgetEnergyBar(int posX, int posY, IEnergyBuffer provider) {
		super(posX, posY, 12, 69, provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<>();
		description.add(provider.getEnergyStored() + " RF / " + provider.getCapacity() + " RF");
		return description;
	}

	@Override
	public void draw(IGuiBase gui) {
		if (provider == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, 180, 187, 12, 69);
		int energy = (int) ((provider.getEnergyStored() * pos.height) / provider.getCapacity());
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y + 69 - energy, 192, 256 - energy, pos.width, energy);
		GlStateManager.disableAlpha();
	}
}