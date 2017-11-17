package modularmachines.api.modules.model;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.ModuleData;

@SideOnly(Side.CLIENT)
public class ModelLocationBuilder {
	
	protected final Set<ModelFormatting> formattings;
	protected final ModuleData data;
	protected String preFix;
	protected String folder;
	protected boolean status;
	protected ResourceLocation location;
	
	public ModelLocationBuilder(ModelLocationBuilder location) {
		this.data = location.data;
		this.formattings = location.formattings;
		this.preFix = location.preFix;
		this.folder = location.folder;
		this.status = location.status;
		this.location = null;
	}
	
	public ModelLocationBuilder(ModuleData data) {
		this.data = data;
		this.formattings = new HashSet<>();
		this.location = null;
		this.preFix = "";
	}
	
	public ModelLocationBuilder addPreFix(String preFix){
		this.preFix = preFix;
		return this;
	}
	
	public ModelLocationBuilder addToPreFix(String preFix){
		this.preFix += preFix;
		return this;
	}
	
	public ModelLocationBuilder addStatus(boolean status) {
		this.status = status;
		formattings.add(ModelFormatting.STATUS);
		return this;
	}
	
	public ModelLocationBuilder addFolder(String folder) {
		this.folder = folder;
		formattings.add(ModelFormatting.FOLDER);
		return this;
	}
	
	public ModelLocationBuilder addToFolder(String folder) {
		this.folder += folder;
		formattings.add(ModelFormatting.FOLDER);
		return this;
	}
	
	public ModelLocationBuilder addSize() {
		formattings.add(ModelFormatting.SIZE);
		return this;
	}
	
	public ModuleData getData() {
		return data;
	}
	
	public ResourceLocation build(){
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
