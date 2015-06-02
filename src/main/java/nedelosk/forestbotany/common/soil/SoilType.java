package nedelosk.forestbotany.common.soil;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.Mods;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum SoilType {

	Dirt(Blocks.dirt, 0, 300, 8000) {
		@Override
		public Mods getMod() {
			return null;
		}
	},
	Farmland(Blocks.farmland, 0, 450, 10000)
	{
		@Override
		public int getIconSide() {
			return 1;
		}

		@Override
		public Mods getMod() {
			return null;
		}
	},
	Enchanted(Blocks.dirt, 0, 1000, 35000)
	{
		@Override
		public Block getSoil() {
			return GameRegistry.findBlock("Botania", "enchantedSoil");
		}

		@Override
		public Mods getMod() {
			return Mods.Botania;
		}
		
		@Override
		public int getIconSide() {
			return 1;
		}
	};

	private SoilType(Block soil, int meta, int maxNutrientsStorage, int maxWaterStorage) {
		this.soil = soil;
		this.soilMeta = meta;
		this.maxNutrientsStorage = maxNutrientsStorage;
		this.maxWaterStorage = maxWaterStorage;
	}
	
	private Block soil;
	private int soilMeta;
	private int maxNutrientsStorage;
	private int maxWaterStorage;
	private int side = 0;

	public Block getSoil() {
		return soil;
	}
	
	public abstract Mods getMod();

	public int getSoilMeta() {
		return soilMeta;
	}
	
	public int getMaxWaterStorage() {
		return maxWaterStorage;
	}
	
	public int getMaxNutrientsStorage() {
		return maxNutrientsStorage;
	}
	
	public void setIconSide(int side)
	{
		this.side = side;
	}
	
	public int getIconSide() {
		return side;
	}

}
