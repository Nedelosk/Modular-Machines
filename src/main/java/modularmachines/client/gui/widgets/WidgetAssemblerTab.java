package modularmachines.client.gui.widgets;

/*public class WidgetAssemblerTab extends Widget<IAssembler> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	protected final IStoragePosition position;
	protected final boolean right;

	public WidgetAssemblerTab(int xPosition, int yPosition, IStoragePosition position, boolean right) {
		super(xPosition, yPosition, 28, 21);
		this.position = position;
		this.right = right;
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GuiBase gui = manager.getGui();
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, (position.equals(source.getSelectedPosition())) ? 0 : 28, right ? 214 : 235, 28, 21);
		IStoragePage page = source.getPage(position);
		if (page != null) {
			ItemStack item = page.getStorageStack();
			if (item != null) {
				gui.drawItemStack(item, guiLeft + pos.x + (right ? 5 : 7), guiTop + pos.y + 2);
			}
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (!source.getSelectedPosition().equals(position)) {
			source.setSelectedPosition(position);
			ModularMachines.proxy.playButtonClick();
			ILocatable locatable= source.getLocatable();
			if(locatable != null){
				World world = locatable.getWorldObj();
				if(world.isRemote){
					PacketHandler.sendToServer(new PacketAssemblerPosition(source, position));
				}
			}
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(Translator.translateToLocal("module.storage." + position.getName() + ".name"));
	}
}*/
