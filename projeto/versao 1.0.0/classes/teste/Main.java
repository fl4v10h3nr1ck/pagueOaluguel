package teste;

import pagueOaluguel.CurrentRecord;
import pagueOaluguel.PagueOAluguel;





public class Main {

	
	
	
	public static void main(String args[]){
		
		
		
	PagueOAluguel registro = new PagueOAluguel();
		
	PagueOAluguel.insertRegisterKeyEnabled = true;
		
	PagueOAluguel.SYSTEM_NAME = "sisERPDepilSoft";
	PagueOAluguel.SYSTEM_VERSION = "1.0.0";
		
	PagueOAluguel.serverURL = "http://www.mscsolucoes.com.br/util/licenseServer/";
	PagueOAluguel.accessKey2Server = "61fFndxo4s1Z0x00ad2c7gC9sAw5rmNH";

	CurrentRecord atual= registro.getCurrentRecord();

		if(registro.licenseIsValid(atual)){	
		
		registro.showRegisterForm(atual);
		}
		
	}
	
	
	
}
