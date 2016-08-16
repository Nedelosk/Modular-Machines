package de.nedelosk.modularmachines.api.modules.items;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleContainer extends IForgeRegistryEntry<IModuleContainer>{

	@Nonnull
	ItemStack getItemStack();

	@Nonnull
	IModule getModule();

	boolean ignorNBT();

	@Nonnull
	IMaterial getMaterial();

	String getDisplayName();

	String getUnlocalizedName();

	String getDescription();

	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, ItemStack stack);

	@Nullable
	IModuleProperties getProperties();

	boolean matches(ItemStack stackToTest);
}