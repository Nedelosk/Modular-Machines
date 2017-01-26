package modularmachines.client.gui.widgets;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

@SideOnly(Side.CLIENT)
public class WidgetEnergyField extends Widget<IEnergyBuffer> {

	public static final NumberFormat NF = NumberFormat.getIntegerInstance();
	private final ResourceLocation widget = new ResourceLocation("modularmachines", "textures/gui/widgets.png");

	public WidgetEnergyField(int posX, int posY, IEnergyBuffer provider) {
		super(posX, posY, 66, 66, provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> description = new ArrayList<>();
		description.add(TextFormatting.WHITE + NF.format(source.getEnergyStored()) + " " + Translator.translateToLocal("mm.tooltip.widget.energy.field.of"));
		description.add(TextFormatting.WHITE + NF.format(source.getCapacity()) + " " + TextFormatting.GRAY + Translator.translateToLocal("mm.tooltip.widget.energy.field.rf"));
		return description;
	}

	@Override
	public void draw(IGuiBase gui) {
		if (source == null) {
			return;
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widget);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + positon.x, gui.getGuiTop() + positon.y, 0, 190, 66, 66);
		if (this.source.getEnergyStored() > 0) {
			int eS = (int) (this.source.getEnergyStored() / 100 * 66);
			int energy = (int) (eS / (this.source.getCapacity() / 100));
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + positon.x, gui.getGuiTop() + positon.y + 66 - energy, 66, 190 + 66 - energy, positon.width, energy);
		}
		GlStateManager.disableAlpha();
	}
}