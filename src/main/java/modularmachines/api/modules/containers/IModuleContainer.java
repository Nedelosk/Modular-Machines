package modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleProperties;

public interface IModuleContainer<M extends IModule, P extends IModuleProperties> {

	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, ItemStack stack);

	@Nonnull
	M getModule();

	@Nullable
	P getProperties();

	@Nullable
	IModuleItemContainer getItemContainer();

	void setItemContainer(@Nonnull IModuleItemContainer itemContainer);

	@Nonnull
	String getUnlocalizedName();

	@Nonnull
	String getDisplayName();

	@Nonnull
	String getDescription();

	int getIndex();
}
