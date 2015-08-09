package nedelosk.nedeloskcore.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.common.core.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IOUtils {

    public static String[] getLinesArrayFromData(String input) {
        int count = 0;
        String unprocessed = input;
        for (int i=0;i<unprocessed.length();i++) {
            if (unprocessed.charAt(i) == '\n') {
                count++;
            }
        }
        ArrayList<String> data = new ArrayList<String>();
        if (unprocessed.length()>0) {
            for (int i=0;i<count;i++) {
                String line = (unprocessed.substring(0,unprocessed.indexOf('\n'))).trim();
                if ((line.trim()).length() > 0 && line.charAt(0) != '#') {
                    data.add(line.trim());
                }
                unprocessed = unprocessed.substring(unprocessed.indexOf('\n')+1);
            }
        }
        if ((unprocessed.trim()).length()>0 && unprocessed.charAt(0)!='#') {
            data.add(unprocessed.trim());
        }
        return data.toArray(new String[data.size()]);
    }

    //splits a comma seperated string into an array
    public static String[] getData(String input) {
        ArrayList<String> output = new ArrayList<String>();
        int start = 0;
        for(int i=0;i<input.length();i++) {
            if(input.charAt(i)==',') {
                String element = (input.substring(start, i)).trim();
                if(element.length()>0) {
                    output.add(element);
                }
                start = i+1;
            }
        }
        String element = (input.substring(start)).trim();
        if(element.length()>0) {
            output.add(element);
        }
        return output.toArray(new String[output.size()]);
    }
    
    public static String readOrWrite(String directory, String fileName, String defaultData) {
        return readOrWrite(directory, fileName, defaultData, false);
    }

    public static String readOrWrite(String directory, String fileName, String defaultData, boolean reset) {
        File file = new File(directory, fileName+".txt");
        if(file.exists() && !file.isDirectory() && !reset) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] input = new byte[(int) file.length()];
                try {
                    inputStream.read(input);
                    inputStream.close();
                    return new String(input, "UTF-8");
                } catch (IOException e) {
                    Log.info("Caught IOException when reading "+fileName+".txt");
                }
            } catch(FileNotFoundException e) {
                Log.info("Caught IOException when reading "+fileName+".txt");
            }
        }
        else {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                try {
                    writer.write(defaultData);
                    writer.close();
                    return defaultData;
                }
                catch(IOException e) {
                    Log.info("Caught IOException when writing "+fileName+".txt");
                }
            }
            catch(IOException e) {
                Log.info("Caught IOException when writing "+fileName+".txt");
            }
        }
        return null;
    }
    
    public static ItemStack getStack(String input)
    {
    	String[] data = input.split(":");
    	int meta = 0;
    	
    	if(data.length <= 1)
    		return null;
    	if(data.length == 3)
    		meta = Integer.parseInt(data[2]);
    	
    	Item item = GameRegistry.findItem(data[0], data[1]);
    	if(item != null)
    		return new ItemStack(item, 1, meta);
    	return null;
    	
    }
	
}
