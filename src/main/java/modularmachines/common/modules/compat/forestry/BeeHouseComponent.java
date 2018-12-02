package modularmachines.common.modules.compat.forestry;

import javax.annotation.Nullable;
import java.util.Collections;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.mojang.authlib.GameProfile;

import modularmachines.common.modules.components.ModuleComponent;

import forestry.api.apiculture.DefaultBeeListener;
import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.climate.IClimateState;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ForestryAPI;
import forestry.api.core.IErrorLogic;
import forestry.core.errors.ErrorLogic;
import forestry.core.tiles.IClimatised;

public class BeeHouseComponent extends ModuleComponent implements IBeeHousing, IClimatised {
	protected IBeeModifier beeModifier;
	protected IBeeListener beeListener;
	//protected final IBeekeepingLogic beeLogic;
	private final ErrorLogic errorHandler = new ErrorLogic();
	protected int breedingProgressPercent;
	@Nullable
	protected World world;
	@Nullable
	protected Biome cachedBiome;
	
	public BeeHouseComponent() {
		this.beeModifier = new DefaultBeeModifier();
		this.beeListener = new DefaultBeeListener();
		//this.beeLogic = new ModuleBeekeepingLogic(this);
	}
	
	public void init(IBeeModifier beeModifier, IBeeListener beeListener) {
		this.beeModifier = beeModifier;
		this.beeListener = beeListener;
	}
	
	@Override
	public BlockPos getCoordinates() {
		return provider.getContainer().getLocatable().getCoordinates();
	}
	
	@Override
	public IErrorLogic getErrorLogic() {
		return errorHandler;
	}
	
	@Override
	public Biome getBiome() {
		if (cachedBiome == null) {
			cachedBiome = getWorldObj().getBiome(getCoordinates());
		}
		return cachedBiome;
	}
	
	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromBiome(getBiome(), getWorldObj(), getCoordinates());
	}
	
	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(getExactHumidity());
	}
	
	@Override
	public float getExactTemperature() {
		IClimateState climateState = ForestryAPI.climateManager.getState(getWorldObj(), getCoordinates());
		return climateState.getTemperature();
	}
	
	@Override
	public float getExactHumidity() {
		IClimateState climateState = ForestryAPI.climateManager.getState(getWorldObj(), getCoordinates());
		return climateState.getHumidity();
	}
	
	public int getHealthScaled(int i) {
		return breedingProgressPercent * i / 100;
	}
	
	public void setBreedingProgressPercent(int breedingProgressPercent) {
		this.breedingProgressPercent = breedingProgressPercent;
	}
	
	@Override
	public World getWorldObj() {
		if (world == null) {
			world = provider.getContainer().getLocatable().getWorldObj();
		}
		return world;
	}
	
	@Override
	public Iterable<IBeeModifier> getBeeModifiers() {
		return Collections.singleton(beeModifier);
	}
	
	@Override
	public Iterable<IBeeListener> getBeeListeners() {
		return Collections.singleton(beeListener);
	}
	
	@Override
	public IBeeHousingInventory getBeeInventory() {
		return null;
	}
	
	@Override
	public IBeekeepingLogic getBeekeepingLogic() {
		return null;
	}
	
	@Override
	public int getBlockLightValue() {
		return getWorldObj().getLightFromNeighbors(getCoordinates().up());
	}
	
	@Override
	public boolean canBlockSeeTheSky() {
		return getWorldObj().canBlockSeeSky(getCoordinates().up());
	}
	
	@Override
	public boolean isRaining() {
		return getWorldObj().isRainingAt(getCoordinates().up());
	}
	
	@Nullable
	@Override
	public GameProfile getOwner() {
		return null;
	}
	
	@Override
	public Vec3d getBeeFXCoordinates() {
		BlockPos pos = getCoordinates();
		return new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
}
