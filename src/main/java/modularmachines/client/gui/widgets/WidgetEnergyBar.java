package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import modularmachines.client.gui.GuiBase;
import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class WidgetEnergyBar extends Widget {

	private IEnergyStorage storage;
	
	public WidgetEnergyBar(int posX, int posY, IEnergyStorage storage) {
		super(posX, posY, 12, 69);
		this.storage = storage;
	}

	@Override
	public List<String> getTooltip() {
		ArrayList<String> description = new ArrayList<>();
		description.add(storage.getEnergyStored() + " RF / " + storage.getMaxEnergyStored() + " RF");
		return description;
	}

	@Override
	public void draw() {
		if (storage == null) {
			return;
		}
		GuiBase gui = manager.getGui();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		gui.drawTexturedModalRect(gui.getGuiLeft() + positon.x, gui.getGuiTop() + positon.y, 180, 187, 12, 69);
		int energy = (int) ((storage.getEnergyStored() * positon.height) / storage.getMaxEnergyStored());
		gui.drawTexturedModalRect(gui.getGuiLeft() + positon.x, gui.getGuiTop() + positon.y + 69 - energy, 192, 256 - energy, positon.width, energy);
		GlStateManager.disableAlpha();
	}
}