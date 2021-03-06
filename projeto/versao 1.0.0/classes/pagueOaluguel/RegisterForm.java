package pagueOaluguel;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;






import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;






public final class RegisterForm extends JDialog{



private static final long serialVersionUID = 1L;


private CurrentRecord currentRecord;
private Util util;

private JPanel registerStatusPanel;

private JLabel lbId;
private JLabel lbDateInicial;
private JLabel lbDateFinal;
private JLabel lbStatus;

private JTextField tf_codClient;
private JTextField tf_codSoft;




	protected RegisterForm( final CurrentRecord currentRecord, final Util util_aux){
			
	super();
		
	this.currentRecord = currentRecord;
	this.util = util_aux;	
	
	this.setTitle("Registro de Software");
	this.setSize( 450, 500); 
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	this.setModal(true);  
	this.getContentPane().setBackground(Color.white);

	
	GridBagConstraints cons = new GridBagConstraints();  
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty =1;
	cons.weightx = 1;
	cons.insets = new Insets(4, 4, 4, 4 );
		
	JPanel backgroundPane = new JPanel();
	backgroundPane.setLayout(new GridBagLayout());
	backgroundPane.setBackground(Color.white);
	this.add( backgroundPane, cons);
		
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(2, 0, 7, 0 );
	backgroundPane.add( new JLabel("<html><b>Informa??es de Registro</b></html>",JLabel.CENTER), cons); 
		
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 0.7;
	cons.insets = new Insets(4, 0, 0, 0 );
	backgroundPane.add( new JLabel("<html><b>Nome do Sistema: </b>"+PagueOAluguel.SYSTEM_NAME+"</html>"), cons); 
	backgroundPane.add( new JLabel("<html><b>Vers?o: </b>"+PagueOAluguel.SYSTEM_VERSION+"</html>"), cons); 
			
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = 2;
	cons.weighty =0;
	cons.weightx = 1;		
	this.lbId = new JLabel("<html><b>ID: </b>"+(this.currentRecord.getId() == null || this.currentRecord.getId().length() == 0?"Indefinido":this.currentRecord.getId())+"</html>");
	backgroundPane.add( lbId, cons); 		
	
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.EAST;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 0;
	JButton btCopyID = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_copy.png")));
	btCopyID.setToolTipText("Copiar ID e enviar para ?rea de transfer?ncia."); 
	backgroundPane.add(btCopyID, cons);
	
	
		btCopyID.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
			
			TextTransfer textTransfer = new TextTransfer();
			
			textTransfer.setClipboardContents(currentRecord.getId() == null || currentRecord.getId().length() == 0?"":currentRecord.getId());
			 
		    JOptionPane.showMessageDialog(null, "ID enviado para ?rea de transfer?ncia.", "", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(0, 0, 5, 0 );
	backgroundPane.add(new JSeparator(SwingConstants.HORIZONTAL), cons);		
		
		
	JPanel panel1 = new JPanel(new GridBagLayout());	
	panel1.setBackground(Color.WHITE);
	backgroundPane.add(panel1, cons);	
		

	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(0, 0, 0, 0 );
	panel1.add(new JLabel("C?digo do Cliente:"), cons);
	
	cons.gridwidth = 1;
	this.tf_codClient = new JTextField();
	this.tf_codClient.setDocument( new Format_TextField_MaxCompSomenNum( 8 ,  this.tf_codClient )  ); 
	this.tf_codClient.setText(this.currentRecord.getClientCod()== null? "":this.currentRecord.getClientCod());
	this.tf_codClient.setEnabled(false);
	panel1.add(this.tf_codClient, cons);
	
	cons.fill = GridBagConstraints.NONE;
	cons.weightx = 0;
	cons.ipady = 2;
	JButton btClientCodeEnabled = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_edit.png")));
	btClientCodeEnabled.setToolTipText("Habilitar altera??o de C?digo do Cliente"); 
	panel1.add(btClientCodeEnabled, cons);
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	JButton btSetClientCod = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
	btSetClientCod.setToolTipText("Salvar C?digo do Cliente"); 
	panel1.add(btSetClientCod, cons);
	

		btClientCodeEnabled.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed( ActionEvent event ){
				
				if(tf_codClient.isEnabled())
				tf_codClient.setEnabled(false);
				else
				tf_codClient.setEnabled(true);	
				}
			});
	
	
		
		btSetClientCod.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
			
