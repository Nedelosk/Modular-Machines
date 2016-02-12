package de.nedelosk.forestmods.common.transport.node;

import de.nedelosk.forestcore.inventory.IGuiHandler;
import de.nedelosk.forestmods.api.transport.ITransportPart;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTransportNode extends TileEntityTransport implements IGuiHandler {

	private ForgeDirection selectedSide;

	@Override
	protected ITransportPart createPart() {
		return new TransportNode(this);
	}

	@Override
	public ITransportNode getPart() {
		return (ITransportNode) part;
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	public void setSelectedSide(ForgeDirection selectedSide) {
		this.selectedSide = selectedSide;
	}

	public ForgeDirection getSelectedSide() {
		return selectedSide;
	}
}
