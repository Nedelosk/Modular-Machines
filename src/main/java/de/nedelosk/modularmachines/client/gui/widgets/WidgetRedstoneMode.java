package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.controller.EnumRedstoneMode;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncRedstoneMode;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

public class WidgetRedstoneMode extends Widget {

	public IModuleState<IModuleControlled> state;

	public WidgetRedstoneMode(int posX, int posY, IModuleState<IModuleControlled> state) {
		super(posX, posY, 18, 18);
		this.state = state;
	}

	private EnumRedstoneMode getMode(){
		return state.getModule().getModuleControl(state).getRedstoneMode();
	}

	@Override
	public ArrayList<String> getTooltip(IGuiProvider gui) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getMode().getLocName());
		return list;
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		EnumRedstoneMode mode = getMode();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 166 + mode.ordinal() * 18, 0, 18, 18);
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		IModuleControlled module = state.getModule();

		if(mouseButton == 1){
			module.getModuleControl(state).setRedstoneMode(getMode().previous());
		}else{
			module.getModuleControl(state).setRedstoneMode(getMode().next());
		}

		PacketHandler.INSTANCE.sendToServer(new PacketSyncRedstoneMode(state.getModular().getHandler(), state));
	}
}