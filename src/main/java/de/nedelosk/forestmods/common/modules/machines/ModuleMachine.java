package de.nedelosk.forestmods.common.modules.machines;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.fluids.IModuleWithFluid;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.MachineRenderer;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import de.nedelosk.forestmods.common.modules.gui.ModuleGuiDefault;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachine extends ModuleAddable implements IModuleMachine, IModuleWithFluid {

	protected final int itemOutputs;
	protected final int itemInputs;
	protected final int fluidOutputs;
	protected final int fluidInputs;

	public ModuleMachine(String moduleUID, int itemInputs, int itemOutputs, int fluidInputs, int fluidOutputs) {
		super(ModuleCategoryUIDs.MACHINE, moduleUID);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = fluidInputs;
		this.fluidOutputs = fluidOutputs;
	}

	public ModuleMachine(String moduleUID, int itemInputs, int itemOutputs) {
		super(ModuleCategoryUIDs.MACHINE, moduleUID);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = 0;
		this.fluidOutputs = 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new MachineRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new MachineRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getFilePath(ModuleStack stack) {
		return getModuleUID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleGuiDefault(getUID());
	}

	/* INVENTORY */
	@Override
	public int getItemInputs(ModuleStack stack) {
		return itemInputs;
	}

	@Override
	public int getItemOutputs(ModuleStack stack) {
		return itemOutputs;
	}

	@Override
	public boolean useFluids(ModuleStack stack) {
		return fluidInputs != 0 || fluidOutputs != 0;
	}

	@Override
	public int getFluidInputs(ModuleStack stack) {
		return fluidInputs;
	}

	@Override
	public int getFluidOutputs(ModuleStack stack) {
		return fluidOutputs;
	}

	@Override
	public List<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList();
		modules.add("Battery");
		modules.add("Engine");
		modules.add("Casing");
		return modules;
	}

	@Override
	public boolean canAssembleModular(IModular modular, ModuleStack moduleStack) {
		return true;
	}

	@Override
	public abstract IModuleInventory createInventory(ModuleStack stack);

	/* WAILA */
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}
}