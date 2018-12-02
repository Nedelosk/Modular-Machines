package modularmachines.common.modules.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

import net.minecraftforge.fml.common.FMLLog;

import modularmachines.api.modules.IModuleDataBuilder;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IComponentFactory;
import modularmachines.common.utils.Log;

public class ModuleParser {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(ModuleBlock.class, new ModuleBlock.Deserializer()).create();
	
	private final File moduleFolder;
	
	public ModuleParser(File moduleFolder) {
		this.moduleFolder = moduleFolder;
	}
	
	public void load() {
		if (!moduleFolder.exists()) {
			moduleFolder.mkdirs();
		}
		Path root = moduleFolder.toPath();
		
		if (!Files.exists(root)) {
			return;
		}
		
		Iterator<Path> itr = null;
		try {
			itr = Files.walk(root).iterator();
		} catch (IOException e) {
			FMLLog.log.error("Error iterating filesystem.", e);
			return;
		}
		
		while (itr.hasNext()) {
			parseModule(itr.next());
		}
	}
	
	private void parseModule(Path path) {
		if (!"json".equals(FilenameUtils.getExtension(path.toString()))) {
			return;
		}
		
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path);
			ModuleBlock moduleBlock = JsonUtils.fromJson(GSON, reader, ModuleBlock.class);
			if (moduleBlock != null) {
				String registryName = moduleBlock.data.registryName;
				String name = moduleBlock.data.name;
				String model = moduleBlock.data.model;
				int complexity = moduleBlock.data.complexity;
				IComponentFactory[] components = moduleBlock.components;
				ItemStack[] type = moduleBlock.types;
				IModuleDataBuilder builder = ModuleManager.factory.createData();
				builder.setRegistryName(registryName);
				builder.setTranslationKey(name);
				builder.setComplexity(complexity);
				//builder.setDefinition();
			}
		} catch (JsonParseException e) {
			Log.err("Parsing error loading module.", e);
		} catch (IOException e) {
			Log.err("Couldn't read module from {}", path, e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}
	
}
