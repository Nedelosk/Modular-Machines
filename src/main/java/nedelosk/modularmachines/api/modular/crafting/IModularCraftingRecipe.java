package nedelosk.modularmachines.api.modular.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IModularCraftingRecipe
{
	
    boolean matches(IInventory var1, World world, EntityPlayer player);

    ItemStack getCraftingResult(IInventory var1);

    int getRecipeSize();

    ItemStack getRecipeOutput();
    
    String[] getEntrys();

    int getTier();
    
}
