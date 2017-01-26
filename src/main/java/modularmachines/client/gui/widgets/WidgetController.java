package modularmachines.client.gui.widgets;

/*public class WidgetController extends Widget {

	public final IModuleState state;
	public final boolean usedBy;

	public WidgetController(int posX, int posY, IModuleState<IModuleControlled> provider, IModuleState state) {
		this(posX, posY, provider, state, false);
	}

	public WidgetController(int posX, int posY, IModuleState<IModuleControlled> provider, IModuleState state, boolean usedBy) {
		super(posX, posY, 18, 18, provider);
		this.usedBy = usedBy;
		this.state = state;
	}

	@Override
	public List<String> getTooltip() {
		ItemStack itemStack = state.getSource().getItemStack();
		if (itemStack != null && itemStack.hasDisplayName()) {
			return Arrays.asList(itemStack.getDisplayName());
		}
		return Arrays.asList(state.getContainer().getDisplayName());
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GuiBase gui = manager.getG
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		boolean hasPermission;
		if (usedBy) {
			IModuleState<IModuleControlled> state = this.state;
			hasPermission = state.getModule().getModuleControl(state).hasPermission(source);
		} else {
			hasPermission = source.getModule().getModuleControl(source).hasPermission(state);
		}
		gui.drawItemStack(state.getSource().getItemStack(), sx + positon.x, sy + positon.y);
		Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
		if (!hasPermission) {
			gui.getGui().drawTexturedModalRect(sx + positon.x + 1, sy + positon.y + 1, 130, 0, 14, 14);
		}
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		if (usedBy) {
			IModuleState<IModuleControlled> state = this.state;
			state.getModule().getModuleControl(state).setPermission(source, !state.getModule().getModuleControl(state).hasPermission(source));
			PacketHandler.sendToServer(new PacketSyncPermission(state.getModular().getHandler(), state, source));
		} else {
			source.getModule().getModuleControl(source).setPermission(state, !source.getModule().getModuleControl(source).hasPermission(state));
			PacketHandler.sendToServer(new PacketSyncPermission(state.getModular().getHandler(), source, state));
		}
	}
}*/