package nedelosk.forestbotany.common.botany;


import java.util.ArrayList;
import java.util.List;

import nedelosk.forestbotany.api.botany.IChamberLogic;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.botany.IInfuserChamber.PlantStatus;
import nedelosk.forestbotany.api.genetics.IPlantDifinition;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
import nedelosk.forestbotany.common.genetics.Genome;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.allele.AlleleInteger;
import nedelosk.forestbotany.common.genetics.allele.AllelePlant;
import nedelosk.forestbotany.common.genetics.allele.AllelePlantCrop;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import nedelosk.forestbotany.common.genetics.templates.crop.CropGenome;
import nedelosk.forestbotany.common.genetics.templates.plant.Plant;
import nedelosk.forestbotany.common.items.ItemFruit;
import nedelosk.forestbotany.common.items.ItemPlant;
import nedelosk.forestbotany.common.items.ItemSeed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ChamberLogic implements IChamberLogic {

	private IInfuserChamber chamber;
	private int updateTimerFluid;
	private int updateTimerFluidTotal;
	public PlantStatus plantStatus;
	
	public ChamberLogic(IInfuserChamber chamber) {
		this.chamber = chamber;
	}
	
	@Override
	public void update()
	{
		if(chamber.getPlant() != null && chamber.getPlant().getItem() instanceof ItemPlant)
		{
			if(updateFluid())
			{
				if(updateSoil())
				{
					updatePlant();
					updateFruit();
				}
			}
		}
		else
		{
			killFruit();
			plantStatus = PlantStatus.None;
		}
	}
	
	public void updatePlant()
	{
		ItemStack plantStack = chamber.getPlant();
		ItemPlant plant = (ItemPlant) plantStack.getItem();
		IPlantDifinition plantDifinition = plant.getDifinition(plantStack);
		if(!chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop") && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") > 0)
		{
			plantStatus = PlantStatus.Sapling;
		}
		if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop"))
		{
			plantStatus = PlantStatus.Plant;
		}
		if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop") && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") == plantDifinition.getFile().getGrowthStages())
			plantStatus = PlantStatus.Mature;
		if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop") && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") == plantDifinition.getFile().getGrowthStages() && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthTime") >= ((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.GROWSPEED, PlantManager.getPlantManager(chamber.getPlant()))).getValue())
		{
			if(plantDifinition.getFile() == SeedFile.Fruit || plantDifinition.getFile() == SeedFile.Stone_Fruit || plantDifinition.getFile() == SeedFile.Mystical)
			{
				for(ItemStack stack : plantDifinition.getProducts())
				{
				addProduct(stack);
				}
				chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthStage", 0);
			}
			else
			{
				killPlant();
			}
		}
		else if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthTime") >= ((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.GROWSPEED, PlantManager.getPlantManager(chamber.getPlant()))).getValue())
		{
			if((chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") == 2 || chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") == 3) && !chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop"))
			{
				chamber.getPlant().getTagCompound().getCompoundTag("Crop").setBoolean("IsCrop", true);
				chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthStage", 0);
			}
			chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthStage", chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") + 1);
			chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthTime", 0);
			if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop") && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") == plantDifinition.getFile().getGrowthStages())
			{
				if(plantDifinition.getFile() == SeedFile.Fruit || plantDifinition.getFile() == SeedFile.Stone_Fruit || plantDifinition.getFile() == SeedFile.Mystical)
				{
					for(ItemStack stack : plantDifinition.getProducts())
					{
					addProduct(stack);
					}
					chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthStage", 0);
				}
				else
				{
					killPlant();
				}
			}
		}
		else
		{
			chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("GrowthTime", chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthTime") + 1);
		}
	}
	
	public boolean updateSoil()
	{
		ItemStack plantStack = chamber.getPlant();
		ItemPlant plant = (ItemPlant) plantStack.getItem();
		IPlantDifinition plantDifinition = plant.getDifinition(plantStack);
		if(chamber.getSoil() == null)
		{
			killPlantWithTimer();
			return false;
		}
		if(plantDifinition.getSoil() == null)
			return true;
		if(chamber.getSoil() == plantDifinition.getSoil())
			return true;
		return false;
	}
	
	public boolean updateFluid()
	{
		updateTimerFluidTotal = ((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.FLUIDCONSUMPTIONSPEED, PlantManager.getPlantManager(chamber.getPlant()))).getValue();
		
		if(updateTimerFluid < updateTimerFluidTotal)
		{
			updateTimerFluid++;
			updateTimerFluid++;
		}
		if(updateTimerFluid < updateTimerFluidTotal)
			return false;
		updateTimerFluid = 0;
		ItemStack plantStack = chamber.getPlant();
		if(!(plantStack.getItem() instanceof ItemPlant))
			return false;
		ItemPlant plant = (ItemPlant) plantStack.getItem();
		IPlantDifinition plantDifinition = plant.getDifinition(plantStack);
		if(chamber.getTankWater().getFluid() != null && chamber.getTankWater().getFluid().amount > ((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.WATERCONSUMPTION, PlantManager.getPlantManager(chamber.getPlant()))).getValue())
		{
			chamber.getTankWater().drain(((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.WATERCONSUMPTION, PlantManager.getPlantManager(chamber.getPlant()))).getValue(), true);
		}
		else
		{
			killPlantWithTimer();
			return false;
		}
		if(plantDifinition.getFluid() == null)
			return true;
		if(chamber.getTank().getFluid() == null)
			return false;
		if(plantDifinition.getFluid() != null && plantDifinition.getFluid().getFluid() == chamber.getTank().getFluid().getFluid())
		{
			if(plantDifinition.getFluid().amount > chamber.getTank().getFluid().amount)
			{
				killPlantWithTimer();
				return false;
			}
			chamber.getTank().drain(((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.FLUIDCONSUMPTION, PlantManager.getPlantManager(chamber.getPlant()))).getValue(), true);
			return true;
		}
		else
			return false;
	}
	
	public void killPlantWithTimer()
	{
		if(((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.DEATHTIME, PlantManager.getPlantManager(chamber.getPlant()))) == Allele.deathTimeNoneDeath)
			return;
		if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("DeathTime") >= ((AlleleInteger)Genome.getActiveAllele(chamber.getPlant(), CropChromosome.DEATHTIME, PlantManager.getPlantManager(chamber.getPlant()))).getValue())
		{
			chamber.setPlant(null);
		}
		else
		{
			int deathTime = chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("DeathTime");
			chamber.getPlant().getTagCompound().getCompoundTag("Crop").setInteger("DeathTime", deathTime + 50);
		}
	}
	
	public void killPlant()
	{
		ItemStack plantStack = chamber.getPlant();
		ItemPlant plant = (ItemPlant) plantStack.getItem();
		//IPlantDifinition plantDifinition = plant.getDifinition(plantStack);
		//if(plantDifinition.getProducts() != null)
		//{
		//for(ItemStack stack : plantDifinition.getProducts())
		//{
		//addProduct(stack);
		//}
		//}
		if(addProduct(PlantManager.getPlantManager(plantStack).getMemberStack(plant.getPlant(plantStack), 0)))
		{
			chamber.setPlant(null);
		}
	}
	
	public boolean addProduct(ItemStack stack)
	{
		return chamber.addProduct(stack);
	}

	@Override
	public PlantStatus getPlantStatus() {
		return plantStatus;
	}
	
	public void setFruit()
	{
		
	}
	
	public void killFruit()
	{
		for(int i = 0;i < 5;i++)
		{
		if(chamber.getFruit(i) != null)
		{
			if(chamber.getFruit(i).getTagCompound().getCompoundTag("Fruit").getInteger("Stage") != 3)
			{
			chamber.setFruit(i, null);
			}
		}
		}
	}
	
	public void updateFruit()
	{
		if(chamber.getPlant() != null && chamber.getPlant().getItem() instanceof ItemSeed)
		{
		List<Integer> noneFruits = new ArrayList<Integer>();
		ItemStack plantStack = chamber.getPlant();
		ItemPlant plantItem = (ItemPlant) plantStack.getItem();
		IPlant plant = ((ItemPlant) plantStack.getItem()).getPlant(plantStack);
		IPlantDifinition plantDifinition = plantItem.getDifinition(plantStack);
		if(chamber.getPlant().getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop") && chamber.getPlant().getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage") >= plantDifinition.getFile().getGrowthStages() / 2)
		{
		for(int i = 0;i < ((AlleleInteger)plant.getGenome().getActiveAllele(CropChromosome.FRUITNESS)).getValue();i++)
		{
		if(chamber.getFruit(i) != null)
		{
			ItemStack stack = chamber.getFruit(i);
			if(stack.getTagCompound().getCompoundTag("Fruit").getInteger("Time") >= ((AlleleInteger)plant.getGenome().getActiveAllele(CropChromosome.FRUITGROWSPEED)).getValue())
			{
				if(stack.getTagCompound().getCompoundTag("Fruit").getInteger("Stage") == 3)
				{
					int stage = (((AllelePlantCrop)plant.getGenome().getActiveAllele(CropChromosome.PLANT)).getSeedFile() != SeedFile.Fruit) ? ((AlleleInteger)plant.getGenome().getActiveAllele(CropChromosome.FRUITGROWSPEED)).getValue() : ((AlleleInteger)plant.getGenome().getActiveAllele(CropChromosome.FRUITGROWSPEED)).getValue() * 2;
					if(stack.getTagCompound().getCompoundTag("Fruit").getInteger("Time") >= stage)
					{
						chamber.setFruit(i, null);
					}
					else
					{
						stack.getTagCompound().getCompoundTag("Fruit").setInteger("Time", stack.getTagCompound().getCompoundTag("Fruit").getInteger("Time") + 1);
					}
				}
				else
				{
				stack.getTagCompound().getCompoundTag("Fruit").setInteger("Stage", stack.getTagCompound().getCompoundTag("Fruit").getInteger("Stage") + 1);
				stack.getTagCompound().getCompoundTag("Fruit").setInteger("Time", 0);
				}
			}
			else
			{
				stack.getTagCompound().getCompoundTag("Fruit").setInteger("Time", stack.getTagCompound().getCompoundTag("Fruit").getInteger("Time") + 1);
			}
		}
		else
		{
			noneFruits.add(i);
		}
		}
		if(noneFruits.size() != 0)
		{
			chamber.setFruit(noneFruits.get(0), ItemFruit.getFruit(plantStack.getTagCompound(), 0));
		}
		}
		}
	}
	
}
