package imageprocessing;

import java.awt.Color;
import java.util.ArrayList;

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
	int height, width, count, N;
	private int[] id, size, parent;
	private ArrayList<Integer>roots;

	private String fileLocation;
	
	/**
	 * Initialise fields
	 * 
	 * @param fileLocation
	 */
	public ConnectedComponentImage(String fileLocation) {
		this.fileLocation = fileLocation;
		Picture picture = binaryComponentImage();
		int N = width*height;
		parent = new int[N];
		id = new int[N];
		size = new int[N];
		roots = new ArrayList<>();
		
		weightedQF(N);
		WeightedQU();
	}

	/**
	 * Is the first method that is called to when the program opens.
	 * @param args
	 */
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
	public Picture colourComponentImage() {
		/*Basically you use your union, make an arraylist 
		 * that then contains each individual root, 
		 * so it should have like the number of components 
		 * in it. Then you can just make an array of random 
		 * colors for each, see if they are equal and that 
		 * way you can color them
		 */
		return null;
	}

	public Picture getPicture() {
		picture = new Picture(fileLocation);
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
	
	/**
	* This method takes in a component picture and cross checks 
	* it to find the amount of roots and populate a roots array
	* with the values of the roots.
	 */
		
		public void findRootsArray(Picture pic){
			for (int x = 0; x < pic.width(); x++) {
				for (int y = 0; y < pic.height(); y++) {
					int currentNumber = find(y*width+x);
					if(x==0 && y==0){
						roots.add(currentNumber);
					}
					if(!roots.contains(currentNumber)){
						roots.add(currentNumber);
					}
				}
			}
	
		}
	
	/**
	 * This method applied a weighted quick union by testing 
	 * the current pixel and its neighbours to see if they are
	 * the same color. If they are the same color then they are 
	 * unioned. 
	 */
	public void WeightedQU(){
		Picture pic = binaryComponentImage();
		
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(x > 0){
					union(x-1, y);
				}else
				if((y-1) >=0){
					union(x, y-1);
				}else
				if(y!= (height-1)){
					union(x,y+1);
				}else
				if (x!= width-1 && y!= height-1){
					union(x+1,y);
			}
		}
	}
}

	/*	Picture pic = binaryComponentImage();
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Color a, b, c, d;
				if(x > 0){//North
					a = pic.get(x-1,y);
				}else{
					a = pic.get(x, y);
				}
				if((y-1) >=0){//West
					c = pic.get(x, y-1);
				}else{
					c = pic.get(x, y);
				}
				if(y!= (height-1)){//East
					d = pic.get(x, y+1);
				}else{
					d = pic.get(x, y);
				}
				if(x!= width-1 && y!= height-1){//South
					b = pic.get(x+1, y);
				}else{
					b = pic.get(x, y);
			}
		}
	}
}*/
	
	/**
	 * This method populated the id and size array. The size of the
	 * array is determined by the size of the picture. 
	 * @param N
	 */
	public void weightedQF(int N){
		id = new int[N];
		size = new int[N];
		
		for (int i = 0; N < width; N++){
			id[i] = i;
			size[i] = 1;
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
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    /**
     * This validates the array.
     * @param p
     */
    // validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
        }
    }
    
    /**
     * This method checks if the elements are already 
     * connected.
     * @param p
     * @param q
     * @return
     */
	public boolean connected(int p, int q) {
       return find(p) == find(q);
    }
	 
	/**
	 * This will return the root i that is passed through.
	 * @param i
	 * @return
	 */
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
