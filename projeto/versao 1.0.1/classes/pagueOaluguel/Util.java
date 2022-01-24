
package pagueOaluguel;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;




public final class Util {

	
private  final String NODE = "com/example/app/prefs";	
private  final String KEY = "sisKey";	
private  final String ID = "sisId";	
private  final String SOFT_COD = "sisCod";	
private  final String CLIENT_COD = "clieCod";

	



	
	protected RegistroAtual getCurrentRecord(){
	
	
	RegistroAtual currentRecord = new RegistroAtual();
	currentRecord.setErro(PagueOAluguel.UNCHECKED);
	
	
	Preferences preferences = this.getPreferences();
		
	if(preferences == null)
	return currentRecord;
	
	if(preferences.get(this.ID, "").length() > 0)	
	currentRecord.setId(preferences.get(this.ID, ""));
	else
	this.generateIDSystem(currentRecord);


	currentRecord.setChave(preferences.get(this.KEY, ""));
	currentRecord.setCodigo_cliente(preferences.get(this.CLIENT_COD, ""));
	currentRecord.setCodigo_sistema(preferences.get(this.SOFT_COD, ""));

	return currentRecord;	
	}


	
	

	
	protected boolean validacao(RegistroAtual currentRecord){
		
		
		if(!this.validador(currentRecord))	{
	
		FormDeRegistro registerForm  = new FormDeRegistro(currentRecord, this);	
		registerForm.showForm();					
		}	
		
	if(currentRecord.getErro() != PagueOAluguel.SUCCESS && currentRecord.getErro() != PagueOAluguel.LICENSE_TERMINATION)	
	return false;	
		
	return true;	
	}
	
	
	
	
	
	protected boolean validador(RegistroAtual currentRecord){
	
	if(currentRecord == null)
	return false;	
		
	
	Preferences preferences = this.getPreferences();
			
		if(preferences == null){
		
		currentRecord.setErro(PagueOAluguel.ERROR_NOT_PREFERENCES);
		return false;
		}			
		
		
		if(preferences.get(this.ID, "").length() == 0){
			
		currentRecord.setErro(PagueOAluguel.ERROR_ID_NOT_DEFINED);
		return false;
		}	

		
		if(currentRecord.getId() == null ||
			currentRecord.getCodigo_sistema() == null ||	
			currentRecord.getChave() == null ||
			currentRecord.getCodigo_cliente() == null ||
			currentRecord.getId().length() == 0 ||
			currentRecord.getCodigo_sistema().length() == 0 ||
			currentRecord.getChave().length() == 0 ||
			currentRecord.getCodigo_cliente().length() == 0){
		
		currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);	
		return false;
		}			

	String generatedKey = this.getChave(currentRecord);
		
