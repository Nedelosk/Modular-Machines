package de.nedelosk.forestmods.common.modules.heater;

import java.util.List;

import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import net.minecraft.item.ItemStack;

public abstract class ModuleHeater extends ModuleAddable implements IModuleHeater {

	public ModuleHeater(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleHeaterSaver();
	}

	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return null;
	}

	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}

	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}
}
