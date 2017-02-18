package modularmachines.api.modules.model;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.ModuleData;
import net.minecraft.util.ResourceLocation;

public class ModelLocation {
	
	protected final List<ModelFormatting> formattings;
	protected final ModuleData data;
	protected String preFix;
	protected String folder;
	protected boolean status;
	protected ResourceLocation location;
	
	public ModelLocation(ModuleData data) {
		this.data = data;
		this.formattings = new ArrayList<>();
		this.location = null;
		this.preFix = "";
	}
	
	public ModelLocation addPreFix(String preFix){
		this.preFix = preFix;
		return this;
	}
	
	public ModelLocation addStatus(boolean status) {
		this.status = status;
		formattings.add(ModelFormatting.STATUS);
		return this;
	}
	
	public ModelLocation addFolder(String folder) {
		this.folder = folder;
		formattings.add(ModelFormatting.FOLDER);
		return this;
	}
	
	public ModelLocation addSize() {
		formattings.add(ModelFormatting.SIZE);
		return this;
	}
	
	public ResourceLocation toLocation(){
		String modID = data.getRegistryName().getResourceDomain();
		String preFix = this.preFix;
		if (preFix == null) {
			preFix = "";
		}
		if (formattings.contains(ModelFormatting.SIZE)) {
			if (!preFix.isEmpty()) {
				preFix += "_";
			}
			preFix += data.getSize().getName();
		}
		if (formattings.contains(ModelFormatting.STATUS)) {
			preFix += (!preFix.isEmpty() ? "_" : "") + (status ? "on" : "off");
		}
		if (preFix.isEmpty()) {
			preFix = "default";
		}
		return new ResourceLocation(modID, "module/" + folder + "/" + preFix);
	}

}
