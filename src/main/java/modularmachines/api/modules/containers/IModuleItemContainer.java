package modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.material.IMaterial;
import modularmachines.api.modular.IModular;
import modularmachines.api.modules.EnumModuleSizes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleItemContainer extends IForgeRegistryEntry<IModuleItemContainer> {

	@Nonnull
	ItemStack getItemStack();

	@Nonnull
	IMaterial getMaterial();

	@Nonnull
	EnumModuleSizes getSize();

	boolean ignorNBT();

	boolean needOnlyOnePosition(IModuleContainer container);

	IModuleItemContainer setNeedOnlyOnePosition(boolean needOnlyOnePosition);

	@Nonnull
	ItemStack createModuleItemContainer();

	@Nonnull
	IModuleProvider createModuleProvider(IModuleItemContainer itemContainer, IModular modular, ItemStack itemStack);

	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, ItemStack stack);

	boolean matches(ItemStack stackToTest);

	@Nonnull
	List<IModuleContainer> getContainers();

	@Nullable
	IModuleContainer getContainer(int index);

	int getIndex(@Nonnull IModuleContainer container);
}