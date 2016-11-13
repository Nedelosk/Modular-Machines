package modularmachines.common.modules.propertys;

import java.util.List;

import net.minecraft.nbt.NBTTagString;

import modularmachines.api.property.IPropertyProvider;
import modularmachines.api.property.PropertyBase;
import modularmachines.api.recipes.IRecipe;

public class PropertyFurnaceRecipe extends PropertyBase<IRecipe, NBTTagString, IPropertyProvider> {

	private List<IRecipe> recipes;

	public PropertyFurnaceRecipe(String name, List<IRecipe> recipes) {
		super(name, IRecipe.class, null);
		this.recipes = recipes;
	}

	@Override
	public NBTTagString writeToNBT(IPropertyProvider state, IRecipe value) {
		return new NBTTagString(value.getRecipeName());
	}

	@Override
	public IRecipe readFromNBT(NBTTagString nbt, IPropertyProvider state) {
		for (IRecipe recipe : recipes) {
			if (recipe != null && recipe.getRecipeName() != null && recipe.getRecipeName().equals(nbt.getString())) {
				return recipe;
			}
		}
		return null;
	}

	public List<IRecipe> getRecipes() {
		return recipes;
	}
}
