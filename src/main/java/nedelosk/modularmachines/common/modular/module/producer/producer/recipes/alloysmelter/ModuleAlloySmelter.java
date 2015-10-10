package nedelosk.modularmachines.common.modular.module.producer.producer.recipes.alloysmelter;

import java.util.ArrayList;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import nedelosk.modularmachines.client.renderers.modules.ModularMachineRenderer;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.ModuleProducerRecipe;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ModuleAlloySmelter extends ModuleProducerRecipe {

	public ModuleAlloySmelter() {
		super("AlloySmelter", 2, 2);
	}
	
	public ModuleAlloySmelter(int speedModifier) {
		super("AlloySmelter", 2, 2, speedModifier);
	}

	//Inventory
	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 36, 35, this.getName()));
		list.add(new SlotModular(modular.getMachine(), 1, 54, 35, this.getName()));
		list.add(new SlotModular(modular.getMachine(), 2, 116, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		list.add(new SlotModular(modular.getMachine(), 3, 134, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		return list;
	}
	
	@Override
	public int getSizeInventory() {
		return 4;
	}
	
	//Gui
	@Override
	public void updateGui(IGuiBase base, int x, int y) {
		ArrayList<Widget> widgets = base.getWidgetManager().getWidgets();
		if(widgets.size() > 0 && widgets.get(0) instanceof WidgetProgressBar){
			((WidgetProgressBar)widgets.get(0)).burntime = burnTime;
			((WidgetProgressBar)widgets.get(0)).burntimeTotal = burnTimeTotal;
		}
	}
	
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton) {
		if(widget instanceof WidgetProgressBar){
			if(Loader.isModLoaded("NEI")){
				GuiCraftingRecipe.openRecipeGui("ModularMachinesAlloySmelter");
			}
		}
	}
	
	//NEI
	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(74, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	//Recipe
	@Override
	public RecipeInput[] getInputs(IModular modular) {
		return getInputItems(modular);
	}

	@Override
	public String getRecipeName() {
		return "AlloySmelter";
	}
	
	@Override
	public int getSpeedModifier() {
		return 95;
	}

	//Item
	@Override
	public PartType[] getRequiredComponents() {
		return new PartType[]{new MachinePartType(ItemRegistry.Burning_Chamber),
							  new MachinePartType(ItemRegistry.Module),
							  new MachinePartType(ItemRegistry.Burning_Chamber) };
	}
	
	@Override
	public int getColor() {
		return 0x932B2B;
	}

	@Override
	public IModule getModule(int speedModifier) {
		return new ModuleAlloySmelter(speedModifier);
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.AlloySmelterRenderer(moduleStack.getModule());
	}

}
