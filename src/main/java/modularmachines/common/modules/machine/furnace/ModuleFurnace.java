package modularmachines.common.modules.machine.furnace;

/*public class ModuleFurnace extends ModuleHeatMachine<IRecipeHeat> implements IModuleJEI {
	
	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(1.0F / 16.0F, 0.0F, 15.0F / 16F, 15.0F / 16.0F, 15.0F / 16.0F, 1.0F);
	public static final List<IRecipeHeat> FURNACE_RECIPES = new ArrayList<>();
	public final ItemHandlerModule itemHandler;
	
	public ModuleFurnace(int workTimeModifier) {
		super(workTimeModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
	}
	
	@Override
	protected AxisAlignedBB getBoundingBox() {
		return BOUNDING_BOX;
	}
	
	@Override
	public String[] getJeiRecipeCategories() {
		return new String[]{MachineCategorys.FURNACE};
	}
	
	@Override
	protected IRecipeConsumer[] getConsumers() {
		return new IRecipeConsumer[]{itemHandler};
	}
	
	@Override
	public RecipeItem[] getInputs() {
		return itemHandler.getInputs();
	}
	
	@Override
	protected String getRecipeCategory() {
		return MachineCategorys.FURNACE;
	}
	
	@Override
	public void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentFurnace(this));
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public List<IRecipeHeat> getRecipes() {
		if (FURNACE_RECIPES.isEmpty()) {
			for (Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
				ItemStack input = entry.getKey();
				ItemStack output = entry.getValue();
				if (input != null && output != null) {
					FURNACE_RECIPES.add(new RecipeFurnace(entry.getKey(), entry.getValue(), 2, 0.15D, 50D));
				}
			}
		}
		return FURNACE_RECIPES;
	}
}*/
