/**
 * 	Copyright 2015 Derek Keogh
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 */
package ie.docoder.docx.searchandtag;

public class Blob {
	private int startIndex;
	private int endIndex;
	private String blob;
	private String title;
	private String sequenceId;

	public Blob() {
		init();
	}

	private void init() {
		setStartIndex(-1);
		setEndIndex(-1);
		setBlob(null);
		setTitle(null);
		setSequenceId(null);
	}
	
	void reset() {
		init();
	}

	public String getBlob() {
		return blob;
	}

	public void setBlob(String blob) {
		this.blob = blob;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public boolean isFound() {
		if(startIndex > -1 && endIndex > -1) {
			return true;
		}
		return false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

}
