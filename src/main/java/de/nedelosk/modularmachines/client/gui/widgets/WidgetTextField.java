package de.nedelosk.modularmachines.client.gui.widgets;

import com.google.common.base.Strings;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import de.nedelosk.modularmachines.client.gui.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;

public class WidgetTextField<T extends IGuiHandler> extends Widget<T> {

	public interface ICharFilter {

		boolean passesFilter(WidgetTextField widgetText, char c);
	}

	public static final ICharFilter FILTER_NUMERIC = new ICharFilter() {

		@Override
		public boolean passesFilter(WidgetTextField widgetText, char c) {
			return Character.isDigit(c) || (c == '-' && Strings.isNullOrEmpty(widgetText.getText()));
		}
	};
	public static ICharFilter FILTER_ALPHABETICAL = new ICharFilter() {

		@Override
		public boolean passesFilter(WidgetTextField widgetText, char c) {
			return Character.isLetter(c);
		}
	};
	public static ICharFilter FILTER_ALPHANUMERIC = new ICharFilter() {

		@Override
		public boolean passesFilter(WidgetTextField widgetText, char c) {
			return FILTER_NUMERIC.passesFilter(widgetText, c) || FILTER_ALPHABETICAL.passesFilter(widgetText, c);
		}
	};
	private final FontRenderer fontRenderer;
	/** Has the current text being edited on the textbox. */
	private String text = "";
	private ICharFilter filter;
	private int maxStringLength = 32;
	private int cursorCounter;
	private boolean enableBackgroundDrawing = true;
	/**
	 * if true the textbox can lose focus by clicking elsewhere on the screen
	 */
	private boolean canLoseFocus = true;
	/**
	 * If this value is true along with isEnabled, keyTyped will process the
	 * keys.
	 */
	private boolean isFocused;
	/**
	 * If this value is true along with isFocused, keyTyped will process the
	 * keys.
	 */
	private boolean isEnabled = true;
	/**
	 * The current character index that should be used as start of the rendered
	 * text.
	 */
	private int lineScrollOffset;
	private int cursorPosition;
	/** other selection position, maybe the same as the cursor */
	private int selectionEnd;
	private int enabledColor = 14737632;
	private int disabledColor = 7368816;
	/** True if this textbox is visible */
	private boolean visible = true;
	private static final String __OBFID = "CL_00000670";

	public WidgetTextField(int posX, int posY, FontRenderer fontRenderer, int width, int height, ICharFilter filter) {
		super(posX, posY, width, height);
		this.fontRenderer = fontRenderer;
		this.filter = filter;
	}

	public WidgetTextField(int posX, int posY, int width, int height, ICharFilter filter) {
		super(posX, posY, width, height);
		this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		this.filter = filter;
	}

	/**
	 * Increments the cursor counter
	 */
	public void updateCursorCounter() {
		++this.cursorCounter;
	}

	/**
	 * Sets the text of the textbox
	 */
	public void setText(String p_146180_1_) {
		if (p_146180_1_.length() > this.maxStringLength) {
			this.text = p_146180_1_.substring(0, this.maxStringLength);
		} else {
			this.text = p_146180_1_;
		}
		this.setCursorPositionEnd();
	}

	/**
	 * Returns the contents of the textbox
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * returns the text between the cursor and selectionEnd
	 */
	public String getSelectedText() {
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		return this.text.substring(i, j);
	}

	/**
	 * replaces selected text, or inserts text at the position on the cursor
	 */
	public void writeText(String p_146191_1_) {
		String s1 = "";
		String s2 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		int k = this.maxStringLength - this.text.length() - (i - this.selectionEnd);
		boolean flag = false;
		if (this.text.length() > 0) {
			s1 = s1 + this.text.substring(0, i);
		}
		int l;
		if (k < s2.length()) {
			s1 = s1 + s2.substring(0, k);
			l = k;
		} else {
			s1 = s1 + s2;
			l = s2.length();
		}
		if (this.text.length() > 0 && j < this.text.length()) {
			s1 = s1 + this.text.substring(j);
		}
		this.text = s1;
		this.moveCursorBy(i - this.selectionEnd + l);
	}

	/**
	 * Deletes the specified number of words starting at the cursor position.
	 * Negative numbers will delete words left of the cursor.
	 */
	public void deleteWords(int p_146177_1_) {
		if (this.text.length() != 0) {
			if (this.selectionEnd != this.cursorPosition) {
				this.writeText("");
			} else {
				this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
			}
		}
	}

