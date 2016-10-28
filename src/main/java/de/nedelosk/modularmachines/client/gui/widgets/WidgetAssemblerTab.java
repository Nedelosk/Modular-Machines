package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WidgetAssemblerTab extends Widget<IModularAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final IStoragePosition position;
	protected final boolean right;

	public WidgetAssemblerTab(int xPosition, int yPosition, IModularAssembler provider, IStoragePosition position, boolean right) {
		super(xPosition, yPosition, 28, 21, provider);
		this.position = position;
		this.right = right;
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, (position.equals(provider.getSelectedPosition())) ? 0 : 28, right ? 214 : 235, 28, 21);
		IStoragePage page = provider.getStoragePage(position);
		if (page != null) {
			ItemStack item = page.getStorageStack();
			if (item != null) {
				gui.drawItemStack(item, gui.getGuiLeft() + pos.x + (right ? 5 : 7), gui.getGuiTop() + pos.y + 2);
			}
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		if (!provider.getSelectedPosition().equals(position)) {
			provider.setSelectedPosition(position);
			IModularHandler modularHandler = provider.getHandler();
			if (modularHandler != null && modularHandler.getWorld() != null) {
				if (modularHandler.getWorld().isRemote) {
					PacketHandler.sendToServer(new PacketSelectAssemblerPosition(modularHandler, position));
				}
			}
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		return Arrays.asList(Translator.translateToLocal("module.storage." + position.getName() + ".name"));
	}
}
