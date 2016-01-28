package nedelosk.modularmachines.common.producers.machines.boiler;

public class ModuleBurningBoiler /* extends ModuleBoiler */ {
	/*
	 * public ModuleBurningBoiler() { this(25, 100, 1000); } public
	 * ModuleBurningBoiler(int speed, int energy, int water) { super("Burning",
	 * 1, 0, 1, 1, speed, energy, water); } public
	 * ModuleBurningBoiler(NBTTagCompound nbt, IModular modular, ModuleStack
	 * stack) { super(nbt, modular, stack); }
	 * @Override public List<Slot> addSlots(IContainerBase container, IModular
	 * modular, ModuleStack stack) { ArrayList<Slot> slots = new
	 * ArrayList<Slot>(); slots.add(new SlotModular(modular.getMachine(), 0, 80,
	 * 34, stack)); return slots; }
	 * @SideOnly(Side.CLIENT)
	 * @Override public void addWidgets(IGuiBase gui, IModular modular,
	 * ModuleStack stack) { gui.getWidgetManager().add(new WidgetBurningBar(80,
	 * 54, fuel, fuelTotal)); }
	 * @SideOnly(Side.CLIENT)
	 * @Override public void updateGui(IGuiBase base, int x, int y, IModular
	 * modular, ModuleStack stack) { List<Widget> widgets =
	 * base.getWidgetManager().getWidgets(); for ( Widget widget : widgets ) {
	 * if (widget instanceof WidgetBurningBar) { ModuleBurningBoiler generator =
	 * (ModuleBurningBoiler) stack.getModule(); if (generator != null) { int
	 * fuel = generator.fuel; int fuelTotal = generator.fuelTotal;
	 * ((WidgetBurningBar) widget).fuel = fuel; ((WidgetBurningBar)
	 * widget).fuelTotal = fuelTotal; } } } }
	 * @Override public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe
	 * recipe) { return null; }
	 * @Override public int getColor() { return 0xAA681C; }
	 * @Override public void updateServer(IModular modular, ModuleStack stack) {
	 * IModularTileEntity<IModularInventory> tile = modular.getMachine(); if
	 * (modular.getManager().getFluidHandler() != null) { if (fuel > 0) {
	 * IFluidHandler handler = tile.getModular().getManager().getFluidHandler();
	 * FluidStack waterF = handler.drain(ForgeDirection.UNKNOWN, new
	 * FluidStack(FluidRegistry.WATER, water), false); if (waterF != null &&
	 * waterF.amount >= water) { handler.drain(ForgeDirection.UNKNOWN, new
	 * FluidStack(FluidRegistry.WATER, water), true); int fluid =
	 * modular.getTankManeger().getModule().fill(ForgeDirection.UNKNOWN, new
	 * FluidStack(FluidRegistry.getFluid("steam"), steam), false, stack,
	 * modular); if (fluid >= steam) {
	 * modular.getTankManeger().getModule().fill(ForgeDirection.UNKNOWN, new
	 * FluidStack(FluidRegistry.getFluid("steam"), steam), true, stack,
	 * modular); } } fuel--; } else { RecipeInput[] inputs = getInputs(modular,
	 * stack); if (inputs != null) { if (inputs[0].isItem() &&
	 * TileEntityFurnace.getItemBurnTime(inputs[0].item) > 0) { int burnTime =
	 * TileEntityFurnace.getItemBurnTime(inputs[0].item); if
	 * (!removeInputs(modular, stack, 1)) { return; } fuel = burnTime; fuelTotal
	 * = burnTime; } } } if (timer > timerTotal) {
	 * modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine(
	 * ).getXCoord(), modular.getMachine().getYCoord(),
	 * modular.getMachine().getZCoord()); timer = 0; } else { timer++; } } }
	 * public boolean removeInputs(IModular modular, ModuleStack stack, int
	 * size) { IModularTileEntity<IModularInventory> tile =
	 * modular.getMachine(); for ( int i = 0; i < getInputs(modular,
	 * stack).length; i++ ) { RecipeInput input = getInputs(modular, stack)[i];
	 * if (input != null) { if (!input.isFluid()) { if (input.isOre()) { if
	 * (tile.getModular().getInventoryManager().decrStackSize(stack.getModule().
	 * getName(stack, false), input.slotIndex, size) == null) { return false; }
	 * } else { if
	 * (tile.getModular().getInventoryManager().decrStackSize(stack.getModule().
	 * getName(stack, false), input.slotIndex, size) == null) { return false; }
	 * } continue; } } else { return false; } } return true; }
	 * @Override public String getRecipeName(ModuleStack stack) { return null; }
	 * @Override public RecipeInput[] getInputs(IModular modular, ModuleStack
	 * stack) { return getInputItems(modular, stack); }
	 * @Override public List<String> getRequiredModules() { ArrayList<String>
	 * modules = new ArrayList(); modules.add("TankManager");
	 * modules.add("Casing"); return modules; }
	 */
}
