package de.nedelosk.forestmods.common.modules.machines.recipe.mode;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeMode;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeGui;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSwitchMachineMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModuleMachineRecipeModeGui<M extends IModuleMachineRecipeMode, S extends IModuleMachineRecipeModeSaver> extends ModuleMachineRecipeGui<M, S> {

	public ModuleMachineRecipeModeGui(String UID) {
		super(UID);
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack) {
		super.updateGui(base, x, y, modular, stack);
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		IModuleMachineRecipeModeSaver producerSaver = stack.getSaver();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetButtonMode) {
				((WidgetButtonMode) widget).setMode(producerSaver.getMode());
			}
		}
	}

	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		IModuleMachineRecipeMode module = stack.getModule();
		IModuleMachineRecipeModeSaver moduleSaver = stack.getSaver();
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if (moduleSaver.getMode().ordinal() == module.getModeClass().getEnumConstants().length - 1) {
				moduleSaver.setMode(module.getModeClass().getEnumConstants()[0]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getMode());
			} else {
				moduleSaver.setMode(module.getModeClass().getEnumConstants()[moduleSaver.getMode().ordinal() + 1]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getMode());
			}
			PacketHandler.INSTANCE.sendToServer(new PacketSwitchMachineMode((TileEntity) tile, moduleSaver.getMode()));
		}
	}
}
