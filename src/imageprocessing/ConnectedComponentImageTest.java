package imageprocessing;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.Picture;

public class ConnectedComponentImageTest {
	Picture picture;
	int height;
	int width;
	int count;
	private int[] id;
	private int[] size;
	int N;

	@Before
	public void setUp() throws Exception {
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/crosses.gif");
		int N = width*height;
		id = new int[N];
		size = new int[N];
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testConnectedComponentImage() {
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/bacteria.bmp");
	}

	@Test
	public void testCountComponents() {
		//assertSame(10, ConnectedComponentImage.countComponents());
	}

	@Test
	public void testIdentifyComonentImage() {
		//fail("Not yet implemented");
	}

	@Test
	public void testColourComponentImage() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPicture() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBinaryComponentImage() {
		//fail("Not yet implemented");
	}

	public void WeightedQuickUF(){
		
	}
}
