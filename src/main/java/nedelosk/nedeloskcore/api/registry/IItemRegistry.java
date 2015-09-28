/**
 * (C) 2015 Nedelosk
 */
package nedelosk.nedeloskcore.api.registry;

import net.minecraft.item.Item;

public interface IItemRegistry {

	public Item registerItem(Item item, String name, String modName);
	
	public String setUnlocalizedItemName(String name, String modName);
	
}
