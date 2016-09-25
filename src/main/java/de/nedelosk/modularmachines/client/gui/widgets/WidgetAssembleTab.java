package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WidgetAssembleTab extends Widget<ItemStack> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");

	public WidgetAssembleTab(int xPosition, int yPosition) {
		super(xPosition, yPosition, 28, 21, null);
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, (provider == null) ? 28 : 0, 214, 28, 21);
		if(provider != null){
			gui.drawItemStack(provider, gui.getGuiLeft() + pos.x + 5, gui.getGuiTop() + pos.y + 2);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
		IModularHandler handler = (IModularHandler) gui.getHandler();
		try{
			IModular modular = handler.getAssembler().assemble();
			if(modular != null){
				PacketHandler.INSTANCE.sendToServer(new PacketSyncAssembler(handler, true));
			}
		}catch(AssemblerException e){
		}
	}

	@Override
	public List<String> getTooltip(IGuiProvider gui) {
		return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
	}
}
