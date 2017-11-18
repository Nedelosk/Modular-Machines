package modularmachines.api.modules.model;

import javax.annotation.Nullable;
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
	@Nullable
	protected String preFix;
	@Nullable
	protected String folder;
	protected boolean status;
	
	public ModelLocationBuilder(ModelLocationBuilder location) {
		this(location.formattings, location.data, location.preFix, location.folder, location.status);
	}
	
	private ModelLocationBuilder(Set<ModelFormatting> formattings, ModuleData data, @Nullable String preFix, @Nullable String folder, boolean status) {
		this.formattings = formattings;
		this.data = data;
		this.preFix = preFix;
		this.folder = folder;
		this.status = status;
	}
	
	public ModelLocationBuilder(ModuleData data) {
		this.data = data;
		this.formattings = new HashSet<>();
		this.preFix = "";
	}
	
	public ModelLocationBuilder addPreFix(String preFix) {
		this.preFix = preFix;
		return this;
	}
	
	public ModelLocationBuilder addToPreFix(String preFix) {
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
	
	public ModuleData data() {
		return data;
	}
	
	public ModelLocationBuilder copy() {
		return new ModelLocationBuilder(this);
	}
	
	public ResourceLocation build() {
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
