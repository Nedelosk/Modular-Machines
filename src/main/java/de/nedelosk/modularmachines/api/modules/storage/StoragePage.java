package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.gui.Page;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.AssemblerItemHandler;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class StoragePage extends Page implements IStoragePage {

	protected final IItemHandlerStorage itemHandler;
	protected final IStoragePosition position;
	protected IModularAssembler assembler;

	public StoragePage(IModularAssembler assembler, IStoragePosition position) {
		this(assembler, new AssemblerItemHandler(1, assembler), position);
	}

	public StoragePage(IModularAssembler assembler, IStorage storage) {
		this(assembler, new AssemblerItemHandler(storage.toPageStacks(), assembler, storage.getPosition()), storage.getPosition());
	}

	public StoragePage(IModularAssembler assembler, IItemHandlerStorage itemHandler, IStoragePosition position) {
		super(null);
		this.assembler = assembler;
		this.itemHandler = itemHandler;
		this.position = position;
	}

	@Override
	protected ResourceLocation getGuiTexture() {
		return null;
	}

	@Override
	public IItemHandlerStorage getItemHandler() {
		return itemHandler;
	}

	@Override
	public int getPlayerInvPosition() {
		return 83;
	}

	@Override
	public void setAssembler(IModularAssembler assembler) {
		this.assembler = assembler;
		this.itemHandler.setAssembler(assembler);
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}

	@Override
	public ItemStack getStorageStack() {
		if(assembler != null){
			return assembler.getItemHandler().getStackInSlot(assembler.getIndex(position));
		}
		return null;
	}

	@Override
	public IStorage assemble(IModular modular) throws AssemblerException {
		ItemStack storageStack = assembler.getItemHandler().getStackInSlot(assembler.getIndex(position));
		IModuleState storageState = ModuleManager.loadOrCreateModuleState(modular, storageStack);
		if(storageState.getModule() instanceof IStorageModule){
			IStorageModule module = (IStorageModule) storageState.getModule();
			return module.createStorage(storageState, modular, position);
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		if(itemHandler != null){
			tagCompound.setTag("itemHandler", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null));
		}
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("itemHandler") && itemHandler != null){
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null, nbt.getTag("itemHandler"));
		}
	}
}
