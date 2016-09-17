package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblerTab extends Button<GuiAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final IStoragePosition position;
	protected final boolean right;
	protected int slotIndex = -1;

	public AssemblerTab(int ID, int xPosition, int yPosition, IStoragePosition position, boolean right) {
		super(ID, xPosition, yPosition, 28, 21, null);
		this.position = position;
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if(!getGui().getHandler().isAssembled()){
			if(slotIndex < 0){
				this.slotIndex = getGui().getHandler().getAssembler().getIndex(position);
			}
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTexture);
			getGui().getGui().drawTexturedModalRect(xPosition, yPosition, (position.equals(getGui().getHandler().getAssembler().getSelectedPosition())) ? 0 : 28,
					right ? 214 : 235, 28, 21);
			ItemStack item = getGui().getHandler().getAssembler().getItemHandler().getStackInSlot(slotIndex);
			if(item != null){
				getGui().drawItemStack(item, xPosition + (right ? 5 : 7), yPosition + 2);
			}
		}
	}

	@Override
	public void onButtonClick() {
		IModularHandler tile = getGui().getHandler();
		if (!getGui().getHandler().getAssembler().getSelectedPosition().equals(position)) {
			getGui().getHandler().getAssembler().setSelectedPosition(position);
			IModularHandler modularHandler = getGui().getHandler();
			if(modularHandler != null && modularHandler.getWorld() != null){
				if (modularHandler.getWorld().isRemote) {
					PacketHandler.INSTANCE.sendToServer(new PacketSelectAssemblerPosition(modularHandler, position));
				}
			}
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(Translator.translateToLocal("module.storage." + position.getName() + ".name"));
	}
}
