package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.client.gui.GuiBase;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.ModuleUtils;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class WidgetAssembleTab extends Widget {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final boolean isRight;
	protected ItemStack itemStack;

	public WidgetAssembleTab(int xPosition, int yPosition, boolean isRight) {
		super(xPosition, yPosition, 28, 21);
		this.isRight = isRight;
	}
	
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void draw() {
		GuiBase gui = getManager().getGui();
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.drawTexturedModalRect(gui.getGuiLeft() + positon.x, gui.getGuiTop() + positon.y, itemStack.isEmpty() ? 28 : 0, isRight ? 214 : 235, 28, 21);
		if (!itemStack.isEmpty()) {
			gui.drawItemStack(itemStack, gui.getGuiLeft() + positon.x + 5, gui.getGuiTop() + positon.y + 2);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		ILocatableSource source = getManager().getGui().getSource();
		if (source instanceof IModuleLogic) {
			IModuleLogic logic = (IModuleLogic) source;
			try {
				IModular modular = handler.getAssembler().createModular();
				if (modular != null) {
					PacketHandler.sendToServer(new PacketSyncHandlerState(handler, true));
				}
			} catch (AssemblerException e) {
			}
		} else if (source instanceof IAssembler){
			IAssembler assembler = (IAssembler) source;
			PacketHandler.sendToServer(new PacketSyncHandlerState(assembler, false));
		}
	}

	@Override
	public List<String> getTooltip() {
		IModularHandler handler = (IModularHandler) gui.getHandler();
		if (!handler.isAssembled()) {
			return Arrays.asList(Translator.translateToLocal("modular.assembler.assemble"));
		} else {
			return Arrays.asList(Translator.translateToLocal("modular.modular.disassemble"));
		}
	}
}
