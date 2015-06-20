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
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
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

public class ItemFruit extends ItemPlant {
	
	public HashMap<Integer, HashMap<Integer, IIcon>> fruitIcons = new HashMap<Integer, HashMap<Integer, IIcon>>();

	public ItemFruit() {
		super("crop.fruit");
	}
	
	public void registerSeedIcon(IIconRegister IIconRegister)
	{
		for(CropDefinition definition : CropDefinition.values())
		{
			fruitIcons.put(definition.ordinal(), new HashMap<Integer, IIcon>());
		}
		for(CropDefinition definition : CropDefinition.values())
		{
			for(int i = 0;i< 4;i++)
			{
				for(int f = 0;f < definition.getFruits();f++)
				{
			fruitIcons.get(definition.ordinal()).put(i, IIconRegister.registerIcon("forestbotany:crops/fruits/" + definition.getFile().name().toLowerCase() + "/" + definition.name().toLowerCase() + "/" + f +"/" + "/fruit." + i));
			File file0 = new File(Loader.instance().getConfigDir().getPath() , "crops");
			File file = new File(file0 , "fruits");
        	File file1 = new File(file, definition.getFile().name().toLowerCase());
			File file2 = new File(file1, definition.name().toLowerCase());
			File file3 = new File(file2, Integer.toString(f));
			File file4 = new File(file3, "fruit." + i + ".png");
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Int", 1);
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

		String customTreeKey = "plants.crops."
				+ species.getUID().replace("fb.plant.crop.", "") + ".species";
		if (StatCollector.canTranslate(customTreeKey)) {
			return StatCollector.translateToLocal(customTreeKey) + " "
					+ StatCollector.translateToLocal(customTreeKey.replace(".species", ".fruit"));
		}
		return customTreeKey;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (IPlant plant : PlantManager.cropManager.getTemplates()) {
			for(int i = 0;i < 4;i++)
			{
			list.add(PlantManager.cropManager.getMemberStackFruit(plant, i));
			}
			}
	}
	
	public static ItemStack getFruit(NBTTagCompound nbtSeed, int stage)
	{
		NBTTagCompound nbt = (NBTTagCompound) nbtSeed.copy();
		ItemStack stack = new ItemStack(ItemRegistry.fruit.item());
		NBTTagCompound nbtFruit = new NBTTagCompound();
		nbtFruit.setInteger("Stage", stage);
		nbt.setTag("Fruit", nbtFruit);
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public IPlant getPlant(ItemStack itemStack) {
		return new Crop(itemStack.getTagCompound());
	}

	@Override
	public IIcon getPlantIcon(ItemStack stack, int renderpass) {
		int stage = stack.getTagCompound().getCompoundTag("Fruit").getInteger("Stage");
		return fruitIcons.get(getSpecies(stack).getDefinitionID()).get(stage);
	}

	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		registerSeedIcon(IIconRegister);
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
		return 16777215;
	}

	@Override
	public IPlantDifinition getDifinition(ItemStack stack) {
		return CropDefinition.values()[getSpecies(stack).getDefinitionID()];
	}
}
