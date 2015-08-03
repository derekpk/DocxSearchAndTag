/**
 * 	Copyright 2015 Derek Keogh
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 */


package ie.docoder.docx.searchandtag;

import java.util.Map.Entry;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.docx4j.wml.P;

import ie.docoder.docx.searchandtag.Sequences.Sequence.Piece.Bit;

public class BlobFinder {
	
	boolean SHOW_DEBUG = false; //simple true of false to show or not show the dev message, this will be deleted for v1.0
	
	private String haystack = null;
	private Unmarshall unmar = null;
	private Sequences sequences = null;
	private int currentCursor = 0;
	private WordprocessingMLPackage wordMLPackage =	null;
	private MainDocumentPart documentPart = null;
	private String docxFilename = null;

	Doc doc = null;

	static final char COMMA 			= ',';
	static final char FULL_STOP 		= '.';
	static final char SPACE 			= ' ';
	static final char CLOSE_TAG_SLASH 	= '/';
	static final char CLOSE_TAG 		= '>';
	static final char OPEN_TAG 			= '<';
	static final char BOLD 				= 'B';
	static final char ITALIC 			= 'I';
	static final char OPEN_BRACKET 		= '(';
	static final char CLOSE_BRACKET 	= ')';
	static final char OPEN_QUOTE 		= '"';
	static final char CLOSE_QUOTE		= '"';
	static final char COLON				= ':';
	static final char SEMI_COLON		= ';';
	
	static//This is just to get over a compile problem for jaxb
	{
	    Logger rootLogger = Logger.getRootLogger();
	    rootLogger.setLevel(Level.ERROR);
	    rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%-6r [%p] %c - %m%n")));
	}

	BlobFinder() {
		this(false);
	}
	
	BlobFinder(boolean showDebug) {
		SHOW_DEBUG = showDebug;
	}
	
