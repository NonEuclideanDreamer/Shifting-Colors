import java.util.ArrayList;

public class Tesselation implements Comparable<Tesselation>
{
	static Group colorgroup;
	static int c=EvolveColor.colors.length;//number of colors
	static ArrayList<int[]>incompleteColors=new ArrayList<int[]>(),colorPermutations=colorPerm(c);
	static int[][] ordernelements;
	static int dim=Tile.dim;
	static int[][] rotimage,mirimage;
	static Prototile[] tile;
	static Tile[][]instr;
	static int[][]rule;static double[][]nullangle;//rule just contains the prototileinfo of instr, nullangle is the rotangle of the subtile with instr 0
	static int[] rot,length, mir;
	
	int[] x,y,z;//color,mirror,rot
		
	
	public Tesselation(String x0,String y0, String z0)
	{
		int l=x0.length();
		x=new int[l];y=new int[l];z=new int[l];
		
		for(int i=0;i<l;i++)
		{
			x[i]=Integer.parseInt(x0.substring(i,i+1));
			y[i]=Integer.parseInt(y0.substring(i,i+1));
			z[i]=Integer.parseInt(z0.substring(i,i+1));
		}
	}
	public Tesselation(int[] x0,int[] y0, int[] z0)
	{
		x=x0;y=y0;z=z0;
	}
	public static void setC(int c0)
	{
		c=c0;
		colorPermutations=colorPerm(c);
	}
	private static ArrayList<int[]> colorPerm(int c2)
	{
		System.out.println(c2);
		ArrayList<int[]>out=new ArrayList<int[]>();
		for(int f=1;f<c2;f++)
		{
			int[]perm=new int[c2];
			for(int i=0;i<c2;i++)
			{
				perm[i]=(i*f)%c2;
			}
			if(onetoone(perm))
			{
				out.add(perm);
				
			for(int s=1;s<c2;s++)
			{
				int[]perm2=new int[c2];
				for(int i=0;i<c2;i++)
				perm2[i]=(perm[i]+s)%c2;
				out.add(perm2);
			}
			}
			else incompleteColors.add(perm);
		}
		
		
			
		
		return out;
	}
	private static boolean onetoone(int[] perm) 
	{
		for(int i=1;i<perm.length;i++)
			for(int j=0;j<i;j++)
				if(perm[i]==perm[j])return false;
		
		return true;
	}

	public int[][]orbit(int prototile, int subtile)//rotated and mirrored images of any subtile
	{	int r=rot[rule[prototile][subtile]];
		int[][]out=new int[r][2];
		int ro=subtile,m=mirimage[prototile][subtile];
		for(int i=0;i<r;i++)
		{
			out[i][0]=ro;
			out[i][1]=m;
			ro=rotimage[prototile][subtile];
			m=rotimage[prototile][subtile];
		}
		return out;
	}
	public int coloringnr(int[]per)//encodes the coloring instr for a suggested colorpermutation
	{	int out=0;
		for(int i=0;i<x.length;i++)
		{
			out*=c;
			out+=per[x[i]];
		}
		return out;	
	}
	public Tesselation representant(boolean colormax)//if colormax==true we return null for Tesselations not using their full colorspace
	{ 	int minimalvalue=(int)Math.pow(c,x.length),value;int[]minper= {-1};
		for(int[] per:colorPermutations)
		{
			value=coloringnr(per);
			if(value<minimalvalue)
			{
				minimalvalue=value;minper=per.clone();
			}
		}
		//if(degenerate(minper))return null;
		int[] newx=new int[x.length];
		for(int i=0;i<x.length;i++)
		{
			newx[i]=minper[x[i]];
		}
		return new Tesselation(newx,y,z);
		//the 3 entries are: rotationnr, 1 if mirrored, for which prototile
		/*ArrayList<int[]>minimalImages=new ArrayList<int[]>(3);
		long mincnr=(long) Math.pow(c, x.length());
		Tesselation t=this.copy();
		for(int i=0;i<tile.length;i++)
		{
			
		}*/
	}

	

