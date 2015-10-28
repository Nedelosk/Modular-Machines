package nedelosk.forestday.common.crafting;

import nedelosk.forestday.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public class WorkbenchRecipe {
	private ItemStack input, inputPattern, inputTool;
	private ItemStack output;
	private OreStack sInput, sInputPattern, sInputTool;
	private int burnTime;

	public WorkbenchRecipe(ItemStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output,
			int burnTime) {
		this.input = input;
		this.inputTool = inputTool;
		this.inputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output,
			int burnTime) {
		this.sInput = input;
		this.inputTool = inputTool;
		this.inputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, ItemStack inputTool, ItemStack output, int burnTime) {
		this.sInput = input;
		this.inputTool = inputTool;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, OreStack inputTool, ItemStack output, int burnTime) {
		this.sInput = input;
		this.sInputTool = inputTool;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(ItemStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output,
			int burnTime) {
		this.input = input;
		this.sInputTool = inputTool;
		this.inputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(ItemStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output,
			int burnTime) {
		this.input = input;
		this.inputTool = inputTool;
		this.sInputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		this.sInput = input;
		this.inputTool = inputTool;
		this.sInputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(ItemStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		this.input = input;
		this.sInputTool = inputTool;
		this.sInputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		this.sInput = input;
		this.sInputTool = inputTool;
		this.sInputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(ItemStack input, OreStack inputTool, ItemStack output, int burnTime) {
		this.input = input;
		this.sInputTool = inputTool;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(OreStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime) {
		this.sInput = input;
		this.sInputTool = inputTool;
		this.inputPattern = inputPattern;
		this.output = output;
		this.burnTime = burnTime;
	}

	public WorkbenchRecipe(ItemStack input, ItemStack inputTool, ItemStack output, int burnTime) {
		this.input = input;
		this.inputTool = inputTool;
		this.output = output;
		this.burnTime = burnTime;
	}

	public ItemStack getInput() {
		return this.input;
	}

	public OreStack getsInputTool() {
		return sInputTool;
	}

	public ItemStack getInputPattern() {
		return inputPattern;
	}

	public OreStack getsInput() {
		return sInput;
	}

	public ItemStack getInputTool() {
		return inputTool;
	}

	public OreStack getsInputPattern() {
		return sInputPattern;
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public int getBurnTime() {
		return burnTime;
	}

}
