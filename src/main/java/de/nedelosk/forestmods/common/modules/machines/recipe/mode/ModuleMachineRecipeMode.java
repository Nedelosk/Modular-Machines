package de.nedelosk.forestmods.common.modules.machines.recipe.mode;

import com.google.gson.JsonObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeMode;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipe;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeGui;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleMachineRecipeMode extends ModuleMachineRecipe implements IModuleMachineRecipeMode {

	public IMachineMode defaultMode;

	public ModuleMachineRecipeMode(String moduleUID, String moduleModifier, int inputs, int outputs, int speed, IMachineMode defaultMode) {
		super(moduleUID, moduleModifier, inputs, outputs, speed);
		this.defaultMode = defaultMode;
	}

	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers) {
		IMachineMode mode = (IMachineMode) craftingModifiers[0];
		NBTTagCompound nbtCrafting = new NBTTagCompound();
		nbtCrafting.setInteger("Mode", mode.ordinal());
		nbt.setTag("Crafting", nbtCrafting);
	}

	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = getModeClass().getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[] { mode };
	}

	@Override
	public Object[] parseCraftingModifiers(JsonObject object) {
		if (object.has("Mode") && object.get("Mode").isJsonPrimitive()) {
			return new Object[] { getModeClass().getEnumConstants()[object.get("Mode").getAsInt()] };
		}
		return null;
	}

	@Override
	public JsonObject writeCraftingModifiers(Object[] objects) {
		JsonObject object = new JsonObject();
		object.addProperty("Mode", ((IMachineMode) objects[0]).ordinal());
		return object;
	}

	@Override
	public Object[] getCraftingModifiers(IModular modular, ModuleStack stack) {
		return new Object[] { ((IModuleMachineRecipeModeSaver) stack.getSaver()).getMode() };
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleMachineRecipeGui(getUID());
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleMachineRecipeModeSaver(defaultMode);
	}
}
