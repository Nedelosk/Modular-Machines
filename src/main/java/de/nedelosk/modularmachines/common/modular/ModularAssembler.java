package de.nedelosk.modularmachines.common.modular;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleController;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class ModularAssembler implements IModularAssembler {

	protected final AssemblerItemHandler assemblerHandler;
	protected final IModularHandler modularHandler;

	public ModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		this.modularHandler = modularHandler;
		this.assemblerHandler = new AssemblerItemHandler();
		deserializeNBT(nbtTag);
	}

	public ModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks) {
		this.modularHandler = modularHandler;
		this.assemblerHandler = new AssemblerItemHandler(moduleStacks);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("assemblerHandler", assemblerHandler.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		assemblerHandler.deserializeNBT(nbt.getCompoundTag("assemblerHandler"));
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory) {
		return new GuiAssembler(tile, inventory);
	}

	protected void testComplexity() throws AssemblerException{
		int complexity = getComplexity(true, null);
		int allowedComplexity = getAllowedComplexity(null);
		if(complexity > allowedComplexity){
			if(allowedComplexity == ModularMachinesApi.DEFAULT_ALLOWED_COMPLEXITY){
				throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.error.no.controller"));
			}
			throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.error.complexity"));
		}
	}

	@Override
	public int getComplexity(boolean withStorage, EnumStoragePosition position) {
		int complexity = 0;
		if(position == null){
			for(ItemStack stack : assemblerHandler.getStacks()){
				if(stack != null){
					IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
					if(container != null){
						if(container.getModule() instanceof IModuleModuleStorage &&!withStorage){
							continue;
						}
						complexity+=container.getModule().getComplexity(container);
					}
				}
			}
		}else{
			for(int index = position.startSlotIndex;index < position.endSlotIndex + 1;index++){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleContainer container = ModularMachinesApi.getContainerFromItem(slotStack);
					if(container != null){
						if(container.getModule() instanceof IModuleModuleStorage &&!withStorage){
							continue;
						}
						complexity+=container.getModule().getComplexity(container);
					}
				}
			}
		}
		return complexity;
	}

	@Override
	public int getAllowedComplexity(EnumStoragePosition position) {
		if(position == null){
			for(ItemStack stack : assemblerHandler.getStacks()){
				if(stack != null){
					IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
					if(container != null){
						IModule module = container.getModule();
						if(module instanceof IModuleController) {
							return ((IModuleController)module).getAllowedComplexity(container);
						}
					}
				}
			}
			return ModularMachinesApi.DEFAULT_ALLOWED_COMPLEXITY;
		}else{
			for(int index = position.startSlotIndex;index < position.endSlotIndex + 1;index++){
				ItemStack slotStack = assemblerHandler.getStackInSlot(index);
				if(slotStack != null){
					IModuleContainer container = ModularMachinesApi.getContainerFromItem(slotStack);
					if(container != null){
						IModule module = container.getModule();
						if(module instanceof IModuleModuleStorage) {
							return ((IModuleModuleStorage) module).getAllowedComplexity(container);
						}
					}
				}
			}
			return 0;
		}
	}

	@Override
	public IItemHandlerModifiable getAssemblerHandler() {
		return assemblerHandler;
	}
}
