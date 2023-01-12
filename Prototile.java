import java.awt.Color;
import java.awt.image.BufferedImage;

public class Prototile 
{
	Rn[] corners;

	public Prototile(Rn[]crn)
	{
		corners=new Rn[crn.length];
		for(int i=0;i<crn.length;i++)
		{
			corners[i]=crn[i].copy();
		}
	}
	public Rn getCorner(int i)
	{
		return corners[i];
	}
	/*public boolean equals(Prototile t)
	{
		if(corners.length!=t.corners.length)
			return false;
		for(int i=0;i<corners.length;i++)
		{
			if(!corners[i].equals(t.corners[i]))
				return false;
		}
		return true;
	}*/
	public double[] min()
	{
		double x=0,y=0;
		for (int i=0;i<corners.length;i++)
		{
			x=Math.min(corners[i].get(0), x);
			y=Math.min(corners[i].get(1), y);
			
		}
		return new double[] {x,y};
	}
	public double[] max()
	{
		double x=0,y=0;
		for (int i=0;i<corners.length;i++)
		{
			x=Math.max(corners[i].get(0), x);
			y=Math.max(corners[i].get(1), y);
			
		}
		return new double[] {x,y};
	}
	public int size(BufferedImage image,double[]min, double[]max)
	{
		int out=0;
		Tile tile=new Tile(this,1);
		tile.draw(image, new int[][] {{0, 0, 0},{255,0,0}},min,max,1);
		int red=Color.red.getRGB();
		for(int i=0;i<image.getWidth();i++)
		{
			for(int j=0;j<image.getHeight();j++)
			{
				if(image.getRGB(i, j)==red)
					out++;
			}
		}
		return out;
	}
	public void print()
	{
		System.out.print("{");
		for(int i=0;i<corners.length;i++) {corners[i].print();System.out.print(",");}
		System.out.println("}");
		
	}
}
