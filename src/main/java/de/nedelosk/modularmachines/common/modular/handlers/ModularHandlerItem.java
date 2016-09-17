package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.List;
import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class ModularHandlerItem extends ModularHandler implements IModularHandlerItem<IModular, IModularAssembler, NBTTagCompound> {
	private static final String KEY_SLOTS = "Slots";
	private static final String KEY_UID = "UID";
	private static final Random rand = new Random();

	protected final ItemStack parent;

	public ModularHandlerItem(ItemStack parent, List<IStoragePosition> positions) {
		super(null, positions);

		this.parent = parent;
	}

	@Override
	public BlockPos getPlayerPos() {
		return getPlayer().getPosition();
	}

	@Override
	public ItemStack getParent() {
		for (EnumHand hand : EnumHand.values()) {
			ItemStack held = getPlayer().getHeldItem(hand);
			if (isSameItemInventory(held, parent)) {
				return held;
			}
		}
		return parent;
	}

	@Override
	public void setUID() {
		ItemStack parent = getParent();

		if (parent.getTagCompound() == null) {
			parent.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound nbt = parent.getTagCompound();
		if (!nbt.hasKey(KEY_UID)) {
			nbt.setString(KEY_UID, String.valueOf(rand.nextInt()));
		}
	}

	private static boolean isSameItemInventory(ItemStack base, ItemStack comparison) {
		if (base == null || comparison == null) {
			return false;
		}

		if (base.getItem() != comparison.getItem()) {
			return false;
		}

		if (!base.hasTagCompound() || !comparison.hasTagCompound()) {
			return false;
		}

		String baseUID = base.getTagCompound().getString(KEY_UID);
		String comparisonUID = comparison.getTagCompound().getString(KEY_UID);
		return baseUID != null && comparisonUID != null && baseUID.equals(comparisonUID);
	}

	public static boolean hasItemUID(ItemStack base, String UID) {
		if (base == null || UID == null) {
			return false;
		}

		if (!base.hasTagCompound()) {
			return false;
		}

		String baseUID = base.getTagCompound().getString(KEY_UID);
		return baseUID != null && UID != null && baseUID.equals(UID);
	}

	@Override
	public String getUID() {
		if (parent == null) {
			return null;
		}

		if (!parent.hasTagCompound()) {
			return null;
		}
		return getParent().getTagCompound().getString(KEY_UID);
	}

	@Override
	public void markDirty() {
		parent.deserializeNBT(parent.serializeNBT());
	}
}
