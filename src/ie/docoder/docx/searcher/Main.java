package ie.docoder.docx.searcher;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

	public static void main(String[] args) {

		String docxFilename = args[0];
		String xmlFilename = args[1];
		for (String s: args) {
			System.out.println(s);
	    }
		
//		JFileChooser chooser = new JFileChooser();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Docx files", "docx");
//	    chooser.setFileFilter(filter);
//	    int returnVal = chooser.showOpenDialog(chooser);
//	    if(returnVal == JFileChooser.APPROVE_OPTION) {
//	       System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
//	    }
		BlobFinder finder = new BlobFinder(true);
//		finder.ShowMessage(docxFilename);		
		finder.ShowMessage("DOCX Searcher");
	
		finder.Search(docxFilename, xmlFilename, null);
		System.out.println("\n----------------------------- The document before -----------------------------\n");
		finder.DisplayTheDocument();
		System.out.println("\n----------------------------------- and after -----------------------------------\n");
		finder.DisplayTheDocumentWithTags();
		System.out.println("\n---------------------------------------- FIN.. -----------------------------------");
		
	}
}
