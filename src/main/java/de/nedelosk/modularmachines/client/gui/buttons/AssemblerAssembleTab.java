package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class AssemblerAssembleTab extends Button<GuiAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public AssemblerAssembleTab(int ID, int xPosition, int yPosition) {
		super(ID, xPosition, yPosition, 28, 21, null);
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		getGui().getGui().drawTexturedModalRect(xPosition, yPosition, (getGui().modularStack == null) ? 28 : 0, 214, 28, 21);
		if(getGui().modularStack != null){
			getGui().drawItemStack(getGui().modularStack, xPosition + 5, yPosition + 2);
		}
	}

	@Override
	public void onButtonClick() {
		IModularHandler handler = getGui().getHandler();
		try{
			IModular modular = handler.getAssembler().assemble();
			if(modular != null){
				PacketHandler.INSTANCE.sendToServer(new PacketSyncAssembler(handler, true));
			}
		}catch(AssemblerException e){
			getGui().lastException = e;
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
	}
}
