package de.nedelosk.modularmachines.common.inventory.slots;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAssembler extends Slot implements IAssemblerSlot {

	private final Class<? extends IModule> moduleClass;
	private final EntityPlayer player;
	private final ContainerModularAssembler container;
	public boolean isActive;
	public boolean isUpdated = false;
	private SlotAssembler[] siblings;
	private boolean isAlwaysActive;
	private List<IAssemblerSlot> parents = new ArrayList();

	public SlotAssembler(ContainerModularAssembler container, int index, int xPosition, int yPosition, EntityPlayer player) {
		this(container, index, xPosition, yPosition, player, null, false);
	}

	public SlotAssembler(ContainerModularAssembler container, int index, int xPosition, int yPosition, EntityPlayer player, Class<? extends IModule> moduleClass){
		this(container, index, xPosition, yPosition, player, moduleClass, false);
	}

	public SlotAssembler(ContainerModularAssembler container, int index, int xPosition, int yPosition, EntityPlayer player, Class<? extends IModule> moduleClass, boolean isActive) {
		super(container.getHandler(), index, xPosition, yPosition);

		this.container = container;
		this.player = player;
		this.moduleClass = moduleClass;
		this.isActive = isActive;
	}

	public SlotAssembler setAlwaysActive(boolean isAlwaysActive) {
		this.isAlwaysActive = isAlwaysActive;
		return this;
	}

	public void setSiblings(SlotAssembler[] siblings) {
		this.siblings = siblings;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public void addParent(IAssemblerSlot parent){
		if(!parents.contains(parent)){
			parents.add(parent);
		}
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();

		container.updateSlots();
	}

	@Override
	public void changeStatus(boolean isActive){
		boolean canChange = true;
		for(IAssemblerLogic logic : ModularManager.getAssemblerLogics().values()){
			if(!logic.canChangeStatus(isActive, this, container.lastStorage)){
				canChange = false;
				break;
			}
		}
		if(canChange){
			this.isActive = isActive;
		}
	}

	@Override
	public boolean canBeHovered() {
		return isActive;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(isActive){
			IModuleContainer container = ModularManager.getContainerFromItem(stack);
			if(container != null){
				if(moduleClass == null || moduleClass.isAssignableFrom(container.getModule().getClass())){
					for(IAssemblerLogic logic : ModularManager.getAssemblerLogics().values()){
						if(!logic.isItemValid(stack, this, this.container.lastStorage)){
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Slot getSlot() {
		return this;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public SlotAssembler[] getSiblings() {
		return siblings;
	}

	@Override
	public List<IAssemblerSlot> getParents() {
		return parents;
	}

	@Override
	public Class<? extends IModule> getModuleClass() {
		return moduleClass;
	}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public boolean isUpdated() {
		return isUpdated;
	}

	@Override
	public boolean isAlwaysActive() {
		return isAlwaysActive;
	}
}
