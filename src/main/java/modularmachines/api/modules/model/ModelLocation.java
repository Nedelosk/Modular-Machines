package modularmachines.api.modules.model;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.ModuleData;

@SideOnly(Side.CLIENT)
public class ModelLocation {
	
	protected final Set<ModelFormatting> formattings;
	protected final ModuleData data;
	protected String preFix;
	protected String folder;
	protected boolean status;
	protected ResourceLocation location;
	
	public ModelLocation(ModelLocation location) {
		this.data = location.data;
		this.formattings = location.formattings;
		this.preFix = location.preFix;
		this.folder = location.folder;
		this.status = location.status;
		this.location = null;
	}
	
	public ModelLocation(ModuleData data) {
		this.data = data;
		this.formattings = new HashSet<>();
		this.location = null;
		this.preFix = "";
	}
	
	public ModelLocation addPreFix(String preFix){
		this.preFix = preFix;
		return this;
	}
	
	public ModelLocation addToPreFix(String preFix){
		this.preFix += preFix;
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
	
	public ModelLocation addToFolder(String folder) {
		this.folder += folder;
		formattings.add(ModelFormatting.FOLDER);
		return this;
	}
	
	public ModelLocation addSize() {
		formattings.add(ModelFormatting.SIZE);
		return this;
	}
	
	public ModuleData getData() {
		return data;
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
