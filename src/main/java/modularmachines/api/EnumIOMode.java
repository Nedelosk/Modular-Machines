package modularmachines.api;

public enum EnumIOMode {
	DISABLED("mm.module.io_mode.disabled", 0x7a7979),
	IN_OUT("mm.module.io_mode.in_out", 0x75c458),
	INPUT("mm.module.io_mode.input", 0x73a4f4),
	OUTPUT("mm.module.io_mode.output", 0xefad73),
	PULL("mm.module.io_mode.pull", 0xd167d3),
	PUSH("mm.module.io_mode.push", 0x6fe6ed),
	PUSH_PULL("mm.module.io_mode.push_pull", 0xd37b6b);
	
	private final int color;
	private final String unlocalizedName;
	
	EnumIOMode(String unlocalizedName, int color) {
		this.unlocalizedName = unlocalizedName;
		this.color = color;
	}
	
	public static EnumIOMode getNext(EnumIOMode mode) {
		int ord = mode.ordinal() + 1;
		if (ord >= EnumIOMode.values().length) {
			ord = 0;
		}
		return EnumIOMode.values()[ord];
	}
	
	public static EnumIOMode getPrevious(EnumIOMode mode) {
		
		int ord = mode.ordinal() - 1;
		if (ord < 0) {
			ord = EnumIOMode.values().length - 1;
		}
		return EnumIOMode.values()[ord];
	}
	
	public boolean pulls() {
		return this == PULL || this == PUSH_PULL;
	}
	
	public boolean pushes() {
		return this == PUSH || this == PUSH_PULL;
	}
	
	public boolean canOutput() {
		return pushes() || this == IN_OUT;
	}
	
	public boolean canReceiveInput() {
		return pulls() || this == IN_OUT;
	}
	
	public boolean acceptsInput() {
		return this == IN_OUT || this == INPUT;
	}
	
	public boolean acceptsOutput() {
		return this == IN_OUT || this == OUTPUT;
	}
	
	public int getColor() {
		return color;
	}
	
	public String getUnlocalizedName() {
		return "";
	}
}
