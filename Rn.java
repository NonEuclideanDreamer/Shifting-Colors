
//******************************************
// Describes a point/vector in n-dimensional space
//*******************************************
public  class Rn
{
	static double exactness=0.00000001;
	public int n;
	public double[] c;//coordinates
	
	public Rn(double[]c0)
	{
		n=c0.length;
		c=c0.clone();
	}
	public double get(int i)
	{
		return c[i];
	}
	public Rn copy()
	{
		double[] xi=new double[n];
		for(int i=0;i<n;i++)
		{
			xi[i]=c[i];
		}
		return new Rn(xi);
	}
	public static Rn zero(int n)
	{
		double[]c0=new double[n];
		return new Rn(c0);
	}
	public static Rn e(int i,int n)
	{
		double[] out=new double[n];
		out[i]=1;
		return new Rn(out);
	}
	public static Rn diag(int n)
	{
		double[]out=new double[n];
		for(int i=0;i<n;i++)
		{
			out[i]=1;
		}
		return new Rn(out);
	}
	public double norm()
	{
		double x=0;
		for(int i=0;i<n;i++)
		{
			x=x+c[i]*c[i];
		}
		return Math.sqrt(x);
	}
	public Rn add(Rn summand)
	{
		double[] coord=new double[n];
		for(int i=0;i<n;i++)
		{
			coord[i]=c[i]+summand.c[i];
		}
		return new Rn(coord);
	}
	public Rn substract(Rn substrahend)
	{
		double[]coord=new double[n];
		for(int i=0;i<n;i++)
		{
			coord[i]=c[i]-substrahend.c[i];
		}
		return new Rn(coord);
	}
	public Rn times(double scalar)
	{
		double[] coord=new double[n];
		for(int i=0;i<n;i++)
		{
			coord[i]=c[i]*scalar;
		}
		return new Rn(coord);
	}

	public double dot(Rn v)
	{
		double x=0;
		for(int i=0;i<n;i++)
		{
			x=x+c[i]*v.c[i];
		}
		return x;
	}
	public Rn transform(double[][] matrix)
	{
		double[] out=new double[ matrix.length];
		//System.out.print("Transforming vector");print(vector);
		for(int i=0;i<matrix.length;i++)
		{
			out[i]=0;
			for(int j=0;j<n;j++)
			{
				out[i]=out[i]+c[j]*matrix[i][j];
			}
		
		}
		return new Rn(out);
	}
	public Matrix toMatrix()
	{
		return new Matrix(new double[][] {c});
	}
	
	public double hangle(int x, int y )//The angle of the vector projected to the ex-ey plane
	{
		 return Math.atan2(c[y],c[x]);
	}
	
	
	public double distance(Rn v)
	{
		return (substract(v)).norm();
	}
	
	//twodimensional point to canvas
	public int[] toGrid(double[] min, double[]max, int width, int height)
	{
		double scale=Math.min(width/(max[0]-min[0]), height/(max[1]-min[1]));
		int[] out=new int[2];
		out[0]=(int)((c[0]-(max[0]+min[0])/2.0)*scale+width/2.0);
		out[1]=(int)((c[1]-(max[1]+min[1])/2.0)*scale+height/2.0);
		return out;
	}
	public void print()
	{
		System.out.print("("+c[0]);
		for(int i=1;i<n;i++)
		{
			System.out.print(", "+c[i]);
		}
		System.out.print(") ");
		
	}
/*
	public boolean inFrontOf(Hyperplane p)
	{
		if(dot(p.normal)>=(p.loc).dot(p.normal))
			{System.out.print(true);return true;}
		else {System.out.print(false); return false;}
	}
	
	public boolean isIn(Polytope p)
	{
		boolean out=false;
		for(int i=0; i<p.face.length;i++)
		{
			if(!inFrontOf(p.face[i])) {System.out.print("not in polytope");return false;}
			if(p.face[i].normal.equals(Rn.zero(n))){System.out.print("degenerate case");return false;}
		}
		out=true;
		System.out.print(out);
		return out;
	}
	
	//Normal hyperplane between this point and p, looking at this point
	public Hyperplane middlenormal(Rn p)
	{
		return new Hyperplane(substract(p),add(p).times(0.5));
	}*/
	public Rn normalize() 
	{
		return times(1.0/norm());
	}
	
	public boolean equals(Rn v)
	{
		for(int i=0;i<n;i++)
			if(Math.abs(get(i)-v.get(i))>exactness)return false;
		
		return true;
	}
	
	/*public int[]=nextVertex(Lattice l)
	{

	}*/
	
	
}
