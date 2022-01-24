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






public final class FormDeRegistro extends JDialog{



private static final long serialVersionUID = 1L;


private RegistroAtual registroAtual;
private Util util;

private JPanel painel_de_status;

private JLabel lb_Id;
private JLabel lb_DateInicial;
private JLabel lb_DateFinal;
private JLabel lb_status;

private JTextField tf_codClient;
private JTextField tf_codSoft;




	protected FormDeRegistro( final RegistroAtual currentRecord, final Util util_aux){
			
	super();
		
	this.registroAtual = currentRecord;
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
	backgroundPane.add( new JLabel("<html><b>Informações de Registro</b></html>",JLabel.CENTER), cons); 
		
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 0.7;
	cons.insets = new Insets(4, 0, 0, 0 );
	backgroundPane.add( new JLabel("<html><b>Nome do Sistema: </b>"+PagueOAluguel.SYSTEM_NAME+"</html>"), cons); 
	backgroundPane.add( new JLabel("<html><b>Versão: </b>"+PagueOAluguel.SYSTEM_VERSION+"</html>"), cons); 
			
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = 2;
	cons.weighty =0;
	cons.weightx = 1;		
	this.lb_Id = new JLabel("<html><b>ID: </b>"+(this.registroAtual.getId() == null || this.registroAtual.getId().length() == 0?"Indefinido":this.registroAtual.getId())+"</html>");
	backgroundPane.add( lb_Id, cons); 		
	
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.EAST;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 0;
	JButton btCopyID = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_copy.png")));
	btCopyID.setToolTipText("Copiar ID e enviar para área de transferência."); 
	backgroundPane.add(btCopyID, cons);
	
	
		btCopyID.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
			
			Link textTransfer = new Link();
			
			textTransfer.setClipboardContents(currentRecord.getId() == null || currentRecord.getId().length() == 0?"":currentRecord.getId());
			 
		    JOptionPane.showMessageDialog(null, "ID enviado para área de transferência.", "", JOptionPane.INFORMATION_MESSAGE);				
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
	panel1.add(new JLabel("Código do Cliente:"), cons);
	
	cons.gridwidth = 1;
	this.tf_codClient = new JTextField();
	this.tf_codClient.setDocument( new Format_TextField_MaxCompSomenNum( 8 ,  this.tf_codClient )  ); 
	this.tf_codClient.setText(this.registroAtual.getCodigo_cliente()== null? "":this.registroAtual.getCodigo_cliente());
	this.tf_codClient.setEnabled(false);
	panel1.add(this.tf_codClient, cons);
	
	cons.fill = GridBagConstraints.NONE;
	cons.weightx = 0;
	cons.ipady = 2;
	JButton btClientCodeEnabled = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_edit.png")));
	btClientCodeEnabled.setToolTipText("Habilitar alteração de Código do Cliente"); 
	panel1.add(btClientCodeEnabled, cons);
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	JButton btSetClientCod = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
	btSetClientCod.setToolTipText("Salvar Código do Cliente"); 
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
				JOptionPane.showMessageDialog(null, "Código de cliente inválido.", "ERRO", JOptionPane.ERROR_MESSAGE);				
				}	 
				else{
					
				if(util.setPreferenceClientCod(tf_codClient.getText())){
				
				currentRecord.setCodigo_cliente(tf_codClient.getText());
					
				JOptionPane.showMessageDialog(null, "Código de cliente atualizado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);	
				tf_codClient.setEnabled(false);
				}
				else
				JOptionPane.showMessageDialog(null, "Impossível alterar o código de cliente.", "ERRO", JOptionPane.ERROR_MESSAGE);	
					
				}
			}
		});

	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	panel1.add(new JLabel("Código do Software:"), cons);
	
	cons.gridwidth = 1;
	this.tf_codSoft = new JTextField();
	this.tf_codSoft.setDocument( new Format_TextField_MaxCompSomenNum( 4,  this.tf_codSoft )  );
	this.tf_codSoft.setText(this.registroAtual.getCodigo_sistema() == null? "":this.registroAtual.getCodigo_sistema());
	this.tf_codSoft.setEnabled(false);
	panel1.add(this.tf_codSoft, cons);
		
	
	cons.fill = GridBagConstraints.NONE;
	cons.weightx = 0;
	cons.ipady = 2;
	JButton btSoftCodeEnabled = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_edit.png")));
	btSoftCodeEnabled.setToolTipText("Habilitar alteração de Código do Software"); 
	panel1.add(btSoftCodeEnabled, cons);
		
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	JButton btSetSoftCod = new JButton(new ImageIcon(getClass().getResource("/icons/pOa_icon_save.png")));
	btSetSoftCod.setToolTipText("Salvar Código do Software"); 
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
				JOptionPane.showMessageDialog(null, "Código de software inválido.", "ERRO", JOptionPane.ERROR_MESSAGE);				
				}	 
				else{
							
				if(util.setPreferenceSoftCod(tf_codSoft.getText())){
				
				currentRecord.setCodigo_sistema(tf_codSoft.getText());	
					
				JOptionPane.showMessageDialog(null, "Código de software atualizado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);	
				tf_codSoft.setEnabled(false);
				}
				else
				JOptionPane.showMessageDialog(null, "Impossível alterar o código de software.", "ERRO", JOptionPane.ERROR_MESSAGE);	
							
				}
			}
		});
	
	
	
	cons.insets = new Insets(4, 0, 0, 0 );
	backgroundPane.add(new JSeparator(SwingConstants.HORIZONTAL), cons);	
	

	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	this.lb_DateInicial = new  JLabel("<html><b>Último Registro: </b>"+String.format("%02d", this.registroAtual.getDia_inicial())+"/"+String.format("%02d", this.registroAtual.getMes_inicial())+"/"+String.format("%04d", this.registroAtual.getAno_inicial())+"</html>");
	backgroundPane.add( this.lb_DateInicial, cons); 	

	this.lb_DateFinal = new  JLabel("");
	this.getDataFinal();
	backgroundPane.add( this.lb_DateFinal, cons); 	

	
	
	cons.insets = new Insets(4, 0, 4, 0 );		
	backgroundPane.add(new JSeparator(SwingConstants.HORIZONTAL), cons);	
	
	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = 1;
	cons.weighty =1;
	cons.weightx = 0.8;
	this.painel_de_status = new JPanel();
	this.painel_de_status.setBackground(Color.WHITE);
	this.painel_de_status.setBorder(javax.swing.BorderFactory.createEtchedBorder()); 
	this.painel_de_status.setLayout(new GridBagLayout());
	backgroundPane.add( this.painel_de_status, cons); 
	
		
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
			
			lb_DateInicial.setText("<html><b>Último Registro: </b>"+String.format("%02d", currentRecord.getDia_inicial())+"/"+String.format("%02d", currentRecord.getMes_inicial())+"/"+String.format("%04d", currentRecord.getAno_inicial())+"</html>");
			
			getDataFinal();
		
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
			
		 	

	
	private void getDataFinal(){
	
		if(this.registroAtual.getDia_inicial() == 0l ||
			this.registroAtual.getMes_inicial() == 0 ||
			this.registroAtual.getAno_inicial() == 0){
			
		this.lb_DateFinal.setText("<html><b>Licenciado até: </b>00/00/0000</html>");	
		return;
		}
	
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");  	

		try {
			
		
		Date initialDate = formatDate.parse(registroAtual.getDia_inicial()+"/"+registroAtual.getMes_inicial()+"/"+registroAtual.getAno_inicial());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate);	
		calendar.add(Calendar.DAY_OF_MONTH, registroAtual.getCont_de_dias());
		
		this.lb_DateFinal.setText("<html><b>Licenciado até: </b>"+formatDate.format(calendar.getTime())+"</html>");	
		} 
		catch (ParseException e) {
			
		this.lb_DateFinal.setText("<html><b>Licenciado até: </b>00/00/0000</html>");	
		return;
		} 	
	}
	
	
	
	
	
	
	private void InfoKeyPanel(){
	
	this.painel_de_status.removeAll();
	this.painel_de_status.repaint();
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(2, 5, 0, 5 );	
	
	this.painel_de_status.add( new JLabel("<html><b>Chave Atual:</b></html>"), cons);
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty =1;
	
	JTextArea KeyArea = new JTextArea((this.registroAtual.getChave() == null || this.registroAtual.getChave().length() == 0?"":this.registroAtual.getChave())); 		
	KeyArea.setEditable(false);	
	this.painel_de_status.add( new JScrollPane(KeyArea , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), cons); 		

	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty =0;
	cons.insets = new Insets(2, 5, 2, 5 );	
	this.lb_status = new JLabel(this.getStatus());
	this.painel_de_status.add(this.lb_status, cons);
	
	this.painel_de_status.validate();
	}
	
	
	
	
	
	private void InfoKeyProgressPanel(){
		
	this.painel_de_status.removeAll();
	this.painel_de_status.repaint();	
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =1;
	cons.weightx = 1;
	cons.insets = new Insets(5, 0, 0, 0 );	
	this.painel_de_status.add( new JLabel("", new ImageIcon(getClass().getResource("/icons/pOa_aguarde.gif")), JLabel.CENTER), cons);
	
	cons.fill = GridBagConstraints.NONE;
	cons.weighty =0;
	cons.insets = new Insets(0, 0, 5, 0 );
	this.painel_de_status.add( new JLabel("<html><b>Aguarde...</b></html>"), cons);	
	
	this.painel_de_status.validate();
	}
		
	
	
	
	
	private String getStatus(){
	
	String aux = "";	
	
		switch(this.registroAtual.getErro()){

		case PagueOAluguel.SUCCESS:
		aux = "<html><b>Status: <font color=green>Software Licenciado</font></b></html>";
		break;	
			case PagueOAluguel.ERROR_NOT_PREFERENCES:
			case PagueOAluguel.ERROR_ID_NOT_DEFINED:
			case PagueOAluguel.ERROR_KEY_INVALID:
			aux = "<html><b>Status: <font color=red>Não Registrado</font></b></html>";
			break;	
				case PagueOAluguel.LICENSE_TERMINATION:
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());	
				break;	
					case PagueOAluguel.UNCHECKED:
					aux = "<html><b>Status: <font color=black>Não Avaliado</font></b></html>";	
			}
		
		return aux;
		}	
	
		

		
		
	protected void showForm(){
			
	this.setVisible(true);	
	}
		
		
		
		
	
	private void licenseOffLine(){
		
	FormDeAdicaoDeChave addKeyForm = new FormDeAdicaoDeChave	(this.registroAtual, this.util);
	addKeyForm.mostrar();
	}
	
	




	private boolean licenseOnLine(){
	
	PainelDeRegistroOnline	form  = new PainelDeRegistroOnline();
	form.setVisible(true);

	
	if(this.registroAtual.getErro()== PagueOAluguel.SUCCESS || 
			this.registroAtual.getErro()== PagueOAluguel.LICENSE_TERMINATION)
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
		backgroundPane.add( new JLabel("O sistema se conectará aos servidores da MSC Soluções e, caso haja licenças disponíveis, realizará o seu lincenciamento.",JLabel.CENTER), cons); 
		
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
		
		RegistroAtual auxRecord = new RegistroAtual();
			
		auxRecord.setId(registroAtual.getId());
		auxRecord.setCodigo_cliente( registroAtual.getCodigo_cliente());
		auxRecord.setCodigo_sistema(registroAtual.getCodigo_sistema());
				
		Servidor server = new Servidor();
			
		String keyCandidate = server.conecta(auxRecord);
		
		System.out.println(keyCandidate);
		
		auxRecord.setChave(keyCandidate);
			
			if(auxRecord.getChave() == null){
				
			JOptionPane.showMessageDialog(null, "Servidor não disponível no momento.", "ERRO", JOptionPane.ERROR_MESSAGE);							
			return;	
			}
			
			
		util.validador(auxRecord);
				
			 
			if(auxRecord.getErro()== PagueOAluguel.SUCCESS || auxRecord.getErro()== PagueOAluguel.LICENSE_TERMINATION){
						
			util.setPreferenceKey(auxRecord.getChave());	
			registroAtual.setChave(auxRecord.getChave());
			registroAtual.setErro(auxRecord.getErro());
			registroAtual.setCont_de_dias(auxRecord.getCont_de_dias());
			registroAtual.setDia_inicial(auxRecord.getDia_inicial());
			registroAtual.setMes_inicial(auxRecord.getMes_inicial());
			registroAtual.setAno_inicial(auxRecord.getAno_inicial());
			
			lb_DateInicial.setText("<html><b>Último Registro: </b>"+String.format("%02d", registroAtual.getDia_inicial())+"/"+String.format("%02d", registroAtual.getMes_inicial())+"/"+String.format("%04d", registroAtual.getAno_inicial())+"</html>");
			getDataFinal();
			
			JOptionPane.showMessageDialog(null, "Licenciamento realizado com sucesso.", "ERRO", JOptionPane.INFORMATION_MESSAGE);					
			
			return;	
			}
				
			
		JOptionPane.showMessageDialog(null, "Chave de licença inválida.", "ERRO", JOptionPane.ERROR_MESSAGE);					
		return ;	
	
		}});
		}
	
	}


}
