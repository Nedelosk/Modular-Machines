package de.nedelosk.modularmachines.api.modules.containers;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		if(size == null || material == null){
			throw new NullPointerException("The mod " + Loader.instance().activeModContainer().getModId() + " has tried to register a module item container, with a size or a material which was null.");
		}
		if(containers == null || containers.length <= 0){
			throw new NullPointerException("The mod " + Loader.instance().activeModContainer().getModId() + " has tried to register a module item container, with no module containers.");
		}
		for(IModuleContainer container : containers){
			if(container == null){
				throw new NullPointerException("The mod " + Loader.instance().activeModContainer().getModId() + " has tried to register a module item container, with a module container which was null.");
			}
		}
		this.material = material;
		this.ignorNBT = ignorNBT;
		this.size = size;
		for(IModuleContainer container : containers){
			container.setItemContainer(this);
		}
		this.containers = Arrays.asList(containers);
		this.tooltip = tooltip;
		if(stack == null){
			stack = ModuleManager.createDefaultStack(this);
		}
		this.stack = stack;

		if(getRegistryName() == null){
			setRegistryName(material.getName() + "." + size.getName() + "." + stack.getItem().getRegistryName().getResourcePath() + "." + stack.getItemDamage() + (stack.hasTagCompound() ? "." + stack.getTagCompound().toString() : ""));
		}
	}

	@Override
	public boolean matches(ItemStack stackToTest){
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
		if(tooltip != null && !tooltip.isEmpty()){
			tooltip.addAll(this.tooltip);
		}
		for(IModuleContainer container : containers){
			container.addTooltip(tooltip, stack);
		}
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
