package de.nedelosk.modularmachines.common.transport.node;

import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import de.nedelosk.modularmachines.api.transport.ITransportPart;
import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import de.nedelosk.modularmachines.common.transport.TileEntityTransport;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;

public class TileEntityTransportNode extends TileEntityTransport implements IGuiHandler {

	private EnumFacing selectedSide;

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

	public void setSelectedSide(EnumFacing selectedSide) {
		this.selectedSide = selectedSide;
	}

	public EnumFacing getSelectedSide() {
		return selectedSide;
	}
}
