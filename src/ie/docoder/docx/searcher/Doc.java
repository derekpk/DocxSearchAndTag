package ie.docoder.docx.searcher;

import java.util.Vector;

public class Doc {
	
	public Vector<Para> paras = new Vector<Para>();
	public String docxFile;
	
	public Doc(String docx) {
		this.docxFile = docx;
	}
}
