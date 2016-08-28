package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleModeMachine;
import de.nedelosk.modularmachines.api.recipes.IToolMode;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncMachineMode;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

public class WidgetButtonMode extends Widget {

	public IModuleState<IModuleModeMachine> state;

	public WidgetButtonMode(int posX, int posY, IModuleState<IModuleModeMachine> state) {
		super(posX, posY, 18, 18);
		this.state = state;
	}

	private IToolMode getMode(){
		return state.getModule().getCurrentMode(state);
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(Translator.translateToLocal("mode." + getMode().getName() + ".name"));
		return list;
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 0, 18, 18);
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 18 * getMode().ordinal() + 18, 18, 18);
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		IModuleModeMachine module = state.getModule();

		module.setCurrentMode(state, module.getNextMode(state));
		PacketHandler.INSTANCE.sendToServer(new PacketSyncMachineMode(state.getModular().getHandler(), state));
	}
}