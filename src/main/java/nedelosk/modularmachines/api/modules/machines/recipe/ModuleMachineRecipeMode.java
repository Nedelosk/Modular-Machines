package nedelosk.modularmachines.api.modules.machines.recipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleMachineRecipeMode extends ModuleMachineRecipe implements IModuleMachineRecipeMode {

	public IMachineMode defaultMode;

	public ModuleMachineRecipeMode(String moduleUID, String moduleModifier, int inputs, int outputs, int speed, IMachineMode defaultMode) {
		super(moduleUID, moduleModifier, inputs, outputs, speed);
		this.defaultMode = defaultMode;
	}

	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers) {
		IMachineMode mode = (IMachineMode) craftingModifiers[0];
		NBTTagCompound nbtCrafting = new NBTTagCompound();
		nbtCrafting.setInteger("Mode", mode.ordinal());
		nbt.setTag("Crafting", nbtCrafting);
	}

	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = getModeClass().getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[] { mode };
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