		if(generatedKey == null){
			
		currentRecord.setChave(null);
		currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);	
		return false;
		}
	
	this.setDatasDeRegistro(currentRecord, generatedKey);
		
		if(currentRecord.getCodigo_sistema().compareTo(generatedKey.substring(17, 21)) != 0 ||
		currentRecord.getCodigo_cliente().compareTo(generatedKey.substring(0, 8)) != 0 ||
		currentRecord.getId().compareTo(generatedKey.substring(8, 17)) != 0){
		
		currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);	
		return false;
		}	
		
	
		if(currentRecord.getDia_inicial() <1  || currentRecord.getDia_inicial() > 31 ||
			currentRecord.getMes_inicial() <1  || currentRecord.getMes_inicial() > 12 ||
			currentRecord.getAno_inicial() < 2014 ||
			currentRecord.getCont_de_dias() == 0){
		
		currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);
		return false;
		}
		
		
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");  
				
		try {
					
		Date initialDate = formatDate.parse(currentRecord.getDia_inicial()+"/"+currentRecord.getMes_inicial()+"/"+currentRecord.getAno_inicial()); 
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate);	
		calendar.add(Calendar.DAY_OF_MONTH, currentRecord.getCont_de_dias());	
		
		Date today = formatDate.parse(formatDate.format(new Date()));		
		Date finalDate = calendar.getTime();
		
			if(initialDate.after(finalDate)){
					
			currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);
			return false;
			}
	
			if(finalDate.before(today)){
					
			currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);
			return false;
			}
			
		calendar.setTime(today);		
		calendar.add(Calendar.DAY_OF_MONTH, 5);	
	
			if(finalDate.before(calendar.getTime())){
			
			currentRecord.setErro(PagueOAluguel.LICENSE_TERMINATION);
			currentRecord.setData_expiracao(new SimpleDateFormat("dd/MM/yyyy").format(finalDate));

			return true;
			}
	
			
		currentRecord.setErro(PagueOAluguel.SUCCESS);
		}
		catch (ParseException e) {
		
		currentRecord.setErro(PagueOAluguel.ERROR_KEY_INVALID);
		return false;
		} 
		
	return true;
	}
	


	
	protected void setDatasDeRegistro(RegistroAtual currentRecord, String generatedKey){
		
	currentRecord.setDia_inicial(Integer.parseInt(generatedKey.substring(21, 23)));
	currentRecord.setMes_inicial(Integer.parseInt(generatedKey.substring(23, 25)));
	currentRecord.setAno_inicial(Integer.parseInt(generatedKey.substring(25, 29)));
	currentRecord.setCont_de_dias(Integer.parseInt(generatedKey.substring(29, 32)));
	}
	
	
		

	
	protected String getChave( RegistroAtual currentRecord){
	
	if(currentRecord.getId() == null || currentRecord.getId().length() != 9)	
	return null;	
	
	String generatedKey = decrypt(currentRecord.getChave(), this.getPrivateKey(currentRecord.getId()));	

	generatedKey = generatedKey.replaceAll("\\D", "");		
	
	if(generatedKey == null || generatedKey.length() != 32)
	return null;
		
	return generatedKey;
	}
	
	
	
	
	
	
	private String decrypt(String serial, String key){
     
	if(key == null || key.length() == 0)	
	return null;
		
	String IV = "AAAAAAAAAAAAAAAA";		
	String result = "";

		try {
			
		Cipher decrypt = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		
	    SecretKeySpec  secretKeySpec= new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	        
		decrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes("UTF-8")));
	    
		result = new String(decrypt.doFinal(Base64.decode(serial.getBytes("UTF-8")))); 
	
		} 
		catch (	Base64DecodingException | 
						IllegalBlockSizeException | 
						BadPaddingException |
						NoSuchAlgorithmException |
						NoSuchProviderException | 
						NoSuchPaddingException | 
						InvalidKeyException | 
						InvalidAlgorithmParameterException | 
						UnsupportedEncodingException e) {e.printStackTrace();  return null;}
		
    return result;
	}
	
	
	
	
	
	private String getPrivateKey(String softId){
		
	if(softId.length() != 9)	
	return null;		
		
	int indice = Integer.parseInt(new StringBuilder(""+softId.charAt(3)+softId.charAt(6)).reverse().toString());
		
	String keys1[]={
				"527f57a40354545h",
				"905z35p31645hi92",
				"45789a12098s7g32",
				"90523f89210vjl98",
				"36993c70845j41xc",
				"5c84p6a04s42ncl2",
				"p70632f62120vc87",
				"q078v941j4781sb0",
				"786g6a91p1vk03fc",
				"4579823f21qrfb11",
				"k8562812f9m3bizc",
				"5m6921a56106bdtb",
				"89c303a04xxsf430",
				"0fk2352a3g461qaf",
				"498494268076zzb1",
				"24912g19a0q098i4",
				"7f99a0j3414gbczm",
				"k56723564m719798",
				"zmar2g503sklpdf3",
				"203a4f23p1134fvz"};
		
		
	String keys2[]={
				"152679f545p45ph0",
				"193xzq107mn55342",
				"7a0nmtft1ab0s732",
				"8210vj2l960p018b",
				"36c70845j4oo1xbc",
				"5c8a02ncbpgll2am",
				"p700pvv87u132zan",
				"q0781v7vv5854sb0",
				"71pz981vk03fcqxb",
				"45198z7982bm1li1",
				"k81f9zweazvcc910",
				"5mybdytwgbhlp18c",
				"89qac3f4gjn30zdz",
				"0fk261qaqfk760af",
				"4984mjkrvp8x2zb1",
				"24u091q098qmi4ka",
				"7f91gbcznbz1j8xm",
				"k567m71y9798kx57",
				"zmar2klpztkn0df3",
				"343fv1azmyrd8189"};
			
			
	String keys3[]={
				"19p520vnde041za0",
				"13xbbaq30710mn42",
				"7lknfab0s7319bv2",
				"821p1tftkqrr58b4",
				"36c7ovckf1rx47bc",
				"5clbqqtril21am1z",
				"p70r0ow724zkanj8",
				"qv58oqbz19l0adml",
				"71p37fc4mqcawxba",
				"451u8o9c87m14li1",
				"k8ftqwr1fzrvcc10",
				"ybdyq7a7lo1lk8cj",
				"89jnm0wr3o00zdyu",
				"0fafq0k0az9651af",
				"498v20m7x2wzpb19",
				"24re49qc17i4mk1a",
				"7f0zs9opaxcrmjam",
				"k7mrwoq84kxcp5c7",
				"zr2d4rp7fa3nb65z",
				"374cp4orwc1a9mia"};
		
		
	if(indice < 0 || indice >= keys1.length)
	return null;
			
		switch(PagueOAluguel.tipo_de_licenca){
		
		case 0:
		return keys1[indice]; 
		
		case 1:
		return keys2[indice]; 
			
		case 2:
		return keys3[indice]; 
		}
		
	return null;
	}


		

		
	private Preferences getPreferences(){
		
	Preferences preferences	= null;
		try {
				
		preferences = Preferences.userRoot().node(this.NODE+"/"+PagueOAluguel.SYSTEM_NAME);
				
			if(!preferences.nodeExists("")){
			
			preferences = null;
			return null;
			}
		}
		catch (BackingStoreException e) {
			
		preferences = null;
		return null;
		}
			
	return preferences;
	}
			
		

		
		

	protected boolean setPreferenceClientCod(String clientCod ){
			
	Preferences preferences = this.getPreferences();
			
	if(preferences == null)
	return false;			

	preferences.put(CLIENT_COD, clientCod);
	return true;
	}
		
		
		
		
	protected boolean setPreferenceSoftCod(String softCod ){
			
	Preferences preferences = this.getPreferences();
			
	if(preferences == null)
	return false;			

	preferences.put(SOFT_COD, softCod);
	return true;
	}
			
		
		
		
		
	protected boolean setPreferenceKey(String key ){
			
	Preferences preferences = this.getPreferences();
				
	if(preferences == null)
	return false;			

	preferences.put(KEY, key);
	return true;
	}
			
		
	
		
		
	public void generateIDSystem(RegistroAtual currentRecord){
		
	Preferences preferences = this.getPreferences();
			
	if(preferences == null)
	return;		
			
	Random generator = new Random();	
		
	String indice = String.format("%02d", generator.nextInt(20));
		
	String aux = 
	String.format("%03d", generator.nextInt(1000))+
	indice.charAt(1)+
	String.format("%02d", generator.nextInt(100))+
	indice.charAt(0)+
	String.format("%02d", generator.nextInt(100));
		
	preferences.put(ID, aux);
	currentRecord.setId(preferences.get(ID, ""));
	}


	
}
