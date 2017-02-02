package modularmachines.common.modules.heaters;

/*public class PageSteamHeater extends ModulePage {

	public static final DecimalFormat FORMATE = new DecimalFormat("#0.00");
	
	public PageSteamHeater(Module parent) {
		super(parent);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		IHeatSource heatSource = ModuleUtil.getHeat(parent.getLogic());
		String heatName = Translator.translateToLocalFormatted("module.heater.heat", FORMATE.format(heatSource.getHeatStored()));
		fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2), 45, Color.gray.getRGB());
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, "liquid", ItemFilterFluid.get(FluidManager.STEAM));
		invBuilder.addInventorySlot(false, 15, 48, "container", OutputFilter.INSTANCE);
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 80, 18, FluidFilter.get(FluidManager.STEAM));
	}
}*/
