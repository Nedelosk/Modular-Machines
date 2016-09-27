package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WidgetModuleTab extends Widget<IModuleState> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final IModularHandler moduleHandler;
	public final boolean right;

	public WidgetModuleTab(int xPosition, int yPosition, IModuleState provider, List<IModuleState> modulesWithPages) {
		super(xPosition, yPosition, 28, 21, provider);
		this.right = modulesWithPages.indexOf(provider) >= 7;
		this.moduleHandler = provider.getModular().getHandler();
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, (provider.equals(moduleHandler.getModular().getCurrentModule())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		ItemStack stack = provider.getStack();
		if(stack == null){
			stack = provider.getContainer().getItemStack();
		}
		gui.drawItemStack(stack, gui.getGuiLeft() + pos.x + (right ? 5 : 7), gui.getGuiTop() + pos.y + 2);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
		IModular modular = moduleHandler.getModular();
		IModuleState currentModule = modular.getCurrentModule();
		if (currentModule.getIndex() != provider.getIndex()) {
			modular.setCurrentModule(provider);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModule(moduleHandler, provider));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage(moduleHandler, moduleHandler.getModular().getCurrentPage().getPageID()));
		}
	}

	@Override
	public void setProvider(IModuleState provider) {
	}

	@Override
	public List<String> getTooltip(IGuiProvider gui) {
		ItemStack stack = provider.getStack();
		if(stack != null && stack.hasDisplayName()){
			return Arrays.asList(stack.getDisplayName());
		}
		return Arrays.asList(provider.getContainer().getDisplayName());
	}
}