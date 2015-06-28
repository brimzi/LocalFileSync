package brimzi.app.filetransferpc.data;

public class UserProfile {
	private String username, pwd, FriendlyName;
	
	public UserProfile(String username,String pwd,String FriendlyName){
		this.username=username;
		this.pwd=pwd;
		this.FriendlyName=FriendlyName;
	}
	public char[] getPwd() {
		
		return pwd.toCharArray();
	}
	
	public String getName() {
		
		return username;
	}

	public String getFriendlyName() {
		
		return FriendlyName;
	}
}
