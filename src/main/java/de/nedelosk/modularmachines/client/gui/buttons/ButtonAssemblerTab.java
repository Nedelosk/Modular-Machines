package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ButtonAssemblerTab extends Button<IModularHandler> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public EnumPosition position;
	public int pageIndex;
	public final boolean right;
	protected int slotIndex;

	public ButtonAssemblerTab(int buttonID, int xPos, int yPos, EnumPosition position, boolean right) {
		super(buttonID, xPos, yPos, 28, 21, null);
		this.position = position;
		this.right = right;
		this.slotIndex = position.startSlotIndex;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if(!gui.getHandler().isAssembled()){
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTextureOverlay);
			gui.getGui().drawTexturedModalRect(xPosition, yPosition, (position.equals(gui.getHandler().getAssembler().getSelectedPosition())) ? 0 : 28,
					right ? 214 : 235, 28, 21);
			ItemStack item = gui.getHandler().getAssembler().getAssemblerHandler().getStackInSlot(slotIndex);
			if(item != null){
				drawItemStack(item, xPosition + (right ? 5 : 7), yPosition + 2);
			}
		}
	}

	@Override
	public void onButtonClick(IGuiBase<IModularHandler> gui) {
		IModularHandler tile = gui.getHandler();
		if (!tile.getAssembler().getSelectedPosition().equals(position)) {
			tile.getAssembler().setSelectedPosition(position);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularHandler> gui) {
		return Arrays.asList(Translator.translateToLocal("module.storage." + position.getName() + ".name"));
	}
}
