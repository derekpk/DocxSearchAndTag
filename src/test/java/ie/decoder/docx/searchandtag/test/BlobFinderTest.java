package ie.decoder.docx.searchandtag.test;

import static org.junit.Assert.*;
import ie.decoder.docx.searchandtag.BlobFinder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlobFinderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBlobFinder() {
		BlobFinder finder = new BlobFinder();
	
		int ret = finder.Search("/home/derek/git/DocxSearchAndTag/src/main/resources/Example.docx", "/home/derek/git/DocxSearchAndTag/src/main/resources/Example.xml");
		
		assertEquals(ret, 26);
	}
}