	private boolean degenerate(int[] minper) 
	{
		for(int[] per:incompleteColors)
		{
			boolean fits=true;
			for(int i=0;i<x.length;i++)
			{
				boolean in=false;
				for(int j=0;j<per.length;j++)
					if(minper[x[i]]==per[j])in=true;
				if(!in)fits=false;
			}
			if(fits)return true;
		}
		return false;
	}

	public Tesselation copy()
	{
		return new Tesselation(x,y,z);
	}
	
	public boolean equals(Tesselation t)
	{
		if(!x.equals(t.x))return false;
		if(!y.equals(t.y))return false;
		if(!z.equals(t.z))return false;
		return true;
	}
	
	public Tesselation colorflip()
	{
		int[] newx=new int[z.length];
		for(int i=0;i<z.length;i++)
			newx[i]=(c-1-z[i]);
		return new Tesselation(newx,y,z);
	}
	public Tesselation coloradd(int n)
	{
		int[] newx=new int[x.length];
		for(int i=0;i<x.length;i++)
			newx[i]=(x[i]+n)%c;
		return new Tesselation(newx,y,z);
	}
	
	//Rotate one if the prototiles by -one(!) n specifies which one
	public Tesselation rotate(int n)
	{
		String newx="",newy="",newz="";
		int past=0;
		for(int i=0;i<n;i++)past+=length[i];
		for(int i=0;i<length[n];i++)
		{
			int index=(rotimage[n][i]+past);
			newx=newx+index;
			newy=newy+index;
			newz=newz+index;
		}
		return new Tesselation(newx,newy,newz);
	}
	
	public Tesselation mirror(int n)//consider what this does to the instructions of the OTHER prototiles! --> further thought required(rotation too)
	{
		String newx="", newy="", newz="";
		int past=0;
		for(int i=0;i<n;i++)past+=length[i];
		for(int i=0;i<length[n];i++)
		{
			int index=(rotimage[n][i]+past);
			newx=newx+index;
			newy=newy+index;
			newz=newz+index;
		}
		return new Tesselation(newx,newy,newz);
	}
	public int compareTo(Tesselation t)
	{
		int x0=toInt(x),xt=toInt(t.x);
		if(x0<(xt))return -3;
		if(x0>(xt))return 3;
		int y0=toInt(y),yt=toInt(t.y);
		if(y0<(yt))return-2;
		if(y0>(yt))return 2;
		int z0=toInt(z),zt=toInt(t.y);
		if(z0<(zt))return -1;
		if(z0>(zt))return 1;
		return 0;
	}
	public static int toInt(int[] array)
	{
		int out=0;
		for(int i=0;i<array.length;i++)
			out+=array[i]*(int)Math.pow(c, i);
		return out;
	}
	public static int gcd(int[]n)
	{
		int out=n[0];
		for(int i=1;i<n.length;i++)
		{   
			if(n[i]>out&&n[i]<out*2)out=n[i]-out;
			while(n[i]<out)out-=n[i];
			
			if(out==1)return out;
		}
		return out;
	}
	public boolean isFullColor()
	{
		int[]numbers=new int[1+x.length];
		numbers[0]=c;
		for(int i=0;i<x.length;i++)
		{
			numbers[i+1]=x[i];
		}
		if(gcd(numbers)==1)return true;
		else return false;
	}
	public boolean nju(ArrayList<Tesselation> done) 
	{
		boolean fc=true;
		Tesselation rep=representant(fc);
	
		//if(rep==null)return false;
		for(Tesselation t : done)
		{
			if(rep.equals(t))return false;
		}
		rep.print();
	//	System.out.println(" is new.");
		done.add(rep);
		return true;
	}
	public void print()
	{
		System.out.print("{");
		print(x);
		System.out.print(", ");
		print(y);
		System.out.print(", ");
		print(y);
		System.out.println("}");
	}
	public static void print(int[] array) 
	{
		System.out.print("{");
		for(int i=0;i<array.length;i++)
			System.out.print(array[i]+",");
		System.out.print("}");
	}
	
}
