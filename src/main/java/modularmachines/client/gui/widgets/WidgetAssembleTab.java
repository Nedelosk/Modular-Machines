package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.ModularMachines;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class WidgetAssembleTab extends Widget {

	protected static final ItemStack ITEM_STACK = new ItemStack(ItemManager.itemChassis);
	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final boolean isRight;

	public WidgetAssembleTab(int xPosition, int yPosition, boolean isRight) {
		super(xPosition, yPosition, 28, 21);
		this.isRight = isRight;
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 0, isRight ? 214 : 235, 28, 21);
		gui.drawItemStack(ITEM_STACK, guiLeft + pos.x + 5, guiTop + pos.y + 3);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		ILocatableSource source = getManager().getGui().getSource();
		if (source instanceof IModuleLogic) {
			IModuleLogic logic = (IModuleLogic) source;
			PacketHandler.sendToServer(new PacketSyncHandlerState(logic, false));
		} else if (source instanceof IAssembler){
			IAssembler assembler = (IAssembler) source;
			PacketHandler.sendToServer(new PacketSyncHandlerState(assembler, true));
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
