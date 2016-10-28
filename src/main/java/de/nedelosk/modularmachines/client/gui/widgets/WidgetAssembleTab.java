package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHandlerState;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WidgetAssembleTab extends Widget<ItemStack> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final boolean isRight;

	public WidgetAssembleTab(int xPosition, int yPosition, boolean isRight) {
		super(xPosition, yPosition, 28, 21, null);
		this.isRight = isRight;
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, (provider == null) ? 28 : 0, isRight ? 214 : 235, 28, 21);
		if (provider != null) {
			gui.drawItemStack(provider, gui.getGuiLeft() + pos.x + 5, gui.getGuiTop() + pos.y + 2);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		IModularHandler handler = (IModularHandler) gui.getHandler();
		if (!handler.isAssembled()) {
			try {
				IModular modular = handler.getAssembler().createModular();
				if (modular != null) {
					PacketHandler.sendToServer(new PacketSyncHandlerState(handler, true));
				}
			} catch (AssemblerException e) {
			}
		} else {
			IModularAssembler modularAssembler = handler.getModular().createAssembler();
			if (modularAssembler != null) {
				PacketHandler.sendToServer(new PacketSyncHandlerState(handler, false));
			}
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		IModularHandler handler = (IModularHandler) gui.getHandler();
		if (!handler.isAssembled()) {
			return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
		} else {
			return Arrays.asList(Translator.translateToLocal("modular.modular.disassemble"));
		}
	}
}
