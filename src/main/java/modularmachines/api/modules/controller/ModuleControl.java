package modularmachines.api.modules.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import modularmachines.api.modules.handlers.BlankModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;

public class ModuleControl extends BlankModuleContentHandler implements IModuleControl, INBTSerializable<NBTTagCompound> {

	private Map<Integer, Boolean> permissions = new HashMap<>();
	private EnumRedstoneMode mode;

	public ModuleControl(IModuleState moduleState) {
		super(moduleState, "Control");
		this.mode = EnumRedstoneMode.IGNORE;
	}

	@Override
	public boolean hasPermission(IModuleState state) {
		if (permissions.containsKey(state.getPosition())) {
			return permissions.get(state.getPosition());
		}
		return false;
	}

	@Override
	public void setPermission(IModuleState state, boolean permission) {
		permissions.put(state.getPosition(), permission);
	}

	@Override
	public EnumRedstoneMode getRedstoneMode() {
		return mode;
	}

	@Override
	public void setRedstoneMode(EnumRedstoneMode mode) {
		this.mode = mode;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setShort("Mode", (short) mode.ordinal());
		NBTTagList list = new NBTTagList();
		for (Entry<Integer, Boolean> permission : permissions.entrySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("position", permission.getKey());
			tag.setBoolean("Permission", permission.getValue());
			list.appendTag(tag);
		}
		nbtTag.setTag("Permissions", list);
		return nbtTag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		mode = EnumRedstoneMode.values()[nbt.getShort("Mode")];
		NBTTagList list = nbt.getTagList("Permissions", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			permissions.put(getPosition(tag), tag.getBoolean("Permission"));
		}
	}

	private int getPosition(NBTTagCompound tag) {
		if (tag.hasKey("Index")) {
			return tag.getInteger("Index");
		} else {
			return tag.getInteger("position");
		}
	}

	@Override
	public void cleanHandler(IModuleState state) {
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
