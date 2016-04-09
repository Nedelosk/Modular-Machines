package de.nedelosk.forestmods.common.modules;

import java.util.List;

import com.google.gson.JsonObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleAdvancedWithMode;
import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSyncMachineMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleAdvancedWithMode extends ModuleAdvanced implements IModuleAdvancedWithMode {

	public IMachineMode mode;

	public ModuleAdvancedWithMode(String name, IMachineMode defaultMode) {
		super(name);
		this.mode = defaultMode;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setInteger("Mode", getCurrentMode().ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		setCurrentMode(getModeClass().getEnumConstants()[nbt.getInteger("Mode")]);
	}

	@Override
	public IMachineMode getCurrentMode() {
		return mode;
	}

	@Override
	public void setCurrentMode(IMachineMode mode) {
		this.mode = mode;
	}

	@Override
	public void serializeNBT(NBTTagCompound nbt, Object[] craftingModifiers) {
		IMachineMode mode = (IMachineMode) craftingModifiers[0];
		NBTTagCompound nbtCrafting = new NBTTagCompound();
		nbtCrafting.setInteger("Mode", mode.ordinal());
		nbt.setTag("Crafting", nbtCrafting);
	}

	@Override
	public Object[] deserializeNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = getModeClass().getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[] { mode };
	}

	@Override
	public Object[] deserializeJson(JsonObject object) {
		if (object.has("Mode") && object.get("Mode").isJsonPrimitive()) {
			return new Object[] { getModeClass().getEnumConstants()[object.get("Mode").getAsInt()] };
		}
		return null;
	}

	@Override
	public JsonObject serializeJson(Object[] objects) {
		JsonObject object = new JsonObject();
		object.addProperty("Mode", ((IMachineMode) objects[0]).ordinal());
		return object;
	}

	@Override
	public Object[] getRecipeModifiers() {
		return new Object[] { mode };
	}

	@Override
	protected abstract ProducerAdvancedWithModePage[] createPages();

	public static abstract class ProducerAdvancedWithModePage extends ModulePage<IModuleAdvancedWithMode> {

		public ProducerAdvancedWithModePage(int pageID, IModular modular, ModuleStack moduleStack) {
			super(pageID, modular, moduleStack);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui(int x, int y) {
			super.updateGui(x, y);
			List<Widget> widgets = gui.getWidgetManager().getWidgets();
			for(Widget widget : widgets) {
				if (widget instanceof WidgetButtonMode) {
					((WidgetButtonMode) widget).setMode(moduleStack.getModule().getCurrentMode());
				}
			}
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
			Widget widget = gui.getWidgetManager().getWidgetAtMouse(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
			IModuleAdvancedWithMode module = moduleStack.getModule();
			if (widget instanceof WidgetButtonMode) {
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				if (module.getCurrentMode().ordinal() == module.getModeClass().getEnumConstants().length - 1) {
					module.setCurrentMode(module.getModeClass().getEnumConstants()[0]);
					((WidgetButtonMode) widget).setMode(module.getCurrentMode());
				} else {
					module.setCurrentMode(module.getModeClass().getEnumConstants()[module.getCurrentMode().ordinal() + 1]);
					((WidgetButtonMode) widget).setMode(module.getCurrentMode());
				}
				PacketHandler.INSTANCE.sendToServer(new PacketSyncMachineMode((TileEntity) modular.getTile(), module.getCurrentMode()));
			}
		}
	}
}
