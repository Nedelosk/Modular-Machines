package nedelosk.modularmachines.client.gui.assembler;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import nedelosk.forestday.api.guis.Button;
import nedelosk.forestday.client.gui.GuiBase;
import nedelosk.modularmachines.api.modular.machines.basic.AssemblerMachineInfo;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.client.gui.assembler.element.GuiElement;
import nedelosk.modularmachines.client.gui.GuiButtonItem;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.inventory.assembler.ContainerModularAssembler;
import nedelosk.modularmachines.common.inventory.slots.SlotAssemblerIn;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularAssemblerSelection;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiModularAssembler extends GuiBase<TileModularAssembler> {

	public static final GuiElement ICON_Button = new GuiElement(180, 216, 18, 18);
	public static final GuiElement ICON_ButtonHover = new GuiElement(180 + 18 * 2, 216, 18, 18);
	public static final GuiElement ICON_ButtonPressed = new GuiElement(180 - 18 * 2, 216, 18, 18);

	private static final GuiElement SlotBorder = new GuiElement(176, 0, 18, 18);
	private static final GuiElement SlotBackground = new GuiElement(194, 0, 18, 18);

	public TileModularAssembler assembler;
	public InventoryPlayer inventory;
	protected int activeSlots;

	protected int selectedSlot = 0;

	public AssemblerMachineInfo currentInfo = GuiButtonAssembler.info;

	public GuiModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		this.inventory = inventory;
		assembler = tile;
	}

	@Override
	public void initGui() {
		this.ySize = 174;
		this.xSize = 176 + 110;
		this.mc.thePlayer.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - 176) / 2 - 110;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonManager.clear();
		addButtons();
		buttonList.addAll(buttonManager.getButtons());
		updateGUI();
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	public void onToolSelection(AssemblerMachineInfo info) {
		activeSlots = Math.min(info.positions.size(), tile.getSizeInventory() - 1);
		currentInfo = info;

		((ContainerModularAssembler) inventorySlots).setToolSelection(info, activeSlots);
		PacketHandler.INSTANCE.sendToServer(new PacketModularAssemblerSelection(tile, info, activeSlots));
		updateGUI();
	}

	public void setSelectedButtonByTool(ItemStack stack) {
		for (Object o : buttonList) {
			GuiButtonItem<AssemblerMachineInfo> btn = (GuiButtonItem<AssemblerMachineInfo>) o;
			btn.pressed = ItemStack.areItemStacksEqual(btn.data.machine, stack);
		}
	}

	public void onToolSelectionPacket(PacketModularAssemblerSelection packet) {
		AssemblerMachineInfo info = packet.info;
		if (info == null) {
			info = GuiButtonAssembler.info;
		}
		activeSlots = packet.activeSlots;
		currentInfo = info;

		setSelectedButtonByTool(currentInfo.machine);

		updateGUI();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		for (Object o : buttonList) {
			((GuiButtonItem) o).pressed = false;
		}
		((GuiButtonItem) button).pressed = true;
		selectedSlot = button.id;

		onToolSelection(((GuiButtonItem<AssemblerMachineInfo>) button).data);
	}

	private static final ResourceLocation background = new ResourceLocation("modularmachines",
			"textures/gui/modular_assembler.png");
	private static final ResourceLocation icons = new ResourceLocation("modularmachines", "textures/gui/icons.png");

	protected void drawBackground(ResourceLocation background) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		final int cornerX = this.guiLeft + 110;
		this.drawTexturedModalRect(cornerX, this.guiTop, 0, 0, 176, this.ySize);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
		drawBackground(background);

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		for (int i = 36; i < tile.getSizeInventory() + 35; i++) {
			Slot slot = inventorySlots.getSlot(i);
			if (slot instanceof SlotAssemblerIn && (((SlotAssemblerIn) slot).isActivated() || slot.getHasStack())) {
				SlotBorder.draw(this.guiLeft + slot.xDisplayPosition - 1, this.guiTop + slot.yDisplayPosition - 1);
				SlotBackground.draw(this.guiLeft + slot.xDisplayPosition - 1, this.guiTop + slot.yDisplayPosition - 1);
			}
		}
	}

	@Override
	protected void renderProgressBar() {
	}

	public void updateGUI() {
		int i;
		for (i = 0; i < activeSlots; i++) {
			Point point = currentInfo.positions.get(i);

			Slot slot = inventorySlots.getSlot(i + 36);
			slot.xDisplayPosition = point.getX() + 110;
			slot.yDisplayPosition = point.getY();
		}

		// remaining slots
		int stillFilled = 0;
		for (; i < tile.getSizeInventory() - 1; i++) {
			Slot slot = inventorySlots.getSlot(i + 36);

			if (slot.getHasStack()) {
				slot.xDisplayPosition = 87 + 20 * stillFilled + 110;
				slot.yDisplayPosition = 62;
				stillFilled++;
			} else {
				slot.xDisplayPosition = 0;
				slot.yDisplayPosition = 0;
			}
		}
	}

	@Override
	public void addButtons() {
		int index = 0;

		{
			GuiButtonItem button = new GuiButtonAssembler(index++, -1, -1);
			shiftButton(button, 0, -18);
			addButton(button);

			if (index - 1 == selectedSlot) {
				button.pressed = true;
			}
		}

		for (String modular : ModuleRegistry.getModular().keySet()) {
			AssemblerMachineInfo info = MMRegistry.getAssemblerInfo(modular);
			if (info != null) {
				GuiButtonItem button = new GuiButtonItem<AssemblerMachineInfo>(index++, -1, -1, info.machine, info);
				shiftButton(button, 0, -18);
				addButton(button);

				if (index - 1 == selectedSlot) {
					button.pressed = true;
				}
			}
		}
		updateGUI();
	}

	public void addButton(Button button) {
		int count = buttonManager.getButtons().size();

		int columns = 5;
		int spacing = 5;
		int xI = button.id % columns;
		int yI = button.id / spacing;

		button.xPosition = guiLeft + xI * (button.width + 2);
		button.yPosition = guiTop + yI * (button.height + 2);

		buttonManager.add(button);
	}

	@Override
	protected String getGuiName() {
		return "modular_assembler";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

	public void updateDisplay() {

	}

	protected void shiftButton(GuiButtonItem button, int xd, int yd) {
		button.setGraphics(ICON_Button.shift(xd, yd), ICON_ButtonHover.shift(xd, yd), ICON_ButtonPressed.shift(xd, yd),
				GuiButtonItem.locBackground);
	}

}
