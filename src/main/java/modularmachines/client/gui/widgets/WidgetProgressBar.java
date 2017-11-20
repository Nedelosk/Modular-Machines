package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.common.modules.IModuleWorking;
import modularmachines.common.modules.integration.IModuleJEI;
import modularmachines.common.utils.PluginUtil;
import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class WidgetProgressBar<M extends IModuleWorking> extends Widget {
	
	public List<String> jeiTooltip;
	public final IModuleWorking module;
	
	public WidgetProgressBar(int posX, int posY, IModuleWorking module) {
		super(posX, posY, 22, 17);
		this.module = module;
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		super.handleMouseClick(mouseX, mouseY, mouseButton);
		if (module instanceof IModuleJEI) {
			PluginUtil.show(((IModuleJEI) module).getJeiRecipeCategories());
		}
	}
	
	@Override
	public List<String> getTooltip() {
		ArrayList<String> list = new ArrayList<>();
		if (module.getWorkTimeTotal() > 0) {
			list.add(module.getWorkTime() + " / " + module.getWorkTimeTotal());
		}
		if (jeiTooltip == null) {
			/*f (gui instanceof GuiModuleLogic) {
				if (module instanceof IModuleJei) {
					jeiTooltip = Collections.singletonList(Translator.translateToLocal("jei.tooltip.show.recipes"));
				}
			}*/
			if (jeiTooltip == null) {
				jeiTooltip = new ArrayList<>();
			}
		}
		if (!jeiTooltip.isEmpty()) {
			list.addAll(jeiTooltip);
		}
		return list;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 54, 0, pos.width, pos.height);
		int process = getTimeScaled();
		if (process > 0) {
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 76, 0, process, pos.height);
		}
		GlStateManager.disableAlpha();
	}
	
	protected int getTimeScaled() {
		int scale = pos.height;
		return (int) MathHelper.clamp(Math.round(scale * getTimeRatio(module.getWorkTime())), 0, scale);
	}
	
	private double getTimeRatio(int time) {
		if (time <= 0) {
			return 0;
		}
		return (double) time / module.getWorkTimeTotal();
	}
}