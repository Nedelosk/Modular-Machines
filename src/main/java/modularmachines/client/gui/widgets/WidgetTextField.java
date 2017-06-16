package modularmachines.client.gui.widgets;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

import modularmachines.common.utils.Translator;

public class WidgetTextField extends Widget {

	  public interface ICharFilter {

		    boolean passesFilter(WidgetTextField tf, char c);
		  }

		  public static final ICharFilter FILTER_NUMERIC = new ICharFilter() {
		    @Override
		    public boolean passesFilter(WidgetTextField tf, char c) {
		      return Character.isDigit(c) || (c == '-' && Strings.isNullOrEmpty(tf.getText()));
		    }
		  };
		  
		  public static final ICharFilter FILTER_NUMERIC_ARRAY = new ICharFilter() {
			    @Override
			    public boolean passesFilter(WidgetTextField tf, char c) {
			      return Character.isDigit(c) || c == ',';
			    }
			  };
	
	private final int id;
	private final FontRenderer fontRendererInstance;
	private String text = "";
	private int maxStringLength = 32;
	private int cursorCounter;
	private boolean enableBackgroundDrawing = true;
	private boolean canLoseFocus = true;
	private boolean isFocused;
	private boolean isEnabled = true;
	private int lineScrollOffset;
	private int cursorPosition;
	private int selectionEnd;
	private int enabledColor = 14737632;
	private int disabledColor = 7368816;
	/** True if this textbox is visible */
	private boolean visible = true;
	private GuiPageButtonList.GuiResponder guiResponder;
	private Predicate<String> validator = Predicates.<String> alwaysTrue();
	private ICharFilter filter;

	public WidgetTextField(int posX, int posY, int width, int height) {
		this(posX, posY, width, height, 0);
	}

	public WidgetTextField(int posX, int posY, int width, int height, int componentId) {
		this(posX, posY, width, height, componentId, Minecraft.getMinecraft().fontRendererObj);
	}

	public WidgetTextField(int posX, int posY, int width, int height, int componentId, FontRenderer fontrendererObj) {
		super(posX, posY, width, height);
		this.id = componentId;
		this.fontRendererInstance = fontrendererObj;
	}
	
	 public WidgetTextField setCharFilter(ICharFilter filter) {
		    this.filter = filter;
		    return this;
		  }

	public void setGuiResponder(GuiPageButtonList.GuiResponder guiResponderIn) {
		this.guiResponder = guiResponderIn;
	}

	public void updateCursorCounter() {
		++this.cursorCounter;
	}

	public void setText(String textIn) {
		if (this.validator.apply(textIn)) {
			if (textIn.length() > this.maxStringLength) {
				this.text = textIn.substring(0, this.maxStringLength);
			} else {
				this.text = textIn;
			}
			this.setCursorPositionEnd();
		}
	}

	@Override
	public String getText() {
		return this.text;
	}

	public String getSelectedText() {
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		return this.text.substring(i, j);
	}

	public WidgetTextField setValidator(Predicate<String> theValidator) {
		this.validator = theValidator;
		return this;
	}

	public void writeText(String textToWrite) {
		String s = "";
		String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		int k = this.maxStringLength - this.text.length() - (i - j);
		if (!this.text.isEmpty()) {
			s = s + this.text.substring(0, i);
		}
		int l;
		if (k < s1.length()) {
			s = s + s1.substring(0, k);
			l = k;
		} else {
			s = s + s1;
			l = s1.length();
		}
		if (!this.text.isEmpty() && j < this.text.length()) {
			s = s + this.text.substring(j);
		}
		if (this.validator.apply(s)) {
			this.text = s;
			this.moveCursorBy(i - this.selectionEnd + l);
			if (this.guiResponder != null) {
				this.guiResponder.setEntryValue(this.id, this.text);
			}
		}
	}

