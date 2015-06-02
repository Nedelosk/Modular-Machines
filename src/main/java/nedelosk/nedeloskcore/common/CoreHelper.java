package nedelosk.nedeloskcore.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CoreHelper {

    public static boolean isItemEqual(ItemStack a, ItemStack b) {
        return isItemEqual(a, b, true, true);
    }

    public static boolean isItemEqualIgnoreNBT(ItemStack a, ItemStack b) {
        return isItemEqual(a, b, true, false);
    }

    public static boolean isItemEqual(final ItemStack a, final ItemStack b, final boolean matchDamage, final boolean matchNBT) {
        if (a == null || b == null)
            return false;
        if (a.getItem() != b.getItem())
            return false;
        if (matchNBT && !ItemStack.areItemStackTagsEqual(a, b))
            return false;
        if (matchDamage && a.getHasSubtypes()) {
            if (isWildcard(a) || isWildcard(b))
                return true;
            if (a.getItemDamage() != b.getItemDamage())
                return false;
        }
        return true;
    }
    
    public static boolean isWildcard(ItemStack stack) {
        return isWildcard(stack.getItemDamage());
    }

    public static boolean isWildcard(int damage) {
        return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
    }
	
}
