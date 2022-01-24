package pagueOaluguel;




public final class PagueOAluguel {

	
	

private Util util;	


public static final int SUCCESS = 0;
public static final int ERROR_NOT_PREFERENCES = 1;
public static final int ERROR_ID_NOT_DEFINED = 3;
public static final int ERROR_KEY_INVALID = 5;
public static final int UNCHECKED = 6;
public static final int LICENSE_TERMINATION = 7;


public static  String SYSTEM_NAME = "Indefinido";
public static  String SYSTEM_VERSION = "Indefinido";

public static String serverURL = "";
public static String accessKey2Server = "";
public static int tipo_de_licenca = 0;


public static boolean onlineRegisterEnabled = true;
public static boolean insertRegisterKeyEnabled = false;
	


	public PagueOAluguel(){
		
	this.util = new Util();	
	}


	
	
	public void showRegisterForm(CurrentRecord currentRecord){
			
	RegisterForm registerForm  = new RegisterForm(currentRecord, this.util);	
	registerForm.showForm();		
	}
	
	
	

	
	public CurrentRecord getCurrentRecord(){
		
	return this.util.getCurrentRecord();
	}
	

	
	
	
	public boolean licenseIsValid(CurrentRecord currentRecord){
		
	return this.util.licenseIsValid(currentRecord);	
	}
	
	
	
	
	
}
