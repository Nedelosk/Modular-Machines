package modularmachines.common.modules.battery;

/*public class PageBattery extends MainPage<IModuleBattery> {

	public PageBattery(IModuleState<IModuleBattery> state) {
		super("battery", state);
	}

	@Override
	protected void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 26, 24, ItemFilterEnergy.INSTANCE);
		invBuilder.addInventorySlot(true, 26, 54, ItemFilterEnergy.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
		super.updateGui();
		for (Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
			if (widget instanceof WidgetEnergyField) {
				((WidgetEnergyField) widget).setProvider(moduleState.getContentHandler(IEnergyBuffer.class));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setGui() {
		super.setGui();
		gui.getWidgetManager().add(new WidgetEnergyField(55, 15, moduleState.getContentHandler(IEnergyBuffer.class)));
	}
}*/
