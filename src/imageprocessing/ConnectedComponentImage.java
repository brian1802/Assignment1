package imageprocessing;

import java.awt.Color;

import edu.princeton.cs.introcs.Picture;



/*************************************************************************
 * Compilation: javac ConnectedComponentImage.java
 * 
 * The <tt>ConnectedComponentImage</tt> class
 * <p>
 * You do the rest....
 * 
 * @author 
 *************************************************************************/


public class ConnectedComponentImage {

	Picture picture;
	int height;
	int width;
	int count;
	private int[] id;
	private int[] size;
	private int[] root;
	int N;
	
	/**
	 * Initialise fields
	 * 
	 * @param fileLocation
	 */
	public ConnectedComponentImage(String fileLocation) {
		picture = new Picture(fileLocation);
		int N = width*height;
		id = new int[N];
		size = new int[N];
		root = new int[N];
		binaryComponentImage();
		countComponents();
		identifyComonentImage();
		colourComponentImage();
		WeightedQuickUF();
	}

	public static void main (String args[]){
		ConnectedComponentImage app = new ConnectedComponentImage("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/crosses.gif");
	}
	
	/**
	 * Returns the number of components identified in the image.
	 * 
	 * @return the number of components (between 1 and N)
	 */
	public int countComponents() {
		return count;
	}

	/**
	 * Returns the original image with each object bounded by a red box.
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture identifyComonentImage() {

		Picture pic = getPicture();

		int maxX = 0;
		int minX = pic.width();
		int maxY = 0;
		int minY = pic.height();

		for (int x = 0; x < pic.width(); x++) {
			for (int y = 0; y < pic.height(); y++) {
				if (!pic.get(x, y).equals(Color.WHITE) ) {

					if (x < minX)
						minX = x;
					if (x > maxX)
						maxX = x;
					if (y < minY)
						minY = y;
					if (y > maxY)
						maxY = y;

				}
			}

		}

		if (minX > maxX || minY > maxY) {
			System.out.println("It's All White Pixels!!!");
		} else {
			for (int x = minX; x <= maxX; x++) {
				pic.set(x, minY, Color.RED);
				pic.set(x, maxY, Color.RED);
			}

			for (int y = minY; y <= maxY; y++) {
				pic.set(minX, y, Color.RED);
				pic.set(maxX, y, Color.RED);
			}
		}
		return picture;
	}


	/**
	 * Returns a picture with each object updated to a random colour.
	 * 
	 * @return a picture object with all components coloured.
	 */
	/*Basically you use your union, make an arraylist 
	 * that then contains each individual root, 
	 * so it should have like the number of components 
	 * in it. Then you can just make an array of random 
	 * colors for each, see if they are equal and that 
	 * way you can color them
	 */
	public Picture colourComponentImage() {
		Picture pic = getPicture();
		while (root.length > 0){
			countComponents();
		}
		return null;

	}

	public Picture getPicture() {
		picture = new Picture("C:/Users/Brian/Workspace/ConnectorStarter(1)/images/crosses.gif");
		return picture;
	}

	/**
	 * Returns a binarised version of the original image
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture binaryComponentImage() {

		Picture picture = getPicture();
		int width = picture.width();
		int height = picture.height();
		double thresholdPixelValue = 128.0;
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Color c = picture.get(x, y);
				if(Luminance.lum(c) < thresholdPixelValue)
				{
					picture.set(x, y, Color.BLACK);
				}
				else
				{
					picture.set(x, y, Color.WHITE);
				}
			}
		}
		return identifyComonentImage();
	}
	
	public void WeightedQuickUF(){
		Picture pic = binaryComponentImage();
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Color a, b, c, d;
				if(x > 0){
					a = pic.get(x-1,y);
				}else{
					a = pic.get(x, y);
				}
				if(x > 0){
					b = pic.get(x+1, y);
				}else{
					b = pic.get(x, y);
				}
				if(y!= (height-1)){
					c = pic.get(x, y-1);
				}else{
					c = pic.get(x, y);
				}
				if(y!= (height+1)){
					d = pic.get(x, y+1);
				}else{
					d = pic.get(x, y);
				}
			}
		}
	}
	
	 /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
     */
	 public int find(int p) {
		 	validate(p);
	        while (p != id[p])
	            p = id[p];
	        return p;
	    }
	 
	 private void validate(int p) {
	        int N = id.length;
	        if (p < 0 || p >= N) {
	            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
	        }
	    }
	 
	  /**
	     * Returns true if the the two sites are in the same component.
	     *
	     * @param  p the integer representing one site
	     * @param  q the integer representing the other site
	     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
	     *         <tt>false</tt> otherwise
	     * @throws IndexOutOfBoundsException unless
	     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
	     */
	public boolean connected(int p, int q) {
       return find(p) == find(q);
    }
	 
    public int root(int i) 
    { 
     while (i != id[i]) 
    { 
     id[i] = id[id[i]]; 
     i = id[i]; }return i; 
    }

	
	 /**
     * Merges the component containing site p with the 
     * the component containing site q.
     * @return 
     */
    /**
     * Merges the component containing site <tt>p</tt> with the 
     * the component containing site <tt>q</tt>.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IndexOutOfBoundsException unless
     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            id[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            id[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
