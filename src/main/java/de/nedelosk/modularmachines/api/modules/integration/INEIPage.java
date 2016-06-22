package de.nedelosk.modularmachines.api.modules.integration;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IPage;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public interface INEIPage extends IPage {

	void setRecipe(IRecipe recipe);

	IRecipe getRecipe();

	void createSlots(List<SlotNEI> modularSlots);
}
