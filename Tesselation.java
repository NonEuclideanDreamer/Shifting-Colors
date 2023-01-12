import java.util.ArrayList;

public class Tesselation implements Comparable<Tesselation>
{
	static Group colorgroup;
	static int c=12;//Tesselate.colors.length;//number of colors
	static ArrayList<int[]>incompleteColors=new ArrayList<int[]>(),colorPermutations=colorPerm(c);
	static int[][] ordernelements;
	static int dim=Tile.dim;
	static int[][] rotimage,mirimage;
	static Prototile[] tile;
	static Tile[][]instr;
	static int[][]rule;static double[][]nullangle;//rule just contains the prototileinfo of instr, nullangle is the rotangle of the subtile with instr 0
	static int[] rot,length, mir;
	
	String x,y,z;//color,mirror,rot
		
	
	public Tesselation(String x0,String y0, String z0)
	{
		x=x0;y=y0;z=z0;
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
		for(int i=0;i<x.length();i++)
		{
			out*=c;
			out+=per[Integer.parseInt(x.substring(i,i+1))];
		}
		return out;	
	}
	public Tesselation representant(boolean colormax)//if colormax==true we return null for Tesselations not using their full colorspace
	{ 	int minimalvalue=(int)Math.pow(c,x.length()),value;int[]minper= {-1};
		for(int[] per:colorPermutations)
		{
			value=coloringnr(per);
			if(value<minimalvalue)
			{
				minimalvalue=value;minper=per.clone();
			}
		}
		//if(degenerate(minper))return null;
		String newx=new String("");
		for(int i=0;i<x.length();i++)
		{
			newx=newx+minper[Integer.parseInt(x.substring(i, i+1))];
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
			for(int i=0;i<x.length();i++)
			{
				boolean in=false;
				for(int j=0;j<per.length;j++)
					if(minper[Integer.parseInt(x.substring(i,i+1))]==per[j])in=true;
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
		String newz="";
		for(int i=0;i<z.length();i++)
			newz=newz+(c-1-z.charAt(i));
		return new Tesselation(x,y,newz);
	}
	public Tesselation coloradd(int n)
	{
		String newz="";
		for(int i=0;i<z.length();i++)
			newz=newz+((z.charAt(i)+n)%c);
		return new Tesselation(x,y,newz);
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
		if(Integer.parseInt(x)<Integer.parseInt(t.x))return -3;
		if(Integer.parseInt(x)>Integer.parseInt(t.x))return 3;
		if(Integer.parseInt(y)<Integer.parseInt(t.y))return-2;
		if(Integer.parseInt(y)>Integer.parseInt(t.y))return 2;
		if(Integer.parseInt(z)<Integer.parseInt(t.z))return -1;
		if(Integer.parseInt(z)>Integer.parseInt(t.z))return 1;
		return 0;
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
		int[]numbers=new int[1+x.length()];
		numbers[0]=c;
		for(int i=0;i<x.length();i++)
		{
			numbers[i+1]=Integer.parseInt(x.substring(i,i+1));
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
		System.out.print("(\""+rep.x+"\" , \""+rep.y+"\",\""+rep.z+"\"),");
	//	System.out.println(" is new.");
		done.add(rep);
		return true;
	}
	
}
