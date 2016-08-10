package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModularAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class AssemblerAssembleTab extends Button<GuiAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_machine.png");
	protected ItemStack stack;

	public AssemblerAssembleTab(int ID, int xPosition, int yPosition) {
		super(ID, xPosition, yPosition, 28, 21, null);
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if(!getGui().getHandler().isAssembled()){
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTexture);
			getGui().getGui().drawTexturedModalRect(xPosition, yPosition, 0,
					214, 28, 21);
			try{
				if(getGui().hasChange){
					stack = ModularMachinesApi.saveModular(new ItemStack(BlockManager.blockModular), getGui().getHandler().getAssembler().assemble(), getGui().getPlayer());
					getGui().hasChange = false;
				}
				if(stack != null){
					getGui().drawItemStack(stack, xPosition + 5, yPosition + 2);
				}
			}catch(AssemblerException exception){
				getGui().lastException = exception;
			}
		}
	}

	@Override
	public void onButtonClick() {
		IModularHandler handler = getGui().getHandler();
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
			getGui().lastException = e;
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
	}
}
