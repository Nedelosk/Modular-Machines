package nedelosk.forestbotany.common.genetics.templates.crop;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cpw.mods.fml.common.Loader;
import nedelosk.forestbotany.api.genetics.IPlantDifinition;
import nedelosk.forestbotany.api.genetics.allele.AlleleManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.crop.ICropGenome;
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.AllelePlantCrop;
import nedelosk.forestbotany.common.genetics.allele.AlleleRegistry;
import nedelosk.forestbotany.common.items.ItemSeed;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.BiomeEvent.GetGrassColor;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum CropDefinition implements IPlantDifinition {
	
	Wheat("wheat", true, 5, 35, 20, 70, 8, SeedFile.Grain, new Color(0xE9D921), new Color(0xC6B70D), new Color(0xFDEE48), new Color(0x186E12), new Color(0x0B5606)) {

		@Override
		protected void setAlleles() {
			
		}

		@Override
		protected void registerMutations() {
			
		}

		@Override
		protected void setSeed() {
			setSeed(new ItemStack(Items.wheat_seeds));
		}

		@Override
		protected void setSpecials() {
			
		}
	},
	Barley("barley", true, 13, 35, 20, 70, 8, SeedFile.Grain, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0x186E12), new Color(0x0B5606)) {
		@Override
		protected void setAlleles() {
		}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Oats("oats", true, 13, 35, 20, 70, 8, SeedFile.Grain, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0x186E12), new Color(0x0B5606)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Rye("rye", true, 13, 35, 20, 70, 8, SeedFile.Grain, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0x186E12), new Color(0x0B5606)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Rice("rice", true, 13, 35, 20, 70, 8, SeedFile.Grain, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0x186E12), new Color(0x0B5606)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
				setFluid(new FluidStack(FluidRegistry.WATER, 8000));
			}

		},
	Carrot("carrot", true, 13, 35, 20, 70, 4, SeedFile.Vegetable, new Color(255, 111, 0), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {
			
		}

		@Override
		protected void registerMutations() {
			
		}

		@Override
		protected void setSeed() {
			setSeed(new ItemStack(Items.carrot));
		}

		@Override
		protected void setSpecials() {
			
		}
	},
	Potatoe("potatoe", true, 13, 35, 20, 70, 4, SeedFile.Vegetable, new Color(0xEBCA5A), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {	
		}

		@Override
		protected void registerMutations() {
		}

		@Override
		protected void setSeed() {
			setSeed(new ItemStack(Items.potato));
		}

		@Override
		protected void setSpecials() {
			
		}
	},
	Pumpkin("pumpkin", true, 13, 35, 20, 70, 5, SeedFile.Vegetable, new Color(0xEFCB68), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {
		}

		@Override
		protected void registerMutations() {
		}

		@Override
		protected void setSeed() {
			setSeed(new ItemStack(Items.pumpkin_seeds));
		}

		@Override
		protected void setSpecials() {
			
		}
	},
	Melon("melon", true, 13, 35, 20, 70, 5, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {	
		}

		@Override
		protected void registerMutations() {
		}

		@Override
		protected void setSeed() {
			setSeed(new ItemStack(Items.melon_seeds));
		}

		@Override
		protected void setSpecials() {
		}
	},
	Cotton("cotton", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Asparagus("asparagus", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Corn("corn", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Bambooshoot("bambooshoot", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Cantaloupe("cantaloupe", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Cucumber("cucumber", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Wintersquash("wintersquash", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Zucchini("zucchini", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Beet("beet", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Onion("onion", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Parsnip("parsnip", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Radish("radish", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Rutabaga("rutabaga", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Sweetpotato("sweetpotato", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Turnip("turnip", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Rhubarb("rhubarb", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Celery("celery", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Garlic("garlic", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Ginger("ginger", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Spiceleaf("spiceleaf", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Tea("tea", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Broccoli("broccoli", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Cauliflower("cauliflower", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Leek("leek", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Lettuce("lettuce", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Scallion("scallion", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Artichoke("artichoke", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Brusselsprout("brusselsprout", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Cabbage("cabbage", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Spinach("spinach", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Bean("bean", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Soybean("soybean", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Bellpepper("bellpepper", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Chilipepper("chilipepper", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Mustard("mustard", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xE9DB5C), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Eggplant("eggplant", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Okra("okra", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

	},
	Peas("peas", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Tomato("tomato", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Seaweed("seaweed", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0x2DC113)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Pineapple("pineapple", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Grape("grape", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Kiwi("kiwi", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Cranberry("cranberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
		@Override
		protected void setAlleles() {}

		@Override
		protected void registerMutations() {}

		@Override
		protected void setSeed() {}

		@Override
		protected void setSpecials() {
			setMod(Mods.Pams_Harvestcraft);
		}

		},
	Blackberry("blackberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Blueberry("blueberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Candleberry("candleberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Raspberry("raspberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Strawberry("strawberry", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Cactusfruit("cactusfruit", true, 13, 35, 20, 70, 8, SeedFile.Fruit, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

			},
	Coffee("coffee", true, 13, 35, 20, 70, 8, SeedFile.Stone_Fruit, new Color(0x7E580B)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	White_Mushroom("white_mushroom", true, 13, 35, 20, 70, 8, SeedFile.Mushroom, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Brown_Mushroom("brown_mushroom", true, 13, 35, 20, 70, 4, SeedFile.Mushroom, new Color(0xF8E2A5), new Color(0x7A5206)) {
			@Override
			protected void setAlleles() {
				
			}

			@Override
			protected void registerMutations() {
				
			}

			@Override
			protected void setSeed() {
				setSeed(new ItemStack(Items.carrot));
			}

			@Override
			protected void setSpecials() {
				
			}
		},
	Red_Mushroom("red_mushroom", true, 13, 35, 20, 70, 4, SeedFile.Mushroom, new Color(0xEBCA5A), new Color(0xB40F0F)) {
			@Override
			protected void setAlleles() {	
			}

			@Override
			protected void registerMutations() {
			}

			@Override
			protected void setSeed() {
				setSeed(new ItemStack(Items.potato));
			}

			@Override
			protected void setSpecials() {
				
			}
		},
	Peanut("peanut", true, 13, 35, 20, 70, 8, SeedFile.Nut, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {
			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Pams_Harvestcraft);
			}

		},
	Mystical_Red("mystical_red", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xE51134)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Yellow("mystical_yellow", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xE5DA11)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Blue("mystical_blue", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x1111E5)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Orange("mystical_orange", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xE59B11)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Purple("mystical_purple", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x780994)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Green("mystical_green", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x1E9409)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Magenta("mystical_magenta", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x940955)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Pink("mystical_pink", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xF263D5)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Lime("mystical_lime", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x34D557)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Cyan("mystical_cyan", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x17D5C9)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_LightBlue("mystical_lightblue", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x2DBAE4)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Black("mystical_black", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x454440)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_White("mystical_white", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xFFFFFF)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Gray("mystical_gray", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x7B7B7B)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_LightGray("mystical_lightgray", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xB0B0B0)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},
	Mystical_Brown("mystical_brown", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0x684A05)) {

			@Override
			protected void setAlleles() {}

			@Override
			protected void registerMutations() {}

			@Override
			protected void setSeed() {}

			@Override
			protected void setSpecials() {
				setMod(Mods.Botania);
			}

		},;
	
	private AllelePlantCrop crop;
	private IAllele[] template;
	private ICropGenome genome;
	private int growthStages;
	private int fruits; 
	private SeedFile file;
	private SoilType soil;
	private Mods mod;
	private FluidStack fluid;
	private ArrayList<ItemStack> products;
	
	private CropDefinition(String uid, boolean isDominant, int minClimate, int maxClimate, int minHumidity, int maxHumidity, int growthStages, SeedFile seedType, int fruit, Color... color) {
		crop = new AllelePlantCrop(uid, isDominant, minClimate, maxClimate, minHumidity, maxHumidity, growthStages, seedType, this, ordinal(), color);
		this.growthStages = growthStages;
		this.file = seedType;
		this.fruits = fruit;
	}
	
	private CropDefinition(String uid, boolean isDominant, int minClimate, int maxClimate, int minHumidity, int maxHumidity, int growthStages, SeedFile seedType, Color... color) {
		crop = new AllelePlantCrop(uid, isDominant, minClimate, maxClimate, minHumidity, maxHumidity, growthStages, seedType, this, ordinal(), color);
		this.growthStages = growthStages;
		this.file = seedType;
		this.fruits = 1;
	}
	
	public static void initCrops(Configuration config) {
		for (CropDefinition crop : values()) {
			if(config.get("Crops", "Crop " + crop.getPlant().getUID().replace("fb.plant.crop.", ""), true, "").getBoolean(true))
			{
			crop.init();
			}
		}
		for (CropDefinition crop : values()) {
			if(config.get("Crops", "Crop " + crop.getPlant().getUID().replace("fb.plant.crop.", ""), true, "").getBoolean(true))
			{
			crop.registerMutations();
			}
		}
	}

	private void init() {

		template = PlantManager.cropManager.getDefaultPlant();
		setAllele(CropChromosome.PLANT, crop);
		setAlleles();
		setSeed();
		setSpecials();

		genome = PlantManager.cropManager.templateAsGenome(template);

		if(mod == null || Loader.isModLoaded(mod.modID))
		{
		AlleleManager.alleleRegistry.registerAllele(crop);
		PlantManager.cropManager.registerTemplate(template);
		}
	}
	
	@Override
	public void addProduct(ItemStack product) {
		this.products.add(product);
	}
	
	@Override
	public ArrayList<ItemStack> getProducts() {
		return products;
	}
	
	@Override
	public int getFruits() {
		return fruits;
	}
	
	@Override
	public SeedFile getFile() {
		return file;
	}
	
	protected final void setAllele(CropChromosome chromosome, IAllele allele) {
			template[chromosome.ordinal()] = allele;
	}

	public final IAllele[] getTemplate() {
		return Arrays.copyOf(template, template.length);
	}
	
	public AllelePlantCrop getPlant()
	{
		return crop;
	}
	
	public void setMod(Mods mod) {
		this.mod = mod;
	}
	
	protected abstract void setAlleles();

	protected abstract void registerMutations();
	
	protected abstract void setSeed();
	
	protected abstract void setSpecials();
	
	public void setSoil(SoilType soil)
	{
		this.soil = soil;
	}
	
	public void setFluid(FluidStack fluid) {
		this.fluid = fluid;
	}
	
	@Override
	public FluidStack getFluid() {
		return fluid;
	}
	
	@Override
	public SoilType getSoil() {
		return soil;
	}
	
	public void setSeed(ItemStack stack)
	{
		crop.setSeed(stack);
	}
	
	public ICropGenome getGenome() {
		return genome;
	}
	
	public enum SeedFile
	{
		Fruit(4, 3, 4, 6), Nut(4, 1, 2, 4), Vegetable(6, 1, 2, 2), Grain(8, 3, 4, 5), Mushroom(5, 2, 3, 4), Stone_Fruit(4, 1, 2, 4), Mystical(6, 1, 2, 4);
		
		private int[] renderPasses;
		private int growthStages;
		
		private SeedFile(int growthStages, int... renderPasses) {
			this.renderPasses = renderPasses;
			this.growthStages = growthStages;
		}
		
		public int[] getRenderPasses() {
			return renderPasses;
		}
		
		public int getGrowthStages() {
			return growthStages;
		}
	}
	
	public enum Mods
	{
		Pams_Harvestcraft("harvestcraft"), Natura(""), Forestry(""), IC2(""), Gregtech(""), Botania("Botania");
		
		public String modID;
		
		private Mods(String modID) {
			this.modID = modID;
		}
	}
	
	public int getGrowthStages() {
		return growthStages;
	}
	
}
