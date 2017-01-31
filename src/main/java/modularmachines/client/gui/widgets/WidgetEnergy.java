package modularmachines.client.gui.widgets;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import modularmachines.common.utils.Translator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class WidgetEnergy extends Widget {

	public static final NumberFormat FORMAT = NumberFormat.getIntegerInstance();
	protected IEnergyStorage storage;

	public WidgetEnergy(int posX, int posY, int width, int height, IEnergyStorage storage) {
		super(posX, posY, width, height);
		this.storage = storage;
	}

	@Override
	public List<String> getTooltip() {
		List<String> description = new ArrayList<>();
		if (source != null) {
			description.add(TextFormatting.WHITE + FORMAT.format(storage.getEnergyStored()) + " "
					+ Translator.translateToLocal("mm.energy.of"));
			description.add(TextFormatting.WHITE + FORMAT.format(storage.getMaxEnergyStored()) + " "
					+ TextFormatting.GRAY + Translator.translateToLocal("mm.energy.fe"));
		}
		return description;
	}

	protected int getEnergyStoredScaled(int energy) {
		int scale = pos.height;
		return (int) MathHelper.clamp(Math.round(scale * getEnergyStoredRatio(energy)), 0, scale);
	}

	private double getEnergyStoredRatio(int energy) {
		if (energy <= 0) {
			return 0;
		}
		return (double) energy / storage.getMaxEnergyStored();
	}
}
