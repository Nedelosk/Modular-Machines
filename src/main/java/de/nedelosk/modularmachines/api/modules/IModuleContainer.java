package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
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

	String getUnlocalizedName();
	
	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip);
	
	@SideOnly(Side.CLIENT)
	String getFilePath(IModuleState state);
	
	@Override
	boolean equals(Object obj);
}