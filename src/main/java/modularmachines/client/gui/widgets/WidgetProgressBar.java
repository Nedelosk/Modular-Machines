package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.GuiModuleLogic;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

@SideOnly(Side.CLIENT)
public class WidgetProgressBar<M extends IModuleWorkerTime> extends Widget<IModuleState<M>> {

	public List<String> jeiTooltip;

	public WidgetProgressBar(int posX, int posY, IModuleState<M> working) {
		super(posX, posY, 22, 17, working);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		super.handleMouseClick(mouseX, mouseY, mouseButton, gui);
		if (source.getModule() instanceof IModuleJEI) {
			((IModuleJEI) source.getModule()).openJEI(source);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		if (source.getModule().getWorkTimeTotal(source) != 0) {
			list.add(source.getModule().getWorkTime(source) + " / " + source.getModule().getWorkTimeTotal(source));
		}
		if (jeiTooltip == null) {
			if (gui instanceof GuiModuleLogic) {
				GuiModuleLogic guiPage = (GuiModuleLogic) gui;
				IPage page = guiPage.getPage();
				if (page instanceof IModulePage) {
					IModuleState state = ((IModulePage) page).getModuleState();
					if (state.getModule() instanceof IModuleJEI) {
						IModuleJEI moduleJei = (IModuleJEI) state.getModule();
						if (moduleJei.getJEIRecipeCategorys(state.getContainer()) != null) {
							jeiTooltip = Collections.singletonList(Translator.translateToLocal("jei.tooltip.show.recipes"));
						}
					}
				}
			}
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
	public void draw(IGuiBase gui) {
		int worktTimeTotal = source.getModule().getWorkTimeTotal(source);
		int workTime = source.getModule().getWorkTime(source);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int process = (worktTimeTotal == 0) ? 0 : workTime * positon.width / worktTimeTotal;
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y, 54, 0, positon.width, positon.height);
		if (workTime > 0) {
			gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y, 76, 0, process, positon.height);
		}
		GlStateManager.disableAlpha();
	}
}