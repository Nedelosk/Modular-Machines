package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
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

public class WidgetRedstoneMode extends Widget<IModuleState<IModuleControlled>> {

	public WidgetRedstoneMode(int posX, int posY, IModuleState<IModuleControlled> provider) {
		super(posX, posY, 18, 18, provider);
	}

	private EnumRedstoneMode getMode(){
		return provider.getModule().getModuleControl(provider).getRedstoneMode();
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		list.add(getMode().getLocName());
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
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
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		IModuleControlled module = provider.getModule();

		if(mouseButton == 1){
			module.getModuleControl(provider).setRedstoneMode(getMode().previous());
		}else{
			module.getModuleControl(provider).setRedstoneMode(getMode().next());
		}

		PacketHandler.sendToServer(new PacketSyncRedstoneMode(provider.getModular().getHandler(), provider));
	}
}