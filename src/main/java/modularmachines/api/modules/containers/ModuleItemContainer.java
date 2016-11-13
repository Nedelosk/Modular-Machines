package modularmachines.api.modules.containers;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.ItemUtil;
import modularmachines.api.material.IMaterial;
import modularmachines.api.modular.IModular;
import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.ModuleManager;

public class ModuleItemContainer extends IForgeRegistryEntry.Impl<IModuleItemContainer> implements IModuleItemContainer {

	protected final ItemStack stack;
	protected final IMaterial material;
	protected final EnumModuleSizes size;
	protected final List<IModuleContainer> containers;
	protected final List<String> tooltip;
	protected final boolean ignorNBT;
	protected boolean needOnlyOnePosition = false;

	public ModuleItemContainer(ItemStack stack, IMaterial material, EnumModuleSizes size, IModuleContainer... containers) {
		this(stack, material, size, null, false, containers);
	}

	public ModuleItemContainer(ItemStack stack, IMaterial material, EnumModuleSizes size, boolean ignorNBT, IModuleContainer... containers) {
		this(stack, material, size, null, ignorNBT, containers);
	}

	public ModuleItemContainer(ItemStack stack, IMaterial material, EnumModuleSizes size, List<String> tooltip, IModuleContainer... containers) {
		this(stack, material, size, tooltip, false, containers);
	}

	public ModuleItemContainer(ItemStack stack, IMaterial material, EnumModuleSizes size, List<String> tooltip, boolean ignorNBT, IModuleContainer... containers) {
		String modID = Loader.instance().activeModContainer().getModId();
		if (size == null || material == null) {
			throw new NullPointerException("The mod " + modID + " has tried to register a module item container, with a size or a material which was null.");
		}
		if (containers == null || containers.length <= 0) {
			throw new NullPointerException("The mod " + modID + " has tried to register a module item container, with no module containers.");
		}
		for (IModuleContainer container : containers) {
			if (container == null) {
				throw new NullPointerException("The mod " + modID + " has tried to register a module item container, with a module container which was null.");
			}
		}
		this.material = material;
		this.ignorNBT = ignorNBT;
		this.size = size;
		for (IModuleContainer container : containers) {
			container.setItemContainer(this);
		}
		this.containers = Arrays.asList(containers);
		this.tooltip = tooltip;
		if (stack == null) {
			stack = ModuleManager.createDefaultStack(this);
		}
		this.stack = stack;
	}

	@Override
	public boolean matches(ItemStack stackToTest) {
		return ItemUtil.isIdenticalItem(stack, stackToTest, ignorNBT, true);
	}

	@Override
	public ItemStack getItemStack() {
		return stack;
	}

	@Override
	public boolean ignorNBT() {
		return ignorNBT;
	}

	@Override
	public IMaterial getMaterial() {
		return material;
	}

	@Override
	public EnumModuleSizes getSize() {
		return size;
	}

	@Override
	public List<IModuleContainer> getContainers() {
		return containers;
	}

	@Override
	public boolean needOnlyOnePosition(IModuleContainer container) {
		return needOnlyOnePosition;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack) {
		if (tooltip != null && !tooltip.isEmpty()) {
			tooltip.addAll(this.tooltip);
		}
		int index = 0;
		for (IModuleContainer container : containers) {
			if (containers.size() > 1) {
				tooltip.add(" - " + index + " - ");
			}
			container.addTooltip(tooltip, stack);
		}
	}

	@Override
	public IModuleProvider createModuleProvider(IModuleItemContainer itemContainer, IModular modular, ItemStack itemStack) {
		return new ModuleProvider(itemContainer, modular, itemStack);
	}

	@Override
	public ItemStack createModuleItemContainer() {
		return new ItemStack(ModuleManager.defaultModuleItemContainer);
	}

	@Override
	public IModuleItemContainer setNeedOnlyOnePosition(boolean needOnlyOnePosition) {
		this.needOnlyOnePosition = needOnlyOnePosition;
		return this;
	}

	@Override
	public IModuleContainer getContainer(int index) {
		return containers.get(index);
	}

	@Override
	public int getIndex(IModuleContainer container) {
		return containers.indexOf(container);
	}
}