				if( tf_codClient.getText().length() != 8){
				
				tf_codClient.setText("");
				JOptionPane.showMessageDialog(null, "C?digo de cliente inv?lido.", "ERRO", JOptionPane.ERROR_MESSAGE);				
				}	 
				else{
					
				if(util.setPreferenceClientCod(tf_codClient.getText())){
				
				currentRecord.setClientCod(tf_codClient.getText());
					
				JOptionPane.showMessageDialog(null, "C?digo de cliente atualizado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);	
				tf_codClient.setEnabled(false);
				}
				else
				JOptionPane.showMessageDialog(null, "Imposs?vel alterar o c?digo de cliente.", "ERRO", JOptionPane.ERROR_MESSAGE);	
					
				}
			}
		});

	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	panel1.add(new JLabel("C?digo do Software:"), cons);
	
	cons.gridwidth = 1;
	this.tf_codSoft = new JTextField();
	this.tf_codSoft.setDocument( new Format_TextField_MaxCompSomenNum( 4,  this.tf_codSoft )  );
	this.tf_codSoft.setText(this.currentRecord.getSoftCod() == null? "":this.currentRecord.getSoftCod());
	this.tf_codSoft.setEnabled(false);
	panel1.add(this.tf_codSoft, cons);
		
	
	cons.fill = GridBagConstraints.NONE;
	cons.weightx = 0;
	cons.ipady = 2;
	JButton btSoftCodeEnabled = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_edit.png")));
	btSoftCodeEnabled.setToolTipText("Habilitar altera??o de C?digo do Software"); 
	panel1.add(btSoftCodeEnabled, cons);
		
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	JButton btSetSoftCod = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
	btSetSoftCod.setToolTipText("Salvar C?digo do Software"); 
	panel1.add(btSetSoftCod, cons);
		

		btSoftCodeEnabled.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
			
			if(tf_codSoft.isEnabled())
			tf_codSoft.setEnabled(false);
			else
			tf_codSoft.setEnabled(true);	
			}
		});

	
	
		btSetSoftCod.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
					
				if( tf_codSoft.getText().length() != 4){
						
				tf_codSoft.setText("");
				JOptionPane.showMessageDialog(null, "C?digo de software inv?lido.", "ERRO", JOptionPane.ERROR_MESSAGE);				
				}	 
				else{
							
				if(util.setPreferenceSoftCod(tf_codSoft.getText())){
				
				currentRecord.setSoftCod(tf_codSoft.getText());	
					
				JOptionPane.showMessageDialog(null, "C?digo de software atualizado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);	
				tf_codSoft.setEnabled(false);
				}
				else
				JOptionPane.showMessageDialog(null, "Imposs?vel alterar o c?digo de software.", "ERRO", JOptionPane.ERROR_MESSAGE);	
							
				}
			}
		});
	
	
	
	cons.insets = new Insets(4, 0, 0, 0 );
	backgroundPane.add(new JSeparator(SwingConstants.HORIZONTAL), cons);	
	

	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	this.lbDateInicial = new  JLabel("<html><b>?ltimo Registro: </b>"+String.format("%02d", this.currentRecord.getInitialDay())+"/"+String.format("%02d", this.currentRecord.getInitialMonth())+"/"+String.format("%04d", this.currentRecord.getInitialYear())+"</html>");
	backgroundPane.add( this.lbDateInicial, cons); 	

	this.lbDateFinal = new  JLabel("");
	this.getFinalDate();
	backgroundPane.add( this.lbDateFinal, cons); 	

	
	
	cons.insets = new Insets(4, 0, 4, 0 );		
	backgroundPane.add(new JSeparator(SwingConstants.HORIZONTAL), cons);	
	
	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = 1;
	cons.weighty =1;
	cons.weightx = 0.8;
	this.registerStatusPanel = new JPanel();
	this.registerStatusPanel.setBackground(Color.WHITE);
	this.registerStatusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder()); 
	this.registerStatusPanel.setLayout(new GridBagLayout());
	backgroundPane.add( this.registerStatusPanel, cons); 
	
		
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =1;
	cons.weightx = 0.2;
	JPanel btsPanel = new JPanel(new GridBagLayout());
	btsPanel.setBackground(Color.WHITE);
	backgroundPane.add( btsPanel, cons); 
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty =0;
	cons.insets = new Insets( 5, 0, 0, 0 );	
	final JButton btRegisterOnline = new JButton("Registro Online");
	btsPanel.add(btRegisterOnline, cons);	
	

	final JButton btRegisterOffline = new JButton("Inserir Chave");
	btsPanel.add(btRegisterOffline, cons);	
	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty =1;
	btsPanel.add(new JLabel(""), cons);	
	
	

	cons.weighty =0;
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.EAST;
	cons.weightx = 1;
	cons.ipadx=40;
	JButton btGoBack = new JButton("Fechar");
	backgroundPane.add(btGoBack, cons);	
	
	

	
		btRegisterOnline.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
		   
			btRegisterOnline.setEnabled(false);
			btRegisterOffline.setEnabled(false);
							
			InfoKeyProgressPanel();	
				
			licenseOnLine();
	
			InfoKeyPanel();
			
			btRegisterOnline.setEnabled(PagueOAluguel.onlineRegisterEnabled);
			btRegisterOffline.setEnabled(PagueOAluguel.insertRegisterKeyEnabled);
			}
		});
	
		
		btRegisterOffline.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
		    
			btRegisterOnline.setEnabled(false);
			btRegisterOffline.setEnabled(false);
						
			InfoKeyProgressPanel();	
			
			licenseOffLine();
			
			btRegisterOnline.setEnabled(PagueOAluguel.onlineRegisterEnabled);
			btRegisterOffline.setEnabled(PagueOAluguel.insertRegisterKeyEnabled);
			
			lbDateInicial.setText("<html><b>?ltimo Registro: </b>"+String.format("%02d", currentRecord.getInitialDay())+"/"+String.format("%02d", currentRecord.getInitialMonth())+"/"+String.format("%04d", currentRecord.getInitialYear())+"</html>");
			
			getFinalDate();
		
			InfoKeyPanel();
			}
		});
	
	
		btGoBack.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
		    
			dispose();
			}
		});
	
		
	this.getRootPane().setDefaultButton(btGoBack);	
	this.InfoKeyPanel();
	
	btRegisterOnline.setEnabled(PagueOAluguel.onlineRegisterEnabled);
	btRegisterOffline.setEnabled(PagueOAluguel.insertRegisterKeyEnabled);
	}
			
		 	

	
	private void getFinalDate(){
	
		if(this.currentRecord.getInitialDay() == 0l ||
			this.currentRecord.getInitialMonth() == 0 ||
			this.currentRecord.getInitialYear() == 0){
			
		this.lbDateFinal.setText("<html><b>Licenciado at?: </b>00/00/0000</html>");	
		return;
		}
	
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");  	

		try {
			
		
		Date initialDate = formatDate.parse(currentRecord.getInitialDay()+"/"+currentRecord.getInitialMonth()+"/"+currentRecord.getInitialYear());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate);	
		calendar.add(Calendar.DAY_OF_MONTH, currentRecord.getContDays());
		
		this.lbDateFinal.setText("<html><b>Licenciado at?: </b>"+formatDate.format(calendar.getTime())+"</html>");	
		} 
		catch (ParseException e) {
			
		this.lbDateFinal.setText("<html><b>Licenciado at?: </b>00/00/0000</html>");	
		return;
		} 	
	}
	
	
	
	
	
	
	private void InfoKeyPanel(){
	
	this.registerStatusPanel.removeAll();
	this.registerStatusPanel.repaint();
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(2, 5, 0, 5 );	
	
	this.registerStatusPanel.add( new JLabel("<html><b>Chave Atual:</b></html>"), cons);
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty =1;
	
	JTextArea KeyArea = new JTextArea((this.currentRecord.getKey() == null || this.currentRecord.getKey().length() == 0?"":this.currentRecord.getKey())); 		
	KeyArea.setEditable(false);	
	this.registerStatusPanel.add( new JScrollPane(KeyArea , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), cons); 		

	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty =0;
	cons.insets = new Insets(2, 5, 2, 5 );	
	this.lbStatus = new JLabel(this.getStatus());
	this.registerStatusPanel.add(this.lbStatus, cons);
	
	this.registerStatusPanel.validate();
	}
	
	
	
	
	
	private void InfoKeyProgressPanel(){
		
	this.registerStatusPanel.removeAll();
	this.registerStatusPanel.repaint();	
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =1;
	cons.weightx = 1;
	cons.insets = new Insets(5, 0, 0, 0 );	
	this.registerStatusPanel.add( new JLabel("", new ImageIcon(getClass().getResource("/icons/pOa_aguarde.gif")), JLabel.CENTER), cons);
	
	cons.fill = GridBagConstraints.NONE;
	cons.weighty =0;
	cons.insets = new Insets(0, 0, 5, 0 );
	this.registerStatusPanel.add( new JLabel("<html><b>Aguarde...</b></html>"), cons);	
	
	this.registerStatusPanel.validate();
	}
		
	
	
	
	
	private String getStatus(){
	
	String aux = "";	
	
		switch(this.currentRecord.getError()){

		case PagueOAluguel.SUCCESS:
		aux = "<html><b>Status: <font color=green>Software Licenciado</font></b></html>";
		break;	
			case PagueOAluguel.ERROR_NOT_PREFERENCES:
			case PagueOAluguel.ERROR_ID_NOT_DEFINED:
			case PagueOAluguel.ERROR_KEY_INVALID:
			aux = "<html><b>Status: <font color=red>N?o Registrado</font></b></html>";
			break;	
				case PagueOAluguel.LICENSE_TERMINATION:
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());	
				break;	
					case PagueOAluguel.UNCHECKED:
					aux = "<html><b>Status: <font color=black>N?o Avaliado</font></b></html>";	
			}
		
		return aux;
		}	
	
		

		
		
	protected void showForm(){
			
	this.setVisible(true);	
	}
		
		
		
		
	
	private void licenseOffLine(){
		
	AddKeyForm addKeyForm = new AddKeyForm	(this.currentRecord, this.util);
	addKeyForm.showForm();
	}
	
	




	private boolean licenseOnLine(){
	
	PainelDeRegistroOnline	form  = new PainelDeRegistroOnline();
	form.setVisible(true);

	
	if(this.currentRecord.getError()== PagueOAluguel.SUCCESS || 
			this.currentRecord.getError()== PagueOAluguel.LICENSE_TERMINATION)
	return true;
	
	return false;
	}
	
	
	

	
	
	
	private class PainelDeRegistroOnline extends JDialog{

	private static final long serialVersionUID = 1L;
		
		
	
		public PainelDeRegistroOnline(){
			
		this.setTitle("Registro de Software Online");
		this.setSize( 750, 140); 
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		this.setModal(true);  
		this.getContentPane().setBackground(Color.white);

			
		GridBagConstraints cons = new GridBagConstraints();  
		cons.fill = GridBagConstraints.BOTH;
		cons.weighty =1;
		cons.weightx = 1;
		cons.insets = new Insets(2, 2, 2, 2 );

		JPanel backgroundPane = new JPanel();
		backgroundPane.setLayout(new GridBagLayout());
		backgroundPane.setBackground(Color.white);
		this.add( backgroundPane, cons);
				
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.weighty =0;
		cons.weightx = 1;
		cons.insets = new Insets(2, 0, 4, 0 );
		backgroundPane.add( new JLabel("O sistema se conectar? aos servidores da MSC Solu??es e, caso haja licen?as dispon?veis, realizar? o seu lincenciamento.",JLabel.CENTER), cons); 
		
		cons.insets = new Insets(10, 0, 4, 0 );			
		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 0;
		cons.ipady = 0;
		JButton btlicenciar = new JButton("Iniciar Licenciamento", new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
		btlicenciar.setToolTipText("Clique aqui para se conectar."); 
		backgroundPane.add(btlicenciar, cons);	
		
		
		

		btlicenciar.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
		
		CurrentRecord auxRecord = new CurrentRecord();
			
		auxRecord.setId(currentRecord.getId());
		auxRecord.setClientCod( currentRecord.getClientCod());
		auxRecord.setSoftCod(currentRecord.getSoftCod());
				
		ServerConnect server = new ServerConnect();
			
		String keyCandidate = server.connect(auxRecord);
		
		System.out.println(keyCandidate);
		
		auxRecord.setKey(keyCandidate);
			
			if(auxRecord.getKey() == null){
				
			JOptionPane.showMessageDialog(null, "Servidor n?o dispon?vel no momento.", "ERRO", JOptionPane.ERROR_MESSAGE);							
			return;	
			}
			
			
		util.validator(auxRecord);
				
			 
			if(auxRecord.getError()== PagueOAluguel.SUCCESS || auxRecord.getError()== PagueOAluguel.LICENSE_TERMINATION){
						
			util.setPreferenceKey(auxRecord.getKey());	
			currentRecord.setKey(auxRecord.getKey());
			currentRecord.setError(auxRecord.getError());
			currentRecord.setContDays(auxRecord.getContDays());
			currentRecord.setInitialDay(auxRecord.getInitialDay());
			currentRecord.setInitialMonth(auxRecord.getInitialMonth());
			currentRecord.setInitialYear(auxRecord.getInitialYear());
			
			lbDateInicial.setText("<html><b>?ltimo Registro: </b>"+String.format("%02d", currentRecord.getInitialDay())+"/"+String.format("%02d", currentRecord.getInitialMonth())+"/"+String.format("%04d", currentRecord.getInitialYear())+"</html>");
			getFinalDate();
			
			JOptionPane.showMessageDialog(null, "Licenciamento realizado com sucesso.", "ERRO", JOptionPane.INFORMATION_MESSAGE);					
			
			return;	
			}
				
			
		JOptionPane.showMessageDialog(null, "Chave de licen?a inv?lida.", "ERRO", JOptionPane.ERROR_MESSAGE);					
		return ;	
	
		}});
		}
	
	}


}
