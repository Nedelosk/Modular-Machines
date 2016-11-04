package modularmachines.api.modular;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.gui.IGuiProvider;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.storage.IItemHandlerStorage;
import modularmachines.api.modules.storage.IStoragePage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IModularAssembler extends INBTSerializable<NBTTagCompound>, IGuiProvider {

	@Nonnull
	IModularHandler getHandler();

	void assemble(EntityPlayer player);

	@Nullable
	IModular createModular() throws AssemblerException;

	int getComplexity(boolean withStorage, @Nullable IStoragePosition position);

	int getAllowedComplexity(@Nullable IStoragePosition position);

	@Nonnull
	IModularAssembler copy(@Nonnull IModularHandler handler);

	void setSelectedPosition(IStoragePosition position);

	IItemHandlerStorage getItemHandler();

	@Nullable
	IStoragePage getStoragePage(IStoragePosition position);

	int getIndex(@Nonnull IStoragePosition position);

	@Nonnull
	Collection<IStoragePage> getStoragePages();

	@Nonnull
	IStoragePosition getSelectedPosition();

	@Nonnull
	List<IStoragePosition> getStoragePositions();

	void updatePages(IStoragePosition position);

	void onStorageSlotChange();

	void beforeSlotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player);

	void afterSlotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player);
}