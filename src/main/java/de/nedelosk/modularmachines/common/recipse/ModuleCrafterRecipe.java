package de.nedelosk.modularmachines.common.recipse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.recipes.IModuleCrafterRecipe;
import de.nedelosk.modularmachines.api.recipes.ModuleCraftingWrapper;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleCrafterRecipe implements IModuleCrafterRecipe, IRecipe {

	// Added in for future ease of change, but hard coded for now.
	private static final int MAX_CRAFT_GRID_WIDTH = 3;
	private static final int MAX_CRAFT_GRID_HEIGHT = 3;
	private ItemStack holder = null;
	private ItemStack output = null;
	private Object[] input = null;
	private int width;
	private int height;
	private boolean mirrored = true;

	public ModuleCrafterRecipe(ItemStack result, Object... recipe) {
		this(result, null, recipe);
	}

	public ModuleCrafterRecipe(ItemStack result, ItemStack holder, Object... recipe) {
		output = result.copy();
		if (holder != null) {
			this.holder = holder.copy();
		}
		String shape = "";
		int idx = 0;
		if (recipe[idx] instanceof Boolean) {
			mirrored = (Boolean) recipe[idx];
			if (recipe[idx + 1] instanceof Object[]) {
				recipe = (Object[]) recipe[idx + 1];
			} else {
				idx = 1;
			}
		}
		if (recipe[idx] instanceof String[]) {
			String[] parts = (String[]) recipe[idx++];
			for(String s : parts) {
				width = s.length();
				shape += s;
			}
			height = parts.length;
		} else {
			while (recipe[idx] instanceof String) {
				String s = (String) recipe[idx++];
				shape += s;
				width = s.length();
				height++;
			}
		}
		if (width * height != shape.length()) {
			String ret = "Invalid shaped ore recipe: ";
			for(Object tmp : recipe) {
				ret += tmp + ", ";
			}
			ret += output;
			throw new RuntimeException(ret);
		}
		HashMap<Character, Object> itemMap = new HashMap<>();
		for(; idx < recipe.length; idx += 2) {
			Character chr = (Character) recipe[idx];
			Object in = recipe[idx + 1];
			if (in instanceof ItemStack) {
				itemMap.put(chr, ((ItemStack) in).copy());
			} else if (in instanceof Item) {
				itemMap.put(chr, new ItemStack((Item) in));
			} else if (in instanceof Block) {
				itemMap.put(chr, new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE));
			} else if (in instanceof String) {
				itemMap.put(chr, OreDictionary.getOres((String) in));
			} else {
				String ret = "Invalid shaped ore recipe: ";
				for(Object tmp : recipe) {
					ret += tmp + ", ";
				}
				ret += output;
				throw new RuntimeException(ret);
			}
		}
		input = new Object[width * height];
		int x = 0;
		for(char chr : shape.toCharArray()) {
			input[x++] = itemMap.get(chr);
		}
	}

	@Override
	public boolean matches(ModuleCraftingWrapper inv, World world) {
		if (holder != null && !ItemUtil.isCraftingEquivalent(holder, inv.getHolder())) {
			return false;
		}
		for(int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
			for(int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
				if (checkMatch(inv, x, y, false)) {
					return true;
				}
				if (mirrored && checkMatch(inv, x, y, true)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (holder != null) {
			return false;
		}
		for(int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
			for(int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
				if (checkMatch(inv, x, y, false)) {
					return true;
				}
				if (mirrored && checkMatch(inv, x, y, true)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
		for(int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
			for(int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
				int subX = x - startX;
				int subY = y - startY;
				Object target = null;
				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror) {
						target = input[width - subX - 1 + subY * width];
					} else {
						target = input[subX + subY * width];
					}
				}
				ItemStack slot = inv.getStackInRowAndColumn(x, y);
				if (target instanceof ItemStack) {
					if (!ItemUtil.isCraftingEquivalent((ItemStack) target, slot)) {
						return false;
					}
				} else if (target instanceof List) {
					boolean matched = false;
					Iterator<ItemStack> itr = ((List<ItemStack>) target).iterator();
					while (itr.hasNext() && !matched) {
						matched = ItemUtil.isCraftingEquivalent(itr.next(), slot);
					}
					if (!matched) {
						return false;
					}
				} else if (target == null && slot != null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack getHolder() {
		return holder;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return output.copy();
	}

	@Override
	public int getRecipeSize() {
		return input.length;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}

	@Override
	public Object[] getInput() {
		return input;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}