package de.nedelosk.modularmachines.common.inventory.slots;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAssembler extends Slot {

	private Class<? extends IModule> moduleClass;
	private EntityPlayer player;
	private ContainerModular container;

	public SlotAssembler(ContainerModularAssembler container, int index, int xPosition, int yPosition, EntityPlayer player) {
		this(container, index, xPosition, yPosition, player, null);
	}

	public SlotAssembler(ContainerModularAssembler container, int index, int xPosition, int yPosition, EntityPlayer player, Class<? extends IModule> moduleClass) {
		super(container.getHandler(), index, xPosition, yPosition);

		this.player = player;
		this.moduleClass = moduleClass;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();

		container.onCraftMatrixChanged(inventory);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer container = ModularManager.getContainerFromItem(stack);
		if(container != null){
			return moduleClass == null || moduleClass.isAssignableFrom(container.getModule().getClass());
		}
		return false;
	}
}
