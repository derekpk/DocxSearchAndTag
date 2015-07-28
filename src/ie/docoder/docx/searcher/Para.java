package ie.docoder.docx.searcher;

import java.util.Collections;
import java.util.TreeMap;
import java.util.Vector;

public class Para {

	private Vector<Blob> blobs = new Vector<Blob>();
	private String paraText;
	private String paraTextWithTags;
	TreeMap<Integer, String> positionMap = new TreeMap<Integer, String>(Collections.reverseOrder());

	public Para(String para) {
		this(para, null);
	}

	public Para(String para, String paraWithTags) {
		this.paraText = para;
		paraTextWithTags = paraWithTags;
	}
	
	public String getParaText() {
		return paraText;
	}

	public void setParaText(String paraText) {
		this.paraText = paraText;
	}

	public String getParaTextWithTags() {
		return paraTextWithTags;
	}

	public void setParaTextWithTags(String paraTextWithTags) {
		this.paraTextWithTags = paraTextWithTags;
	}

	public TreeMap<Integer, String> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(TreeMap<Integer, String> positionMap) {
		this.positionMap = positionMap;
	}

	public Vector<Blob> getBlobs() {
		return blobs;
	}

	public void setBlobs(Vector<Blob> blobs) {
		this.blobs = blobs;
	}
}
