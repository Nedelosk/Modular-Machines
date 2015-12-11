package nedelosk.forestcore.api.modules.basic;

public interface IObjectManager<O> {

	void register(O object, Object... objects);
	
	O getObject();
	
}
