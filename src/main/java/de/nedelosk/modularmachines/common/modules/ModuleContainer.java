package de.nedelosk.modularmachines.common.modules;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.common.items.ItemModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleContainer extends IForgeRegistryEntry.Impl<IModuleContainer> implements IModuleContainer {

	public ItemStack stack;
	public final IMaterial material;
	public final boolean ignorNBT;
	public final IModule module;
	public final List<String> tooltip;

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
		this.module = module;
		this.stack = stack;
		this.material = material;
		this.ignorNBT = ignorNBT;
		this.tooltip = tooltip;

		setRegistryName(module.getRegistryName().getResourcePath() + "/" + stack.getItem().getRegistryName().getResourcePath() + "/" + material.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof IModuleContainer)) {
			return false;
		}
		IModuleContainer i = (IModuleContainer) obj;
		if (stack != null && i.getItemStack() != null && i.getItemStack().getItem() != null && stack.getItem() != null
				&& stack.getItemDamage() == i.getItemStack().getItemDamage()
				&& (ignorNBT && i.ignorNBT() || stack.getTagCompound() == null && i.getItemStack().getTagCompound() == null || stack.getTagCompound() != null
				&& i.getItemStack().getTagCompound() != null && stack.getTagCompound().equals(i.getItemStack().getTagCompound()))) {
		}
		return false;
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
	public void addTooltip(List<String> tooltip) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.material") + material.getLocalizedName());
		if(!(stack.getItem() instanceof ItemModule)){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.name") + getDisplayName());
		}
		module.addTooltip(tooltip, this);

		tooltip.addAll(this.tooltip);
	}
}
