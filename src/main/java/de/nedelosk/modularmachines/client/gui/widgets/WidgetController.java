package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncPermission;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class WidgetController extends Widget<IModuleState<IModuleControlled>> {

	public final IModuleState state;
	public final boolean usedBy;

	public WidgetController(int posX, int posY, IModuleState<IModuleControlled> provider, IModuleState state) {
		this(posX, posY, provider, state, false);
	}
	
	public WidgetController(int posX, int posY, IModuleState<IModuleControlled> provider, IModuleState state, boolean usedBy) {
		super(posX, posY, 18, 18, provider);
		this.usedBy = usedBy;
		this.state = state;
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ItemStack itemStack = state.getProvider().getItemStack();
		if (itemStack != null && itemStack.hasDisplayName()) {
			return Arrays.asList(itemStack.getDisplayName());
		}
		return Arrays.asList(state.getContainer().getDisplayName());
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		boolean hasPermission;
		if(usedBy){
			IModuleState<IModuleControlled> state = this.state;
			hasPermission = state.getModule().getModuleControl(state).hasPermission(provider);
		}else{
			hasPermission = provider.getModule().getModuleControl(provider).hasPermission(state);
		}
		gui.drawItemStack(state.getProvider().getItemStack(), sx + pos.x, sy + pos.y);
		Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
		if (!hasPermission) {
			gui.getGui().drawTexturedModalRect(sx + pos.x + 1, sy + pos.y + 1, 130, 0, 14, 14);
		}
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		if(usedBy){
			IModuleState<IModuleControlled> state = this.state;
			state.getModule().getModuleControl(state).setPermission(provider, !state.getModule().getModuleControl(state).hasPermission(provider));
			PacketHandler.sendToServer(new PacketSyncPermission(state.getModular().getHandler(), state, provider));
		}else{
			provider.getModule().getModuleControl(provider).setPermission(state, !provider.getModule().getModuleControl(provider).hasPermission(state));
			PacketHandler.sendToServer(new PacketSyncPermission(state.getModular().getHandler(), provider, state));
		}
	}
}