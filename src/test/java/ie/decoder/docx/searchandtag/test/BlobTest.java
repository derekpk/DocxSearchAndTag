package ie.decoder.docx.searchandtag.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ie.decoder.docx.searchandtag.Blob;

public class BlobTest extends Blob {

	@Test
	public void testBlob() {
		Blob blob = new Blob();
		assertEquals(null, blob.getTitle());
	}

	@Test
	public void testReset() {
		Blob blob = new Blob();
		assertEquals(null, blob.getTitle());
		blob.setTitle("title");
		assertEquals("title", blob.getTitle());
		blob.reset();
		assertEquals(null, blob.getTitle());
	}

}
