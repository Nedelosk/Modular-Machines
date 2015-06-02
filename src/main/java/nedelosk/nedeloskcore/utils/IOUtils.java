package nedelosk.nedeloskcore.utils;

import java.util.ArrayList;

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
	
}
