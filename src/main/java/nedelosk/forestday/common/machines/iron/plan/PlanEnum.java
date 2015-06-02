package nedelosk.forestday.common.machines.iron.plan;

import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.api.plan.IPlanEnum;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public enum PlanEnum implements IPlanEnum {
	
	Alloy_Smelter(new ItemStack(ForestdayBlockRegistry.alloySmelter), 3, new ItemStack[][] {{new ItemStack(Blocks.cobblestone, 64), new ItemStack(Blocks.log, 32)}, {new ItemStack(Blocks.cobblestone, 64), new ItemStack(Blocks.log, 32)}, {new ItemStack(Blocks.cobblestone, 64), new ItemStack(Blocks.log, 32)}});

	private ItemStack[][] input;
	private OreStack[][] inputOre;
	private ItemStack output;
	private int buildingStages;
	
	private PlanEnum(ItemStack output, int buildingStages, ItemStack[]... input) {
		this.input = input;
		this.output = output;
		this.buildingStages = buildingStages;
	}
	
	private PlanEnum(ItemStack output, int buildingStages, OreStack[]... input) {
		this.inputOre = input;
		this.output = output;
		this.buildingStages = buildingStages;
	}
	
	@Override
	public ItemStack[][] getInput() {
		return input;
	}

	@Override
	public OreStack[][] getInputOre() {
		return inputOre;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public int getBuildingStages() {
		return buildingStages;
	}

}
