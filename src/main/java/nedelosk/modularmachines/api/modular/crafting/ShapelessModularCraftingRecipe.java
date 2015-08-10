package nedelosk.modularmachines.api.modular.crafting;

import java.util.ArrayList;
import java.util.Iterator;

import nedelosk.modularmachines.api.IModularWorkbench;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessModularCraftingRecipe implements IModularCraftingRecipe
{
    private ItemStack output = null;
    private ArrayList input = new ArrayList();
    public int tier;
    
    public String[] entrys; 

    public ShapelessModularCraftingRecipe(String[] research, Block result, int tier, Object... recipe){ this(research,new ItemStack(result), tier, recipe); }
    public ShapelessModularCraftingRecipe(String[] research, Item  result, int tier, Object... recipe){ this(research,new ItemStack(result), tier, recipe); }

    public ShapelessModularCraftingRecipe(String[] research, ItemStack result, int tier, Object... recipe)
    {
    	this.tier = tier;
        output = result.copy();
        this.entrys = research;
        for (Object in : recipe)
        {
            if (in instanceof ItemStack)
            {
                input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    @Override
    public int getRecipeSize(){ return input.size(); }

    @Override
    public ItemStack getRecipeOutput(){ return output; }

    @Override
    public ItemStack getCraftingResult(IInventory var1){ return output.copy(); }

    @Override
    public boolean matches(IInventory inv, World world, EntityPlayer player)
    {
    	if (entrys != null && entrys.length>0 && !TechTreeManager.isEntryComplete(player, entrys)) {
    		return false;
    	}
    	
    	if(!(getTier() >= ((IModularWorkbench)inv).getTier()))
    		return false;
    	
        ArrayList required = new ArrayList(input);
        
        for (int x = 0; x < 25; x++)
        {
            ItemStack slot = inv.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = checkItemEquals((ItemStack)next, slot);
                    }
                    else if (next instanceof ArrayList)
                    {
                        for (ItemStack item : (ArrayList<ItemStack>)next)
                        {
                            match = match || checkItemEquals(item, slot);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }
        
        return required.isEmpty();
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

    public ArrayList getInput()
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
