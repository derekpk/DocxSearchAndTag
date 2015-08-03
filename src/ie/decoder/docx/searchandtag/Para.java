/**
 * 	Copyright 2015 Derek Keogh
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ie.decoder.docx.searchandtag;

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
