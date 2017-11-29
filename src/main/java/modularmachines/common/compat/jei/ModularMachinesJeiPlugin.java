package modularmachines.common.compat.jei;

//@JEIPlugin
public class ModularMachinesJeiPlugin/* extends BlankModPlugin*/ {

	/*public static IJeiRuntime jeiRuntime;

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		/*for (ModuleData data : ModularMachines.DATAS) {
			IModule module = moduleContainer.getModule();
			if (module instanceof IModuleJEI) {
				registry.addRecipeCategoryCraftingItem(container.getItemStack(), ((IModuleJEI) module).getJEIRecipeCategorys(moduleContainer));
			}
			String description = data.getDescriptionKey();
			if (description != null && !description.isEmpty() && Translator.canTranslateToLocal(description)) {
				registry.addDescription(container.getItemStack(), description);
			}
		}
		registry.addAdvancedGuiHandlers(new AssemblerGuiHandler(), new ModularGuiHandler());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		JeiPlugin.jeiRuntime = jeiRuntime;
	}

	private static class AssemblerGuiHandler extends BlankAdvancedGuiHandler<GuiAssembler> {

		@Nonnull
		@Override
		public Class<GuiAssembler> getGuiContainerClass() {
			return GuiAssembler.class;
		}

		@Nullable
		@Override
		public List<Rectangle> getGuiExtraAreas(GuiAssembler guiContainer) {
			return guiContainer.getExtraGuiAreas();
		}
	}

	private static class ModularGuiHandler extends BlankAdvancedGuiHandler<GuiModuleLogic> {

		@Nonnull
		@Override
		public Class<GuiModuleLogic> getGuiContainerClass() {
			return GuiModuleLogic.class;
		}

		@Nullable
		@Override
		public Object getIngredientUnderMouse(GuiModuleLogic guiContainer, int mouseX, int mouseY) {
			Widget widget = guiContainer.getWidgetManager().getWidgetAtMouse(mouseX, mouseY);
			if (widget instanceof WidgetFluidTank) {
				return ((WidgetFluidTank) widget).tank.getFluid();
			}
			return null;
		}
	}*/
}
