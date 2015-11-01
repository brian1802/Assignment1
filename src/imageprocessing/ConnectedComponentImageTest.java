package imageprocessing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.Picture;

public class ConnectedComponentImageTest {
	Picture picture;

	@Before
	public void setUp() throws Exception {
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/bacteria.bmp");
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/crosses.gif");
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/image1.jpg");
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/shapes.bmp");
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/stars.jpg");
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/STARS2.jpg");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnectedComponentImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountComponents() {
		fail("Not yet implemented");
	}

	@Test
	public void testIdentifyComonentImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testColourComponentImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPicture() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinaryComponentImage() {
		fail("Not yet implemented");
	}

}
