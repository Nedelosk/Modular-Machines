package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.GuiPage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetProgressBar extends Widget {

	public int workTime;
	public int worktTimeTotal;
	public List<String> jeiTooltip;

	public WidgetProgressBar(int posX, int posY, int workTime, int workTimeTotal) {
		super(posX, posY, 22, 17);
		this.workTime = workTime;
		this.worktTimeTotal = workTimeTotal;
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> list = new ArrayList<>();
		if (worktTimeTotal != 0) {
			list.add(workTime + " / " + worktTimeTotal);
		}
		if(jeiTooltip == null){
			if(gui instanceof GuiPage){
				GuiPage guiPage = (GuiPage) gui;
				IPage page = guiPage.getPage();
				if(page instanceof IModulePage){
					IModuleState state = ((IModulePage) page).getModuleState();
					if(state.getModule() instanceof IModuleJEI){
						IModuleJEI moduleJei = (IModuleJEI) state.getModule();
						if(moduleJei.getJEIRecipeCategorys(state.getContainer()) != null){
							jeiTooltip = Collections.singletonList(Translator.translateToLocal("jei.tooltip.show.recipes"));
						}
					}
				}
			}
			if(jeiTooltip == null){
				jeiTooltip = new ArrayList<>();
			}
		}
		if(!jeiTooltip.isEmpty()){
			list.addAll(jeiTooltip);
		}
		return list;
	}

	@Override
	public void draw(IGuiProvider gui) {
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