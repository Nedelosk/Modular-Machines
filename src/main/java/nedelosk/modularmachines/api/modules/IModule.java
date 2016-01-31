package nedelosk.modularmachines.api.modules;

import java.util.List;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.special.IModuleController;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;

public interface IModule<S extends IModuleSaver> {

	void updateServer(IModular modular, ModuleStack stack);

	void updateClient(IModular modular, ModuleStack stack);

	List<String> getRequiredModules();

	boolean canAssembleModular(IModular modular, ModuleStack stack, ModuleStack<IModuleController<IModuleSaver>, IModuleSaver> controller,
			List<ModuleStack> modules) throws ModularException;

	String getName(ModuleStack stack);

	String getUnlocalizedName(ModuleStack stack);

	void setRegistry(ResourceLocation registry);

	ResourceLocation getRegistry();

	String getCategoryUID();

	String getModuleUID();

	S createSaver(ModuleStack stack);
}
