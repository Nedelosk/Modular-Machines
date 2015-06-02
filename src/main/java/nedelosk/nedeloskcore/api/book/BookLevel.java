package nedelosk.nedeloskcore.api.book;

import net.minecraft.util.EnumChatFormatting;

public class BookLevel {

	public EnumChatFormatting color;
	public  final String unlocalizedName;
	
	public BookLevel(String unlocalizedName, EnumChatFormatting color) {
		this.unlocalizedName = unlocalizedName;
		this.color = color;
	}
	
	public EnumChatFormatting getColor() {
		return color;
	}
	
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
}