	/**
	 * delete the selected text, otherwsie deletes characters from either side
	 * of the cursor. params: delete num
	 */
	public void deleteFromCursor(int p_146175_1_) {
		if (this.text.length() != 0) {
			if (this.selectionEnd != this.cursorPosition) {
				this.writeText("");
			} else {
				boolean flag = p_146175_1_ < 0;
				int j = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
				int k = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
				String s = "";
				if (j >= 0) {
					s = this.text.substring(0, j);
				}
				if (k < this.text.length()) {
					s = s + this.text.substring(k);
				}
				this.text = s;
				if (flag) {
					this.moveCursorBy(p_146175_1_);
				}
			}
		}
	}

	/**
	 * see @getNthNextWordFromPos() params: N, position
	 */
	public int getNthWordFromCursor(int p_146187_1_) {
		return this.getNthWordFromPos(p_146187_1_, this.getCursorPosition());
	}

	/**
	 * gets the position of the nth word. N may be negative, then it looks
	 * backwards. params: N, position
	 */
	public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
		return this.func_146197_a(p_146183_1_, this.getCursorPosition(), true);
	}

	public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
		int k = p_146197_2_;
		boolean flag1 = p_146197_1_ < 0;
		int l = Math.abs(p_146197_1_);
		for(int i1 = 0; i1 < l; ++i1) {
			if (flag1) {
				while (p_146197_3_ && k > 0 && this.text.charAt(k - 1) == 32) {
					--k;
				}
				while (k > 0 && this.text.charAt(k - 1) != 32) {
					--k;
				}
			} else {
				int j1 = this.text.length();
				k = this.text.indexOf(32, k);
				if (k == -1) {
					k = j1;
				} else {
					while (p_146197_3_ && k < j1 && this.text.charAt(k) == 32) {
						++k;
					}
				}
			}
		}
		return k;
	}

	/**
	 * Moves the text cursor by a specified number of characters and clears the
	 * selection
	 */
	public void moveCursorBy(int p_146182_1_) {
		this.setCursorPosition(this.selectionEnd + p_146182_1_);
	}

	/**
	 * sets the position of the cursor to the provided index
	 */
	public void setCursorPosition(int p_146190_1_) {
		this.cursorPosition = p_146190_1_;
		int j = this.text.length();
		if (this.cursorPosition < 0) {
			this.cursorPosition = 0;
		}
		if (this.cursorPosition > j) {
			this.cursorPosition = j;
		}
		this.setSelectionPos(this.cursorPosition);
	}

	/**
	 * sets the cursors position to the beginning
	 */
	public void setCursorPositionZero() {
		this.setCursorPosition(0);
	}

	/**
	 * sets the cursors position to after the text
	 */
	public void setCursorPositionEnd() {
		this.setCursorPosition(this.text.length());
	}

	/**
	 * Call this method from your GuiScreen to process the keys into the textbox
	 */
	@Override
	public boolean keyTyped(char keyChar, int keyCode, IGuiBase<T> gui) {
		if (filter == null || filter.passesFilter(this, keyChar) || isSpecialChar(keyChar, keyCode)) {
			if (!this.isFocused) {
				return false;
			} else {
				switch (keyChar) {
					case 1:
						this.setCursorPositionEnd();
						this.setSelectionPos(0);
						return true;
					case 3:
						GuiScreen.setClipboardString(this.getSelectedText());
						return true;
					case 22:
						if (this.isEnabled) {
							this.writeText(GuiScreen.getClipboardString());
						}
						return true;
					case 24:
						GuiScreen.setClipboardString(this.getSelectedText());
						if (this.isEnabled) {
							this.writeText("");
						}
						return true;
					default:
						switch (keyCode) {
							case 14:
								if (GuiScreen.isCtrlKeyDown()) {
									if (this.isEnabled) {
										this.deleteWords(-1);
									}
								} else if (this.isEnabled) {
									this.deleteFromCursor(-1);
								}
								return true;
							case 199:
								if (GuiScreen.isShiftKeyDown()) {
									this.setSelectionPos(0);
								} else {
									this.setCursorPositionZero();
								}
								return true;
							case 203:
								if (GuiScreen.isShiftKeyDown()) {
									if (GuiScreen.isCtrlKeyDown()) {
										this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
									} else {
										this.setSelectionPos(this.getSelectionEnd() - 1);
									}
								} else if (GuiScreen.isCtrlKeyDown()) {
									this.setCursorPosition(this.getNthWordFromCursor(-1));
								} else {
									this.moveCursorBy(-1);
								}
								return true;
							case 205:
								if (GuiScreen.isShiftKeyDown()) {
									if (GuiScreen.isCtrlKeyDown()) {
										this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
									} else {
										this.setSelectionPos(this.getSelectionEnd() + 1);
									}
								} else if (GuiScreen.isCtrlKeyDown()) {
									this.setCursorPosition(this.getNthWordFromCursor(1));
								} else {
									this.moveCursorBy(1);
								}
								return true;
							case 207:
								if (GuiScreen.isShiftKeyDown()) {
									this.setSelectionPos(this.text.length());
								} else {
									this.setCursorPositionEnd();
								}
								return true;
							case 211:
								if (GuiScreen.isCtrlKeyDown()) {
									if (this.isEnabled) {
										this.deleteWords(1);
									}
								} else if (this.isEnabled) {
									this.deleteFromCursor(1);
								}
								return true;
							default:
								if (ChatAllowedCharacters.isAllowedCharacter(keyChar)) {
									if (this.isEnabled) {
										this.writeText(Character.toString(keyChar));
									}
									return true;
								} else {
									return false;
								}
						}
				}
			}
		}
		return false;
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<T> gui) {
		boolean flag = isMouseOver(mouseX, mouseY);
		if (this.canLoseFocus) {
			this.setFocused(flag);
		}
		if (this.isFocused && mouseButton == 0) {
			int l = mouseX - gui.getGuiLeft() + pos.x;
			if (this.enableBackgroundDrawing) {
				l -= 4;
			}
			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
			this.setCursorPosition(this.fontRenderer.trimStringToWidth(s, l).length() + this.lineScrollOffset);
		}
	}

	/**
	 * Draws the textbox
	 */
	@Override
	public void draw(IGuiBase<T> gui) {
		if (this.getVisible()) {
			if (this.getEnableBackgroundDrawing()) {
				Gui.drawRect(gui.getGuiLeft() + pos.x - 1, gui.getGuiTop() + pos.y - 1, gui.getGuiLeft() + pos.x + pos.width + 1,
						gui.getGuiTop() + pos.y + pos.height + 1, -6250336);
				Gui.drawRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, gui.getGuiLeft() + pos.x + pos.width, gui.getGuiTop() + pos.y + pos.height,
						-16777216);
			}
			int i = this.isEnabled ? this.enabledColor : this.disabledColor;
			int j = this.cursorPosition - this.lineScrollOffset;
			int k = this.selectionEnd - this.lineScrollOffset;
			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
			boolean flag = j >= 0 && j <= s.length();
			boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
			int l = this.enableBackgroundDrawing ? gui.getGuiLeft() + pos.x + 4 : gui.getGuiLeft() + pos.x;
			int i1 = this.enableBackgroundDrawing ? gui.getGuiTop() + pos.y + (pos.height - 8) / 2 : gui.getGuiTop() + pos.y;
			int j1 = l;
			if (k > s.length()) {
				k = s.length();
			}
			if (s.length() > 0) {
				String s1 = flag ? s.substring(0, j) : s;
				j1 = this.fontRenderer.drawStringWithShadow(s1, l, i1, i);
			}
			boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
			int k1 = j1;
			if (!flag) {
				k1 = j > 0 ? l + pos.width : l;
			} else if (flag2) {
				k1 = j1 - 1;
				--j1;
			}
			if (s.length() > 0 && flag && j < s.length()) {
				this.fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, i);
			}
			if (flag1) {
				if (flag2) {
					Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
				} else {
					this.fontRenderer.drawStringWithShadow("_", k1, i1, i);
				}
			}
			if (k != j) {
				int l1 = l + this.fontRenderer.getStringWidth(s.substring(0, k));
				this.drawCursorVertical(gui, k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT);
			}
		}
	}

	/**
	 * draws the vertical line cursor in the textbox
	 */
	private void drawCursorVertical(IGuiBase<T> gui, int startX, int startY, int endX, int endY)
	{
		if (startX < endX)
		{
			int i = startX;
			startX = endX;
			endX = i;
		}

		if (startY < endY)
		{
			int j = startY;
			startY = endY;
			endY = j;
		}

		if (endX > pos.x + pos.width)
		{
			endX = pos.x + pos.width;
		}

		if (startX > pos.x + pos.width)
		{
			startX = pos.x + pos.width;
		}

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.enableColorLogic();
		GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(startX, endY, 0.0D).endVertex();
		vertexbuffer.pos(endX, endY, 0.0D).endVertex();
		vertexbuffer.pos(endX, startY, 0.0D).endVertex();
		vertexbuffer.pos(startX, startY, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.disableColorLogic();
		GlStateManager.enableTexture2D();
	}

	public void setMaxStringLength(int p_146203_1_) {
		this.maxStringLength = p_146203_1_;
		if (this.text.length() > p_146203_1_) {
			this.text = this.text.substring(0, p_146203_1_);
		}
	}

	/**
	 * returns the maximum number of character that can be contained in this
	 * textbox
	 */
	public int getMaxStringLength() {
		return this.maxStringLength;
	}

	/**
	 * returns the current position of the cursor
	 */
	public int getCursorPosition() {
		return this.cursorPosition;
	}

	/**
	 * get enable drawing background and outline
	 */
	public boolean getEnableBackgroundDrawing() {
		return this.enableBackgroundDrawing;
	}

	/**
	 * enable drawing background and outline
	 */
	public void setEnableBackgroundDrawing(boolean p_146185_1_) {
		this.enableBackgroundDrawing = p_146185_1_;
	}

	/**
	 * Sets the text colour for this textbox (disabled text will not use this
	 * colour)
	 */
	public void setTextColor(int p_146193_1_) {
		this.enabledColor = p_146193_1_;
	}

	public void setDisabledTextColour(int p_146204_1_) {
		this.disabledColor = p_146204_1_;
	}

	/**
	 * Sets focus to this gui element
	 */
	public void setFocused(boolean p_146195_1_) {
		if (p_146195_1_ && !this.isFocused) {
			this.cursorCounter = 0;
		}
		this.isFocused = p_146195_1_;
	}

	/**
	 * Getter for the focused field
	 */
	public boolean isFocused() {
		return this.isFocused;
	}

	public void setEnabled(boolean p_146184_1_) {
		this.isEnabled = p_146184_1_;
	}

	/**
	 * the side of the selection that is not the cursor, may be the same as the
	 * cursor
	 */
	public int getSelectionEnd() {
		return this.selectionEnd;
	}

	/**
	 * returns the width of the textbox depending on if background drawing is
	 * enabled
	 */
	public int getWidth() {
		return this.getEnableBackgroundDrawing() ? pos.width - 8 : pos.width;
	}

	/**
	 * Sets the position of the selection anchor (i.e. position the selection
	 * was started at)
	 */
	public void setSelectionPos(int p_146199_1_) {
		int j = this.text.length();
		if (p_146199_1_ > j) {
			p_146199_1_ = j;
		}
		if (p_146199_1_ < 0) {
			p_146199_1_ = 0;
		}
		this.selectionEnd = p_146199_1_;
		if (this.fontRenderer != null) {
			if (this.lineScrollOffset > j) {
				this.lineScrollOffset = j;
			}
			int k = this.getWidth();
			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), k);
			int l = s.length() + this.lineScrollOffset;
			if (p_146199_1_ == this.lineScrollOffset) {
				this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.text, k, true).length();
			}
			if (p_146199_1_ > l) {
				this.lineScrollOffset += p_146199_1_ - l;
			} else if (p_146199_1_ <= this.lineScrollOffset) {
				this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
			}
			if (this.lineScrollOffset < 0) {
				this.lineScrollOffset = 0;
			}
			if (this.lineScrollOffset > j) {
				this.lineScrollOffset = j;
			}
		}
	}

	/**
	 * if true the textbox can lose focus by clicking elsewhere on the screen
	 */
	public void setCanLoseFocus(boolean p_146205_1_) {
		this.canLoseFocus = p_146205_1_;
	}

	/**
	 * returns true if this textbox is visible
	 */
	public boolean getVisible() {
		return this.visible;
	}

	/**
	 * Sets whether or not this textbox is visible
	 */
	public void setVisible(boolean p_146189_1_) {
		this.visible = p_146189_1_;
	}

	public static boolean isSpecialChar(char c, int key) {
		// taken from the giant switch statement in GuiTextField
		return c == 1 || c == 3 || c == 22 || c == 24 || key == 14 || key == 199 || key == 203 || key == 205 || key == 207 || key == 211;
	}
}
