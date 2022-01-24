package pagueOaluguel;


public class CurrentRecord {

	
private String key;
private String id;
private String softCod;
private String clientCod;


private int initialDay;
private int initialMonth;
private int initialYear;
private int contDays;


private int error;


private String data_expiracao = "";




	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getInitialDay() {
		return initialDay;
	}
	public void setInitialDay(int initialDay) {
		this.initialDay = initialDay;
	}
	public int getInitialMonth() {
		return initialMonth;
	}
	public void setInitialMonth(int initialMonth) {
		this.initialMonth = initialMonth;
	}
	public int getInitialYear() {
		return initialYear;
	}
	public void setInitialYear(int initialYear) {
		this.initialYear = initialYear;
	}
	

	public int getError() {return this.error;	}
	public void setError(int error) {this.error = error;	}
	public String getSoftCod() {
		return softCod;
	}
	public void setSoftCod(String softCod) {
		this.softCod = softCod;
	}
	public void setClientCod(String clientCod) {
		this.clientCod = clientCod;
	}
	public String getClientCod() {
		return clientCod;
	}
	public void setContDays(int contDays) {
		this.contDays = contDays;
	}
	public int getContDays() {
		return contDays;
	}
	
	
	
	public String getStatus(){
		
	if(error == PagueOAluguel.SUCCESS)
	return "Software licenciado";	
	
	if(error == PagueOAluguel.LICENSE_TERMINATION)
	return "Licença expira em menos de  5 dias "+(data_expiracao!=null && data_expiracao.length()>0?"(Expira em "+data_expiracao+")":"");	
			
		
	return "Software não licenciado, realize o registro do seu software.";	
	}



	public void setDataExpiracao(String data){
		
	this.data_expiracao  =data;
	}


}
