package modularmachines.client.gui.widgets;

/*public class WidgetMode extends Widget<IModuleState<IModuleModeMachine>> {

	public WidgetMode(int posX, int posY, IModuleState<IModuleModeMachine> provider) {
		super(posX, posY, 18, 18, provider);
	}

	private IToolMode getMode() {
		return source.getModule().getCurrentMode(source);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		list.add(Translator.translateToLocal("mode." + getMode().getName() + ".name"));
		return list;
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + positon.x, guiTop + positon.y, 238, 0, 18, 18);
		gui.drawTexturedModalRect(guiLeft + positon.x, guiTop + positon.y, 238, 18 * getMode().ordinal() + 18, 18, 18);
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		ModularMachines.proxy.playButtonClick();
		IModuleModeMachine module = source.getModule();
		module.setCurrentMode(source, module.getNextMode(source));
		PacketHandler.sendToServer(new PacketSyncToolMode(source.getModular().getHandler(), source));
	}
}*/