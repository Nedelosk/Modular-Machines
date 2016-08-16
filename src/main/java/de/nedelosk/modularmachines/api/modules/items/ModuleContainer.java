package de.nedelosk.modularmachines.api.modules.items;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleContainer extends IForgeRegistryEntry.Impl<IModuleContainer> implements IModuleContainer {

	protected final ItemStack stack;
	protected final IMaterial material;
	protected final boolean ignorNBT;
	protected final IModule module;
	protected final IModuleProperties properties;
	protected final List<String> tooltip;

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material) {
		this(module, null, stack, material);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, boolean ignorNBT) {
		this(module, null, stack, material, ignorNBT);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, List<String> tooltip) {
		this(module, null, stack, material, tooltip);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, ItemStack stack, IMaterial material) {
		this(module, properties, stack, material, Collections.emptyList(), false);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, ItemStack stack, IMaterial material, boolean ignorNBT) {
		this(module, properties, stack, material, Collections.emptyList(), ignorNBT);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, ItemStack stack, IMaterial material, List<String> tooltip) {
		this(module, properties, stack, material, tooltip, false);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, List<String> tooltip, boolean ignorNBT) {
		this(module, null, stack, material, tooltip, ignorNBT);
	}

	public ModuleContainer(IModule module, IMaterial material) {
		this(module, null, null, material);
	}

	public ModuleContainer(IModule module, IMaterial material, boolean ignorNBT) {
		this(module, null, null, material, ignorNBT);
	}

	public ModuleContainer(IModule module, IMaterial material, List<String> tooltip) {
		this(module, null, null, material, tooltip);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, IMaterial material) {
		this(module, properties, null, material, Collections.emptyList(), false);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, IMaterial material, boolean ignorNBT) {
		this(module, properties, null, material, Collections.emptyList(), ignorNBT);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, IMaterial material, List<String> tooltip) {
		this(module, properties, null, material, tooltip, false);
	}

	public ModuleContainer(IModule module, IMaterial material, List<String> tooltip, boolean ignorNBT) {
		this(module, null, null, material, tooltip, ignorNBT);
	}

	public ModuleContainer(IModule module, IModuleProperties properties, ItemStack stack, IMaterial material, List<String> tooltip, boolean ignorNBT) {
		if(module == null || material == null){
			throw new NullPointerException("The mod " + Loader.instance().activeModContainer().getModId() + " has tried to register a module container, with a module or a material which was null.");
		}
		this.module = module;
		this.properties = properties;
		this.material = material;
		this.ignorNBT = ignorNBT;
		this.tooltip = tooltip;
		String registryName = module.getRegistryName().getResourcePath() + "/";
		if(properties != null){
			registryName +=  properties.getSize(this) + "/" + properties.getComplexity(this) + "/";
		}
		if(stack == null){
			registryName+=ModularMachinesApi.defaultModuleItem.getRegistryName().getResourcePath() + "/" + material.getName();
			setRegistryName(registryName);
			stack = ModularMachinesApi.createDefaultStack(this);
		}
		this.stack = stack;

		if(getRegistryName() == null){
			registryName+=stack.getItem().getRegistryName().getResourcePath() + "/" + material.getName();
			setRegistryName(registryName);
		}
	}

	@Override
	public boolean matches(ItemStack stackToTest){
		return ItemUtil.isIdenticalItem(stack, stackToTest, ignorNBT);
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
	public IModule getModule() {
		return module;
	}

	@Override
	public IModuleProperties getProperties() {
		return properties;
	}

	@Override
	public String getDisplayName() {
		return module.getDisplayName(this);
	}

	@Override
	public String getUnlocalizedName() {
		return module.getUnlocalizedName(this);
	}

	@Override
	public String getDescription() {
		return module.getDescription(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack) {
		tooltip.add(I18n.translateToLocal("mm.module.tooltip.material") + material.getLocalizedName());
		module.addTooltip(tooltip, stack, this);

		tooltip.addAll(this.tooltip);
	}
}
