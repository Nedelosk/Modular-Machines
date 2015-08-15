package nedelosk.modularmachines.api.basic.modular.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import nedelosk.modularmachines.api.basic.modular.IModularWorkbench;
import nedelosk.modularmachines.api.basic.techtree.TechTreeManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapedModularCraftingRecipe implements IModularCraftingRecipe
{
    private static final int MAX_CRAFT_GRID_WIDTH = 5;
    private static final int MAX_CRAFT_GRID_HEIGHT = 5;

    public ItemStack output = null;
    public  Object[] input = null;
    public String[] entrys; 
    public int tier;
    public int width = 0;
    public int height = 0;
    private boolean mirrored = true;

    public ShapedModularCraftingRecipe(String[] research, Block     result, int tier, Object... recipe){ this(research, new ItemStack(result), tier, recipe); }
    public ShapedModularCraftingRecipe(String[] research, Item      result, int tier, Object... recipe){ this(research, new ItemStack(result), tier, recipe); }
    public ShapedModularCraftingRecipe(String[] research, ItemStack result, int tier, Object... recipe)
    {
    	this.tier = tier;
        output = result.copy();
        this.entrys = research;
        String shape = "";
        
        int idx = 0;

        if (recipe[idx] instanceof Boolean)
        {
            mirrored = (Boolean)recipe[idx];
            if (recipe[idx+1] instanceof Object[])
            {
                recipe = (Object[])recipe[idx+1];
            }
            else
            {
                idx = 1;
            }
        }

        if (recipe[idx] instanceof String[])
        {
            String[] parts = ((String[])recipe[idx++]);

            for (String s : parts)
            {
                width = s.length();
                shape += s;
            }

            height = parts.length;
        }
        else
        {
            while (recipe[idx] instanceof String)
            {
                String s = (String)recipe[idx++];
                shape += s;
                width = s.length();
                height++;
            }
        }

        if (width * height != shape.length())
        {
            String ret = "Invalid shaped ore recipe: ";
            for (Object tmp :  recipe)
            {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, Object> itemMap = new HashMap<Character, Object>();

        for (; idx < recipe.length; idx += 2)
        {
            Character chr = (Character)recipe[idx];
            Object in = recipe[idx + 1];

            if (in instanceof ItemStack)
            {
                itemMap.put(chr, ((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                itemMap.put(chr, new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                itemMap.put(chr, new ItemStack((Block)in, 1, OreDictionary.WILDCARD_VALUE));
            }
            else if (in instanceof String)
            {
                itemMap.put(chr, OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }

        input = new Object[width * height];
        int x = 0;
        for (char chr : shape.toCharArray())
        {
            input[x++] = itemMap.get(chr);
        }
    }

    @Override
    public ItemStack getCraftingResult(IInventory var1){ return output.copy(); }

    @Override
    public int getRecipeSize(){ return input.length; }

    @Override
    public ItemStack getRecipeOutput(){ return output; }

    @Override
    public boolean matches(IInventory inv, World world, EntityPlayer player)
    {
    	if (entrys != null && entrys.length>0 && !TechTreeManager.isEntryComplete(player, entrys)) {
    		return false;
    	}
    	if(!(getTier() >= ((IModularWorkbench)inv).getTier()))
    		return false;
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++)
        {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y)
            {
                if (checkMatch(inv, x, y, false))
                {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(IInventory inv, int startX, int startY, boolean mirror)
    {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    if (mirror)
                    {
                        target = input[width - subX - 1 + subY * width];
                    }
                    else
                    {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = ((IModularWorkbench)inv).getStackInRowAndColumn(x, y);

                if (target instanceof ItemStack)
                {
                    if (!checkItemEquals((ItemStack)target, slot))
                    {
                        return false;
                    }
                }
                else if (target instanceof ArrayList)
                {
                    boolean matched = false;

                    for (ItemStack item : (ArrayList<ItemStack>)target)
                    {
                        matched = matched || checkItemEquals(item, slot);
                    }

                    if (!matched)
                    {
                        return false;
                    }
                }
                else if (target == null && slot != null)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkItemEquals(ItemStack target, ItemStack input)
    {
        if (input == null && target != null || input != null && target == null)
        {
            return false;
        }
        return (target.getItem() == input.getItem() && 
        		(!target.hasTagCompound() || ItemStack.areItemStackTagsEqual(input, target)) &&
        		(target.getItemDamage() == OreDictionary.WILDCARD_VALUE|| target.getItemDamage() == input.getItemDamage()));
    }

    public ShapedModularCraftingRecipe setMirrored(boolean mirror)
    {
        mirrored = mirror;
        return this;
    }

    public Object[] getInput()
    {
        return this.input;
    }
	
	@Override
	public String[] getEntrys() {
		return entrys;
	}
	
	@Override
	public int getTier() {
		return tier;
	}
}
