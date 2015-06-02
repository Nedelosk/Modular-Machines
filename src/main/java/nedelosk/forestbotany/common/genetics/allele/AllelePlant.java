package nedelosk.forestbotany.common.genetics.allele;

import java.util.Collection;
import java.util.Map;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AllelePlant extends Allele implements IAllelePlant {

	private int minClimate;
	private int maxClimate;
	private int minHumidity;
	private int maxHumidity;
	private boolean hasEffect = false;
	public int definitionID;

	public AllelePlant(String uid, boolean isDominant, int minClimate, int maxClimate, int minHumidity, int maxHumidity) {
		super("plant." + uid, isDominant, false);

		this.minClimate = minClimate;
		this.maxClimate = maxClimate;
		this.minHumidity = minHumidity;
		this.maxHumidity = maxHumidity;
	}

	@Override
	public int getTemperatureMax() {
		return maxClimate;
	}

	@Override
	public int getTemperatureMin() {
		return minClimate;
	}

	@Override
	public boolean hasEffect() {
		return hasEffect;
	}
	
	@Override
	public int getMinHumidity() {
		return minHumidity;
	}
	
	@Override
	public int getMaxHumidity() {
		return maxHumidity;
	}
	
	public AllelePlant setEffect()
	{
		this.hasEffect = true;
		return this;
	}
	
	@Override
	public int getDefinitionID() {
		return definitionID;
	}
}
