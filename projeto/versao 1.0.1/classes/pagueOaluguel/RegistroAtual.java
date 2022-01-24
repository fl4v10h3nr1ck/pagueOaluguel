package pagueOaluguel;


public class RegistroAtual {

	
private String chave;
private String id;
private String codigo_sistema;
private String codigo_cliente;

private int dia_inicial;
private int mes_inicial;
private int ano_inicial;
private int cont_de_dias;

private int erro;

private String data_expiracao = "";





public String getChave() {	return chave;}
public void setChave(String chave) {	this.chave = chave;}

public String getId() {	return id;}
public void setId(String id) {	this.id = id;}

public String getCodigo_sistema() {	return codigo_sistema;}
public void setCodigo_sistema(String codigo_sistema) {	this.codigo_sistema = codigo_sistema;}

public String getCodigo_cliente() {	return codigo_cliente;}
public void setCodigo_cliente(String codigo_cliente) {	this.codigo_cliente = codigo_cliente;}

public int getDia_inicial() {	return dia_inicial;}
public void setDia_inicial(int dia_inicial) {	this.dia_inicial = dia_inicial;}

public int getMes_inicial() {	return mes_inicial;}
public void setMes_inicial(int mes_inicial) {	this.mes_inicial = mes_inicial;}

public int getAno_inicial() {	return ano_inicial;}
public void setAno_inicial(int ano_inicial) {	this.ano_inicial = ano_inicial;}

public int getCont_de_dias() {	return cont_de_dias;}
public void setCont_de_dias(int cont_de_dias) {	this.cont_de_dias = cont_de_dias;}

public int getErro() {	return erro;}
public void setErro(int erro) {	this.erro = erro;}

public String getData_expiracao() {	return data_expiracao;}
public void setData_expiracao(String data_expiracao) {	this.data_expiracao = data_expiracao;}



	public String getStatus(){
		
	if(erro == PagueOAluguel.SUCCESS)
	return "Software licenciado";	
	
	if(erro == PagueOAluguel.LICENSE_TERMINATION)
	return "Licença expira em menos de  5 dias "+(data_expiracao!=null && data_expiracao.length()>0?"(Expira em "+data_expiracao+")":"");	
			
		
	return "Software não licenciado, realize o registro do seu software.";	
	}



	

}
