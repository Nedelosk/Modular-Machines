package modularmachines.client.gui.widgets;

/*public class WidgetRedstoneMode extends Widget<IModuleState<IModuleControlled>> {

	public WidgetRedstoneMode(int posX, int posY, IModuleState<IModuleControlled> provider) {
		super(posX, posY, 18, 18, provider);
	}

	private EnumRedstoneMode getMode() {
		return source.getModule().getModuleControl(source).getRedstoneMode();
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		list.add(getMode().getLocName());
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		EnumRedstoneMode mode = getMode();
		gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y, 166 + mode.ordinal() * 18, 0, 18, 18);
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		IModuleControlled module = source.getModule();
		if (mouseButton == 1) {
			module.getModuleControl(source).setRedstoneMode(getMode().previous());
		} else {
			module.getModuleControl(source).setRedstoneMode(getMode().next());
		}
		PacketHandler.sendToServer(new PacketSyncRedstoneMode(source.getModular().getHandler(), source));
	}
}*/