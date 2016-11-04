package modularmachines.api.modules.storage;

import java.util.Arrays;
import java.util.List;

import modularmachines.api.modular.ExpandedStoragePositions;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.storage.module.IModuleModuleStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public abstract class StorageModule extends Module implements IStorageModule {

	public StorageModule(String name) {
		super(name);
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		if (showPosition(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.storage.position") + Arrays.toString(getPositions(container)).replace("[", "").replace("]", ""));
		}
		super.addTooltip(tooltip, stack, container);
	}

	protected IStoragePosition[] getPositions(IModuleContainer container) {
		if (this instanceof IModuleModuleStorage) {
			EnumModuleSizes size = container.getItemContainer().getSize();
			if (size == EnumModuleSizes.LARGE) {
				return new IStoragePosition[] { ExpandedStoragePositions.LEFT, ExpandedStoragePositions.RIGHT };
			} else if (size == EnumModuleSizes.SMALL) {
				return new IStoragePosition[] { ExpandedStoragePositions.TOP, ExpandedStoragePositions.BACK };
			}
		}
		return new IStoragePosition[] { ExpandedStoragePositions.LEFT, ExpandedStoragePositions.RIGHT, ExpandedStoragePositions.TOP, ExpandedStoragePositions.BACK, ExpandedStoragePositions.CASING };
	}

	@Override
	protected boolean showPosition(IModuleContainer container) {
		return true;
	}

	@Override
	public boolean isValidForPosition(IStoragePosition position, IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if (properties instanceof IStorageModuleProperties) {
			return ((IStorageModuleProperties) properties).isValidForPosition(position, container);
		}
		return false;
	}

	@Override
	public IStorage createStorage(IModuleProvider provider, IStoragePosition position) {
		return new Storage(position, provider);
	}

	@Override
	public IStoragePage createPage(IModularAssembler assembler, IModular modular, IStorage storage, IStoragePosition position) {
		if (storage != null) {
			return new BasicStoragePage(assembler, storage);
		}
		return new BasicStoragePage(assembler, position);
	}

	@Override
	public IStoragePage createSecondPage(IStoragePosition secondPosition) {
		return new ChildPage(secondPosition);
	}

	@Override
	public IStoragePosition getSecondPosition(IModuleContainer container, IStoragePosition position) {
		IModuleProperties properties = container.getProperties();
		if (properties instanceof IStorageModuleProperties) {
			return ((IStorageModuleProperties) properties).getSecondPosition(container, position);
		}
		return null;
	}
}
