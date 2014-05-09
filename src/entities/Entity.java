package entities;

public abstract class Entity {
	
	protected String searchParameter;
	
	public String getSearchParameter(){
		return searchParameter;
	}
	
	public Entity() {
	}

	public abstract  String getSearchParameterValue();
}
