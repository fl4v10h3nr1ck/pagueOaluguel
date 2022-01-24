package pagueOaluguel;



import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;




public class AddKeyForm extends JDialog{


private static final long serialVersionUID = 1L;


private CurrentRecord currentRecord;
private Util util;

private JTextArea KeyArea;
	




	public AddKeyForm( CurrentRecord currentRecord, Util util){
		
	this.currentRecord = currentRecord;
	this.util = util;	
		
	this.setTitle("Adição de Chave de Registro");
	this.setSize( 450, 200); 
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	this.setModal(true);  
	this.getContentPane().setBackground(Color.white);
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(5, 5, 0, 5 );	
	this.add( new JLabel("<html>Informe a Nova Chave:<font color=red>*</font></html>"), cons);
	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty =1;
	this.KeyArea = new JTextArea(""); 		
	this.add( new JScrollPane(this.KeyArea , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), cons); 	

	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.EAST;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 0;
	cons.insets = new Insets(5, 5, 10, 5 );	
	JButton btRegister = new JButton("Registrar", new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
	this.add(btRegister, cons);	

		btRegister.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
		   
				if(addKey()){
				
				JOptionPane.showMessageDialog(null, "Software registrado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);					
				dispose();
				}
				else
				JOptionPane.showMessageDialog(null, "Chave de licença inválida.", "ERRO", JOptionPane.ERROR_MESSAGE);					
					
			}
		});
	
	this.getRootPane().setDefaultButton(btRegister);
	}
	
	
	
	
	protected void showForm(){
		
	this.setVisible(true);	
	}
		
	
	
	
	
	private boolean addKey(){
	
	String keyCandidate = keyFormat();	
	
	CurrentRecord auxRecord = new CurrentRecord();
	
	auxRecord.setId(this.currentRecord.getId());
	auxRecord.setKey( keyCandidate);
	auxRecord.setClientCod( this.currentRecord.getClientCod());
	auxRecord.setSoftCod( this.currentRecord.getSoftCod());
	
	this.util.validator(auxRecord);
	 
		if(auxRecord.getError()== PagueOAluguel.SUCCESS || auxRecord.getError()== PagueOAluguel.LICENSE_TERMINATION){
			
		this.util.setPreferenceKey(auxRecord.getKey());	
		this.currentRecord.setKey(auxRecord.getKey());
		this.currentRecord.setError(auxRecord.getError());
		this.currentRecord.setContDays(auxRecord.getContDays());
		this.currentRecord.setInitialDay(auxRecord.getInitialDay());
		this.currentRecord.setInitialMonth(auxRecord.getInitialMonth());
		this.currentRecord.setInitialYear(auxRecord.getInitialYear());
	
		return true;	
		}
	
	return false;	
	}
	
	
	
	
	private String keyFormat(){
		
	String aux = this.KeyArea.getText();
			
	return aux.replace(" ", "").replace("\n", "").replace("\r\n", "");
	}
	
	
}
