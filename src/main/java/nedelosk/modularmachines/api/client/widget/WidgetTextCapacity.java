package nedelosk.modularmachines.api.client.widget;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketTankManager;
import nedelosk.modularmachines.api.utils.ModularUtils;
import net.minecraft.tileentity.TileEntity;

public class WidgetTextCapacity<T extends TileEntity & IModularTileEntity> extends WidgetTextField<T> {

	public int ID;
	public int capacity;

	public WidgetTextCapacity(int posX, int posY, int ID) {
		super(posX, posY, 64, 18);
		this.ID = ID;
	}

	public int getCapacity() {
		return Integer.parseInt(getText());
	}

	@Override
	public boolean keyTyped(char keyChar, int keyCode, IGuiBase<T> gui) {
		if (keyCode != 28 && keyCode != 156) {
			return super.keyTyped(keyChar, keyCode, gui);
		} else {
			String capacityString = getText().trim();
			capacity = Integer.parseInt(capacityString);
			ModularUtils.getTankManager(gui.getTile().getModular()).getSaver().getData(ID).setCapacity(capacity);
			ModularUtils.getTankManager(gui.getTile().getModular()).getSaver().setUnusedCapacity(-capacity);
			PacketHandler.INSTANCE.sendToServer(new PacketTankManager(gui.getTile(), capacity, ID));
			return super.keyTyped(keyChar, keyCode, gui);
		}
	}

	@Override
	public void writeText(String text) {
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return;
		}
		super.writeText(text);
	}
}
