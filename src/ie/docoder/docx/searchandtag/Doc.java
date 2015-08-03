/**
 * 	Copyright 2015 Derek Keogh
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 */
package ie.docoder.docx.searchandtag;

import java.util.Vector;

public class Doc {
	
	public Vector<Para> paras = new Vector<Para>();
	public String docxFile;
	
	public Doc(String docx) {
		this.docxFile = docx;
	}
}
