package pagueOaluguel;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Link implements ClipboardOwner {

	
	
	
	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	
	}

	

	public void setClipboardContents(String aString){
	    
	StringSelection stringSelection = new StringSelection(aString);
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	clipboard.setContents(stringSelection, this);
	}

	
	
	
	public String getClipboardContents() {
	    
	String result = "";
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	Transferable contents = clipboard.getContents(null);
	boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
	   
		if (hasTransferableText){
		
			try { result = (String)contents.getTransferData(DataFlavor.stringFlavor);}
			catch (UnsupportedFlavorException ex){ex.printStackTrace();}
			catch (IOException ex){ex.printStackTrace();}
	
	    }
	    return result;
	}



} 
