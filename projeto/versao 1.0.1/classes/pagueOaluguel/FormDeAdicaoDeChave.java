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




public class FormDeAdicaoDeChave extends JDialog{


private static final long serialVersionUID = 1L;


private RegistroAtual registroAtual;
private Util util;

private JTextArea ta_chave;
	



	public FormDeAdicaoDeChave( RegistroAtual currentRecord, Util util){
		
	this.registroAtual = currentRecord;
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
	this.ta_chave = new JTextArea(""); 		
	this.add( new JScrollPane(this.ta_chave , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
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
		   
				if(addChave()){
				
				JOptionPane.showMessageDialog(null, "Software registrado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);					
				dispose();
				}
				else
				JOptionPane.showMessageDialog(null, "Chave de licença inválida.", "ERRO", JOptionPane.ERROR_MESSAGE);					
					
			}
		});
	
	this.getRootPane().setDefaultButton(btRegister);
	}
	
	
	
	
	protected void mostrar(){
		
	this.setVisible(true);	
	}
		
	
	
	
	
	private boolean addChave(){
	
	String keyCandidate = formatarChave();	
	
	RegistroAtual auxRecord = new RegistroAtual();
	
	auxRecord.setId(this.registroAtual.getId());
	auxRecord.setChave( keyCandidate);
	auxRecord.setCodigo_cliente( this.registroAtual.getCodigo_cliente());
	auxRecord.setCodigo_sistema( this.registroAtual.getCodigo_sistema());
	
	this.util.validador(auxRecord);
	 
		if(auxRecord.getErro()== PagueOAluguel.SUCCESS || auxRecord.getErro()== PagueOAluguel.LICENSE_TERMINATION){
			
		this.util.setPreferenceKey(auxRecord.getChave());	
		this.registroAtual.setChave(auxRecord.getChave());
		this.registroAtual.setErro(auxRecord.getErro());
		this.registroAtual.setCont_de_dias(auxRecord.getCont_de_dias());
		this.registroAtual.setDia_inicial(auxRecord.getDia_inicial());
		this.registroAtual.setMes_inicial(auxRecord.getMes_inicial());
		this.registroAtual.setAno_inicial(auxRecord.getAno_inicial());
	
		return true;	
		}
	
	return false;	
	}
	
	
	
	
	private String formatarChave(){
		
	String aux = this.ta_chave.getText();
			
	return aux.replace(" ", "").replace("\n", "").replace("\r\n", "");
	}
	
	
}
