package modularmachines.api;

import net.minecraft.util.math.MathHelper;

public enum IOMode {
	NONE("mm.module.io_mode.none", 0xafafaf),
	INPUT("mm.module.io_mode.input", 0x73a4f4),
	OUTPUT("mm.module.io_mode.output", 0xefad73),
	PULL("mm.module.io_mode.pull", 0xd167d3),
	PUSH("mm.module.io_mode.push", 0x6fe6ed),
	PUSH_PULL("mm.module.io_mode.push_pull", 0xd37b6b),
	DISABLED("mm.module.io_mode.disabled", 0x7a7979);
	
	public static final IOMode[] VALUES = values();
	
	private final int color;
	private final String unlocalizedName;
	
	IOMode(String unlocalizedName, int color) {
		this.unlocalizedName = unlocalizedName;
		this.color = color;
	}
	
	public static IOMode getNext(IOMode mode) {
		int ord = mode.ordinal() + 1;
		if (ord >= IOMode.values().length) {
			ord = 0;
		}
		return IOMode.values()[ord];
	}
	
	public static IOMode getPrevious(IOMode mode) {
		
		int ord = mode.ordinal() - 1;
		if (ord < 0) {
			ord = IOMode.values().length - 1;
		}
		return IOMode.values()[ord];
	}
	
	public static IOMode get(int index) {
		return VALUES[MathHelper.abs(index % VALUES.length)];
	}
	
	public boolean pulls() {
		return this == PULL || this == PUSH_PULL;
	}
	
	public boolean pushes() {
		return this == PUSH || this == PUSH_PULL;
	}
	
	public boolean canOutput() {
		return pushes() || this == NONE;
	}
	
	public boolean canReceiveInput() {
		return pulls() || this == NONE;
	}
	
	public boolean acceptsInput() {
		return this == NONE || this == INPUT;
	}
	
	public boolean acceptsOutput() {
		return this == NONE || this == OUTPUT;
	}
	
	public int getColor() {
		return color;
	}
	
	public String getTranslationKey() {
		return unlocalizedName;
	}
}
