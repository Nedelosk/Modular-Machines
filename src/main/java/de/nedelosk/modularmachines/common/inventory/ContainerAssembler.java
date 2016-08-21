package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class ContainerAssembler<H extends IModularHandler> extends BaseContainer<H> {

	public ContainerAssembler(H tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}
}
