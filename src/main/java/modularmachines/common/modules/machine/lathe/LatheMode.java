package modularmachines.common.modules.machine.lathe;

import javax.annotation.Nullable;

import modularmachines.api.recipes.IMode;

public enum LatheMode implements IMode {
	ROD("rod"), WIRE("wire"), SCREW("screw");
	
	public static final LatheMode[] VALUES = values();
	
	private String name;
	
	LatheMode(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IMode next() {
		int ord = ordinal();
		if (ord == VALUES.length - 1) {
			ord = 0;
		} else {
			ord++;
		}
		return VALUES[ord];
	}
	
	@Override
	public IMode previous() {
		int ord = ordinal();
		ord--;
		if (ord < 0) {
			ord = VALUES.length - 1;
		}
		return VALUES[ord];
	}
	
	@Nullable
	@Override
	public IMode getMode(int index) {
		if (index < 0 || index >= VALUES.length) {
			return null;
		}
		return VALUES[index];
	}
}
