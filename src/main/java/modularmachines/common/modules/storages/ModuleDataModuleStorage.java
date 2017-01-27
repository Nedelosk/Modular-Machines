package modularmachines.common.modules.storages;

import java.util.Arrays;
import java.util.List;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ModuleDataModuleStorage extends ModuleData {

	@Override
	public void addTooltip(List<String> tooltip, ItemStack itemStack, IModuleContainer container) {
		if (showPosition(container)) {
			tooltip.add(I18n.translateToLocal("mm.module.tooltip.storage.position") + Arrays.toString(getPositions(container)).replace("[", "").replace("]", ""));
		}
		super.addTooltip(tooltip, itemStack, container);
	}

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
	public StoragePage createChildPage(IAssembler assembler, IStoragePosition position) {
		return null;
	}
	
}
