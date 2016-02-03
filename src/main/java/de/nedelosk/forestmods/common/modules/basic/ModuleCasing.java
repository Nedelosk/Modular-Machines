package de.nedelosk.forestmods.common.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.basic.IModuleCasing;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.ModuleRenderer;
import de.nedelosk.forestmods.common.modules.ModuleDefault;
import de.nedelosk.forestmods.common.modules.gui.ModuleGuiDefault;
import de.nedelosk.forestmods.common.modules.inventory.ModuleInventoryDefault;
import net.minecraft.item.ItemStack;

public class ModuleCasing extends ModuleDefault implements IModuleCasing {

	public ModuleCasing(String moduleUID) {
		super(ModuleCategoryUIDs.CASING, moduleUID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModuleRenderer.CasingRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModuleRenderer.CasingRenderer(moduleStack);
	}

	@Override
	public int getMaxHeat() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleInventoryDefault(getUID(), 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleGuiDefault(getUID());
	}
}
