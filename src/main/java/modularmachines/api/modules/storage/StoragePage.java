package modularmachines.api.modules.storage;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

import modularmachines.api.gui.Page;
import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.AssemblerItemHandler;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;

public abstract class StoragePage extends Page implements IStoragePage {

	protected final IItemHandlerStorage itemHandler;
	protected final IStoragePosition position;
	private int positionIndex = -1;
	protected final List<IStoragePage> childs;
	protected IStoragePage parent;
	protected IModularAssembler assembler;

	public StoragePage(IModularAssembler assembler, IStoragePosition position) {
		this(assembler, new AssemblerItemHandler(1, assembler), position);
	}

	public StoragePage(IModularAssembler assembler, IStorage storage) {
		this(assembler, new AssemblerItemHandler(storage.toPageStacks(), assembler, storage.getPosition()), storage.getPosition());
	}

	public StoragePage(IModularAssembler assembler, IItemHandlerStorage itemHandler, IStoragePosition position) {
		super(null);
		this.itemHandler = itemHandler;
		this.position = position;
		setAssembler(assembler);
		this.childs = new ArrayList<>();
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
		if (assembler != null) {
			this.positionIndex = assembler.getIndex(position);
		}
		this.assembler = assembler;
		this.itemHandler.setAssembler(assembler);
	}

	@Override
	public IModularAssembler getAssembler() {
		return assembler;
	}

	@Override
	public ItemStack getStorageStack() {
		if (positionIndex < 0) {
			return null;
		}
		if (assembler != null) {
			return assembler.getItemHandler().getStackInSlot(positionIndex);
		}
		return null;
	}

	@Override
	public IStorage assemble(IModular modular) throws AssemblerException {
		ItemStack storageStack = getStorageStack();
		if (storageStack != null) {
			IModuleProvider provider = ModuleManager.loadOrCreateModuleProvider(modular, storageStack);
			IModuleState<IStorageModule> storageState = ModuleManager.getStorageState(provider, position);
			if (storageState != null && storageState.getModule() != null) {
				return storageState.getModule().createStorage(provider, position);
			}
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		if (itemHandler != null) {
			tagCompound.setTag("itemHandler", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null));
		}
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("itemHandler") && itemHandler != null) {
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null, nbt.getTag("itemHandler"));
		}
	}

	@Override
	public IStoragePage setParent(IStoragePage parentPage) {
		if (parent != parentPage) {
			parent = parentPage;
		}
		return this;
	}

	@Override
	public IStoragePage getParent() {
		return parent;
	}

	@Override
	public IStoragePage addChild(IStoragePage childPage) {
		if (!childs.contains(childPage)) {
			childs.add(childPage);
			childPage.setParent(this);
		}
		return this;
	}

	@Override
	public List<IStoragePage> getChilds() {
		return childs;
	}
}
