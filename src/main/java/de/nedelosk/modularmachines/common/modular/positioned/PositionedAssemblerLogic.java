package de.nedelosk.modularmachines.common.modular.positioned;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class PositionedAssemblerLogic implements IAssemblerLogic {

	public final IPositionedModularAssembler assembler;
	public final EnumPosition position;

	public PositionedAssemblerLogic(IPositionedModularAssembler assembler, EnumPosition position) {
		this.assembler = assembler;
		this.position = position;
	}

	@Override
	public boolean isItemValid(ItemStack stack, Slot slot, Slot storageSlot) {
		EnumPosition pos = assembler.getSelectedPosition();
		IItemHandler itemHandler = assembler.getAssemblerHandler();
		int index = slot.getSlotIndex() - pos.startSlotIndex;
		IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
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
						IModuleContainer otherContainer = ModularMachinesApi.getContainerFromItem(itemHandler.getStackInSlot(i));
						if(otherContainer != null){
							usedSize = EnumModuleSize.getNewSize(usedSize, otherContainer.getModule().getSize(otherContainer));
						}
					}
					usedSize = EnumModuleSize.getNewSize(usedSize, container.getModule().getSize(container));
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
		if(modular.getModules(IModuleCasing.class).isEmpty()){
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.casing"));
		}
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}
}