	public void deleteWords(int num) {
		if (!this.text.isEmpty()) {
			if (this.selectionEnd != this.cursorPosition) {
				this.writeText("");
			} else {
				this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursorPosition);
			}
		}
	}

	public void deleteFromCursor(int num) {
		if (!this.text.isEmpty()) {
			if (this.selectionEnd != this.cursorPosition) {
				this.writeText("");
			} else {
				boolean flag = num < 0;
				int i = flag ? this.cursorPosition + num : this.cursorPosition;
				int j = flag ? this.cursorPosition : this.cursorPosition + num;
				String s = "";
				if (i >= 0) {
					s = this.text.substring(0, i);
				}
				if (j < this.text.length()) {
					s = s + this.text.substring(j);
				}
				if (this.validator.apply(s)) {
					this.text = s;
					if (flag) {
						this.moveCursorBy(num);
					}
					if (this.guiResponder != null) {
						this.guiResponder.setEntryValue(this.id, this.text);
					}
				}
			}
		}
	}

	public int getId() {
		return this.id;
	}

	public int getNthWordFromCursor(int numWords) {
		return this.getNthWordFromPos(numWords, this.getCursorPosition());
	}

	public int getNthWordFromPos(int n, int pos) {
		return this.getNthWordFromPosWS(n, pos, true);
	}

	public int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
		int i = pos;
		boolean flag = n < 0;
		int j = Math.abs(n);
		for (int k = 0; k < j; ++k) {
			if (!flag) {
				int l = this.text.length();
				i = this.text.indexOf(32, i);
				if (i == -1) {
					i = l;
				} else {
					while (skipWs && i < l && this.text.charAt(i) == 32) {
						++i;
					}
				}
			} else {
				while (skipWs && i > 0 && this.text.charAt(i - 1) == 32) {
					--i;
				}
				while (i > 0 && this.text.charAt(i - 1) != 32) {
					--i;
				}
			}
		}
		return i;
	}

	public void moveCursorBy(int num) {
		this.setCursorPosition(this.selectionEnd + num);
	}

	public void setCursorPosition(int pos) {
		this.cursorPosition = pos;
		int i = this.text.length();
		this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, i);
		this.setSelectionPos(this.cursorPosition);
	}

	public void setCursorPositionZero() {
		this.setCursorPosition(0);
	}

	public void setCursorPositionEnd() {
		this.setCursorPosition(this.text.length());
	}

	@Override
	public boolean keyTyped(char typedChar, int keyCode) {
		if (!this.isFocused) {
			return false;
		} else if (GuiScreen.isKeyComboCtrlA(keyCode)) {
			this.setCursorPositionEnd();
			this.setSelectionPos(0);
			return true;
		} else if (GuiScreen.isKeyComboCtrlC(keyCode)) {
			GuiScreen.setClipboardString(this.getSelectedText());
			return true;
		} else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
			if (this.isEnabled) {
				this.writeText(GuiScreen.getClipboardString());
			}
			return true;
		} else if (GuiScreen.isKeyComboCtrlX(keyCode)) {
			GuiScreen.setClipboardString(this.getSelectedText());
			if (this.isEnabled) {
				this.writeText("");
			}
			return true;
		} else {
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
					if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && filter == null || filter.passesFilter(this, typedChar)) {
						if (this.isEnabled) {
							this.writeText(Character.toString(typedChar));
						}
						return true;
					} else {
						return false;
					}
			}
		}
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		if (this.isVisible()) {
			if (this.getEnableBackgroundDrawing()) {
				Gui.drawRect(guiLeft + pos.x - 1, guiTop + pos.y - 1, guiLeft + pos.x + pos.width + 1, guiTop + pos.y + pos.height + 1, -6250336);
				Gui.drawRect(guiLeft + pos.x, guiTop + pos.y, guiLeft + pos.x + pos.width, guiTop + pos.y + pos.height, -16777216);
			}
			int color = this.isEnabled ? this.enabledColor : this.disabledColor;
			int j = this.cursorPosition - this.lineScrollOffset;
			int k = this.selectionEnd - this.lineScrollOffset;
			String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
			boolean flag = j >= 0 && j <= s.length();
			boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
			int xPos = this.enableBackgroundDrawing ? pos.x + 4 : pos.x;
			int yPos = this.enableBackgroundDrawing ? pos.y + (pos.height - 8) / 2 : pos.y;
			xPos+=guiLeft;
			yPos+=guiTop;
			int j1 = xPos;
			if (k > s.length()) {
				k = s.length();
			}
			if (!s.isEmpty()) {
				String s1 = flag ? s.substring(0, j) : s;
				j1 = this.fontRendererInstance.drawStringWithShadow(s1, xPos, yPos, color);
			}
			boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
			int k1 = j1;
			if (!flag) {
				k1 = j > 0 ? xPos + pos.width : xPos;
			} else if (flag2) {
				k1 = j1 - 1;
				--j1;
			}
			if (!s.isEmpty() && flag && j < s.length()) {
				j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, yPos, color);
			}
			if (flag1) {
				if (flag2) {
					Gui.drawRect(k1, yPos - 1, k1 + 1, yPos + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
				} else {
					this.fontRendererInstance.drawStringWithShadow("_", k1,  yPos, color);
				}
			}
			if (k != j) {
				int l1 = xPos + this.fontRendererInstance.getStringWidth(s.substring(0, k));
				this.drawCursorVertical(k1, yPos - 1, l1 - 1, yPos + 1 + this.fontRendererInstance.FONT_HEIGHT);
			}
		}
	}
	
	private void drawCursorVertical(int startX, int startY, int endX, int endY) {
		if (startX < endX) {
			int i = startX;
			startX = endX;
			endX = i;
		}
		if (startY < endY) {
			int j = startY;
			startY = endY;
			endY = j;
		}
		if (endX > pos.x + pos.width) {
			endX = pos.x + pos.width;
		}
		if (startX > pos.x + pos.width) {
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

	public void setMaxStringLength(int length) {
		this.maxStringLength = length;
		if (this.text.length() > length) {
			this.text = this.text.substring(0, length);
		}
	}

	public int getMaxStringLength() {
		return this.maxStringLength;
	}

	public int getCursorPosition() {
		return this.cursorPosition;
	}

	public boolean getEnableBackgroundDrawing() {
		return this.enableBackgroundDrawing;
	}

	public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
		this.enableBackgroundDrawing = enableBackgroundDrawingIn;
	}

	public void setTextColor(int color) {
		this.enabledColor = color;
	}
	
	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		boolean flag = isMouseOver(mouseX, mouseY);
		if (this.canLoseFocus) {
			this.setFocused(flag);
		}
		if (this.isFocused && flag && mouseButton == 0) {
			int i = mouseX - pos.x;
			if (this.enableBackgroundDrawing) {
				i -= 4;
			}
			String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
			this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
		}
	}

	public void setDisabledTextColour(int color) {
		this.disabledColor = color;
	}

	@Override
	public void setFocused(boolean isFocusedIn) {
		if (isFocusedIn && !this.isFocused) {
			this.cursorCounter = 0;
		}
		this.isFocused = isFocusedIn;
	}

	@Override
	public boolean isFocused() {
		return this.isFocused;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	public int getSelectionEnd() {
		return this.selectionEnd;
	}

	public int getWidth() {
		return this.getEnableBackgroundDrawing() ? pos.width - 8 : pos.width;
	}

	public void setSelectionPos(int position) {
		int i = this.text.length();
		if (position > i) {
			position = i;
		}
		if (position < 0) {
			position = 0;
		}
		this.selectionEnd = position;
		if (this.fontRendererInstance != null) {
			if (this.lineScrollOffset > i) {
				this.lineScrollOffset = i;
			}
			int j = this.getWidth();
			String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
			int k = s.length() + this.lineScrollOffset;
			if (position == this.lineScrollOffset) {
				this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
			}
			if (position > k) {
				this.lineScrollOffset += position - k;
			} else if (position <= this.lineScrollOffset) {
				this.lineScrollOffset -= this.lineScrollOffset - position;
			}
			this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
		}
	}

	public void setCanLoseFocus(boolean canLoseFocusIn) {
		this.canLoseFocus = canLoseFocusIn;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}
	
	public String tootltip;
	
	public WidgetTextField setTootltip(String tootltip) {
		this.tootltip = tootltip;
		return this;
	}
	
	@Override
	public List getTooltip() {
		if(tootltip != null){
			return Collections.singletonList(Translator.translateToLocal(tootltip));
		}
		return super.getTooltip();
	}
}
