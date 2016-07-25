package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ButtonAssemblerAssembleTab extends Button<IModularHandler> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");

	public ButtonAssemblerAssembleTab(int buttonID, int xPos, int yPos) {
		super(buttonID, xPos, yPos, 28, 21, null);
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if(!gui.getHandler().isAssembled()){
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTextureOverlay);
			gui.getGui().drawTexturedModalRect(xPosition, yPosition, 0,
					214, 28, 21);
			try{
				ItemStack stack = ModularManager.writeModularToItem(new ItemStack(BlockManager.blockModular), gui.getHandler().getAssembler().assemble(), gui.getPlayer());
				if(stack != null){
					drawItemStack(stack, xPosition + 5, yPosition + 2);
				}
			}catch(AssemblerException e){
				((GuiAssembler)gui).lastException = e;
			}
		}
	}

	@Override
	public void onButtonClick(IGuiBase<IModularHandler> gui) {
		IModularHandler handler = gui.getHandler();
		try{
			IModular modular = handler.getAssembler().assemble();
			if(modular != null){
				handler.setAssembled(true);
				handler.setModular(modular);
				handler.setAssembler(null);
				PacketHandler.INSTANCE.sendToServer(new PacketModularAssembler(handler, true));
				if(handler instanceof IModularHandlerTileEntity){
					BlockPos pos = ((IModularHandlerTileEntity) handler).getPos();
					handler.getWorld().markBlockRangeForRenderUpdate(pos, pos);
				}
			}
		}catch(AssemblerException e){
			((GuiAssembler)gui).lastException = e;
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularHandler> gui) {
		return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
	}
}
