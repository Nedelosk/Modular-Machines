package modularmachines.common.modules.pages;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import modularmachines.api.gui.Button;
import modularmachines.api.modules.ModulePage;
import modularmachines.api.modules.controller.IModuleControl;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.client.gui.widgets.WidgetController;
import modularmachines.client.gui.widgets.WidgetRedstoneMode;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControllerPage extends ModulePage<IModuleControlled> {

	private int usedPage;
	private final int usedPages;
	private final List<Integer> usedModules;
	private int usedByPage;
	private final int usedByPages;
	private final List<Integer> usedByModules;

	public ControllerPage(IModuleState<IModuleControlled> module) {
		super("Controller", "controller", module);
		this.usedModules = new ArrayList<>();
		IModuleControl control = module.getModule().getModuleControl(module);
		for(IModuleState state : module.getModule().getUsedModules(module)) {
			if (state.getIndex() != module.getIndex()) {
				this.usedModules.add(state.getIndex());
				control.setPermission(state, true);
			}
		}
		usedPage = 1;
		int usedPages = usedModules.size() / 9;
		if(usedPages <= 0){
			usedPages = 1;
		}
		this.usedPages = usedPages;


		usedByModules = new ArrayList<>();
		usedByPage = 1;
		for(IModuleState state : module.getModular().getModules()){
			if(state.getIndex() != module.getIndex()){
				if(state.getModule() instanceof IModuleControlled){
					for(IModuleState otherState : ((IModuleControlled)state.getModule()).getUsedModules(state)){
						if(otherState.getIndex() == module.getIndex()){
							usedByModules.add(state.getIndex());
							break;
						}
					}
				}
			}
		}
		int usedByPages = usedByModules.size() / 9;
		if(usedByPages <= 0){
			usedByPages = 1;
		}
		this.usedByPages = usedByPages;
	}

	@Override
	public int getPlayerInvPosition() {
		return -1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		super.drawBackground(mouseX, mouseY);
		Gui.drawRect(gui.getGuiLeft() + 48, gui.getGuiTop() + 56, gui.getGuiLeft() + 48 + 24, gui.getGuiTop() + 56 + 12, 0x30000000);
		Gui.drawRect(gui.getGuiLeft() + 106, gui.getGuiTop() + 56, gui.getGuiLeft() + 106 + 24, gui.getGuiTop() + 56 + 12, 0x30000000);
		RenderUtil.bindTexture( new ResourceLocation("modularmachines", "textures/gui/widgets.png"));

		GlStateManager.color(1F, 1F, 1F, 1F);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + 35, gui.getGuiTop() + 68, 82, 140, 50, 50);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + 93, gui.getGuiTop() + 68, 82, 140, 50, 50);

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);

		String usedText = Translator.translateToLocal("module.page.controller.can.use");
		fontRenderer.drawString(usedText, 79 - fontRenderer.getStringWidth(usedText), 42, Color.WHITE.getRGB(), false);

		String usedPageText = usedPage + "/" + usedPages;
		fontRenderer.drawString(usedPageText, 69 - fontRenderer.getStringWidth(usedPageText), 58, Color.WHITE.getRGB(), true);

		String usedByText = Translator.translateToLocal("module.page.controller.can.used.by");
		fontRenderer.drawSplitString(usedByText, 98, 36, 40, Color.WHITE.getRGB());

		String usedByPageText = usedByPage + "/" + usedByPages;
		fontRenderer.drawString(usedByPageText, 127 - fontRenderer.getStringWidth(usedByPageText), 58, Color.WHITE.getRGB(), true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addButtons() {
		add(new ControllerModulePageButton(0, gui.getGuiLeft() + 35, gui.getGuiTop() + 56, 13, 12, "<"));
		add(new ControllerModulePageButton(1, gui.getGuiLeft() + 72, gui.getGuiTop() + 56, 13, 12, ">"));
		add(new ControllerModulePageButton(2, gui.getGuiLeft() + 93, gui.getGuiTop() + 56, 13, 12, "<"));
		add(new ControllerModulePageButton(3, gui.getGuiLeft() + 130, gui.getGuiTop() + 56, 13, 12, ">"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		add(new WidgetRedstoneMode(6, 9, moduleState));
		if (!usedModules.isEmpty()) {
			int index = 0;
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++) {
					if (usedModules.size() == index) {
						return;
					}
					IModuleState state = moduleState.getModular().getModule(usedModules.get(index));
					add(new WidgetController(36 + x * 16, 69 + y * 16, moduleState, state));
					index++;
				}
			}
		}
		if (!usedByModules.isEmpty()) {
			int index = 0;
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++) {
					if (usedByModules.size() == index) {
						return;
					}
					IModuleState state = moduleState.getModular().getModule(usedByModules.get(index));
					add(new WidgetController(94 + x * 16, 69 + y * 16, moduleState, state, true));
					index++;
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ControllerModulePageButton extends Button{
		public ControllerModulePageButton(int id, int xPos, int yPos, String displayString)
		{
			super(id, xPos, yPos, displayString);
		}

		public ControllerModulePageButton(int id, int xPos, int yPos, int width, int height, String displayString)
		{
			super(id, xPos, yPos, width, height, displayString);
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
				this.mouseDragged(mc, mouseX, mouseY);
				int color = 14737632;

				if (packedFGColour != 0)
				{
					color = packedFGColour;
				}
				else if (!this.enabled)
				{
					color = 10526880;
				}
				else if (this.hovered)
				{
					color = 16777120;
				}

				String buttonText = this.displayString;
				int strWidth = mc.fontRendererObj.getStringWidth(buttonText);
				int ellipsisWidth = mc.fontRendererObj.getStringWidth("...");

				if (strWidth > width - 6 && strWidth > ellipsisWidth) {
					buttonText = mc.fontRendererObj.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
				}

				this.drawCenteredString(mc.fontRendererObj, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
			}
		}
	}
}
