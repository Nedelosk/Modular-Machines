package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.IModuleWorkerTime;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.GuiPage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetProgressBar<M extends IModuleWorkerTime> extends Widget<IModuleState<M>> {

	public List<String> jeiTooltip;

	public WidgetProgressBar(int posX, int posY, IModuleState<M> working) {
		super(posX, posY, 22, 17, working);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		super.handleMouseClick(mouseX, mouseY, mouseButton, gui);
		if (provider.getModule() instanceof IModuleJEI) {
			((IModuleJEI) provider.getModule()).openJEI(provider);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		if (provider.getModule().getWorkTimeTotal(provider) != 0) {
			list.add(provider.getModule().getWorkTime(provider) + " / " + provider.getModule().getWorkTimeTotal(provider));
		}
		if (jeiTooltip == null) {
			if (gui instanceof GuiPage) {
				GuiPage guiPage = (GuiPage) gui;
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
		int worktTimeTotal = provider.getModule().getWorkTimeTotal(provider);
		int workTime = provider.getModule().getWorkTime(provider);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int process = (worktTimeTotal == 0) ? 0 : workTime * pos.width / worktTimeTotal;
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 54, 0, pos.width, pos.height);
		if (workTime > 0) {
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 76, 0, process, pos.height);
		}
		GlStateManager.disableAlpha();
	}
}