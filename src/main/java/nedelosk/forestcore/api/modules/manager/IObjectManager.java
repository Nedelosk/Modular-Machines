package nedelosk.forestcore.api.modules.manager;

public interface IObjectManager<O> {

	void register(O object, Object... objects);
	
	O getObject();
	
}
