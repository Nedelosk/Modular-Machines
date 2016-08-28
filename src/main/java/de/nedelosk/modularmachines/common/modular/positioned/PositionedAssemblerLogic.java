package de.nedelosk.modularmachines.common.modular.positioned;

import java.util.Locale;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class PositionedAssemblerLogic implements IAssemblerLogic {

	public final IPositionedModularAssembler assembler;
	public final EnumStoragePosition position;

	public PositionedAssemblerLogic(IPositionedModularAssembler assembler, EnumStoragePosition position) {
		this.assembler = assembler;
		this.position = position;
	}

	@Override
	public boolean isItemValid(ItemStack stack, Slot slot, Slot storageSlot) {
		EnumStoragePosition pos = assembler.getSelectedPosition();
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
					if(container.getModule().getPosition(container) == EnumModulePosition.INTERNAL){
						return true;
					}
				}
				break;
			default:
				if(storageSlot == null){
					if(container.getModule() instanceof IModuleModuleStorage){
						if(((IModuleModuleStorage)container.getModule()).isValidForPosition(pos, container)){
							return true;
						}
					}
				}else{
					if(!storageSlot.getHasStack()){
						return false;
					}
					EnumModulePosition modulePosition = container.getModule().getPosition(container);
					if(!(modulePosition == EnumModulePosition.SIDE && (pos == EnumStoragePosition.LEFT || pos == EnumStoragePosition.RIGHT) || modulePosition == EnumModulePosition.BACK && pos == EnumStoragePosition.BACK || modulePosition == EnumModulePosition.DOWN && pos == EnumStoragePosition.DOWN || modulePosition == EnumModulePosition.TOP && pos == EnumStoragePosition.TOP)){
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
		int complexity = assembler.getComplexity(false, position);
		int allowedComplexity = assembler.getAllowedComplexity(position);
		if(allowedComplexity == 0){
			return;
		}
		if(allowedComplexity < complexity){
			throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.error.complexity.position", position.getLocName().toLowerCase(Locale.ENGLISH)));
		}
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}
}
