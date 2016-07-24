package de.nedelosk.modularmachines.common.modular.assembler;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class AssemblerLogic implements IAssemblerLogic {

	public final IModularAssembler assembler;
	public final EnumPosition position;

	public AssemblerLogic(IModularAssembler assembler, EnumPosition position) {
		this.assembler = assembler;
		this.position = position;
	}

	@Override
	public boolean isItemValid(ItemStack stack, Slot slot, Slot storageSlot) {
		EnumPosition pos = assembler.getSelectedPosition();
		IItemHandler itemHandler = assembler.getAssemblerHandler();
		int index = slot.getSlotIndex() - pos.startSlotIndex;
		IModuleContainer container = ModularManager.getContainerFromItem(stack);
		if(container == null){
			return false;
		}
		switch (pos) {
			case INTERNAL:
				if(storageSlot == null){
					if(container.getModule() instanceof IModuleCasing){
						return true;
					}
				}else{
					if(!storageSlot.getHasStack()){
						return false;
					}
					if(container.getModule().getPosition(container) == pos){
						return true;
					}
				}
				break;
			default:
				if(storageSlot == null){
					if(container.getModule() instanceof IModuleModuleStorage){
						if(((IModuleModuleStorage)container.getModule()).canUseFor(pos, container)){
							return true;
						}
					}
				}else{
					if(!storageSlot.getHasStack()){
						return false;
					}
					if(container.getModule().getPosition(container) != pos){
						return false;
					}
					EnumModuleSize usedSize = null;
					for(int i = pos.startSlotIndex + 1;i < pos.endSlotIndex + 1;i++){
						IModuleContainer otherContainer = ModularManager.getContainerFromItem(itemHandler.getStackInSlot(i));
						if(otherContainer != null){
							usedSize = EnumModuleSize.getNewSize(usedSize, otherContainer.getModule().getSize());
						}
					}
					usedSize = EnumModuleSize.getNewSize(usedSize, container.getModule().getSize());
					if(usedSize != EnumModuleSize.UNKNOWN){
						return true;
					}
				}
				break;
		}
		return false;
	}

	@Override
	public void canAssemble(IModular modular) throws AssemblerException {
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}
}
