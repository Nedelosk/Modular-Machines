package nedelosk.forestbotany.common.items;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.Loader;
import nedelosk.forestbotany.api.genetics.IPlantDifinition;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.allele.AlleleGender;
import nedelosk.forestbotany.common.genetics.templates.crop.Crop;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import nedelosk.forestbotany.common.genetics.templates.crop.CropGenome;
import nedelosk.forestbotany.common.genetics.templates.tree.Tree;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeGenome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSeed extends ItemPlant {
	
	public HashMap<Integer, HashMap<Integer, HashMap<Integer, IIcon>>> seedIcons = new HashMap<Integer, HashMap<Integer, HashMap<Integer, IIcon>>>();
	public HashMap<Integer, HashMap<Integer, IIcon>> cropIcons = new HashMap<Integer, HashMap<Integer , IIcon>>();

	public ItemSeed() {
		super("crop");
	}
	
	public void registerSeedIcon(IIconRegister IIconRegister)
	{
		for(SeedFile seed : SeedFile.values())
		{
		seedIcons.put(seed.ordinal(), new HashMap<Integer, HashMap<Integer, IIcon>>());
		}
		for(CropDefinition crop : CropDefinition.values())
		{
		cropIcons.put(crop.ordinal(), new HashMap<Integer, IIcon>());
		}
			for(SeedFile seed : SeedFile.values())
			{
				for(int i = 0;i < 3;i++)
				{
					if(seed.getRenderPasses().length > i)
					{
				for(int r = 0;r < seed.getRenderPasses()[i];r++)
				{
					if(seedIcons.get(seed.ordinal()).get(i) == null)
					{
						seedIcons.get(seed.ordinal()).put(i, new HashMap<Integer, IIcon>());
					}
					seedIcons.get(seed.ordinal()).get(i).put(r, IIconRegister.registerIcon("forestbotany:crops/seeds/" + seed.name().toLowerCase() + "/" + i + "/" + "seed." + r));
					
					File file0 = new File(Loader.instance().getConfigDir().getPath() , "crops");
					File file = new File(file0 , "seeds");
		        	File file1 = new File(file, seed.name().toLowerCase());
					File file3 = new File(file1, Integer.toString(i));
					File file4 = new File(file3, "seed." + r + ".png");
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Int", i);
					try {
			            file4.getParentFile().mkdirs();
			            if (!file4.exists())
			            {
			                if (!file4.createNewFile())
			                    return;
			            }
					FileOutputStream fileoutputstream = new FileOutputStream(file4);
					CompressedStreamTools.writeCompressed(tag, fileoutputstream);
					fileoutputstream.close();
					}
					catch (Exception e) {
					}
				}
				}
				
				}
				List<CropDefinition> seeds = new ArrayList<CropDefinition>();
				for(CropDefinition crop : CropDefinition.values())
				{
					if(crop.getFile() == seed)
					{
						seeds.add(crop);
					}
				}
				for(int i = 0;i < seed.getGrowthStages();i++)
				{
					for(CropDefinition crop : seeds)
					{
					cropIcons.get(crop.ordinal()).put(i, IIconRegister.registerIcon("forestbotany:crops/plants/" + seed.name().toLowerCase() + "/" + crop.name().toLowerCase() + "/plant." + i));
					
					File file0 = new File(Loader.instance().getConfigDir().getPath() , "crops");
					File file = new File(file0 , "plants");
		        	File file1 = new File(file, seed.name().toLowerCase());
					File file3 = new File(file1, crop.name().toLowerCase());
					File file4 = new File(file3, "plant." + i + ".png");
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Int", i);
					try {
			            file4.getParentFile().mkdirs();
			            if (!file4.exists())
			            {
			                if (!file4.createNewFile())
			                    return;
			            }
					FileOutputStream fileoutputstream = new FileOutputStream(file4);
					CompressedStreamTools.writeCompressed(tag, fileoutputstream);
					fileoutputstream.close();
					}
					catch (Exception e) {
					}
					}
				}
		}
	}

	@Override
	public IAllelePlantCrop getSpecies(ItemStack itemStack) {
		return CropGenome.getSpecies(itemStack);
	}

	@Override
	public IAlleleGender getGender(ItemStack itemStack) {
		return CropGenome.getGender(itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return "Unknown";
		}
		IAllelePlant species = getSpecies(itemstack);
		IAlleleGender gender = getGender(itemstack);

		String customTreeKey = "plants.crops."
				+ species.getUID().replace("fb.plant.crop.", "") + ".species";
		if (StatCollector.canTranslate(customTreeKey)) {
			return StatCollector.translateToLocal(customTreeKey) + " "
					+ StatCollector.translateToLocal("plants.crops.seed");
		}
		return customTreeKey;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (IPlant plant : PlantManager.cropManager.getTemplates()) {
			for(int i = 0;i < 3;i++)
			{
			list.add(PlantManager.cropManager.getMemberStack(plant, i));
			}
		}
	}

	@Override
	public IPlant getPlant(ItemStack itemStack) {
		return new Crop(itemStack.getTagCompound());
	}

	@Override
	public IIcon getPlantIcon(ItemStack stack, int renderpass) {
		if(!stack.getTagCompound().getCompoundTag("Crop").getBoolean("IsCrop"))
		{
		return seedIcons.get(getSpecies(stack).getSeedType().ordinal()).get(stack.getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage")).get(renderpass);
		}
		else
		{
			return cropIcons.get(getSpecies(stack).getDefinitionID()).get(stack.getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage"));
		}
	}

	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		//registerSeedIcons(IIconRegister);
		registerSeedIcon(IIconRegister);
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
		IAllelePlantCrop crop = getSpecies(itemStack);
		return crop.getColorFromRenderPass(renderPass);
	}

	@Override
	public IPlantDifinition getDifinition(ItemStack stack) {
		return CropDefinition.values()[getSpecies(stack).getDefinitionID()];
	}
}