	void BlobSetup(final String docxFile, final String xmlFile) {
		try {
			setCurrentCursor(0);
			docxFilename = docxFile;
			haystack = null;
			
			wordMLPackage =	Docx4J.load(new java.io.File(docxFile));
			documentPart = wordMLPackage.getMainDocumentPart();
			
			doc = new Doc(docxFile);
			if(doc != null) {
				doc.docxFile = XmlUtils.marshaltoString(documentPart.getContents(), true, true);
			}
			unmar = new Unmarshall(xmlFile);
			sequences = unmar.UnmarshallTheDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Just a little helper
	 * @param message
	 */
	public void ShowMessage(String message) {
		if(SHOW_DEBUG) {
			System.out.println(message);
		}
	}

	/**
	 * 
	 */
	void DisplayTheDocumentWithTags() {
		if(doc.paras.size() > 0) {
			for (int i = 0; i < doc.paras.size(); i++) {
				System.out.println(doc.paras.get(i).getParaTextWithTags());
			}
		}
	}

	/**
	 * Loop through all the paras and display the text before search if param
	 * is true and after search if the param is false.
	 * @param before
	 */
	void DisplayTheDocument() {
		if(doc.paras.size() > 0) {
			for (int i = 0; i < doc.paras.size(); i++) {
				System.out.println(doc.paras.get(i).getParaText());
			}
		}
	}
	
	/**
	 * 
	 * @param docxFile, file that will be searched
	 * @param xmlFile, file containing the searches 
	 */
	void Search(final String docxFile, final String xmlFile) {
		Search(docxFile, xmlFile, null);
	}
	/**
	 * 
	 * @param docxFile, file that will be searched
	 * @param xmlFile, file containing the searches 
	 * @param type
	 */
	void Search(final String docxFile, final String xmlFile, String type) {
		try {
			
			BlobSetup(docxFile, xmlFile);
			
			Document docX4J = (Document)documentPart.getContents();
			for(Object o : docX4J.getContent()) {//Loop through the paragraphs one at time
	
				if(o instanceof P) {
					String val = (String)o.toString();
					if(val.length() > 0) {
						
						ShowMessage("\n\n------------------------ SEARCH THE FOLLWING TEXT ------------------------");
						ShowMessage("******************************************************************************");
						ShowMessage("PARAGRAPH : " + val);
						ShowMessage("------------------ SEARCHING ------------------");

						setHaystack(val);
						doc.paras.add(new Para(val));	
						SequenceSearcher(type, doc.paras.size());
						
						ShowMessage("******************************************************************************");
					}
				}
			}
			ApplyTheTags();
			ShowMessage("\n\nDOCX Search Complete for FILE: " + docxFilename);
		} catch (Docx4JException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * When the search is complete, go through the paras and add the tags
	 */
	private void ApplyTheTags() {
		if(doc.paras.size() > 0) {
			for (int i = 0; i < doc.paras.size(); i++) {
				
				StringBuilder paraBuilder = new StringBuilder(doc.paras.get(i).getParaText());
				
				ShowMessage("\tPARA TEXT: " + paraBuilder.toString());
				
				for (int j = 0; j < doc.paras.get(i).getBlobs().size(); j++) {
					String title = doc.paras.get(i).getBlobs().get(j).getTitle();
					int start = doc.paras.get(i).getBlobs().get(j).getStartIndex();
					int end = doc.paras.get(i).getBlobs().get(j).getEndIndex();
					
					ShowMessage("\t\tBLOB name : " + title + ", cursor position start : " + start + ", end : " + end + "\n");
					doc.paras.get(i).positionMap.put(start, "<" + title + ">");
					doc.paras.get(i).positionMap.put(end, "</" + title + ">");
				}
				
				for (Entry<Integer, String> entry : doc.paras.get(i).positionMap.entrySet()) {
					ShowMessage("\t\tTYPE: " + entry.getValue() + " , INDEX: " + entry.getKey());
					paraBuilder.insert(entry.getKey(), entry.getValue());
				}
				doc.paras.get(i).setParaTextWithTags(paraBuilder.toString());
			}
		}
	}
	
	/**
	 * This will compare the passed in blob with the other blobs in the para at paraIndex
	 * @param blob
	 * @return true if no matching blob is found, otherwise false 
	 */
	private boolean blobIsUnique(Blob blob, final int paraIndex) {
		if(doc.paras.size() > 0) {
				
			for (int j = 0; j < doc.paras.get(paraIndex-1).getBlobs().size(); j++) {
				
				int curStart = doc.paras.get(paraIndex-1).getBlobs().get(j).getStartIndex();
				int curEnd = doc.paras.get(paraIndex-1).getBlobs().get(j).getEndIndex();
				String curTitle = doc.paras.get(paraIndex-1).getBlobs().get(j).getTitle();
				String curBlob = doc.paras.get(paraIndex-1).getBlobs().get(j).getBlob();

				int newStart = blob.getStartIndex();
				int newEnd = blob.getEndIndex();
				String newTitle = blob.getTitle();
				String newBlob = blob.getBlob();
				
				if(newStart == curStart && newEnd == curEnd && newTitle.equals(curTitle) && newBlob.equals(curBlob)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This starts the ball rolling, it will loop through the sequences.
	 * If the passed in type is null, search all of the sequences else only search
	 * for sequences that math the passed in type
	 * 
	 * @param type
	 */
	private void SequenceSearcher(final String type, final int paraIndex) {
		
		for(Sequences.Sequence seq: getSequences().getSequence()) {//Loop through sequences

			if(type != null) {
				if(seq.getName().equals(type)) {
					ProcessPara(type, seq, paraIndex);
				}
			}
			else {
				ProcessPara(seq.getName(), seq, paraIndex);
			}
		}			
	}

	/**
	 * 
	 * @param blob
	 */
	private void AddABlob(Blob blob, final int paraIndex) {
		Para para = null;
		
		if(blob.isFound()) {
			if(blobIsUnique(blob, paraIndex)) {
				
				if(doc.paras.size() > 0) {
					if(doc.paras.get(paraIndex-1).getParaText().equals(getHaystack())) {
						para = doc.paras.get(paraIndex-1);
						doc.paras.get(paraIndex-1).getBlobs().add(blob);
					} else {
						para = new Para(getHaystack());
						para.getBlobs().add(blob);
						doc.paras.add(para);
					}
				} else {
					para = new Para(getHaystack());
					para.getBlobs().add(blob);
					doc.paras.add(para);	
				}
			}
		} else {
			setCurrentCursor(0);
		}
	}

	/**
	 * Search the text and look for the blobs that match the sequence, if a match if found it's added to the para,
	 * and then added to the document
	 * @param type
	 * @param seq
	 * @return 
	 */
	private void ProcessPara(final String type, final Sequences.Sequence seq, final int paraIndex) {
		
		SequenceActionType seqAction = seq.getAction();
		if(seqAction == SequenceActionType.SINGLE) {
			Blob blob = new Blob();
			blob = FindABlob(type, seq);
			AddABlob(blob, paraIndex);
		} else if(seqAction == SequenceActionType.CONTINUOUS) {
			boolean keepSearching = true;

			while(keepSearching) {
				Blob blob = new Blob();
				blob = FindABlob(type, seq);
				AddABlob(blob, paraIndex);
				if(blob.isFound() == true) {
					setCurrentCursor(blob.getEndIndex());
				} else {
					keepSearching = false;
				}
			}
		}
	}
	
	public String getHaystack() {
		return haystack;
	}

	public void setHaystack(String haystack) {
		this.haystack = haystack;
	}
	
	private  int skipToMultiChar(int cursor, ie.docoder.docx.searchandtag.Sequences.Sequence.Piece.Bit bit) {
		for(int i = cursor; i < haystack.length(); i++) {
			
			for(Sequences.Sequence.Piece.Bit.Multi part: bit.getMulti()) {
				
				if(IsCharMatch(i, part.getType(), bit) == true) {
					return i;
				}
			}
		}
		return -1;
	}

	private  int skipPastExact(int cursor, ie.docoder.docx.searchandtag.Sequences.Sequence.Piece.Bit bit, boolean skipPast) {
		int ret = haystack.indexOf(bit.getExact().getValue(), cursor);
		if(ret > -1) {
			if(skipPast)
				return ret + bit.getExact().getValue().length() - 1;
			else
				return ret;
		}
		
		return -1;
	}
	
	private  boolean IsMultiCharMatch(int cursor, ie.docoder.docx.searchandtag.Sequences.Sequence.Piece.Bit bit) {
		
		for(Sequences.Sequence.Piece.Bit.Multi part: bit.getMulti()) {
			if(IsCharMatch(cursor, part.getType(), bit) == true) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param cursor
	 * @param type
	 * @return
	 */
	private  int SkipTo(int cursor, ie.docoder.docx.searchandtag.Sequences.Sequence.Piece.Bit bit) {
		if(cursor < 0) {
			return -1;
		}
		switch (bit.getType()) {
			case CAP_ALPHA:
				for(int i = cursor; i < haystack.length(); i++) {
					if(Character.isUpperCase(haystack.charAt(i))) {
						return i;
					}
				}
			case LOW_ALPHA:
				for(int i = cursor; i < haystack.length(); i++) {
					if(Character.isLowerCase(haystack.charAt(i))) {
						return i;
					}
				}
			case ALPHA:
				for(int i = cursor; i < haystack.length(); i++) {
					if(Character.isAlphabetic(haystack.charAt(i))) {
						return i;
					}
				}
			case NUMERIC:
				for(int i = cursor; i < haystack.length(); i++) {
					if(Character.isDigit(haystack.charAt(i))) {
						return i;
					}
				}
			case FULL_STOP:
				return haystack.indexOf(BlobFinder.FULL_STOP, cursor);
			case COMMA:
				return haystack.indexOf(BlobFinder.COMMA, cursor);
			case SPACE:
				return haystack.indexOf(BlobFinder.SPACE, cursor);
			case CLOSE_TAG_SLASH:
				return haystack.indexOf(BlobFinder.CLOSE_TAG_SLASH, cursor);
			case CLOSE_TAG:
				return haystack.indexOf(BlobFinder.CLOSE_TAG, cursor);
			case OPEN_TAG:
				return haystack.indexOf(BlobFinder.OPEN_TAG, cursor);
			case BOLD:
				return haystack.indexOf(BlobFinder.BOLD, cursor);
			case ITALIC:
				return haystack.indexOf(BlobFinder.ITALIC, cursor);
			case OPEN_BRACKET:
				return haystack.indexOf(BlobFinder.OPEN_BRACKET, cursor);
			case CLOSE_BRACKET:
				return haystack.indexOf(BlobFinder.CLOSE_BRACKET, cursor);
			case COLON:
				return haystack.indexOf(BlobFinder.COLON, cursor);
			case SEMI_COLON:
				return haystack.indexOf(BlobFinder.SEMI_COLON, cursor);
			case LAST_CHAR:
				return haystack.length() - 1;
			case MULTI:
				return skipToMultiChar(cursor, bit);
			//case EXACT:
				//return skipPastExact(cursor, bit, false);
			case ANY:
				break;
			default:
				break;	
		}
		
		return -1;
	}

	/**
	 * 
	 * @param cursor
	 * @param type
	 * @return
	 */
	private  boolean IsCharMatch(int cursor, BitType type, Bit bit) {
		if(cursor > haystack.length() || cursor < 0) {
			return false;
		}
		
		char curChar = haystack.charAt(cursor);
		switch (type) {
			case CAP_ALPHA: 		return Character.isUpperCase(curChar);
			case LOW_ALPHA: 		return Character.isLowerCase(curChar);
			case ALPHA:				return Character.isAlphabetic(curChar);
			case NUMERIC:			return Character.isDigit(curChar);
			case FULL_STOP:			return curChar == BlobFinder.FULL_STOP ? true : false;
			case COMMA:				return curChar == BlobFinder.COMMA ? true : false;
			case SPACE:				return curChar == BlobFinder.SPACE ? true : false;
			case CLOSE_TAG_SLASH:	return curChar == BlobFinder.CLOSE_TAG_SLASH ? true : false;
			case CLOSE_TAG:			return curChar == BlobFinder.CLOSE_TAG ? true : false;
			case OPEN_TAG:			return curChar == BlobFinder.OPEN_TAG ? true : false;
			case BOLD:				return curChar == BlobFinder.BOLD ? true : false;
			case ITALIC:			return curChar == BlobFinder.ITALIC ? true : false;
			case OPEN_BRACKET:		return curChar == BlobFinder.OPEN_BRACKET ? true : false;
			case CLOSE_BRACKET:		return curChar == BlobFinder.CLOSE_BRACKET ? true : false;
			case OPEN_QUOTE:		return curChar == BlobFinder.OPEN_QUOTE ? true : false;
			case CLOSE_QUOTE:		return curChar == BlobFinder.CLOSE_QUOTE ? true : false;
			case COLON:				return curChar == BlobFinder.COLON ? true : false;
			case SEMI_COLON:		return curChar == BlobFinder.SEMI_COLON ? true : false;
			case LAST_CHAR:			return cursor == haystack.length() - 1 ? true : false;
			case ANY:		 		return true;
			case MULTI:				return IsMultiCharMatch(cursor, bit);
			case EXACT:				return curChar == bit.getExact().getValue().charAt(bit.getExact().getValue().length() - 1);
			default:
				break;
			
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param title
	 * @param seq
	 * @return
	 */
	private  Blob FindABlob(final String name, Sequences.Sequence seq) {
		Blob blob = new Blob();
		
		if(getCurrentCursor() < 0) {
			return blob;
		}
		int localCursor = getCurrentCursor();
		ShowMessage("SEQUENCE ACTION: " + seq.getAction() + ", ID : " + seq.getId() + " NAME : " + seq.getName() + ", passed in name : " + name);
		int matchCount = 0;
		try {
			if(seq.getName().equals(name)) {
				int bitCounter = 0;

				for(Sequences.Sequence.Piece piece: seq.getPiece()) {//Loop through pieces
					
					int bitsCount = piece.getBit().size();

					for(Sequences.Sequence.Piece.Bit bit: piece.getBit()) {//Loop through bits
						if(localCursor >= haystack.length()) {
							//System.out.println("Too long");
							break;
						}
			
						if(bit.getAction() == ActionType.EXACT) {
							localCursor = skipPastExact(getCurrentCursor(), bit, true);	
						}
						if(bit.getAction() == ActionType.SKIP) {
							localCursor = SkipTo(localCursor, bit);	
						}
						
						if(IsCharMatch(localCursor, bit.getType(), bit)) {
							matchCount++;
							if(bitCounter == 0) {
								blob.setStartIndex(localCursor);
							}
							if(matchCount == bitsCount) {
								localCursor++;
								break;
							}

						} else {
							//System.out.println("************** NO MATCH ******************");
							setCurrentCursor(++localCursor);
							blob.setStartIndex(-1);
							return blob;
						}
						localCursor++;
						bitCounter++;
					}//end of for bits loop
					if(matchCount != bitsCount) { // No match unless all bits are found
						//System.out.println("matchCount " + matchCount + " != " + bitsCount + " bitsCount");
						setCurrentCursor(++localCursor);
						blob.setStartIndex(-1);
						return blob;
					}

				}//end of for pieces loop
			} else {
				return blob;
			}
			if(blob.getStartIndex() > -1) {
				blob.setEndIndex(localCursor);
			}
		} catch (StringIndexOutOfBoundsException e) {
			
			e.printStackTrace();
		}
		if(blob.getStartIndex() > -1 && blob.getEndIndex() <= haystack.length()) {
			blob.setBlob(haystack.substring(blob.getStartIndex(), blob.getEndIndex()));
			blob.setTitle(name.toString());
			blob.setSequenceId(seq.getId());
		}
		return blob;
	}

	public void DisplayTheSequence() {
		
		try {
			System.out.println("The sequence has " + sequences.getSequence().get(0).piece.size() + " piece's\n");

			for(Sequences.Sequence seq: sequences.getSequence()) {
				for(Sequences.Sequence.Piece piece: seq.getPiece()) {
					System.out.println("This piece is made up of " + piece.getBit().size() + " bit's, it can appear up to " + piece.getRecurrence() + " times consecutively");
					for(Sequences.Sequence.Piece.Bit bit: piece.getBit()) {
						System.out.println("\tThis bit should have 1 of this type " + bit.getType());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Sequences getSequences() {
		return sequences;
	}

	public void setSequences(Sequences sequences) {
		this.sequences = sequences;
	}

	public int getCurrentCursor() {
		return this.currentCursor;
	}

	public void setCurrentCursor(int currentCursor) {
		this.currentCursor = currentCursor;
	}

}
