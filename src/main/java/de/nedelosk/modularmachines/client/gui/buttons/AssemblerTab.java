package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblerTab extends Button<GuiAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_machine.png");
	public EnumPosition position;
	public int pageIndex;
	public final boolean right;
	protected int slotIndex;

	public AssemblerTab(int ID, int xPosition, int yPosition, EnumPosition position, boolean right) {
		super(ID, xPosition, yPosition, 28, 21, null);
		this.position = position;
		this.right = right;
		this.slotIndex = position.startSlotIndex;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if(!getGui().getHandler().isAssembled()){
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTexture);
			getGui().getGui().drawTexturedModalRect(xPosition, yPosition, (position.equals(((IPositionedModularAssembler)getGui().getHandler().getAssembler()).getSelectedPosition())) ? 0 : 28,
					right ? 214 : 235, 28, 21);
			ItemStack item = getGui().getHandler().getAssembler().getAssemblerHandler().getStackInSlot(slotIndex);
			if(item != null){
				getGui().drawItemStack(item, xPosition + (right ? 5 : 7), yPosition + 2);
			}
		}
	}

	@Override
	public void onButtonClick() {
		IModularHandler tile = getGui().getHandler();
		if (!((IPositionedModularAssembler)getGui().getHandler().getAssembler()).getSelectedPosition().equals(position)) {
			((IPositionedModularAssembler)getGui().getHandler().getAssembler()).setSelectedPosition(position);
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(Translator.translateToLocal("module.storage." + position.getName() + ".name"));
	}
}
