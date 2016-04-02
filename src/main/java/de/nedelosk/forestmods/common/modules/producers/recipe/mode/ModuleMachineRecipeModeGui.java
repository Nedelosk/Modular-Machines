package de.nedelosk.forestmods.common.modules.producers.recipe.mode;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleProducerRecipeGui;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSyncMachineMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModuleMachineRecipeModeGui<M extends IModuleProducerRecipeMode, S extends IModuleProducerRecipeModeSaver> extends ModuleProducerRecipeGui<M, S> {

	public ModuleMachineRecipeModeGui(ModuleUID UID) {
		super(UID);
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack) {
		super.updateGui(base, x, y, modular, stack);
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		IModuleProducerRecipeModeSaver producerSaver = stack.getSaver();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetButtonMode) {
				((WidgetButtonMode) widget).setMode(producerSaver.getCurrentMode());
			}
		}
	}

	@Override
	public void handleMouseClicked(IModularState tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		IModuleProducerRecipeMode module = stack.getModuleStack();
		IModuleProducerRecipeModeSaver moduleSaver = stack.getSaver();
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if (moduleSaver.getCurrentMode().ordinal() == module.getModeClass().getEnumConstants().length - 1) {
				moduleSaver.setCurrentMode(module.getModeClass().getEnumConstants()[0]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getCurrentMode());
			} else {
				moduleSaver.setCurrentMode(module.getModeClass().getEnumConstants()[moduleSaver.getCurrentMode().ordinal() + 1]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getCurrentMode());
			}
			PacketHandler.INSTANCE.sendToServer(new PacketSyncMachineMode((TileEntity) tile, moduleSaver.getCurrentMode()));
		}
	}
}
