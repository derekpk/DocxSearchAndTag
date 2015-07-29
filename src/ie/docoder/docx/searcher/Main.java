package ie.docoder.docx.searcher;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

	  private void showOpenFileDialog() {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
	        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
	        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
	        fileChooser.setAcceptAllFileFilterUsed(true);
	        int result = fileChooser.showSaveDialog(fileChooser);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
	        }
	    }
	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Docx files", "docx");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser);
		String docxFilename = null;
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	docxFilename = chooser.getSelectedFile().getPath();
	    	System.out.println("You chose to search this file: " + docxFilename);
	    }
		filter = new FileNameExtensionFilter("Sequence Definition files", "xml");
	    chooser.setFileFilter(filter);
	    returnVal = chooser.showOpenDialog(chooser);
		String xmlFilename = null;
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	xmlFilename = chooser.getSelectedFile().getPath();
	    	System.out.println("With this sequence file: " + xmlFilename);
	    }

	    BlobFinder finder = new BlobFinder(false);
		finder.ShowMessage("DOCX Searcher");
	
		finder.Search(docxFilename, xmlFilename, null);
		System.out.println("\n----------------------------- The document before -----------------------------\n");
		finder.DisplayTheDocument();
		System.out.println("\n----------------------------------- and after -----------------------------------\n");
		finder.DisplayTheDocumentWithTags();
		System.out.println("\n---------------------------------------- FIN.. -----------------------------------");
		
	}
}
