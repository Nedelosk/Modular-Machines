/**
 * (C) 2015 Nedelosk
 */
package nedelosk.nedeloskcore.api.book;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class BookCategory {

	public Map<String, BookEntry> entrys = new HashMap();
	private ResourceLocation icon;

	public BookCategory(ResourceLocation icon) {
		this.icon = icon;
	}

	public ResourceLocation getIcon() {
		return icon;
	}

}
