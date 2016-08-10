package de.nedelosk.modularmachines.api.modules.items;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.common.utils.ItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleContainer extends IForgeRegistryEntry.Impl<IModuleContainer> implements IModuleContainer {

	protected ItemStack stack;
	protected final IMaterial material;
	protected final boolean ignorNBT;
	protected final IModule module;
	protected final List<String> tooltip;

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material) {
		this(module, stack, material, Collections.emptyList(), false);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, boolean ignorNBT) {
		this(module, stack, material, Collections.emptyList(), ignorNBT);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, List<String> tooltip) {
		this(module, stack, material, tooltip, false);
	}

	public ModuleContainer(IModule module, ItemStack stack, IMaterial material, List<String> tooltip, boolean ignorNBT) {
		if(module == null || stack == null || material == null){
			throw new NullPointerException("The mod " + Loader.instance().activeModContainer().getModId() + " has tried to register a module container, with a module or a item stack or a material which was null.");
		}
		this.module = module;
		this.stack = stack;
		this.material = material;
		this.ignorNBT = ignorNBT;
		this.tooltip = tooltip;

		setRegistryName(module.getRegistryName().getResourcePath() + "/" + stack.getItem().getRegistryName().getResourcePath() + "/" + material.getName());
	}

	@Override
	public boolean matches(ItemStack stackToTest){
		return ItemUtil.isIdenticalItem(stack, stackToTest);
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
