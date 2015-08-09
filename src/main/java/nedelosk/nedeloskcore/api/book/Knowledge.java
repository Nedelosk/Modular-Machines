package nedelosk.nedeloskcore.api.book;

public class Knowledge {

	public final String unlocalizedName;

	public Knowledge(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName() {
		return "nc.book.knowledge." + unlocalizedName;
	}
}
