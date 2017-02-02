package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class WidgetAssembleTab extends Widget {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final boolean isRight;
	protected ItemStack itemStack;

	public WidgetAssembleTab(int xPosition, int yPosition, boolean isRight) {
		super(xPosition, yPosition, 28, 21);
		this.isRight = isRight;
		this.itemStack = ItemStack.EMPTY;
	}
	
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, itemStack.isEmpty() ? 28 : 0, isRight ? 214 : 235, 28, 21);
		if (!itemStack.isEmpty()) {
			gui.drawItemStack(itemStack, guiLeft + pos.x + 5, guiLeft + pos.y + 2);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		ILocatableSource source = getManager().getGui().getSource();
		if (source instanceof IModuleLogic) {
			IModuleLogic logic = (IModuleLogic) source;
			//TODO: fix
			/*try {
				IModular modular = handler.getAssembler().createModular();
				if (modular != null) {
					PacketHandler.sendToServer(new PacketSyncHandlerState(handler, true));
				}
			} catch (AssemblerException e) {
			}*/
		} else if (source instanceof IAssembler){
			IAssembler assembler = (IAssembler) source;
			PacketHandler.sendToServer(new PacketSyncHandlerState(assembler, false));
		}
	}

	@Override
	public List<String> getTooltip() {
		if (source instanceof IAssembler) {
			return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
		} else {
			return Arrays.asList(Translator.translateToLocal("modular.modular.disassemble"));
		}
	}
}
