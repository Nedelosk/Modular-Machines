package nedelosk.forestbotany.common.genetics.templates.crop;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import scala.util.parsing.json.JSONObject;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import cpw.mods.fml.common.Loader;

public class CropModWriter {

	public static String[] plant = new String[]
	{
		"Cotton",
        "Blackberry",
        "Blueberry",
        "Candleberry",
        "Raspberry",
        "Strawberry",
        "Cactusfruit",
        "Asparagus",
        "Barley",
        "Oats",	
        "Ryeseed",
        "Corn",
        "Bambooshoot",
        "Cantaloupe",
        "Cucumber",
        "Wintersquash",
        "Zucchini",
        "Beet",
        "Onion",
        "Parsnip",
        "Peanut",
        "Radish",
        "Rutabaga",
        "Sweetpotato",
        "Turnip",
        "Rhubarb",
        "Celery",
        "Garlic",
        "Ginger",
        "Spiceleaf",
        "Tea",
        "Coffee",
        "Mustard",
        "Broccoli",
        "Cauliflower",
        "Leek",
        "Lettuce",
        "Scallion",
        "Artichoke",
        "Brusselsprout",
        "Cabbage",
        "Spinach",
        "Whitemushroom",
        "Bean",
        "Soybean",
        "Bellpepper",
        "Chilipepper",
        "Eggplant",
        "Okra",
        "Peas",
        "Tomato",
        "Pineapple",
        "Grape",
        "Kiwi",
        "Cranberry",
        "Rice",
        "Seaweed",
	};
	
	public static String[] flower = new String[] {
	                   "Mystical_Red",
	                   "Mystical_Yellow",
	                   "Mystical_Blue",
	                   "Mystical_Orange",
	                   "Mystical_Purple",
	                   "Mystical_Green",
	                   "Mystical_Magenta",
	                   "Mystical_Pink",
	                   "Mystical_Lime",
	                   "Mystical_Cyan",
	                   "Mystical_LightBlue",
	                   "Mystical_Black",
	                   "Mystical_White",
	                   "Mystical_Gray",
	                   "Mystical_LightGray",
	                   "Mystical_Brown",
	};
	
	
	public static void writeHarvestcraft()
	{
		try{
			File file = new File(Loader.instance().getConfigDir(), "Pam's Harvestcraft Definition.cfg");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            file.getParentFile().mkdirs();
            file.createNewFile();
            
            for(String s : plant)
            {
            	buffer.write(s + "("+ '"' + s.toLowerCase() + '"' + ", true, 13, 35, 20, 70, 8, SeedFile.Vegetable, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void setAlleles() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void registerMutations() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void setSeed() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("},");
            	buffer.write(System.getProperty("line.separator"));
            }
        	buffer.write(System.getProperty("line.separator"));
        	buffer.write("####################################################");
            for(String s : plant)
            {
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("plants.crops." + s.toLowerCase() + ".species=" + s);
            	
            }
        	buffer.write(System.getProperty("line.separator"));
        	buffer.write(System.getProperty("line.separator"));
        	buffer.write("####################################################");
        	buffer.write(System.getProperty("line.separator"));
        	buffer.write(System.getProperty("line.separator"));
            for(String s : flower)
            {
            	buffer.write(s + "("+ '"' + s.toLowerCase() + '"' + ", true, 13, 35, 20, 70, 8, SeedFile.Mystical, new Color(0xF8E2A5), new Color(0xF8E2A5), new Color(0xF8E2A5)) {");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void setAlleles() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void registerMutations() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("@Override");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("protected void setSeed() {}");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("},");
            	buffer.write(System.getProperty("line.separator"));
            }
        	buffer.write(System.getProperty("line.separator"));
        	buffer.write("####################################################");
            for(String s : flower)
            {
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("plants.crops." + s.toLowerCase() + ".species=" + s);
            	
            }
            buffer.close();
            fos.close();
            
            
		}
		catch(Exception e)
		{
		}
		try{
			File file = new File(Loader.instance().getConfigDir(), "book_crop.xml");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            file.getParentFile().mkdirs();
            file.createNewFile();
            
        	buffer.write("<book>");
        	buffer.write(System.getProperty("line.separator"));
            for(CropDefinition defi : CropDefinition.values())
            {
            	buffer.write("<Specie>");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("	<Page>");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("		<Text>This are " + defi.name().toLowerCase() + ".</Text>");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("	</Page>");
            	buffer.write(System.getProperty("line.separator"));
            	buffer.write("</Specie>");
            	buffer.write(System.getProperty("line.separator"));
            }
        	buffer.write("</book>");
            buffer.close();
            fos.close();
            
            
		}
		catch(Exception e)
		{
		}
	}
	
}
