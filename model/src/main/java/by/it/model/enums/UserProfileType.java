package by.it.model.enums;

public enum UserProfileType {
	USER("USER"),
	DBA("DBA"),
	ADMIN("ADMIN");
	
	String type;
	
	private UserProfileType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}


	@Override
	public String toString(){
		return this.type;
	}

	public String getName(){
		return this.name();
	}
}
