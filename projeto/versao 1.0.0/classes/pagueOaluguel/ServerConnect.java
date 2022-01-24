package pagueOaluguel;

import java.io.IOException;








import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public final class ServerConnect {


	
	
	
	protected String connect( CurrentRecord auxRecord){
   
	String key = null;	
		
		try {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
       
		HttpGet httpget = new HttpGet(PagueOAluguel.serverURL+"?ID="+auxRecord.getId()+"&accessKey2Server="+PagueOAluguel.accessKey2Server+"&softCOD="+auxRecord.getSoftCod()+"&clientCOD="+auxRecord.getClientCod());
		
        CloseableHttpResponse response = httpclient.execute(httpget);
        
        HttpEntity entity = response.getEntity();

        if (entity != null)
        key = this.keyFormat(EntityUtils.toString(entity));
      
        httpclient.close();
        response.close();

        
	        if(key.contains("ERRO_10000") || key.contains("ERRO_10001")){
	       
	         JOptionPane.showMessageDialog(null, "Uma ou mais informações do software é inválida, impossível realizar o licenciamento.", "ERRO", JOptionPane.ERROR_MESSAGE);
	        return null;
	        }
	        
	        if(key.contains("ERRO_20000") || key.contains("ERRO_20001")){
	 	       
		    JOptionPane.showMessageDialog(null, "Não há novas licença disponíveis para este software.", "ERRO", JOptionPane.ERROR_MESSAGE);
		    return null;
		    }
	        if(key.contains("ERRO_40000")){
		 	       
		    JOptionPane.showMessageDialog(null, "Servidor indisponível no momento (40000).", "ERRO", JOptionPane.ERROR_MESSAGE);
			return null;
			}
	        
	        if(key.contains("ERRO_30000")){
		 	       
	        JOptionPane.showMessageDialog(null, "Servidor indisponível no momento (30000).", "ERRO", JOptionPane.ERROR_MESSAGE);
			return null;
			}
		} 
		catch (IOException e) { return null;}
        catch (Exception e) {return null;}
   
    return key;
	}	
		
		
	
	
	private String keyFormat(String key){
		
	if(key == null)
	return null;
		
	return key.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r\n", "");
	}
	
}
