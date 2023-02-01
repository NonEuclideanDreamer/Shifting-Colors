import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Tesselate 
{ 	static ArrayList<Tesselation>done=new ArrayList<Tesselation>();
	static int x=1,size=8,rotation=0,totalSubtiles;//64+3*4;
	//static Random rand=new Random();3farben
	static int[][] colors=EvolveColor.circ("oliv",4,60,250); //
//("lime",4,41,-253); //{new int[] {1*255/x,1*255/x,1*255/x},new int[] {1*255/x,0*x,0*x},new int[] {0*x,1*255/x,1*255/x},new int[] {0*x,1*255/x,0*x},new int[] {0*255/x,0*x,1*255/x}/*,  
				//new int[] {2*x,4*x,2*x},new int[] {4*x,2*x,2*x}*/};
	static ArrayList<Tile> tiles; 
	static String format="png";
	static TileSystems ts=TileSystems.chair(); 
	static Prototile[] proto=ts.proto;
	static double infl=0.5;  
	static int start=0,color=0; 
	static Tile[][] ins; 
	static double[] min=ts.min,max=ts.max;
	static int width=/*size*150*/800, height=800/*size*150*/,it=5,//(int)((Math.log(25*size)/Math.log(2))), 
			n=0;static String name0="chair",name;
	static int[] rot,mir,
			length= {4};
			//rot= rotation symmetry for...
			//length= how many small tiles 
	static BufferedImage image;
	static Random rand=new Random();
	//static Rn[]centers=new Rn[length];
	public static void main(String[] args)  
	{ //Instantiate color group
		//Tesselation.colorgroup=Group.ncolors(colors.length);
		
		
		/*for(int i=0;i<length;i++) 
		{
			centers[i]=new Rn(new double[]{rand.nextDouble()*4-2,rand.nextDouble()*2-1});
		}*/
		Instructions in=new Instructions(ts);
		int sclength=0;  
	//	System.out.print("line 36"); 
		for(int i=0;i<proto.length;i++)    
			sclength+=length[i];int s=0;
		System.out.print("sclength="+sclength); int k0=1,k1=1,k2=1,k3=1,k4=0,st=6*k0+36*k1+217*k2;
		while(k0<12)//126,30,16250,468750
  {for(long c=5/*0+12*k0+k1*250560*/;c<Math.pow(colors.length, sclength)-1;c+=1)
		// if(c!=0)
		{    
		 
			int[]x=new int[sclength];
			long l;
			
			for(int i=0;i<sclength;i++)
			{		 
				l=(long) (c%(long)Math.pow(colors.length, i+1));
				l=l/(long)Math.pow(colors.length, i);
				x[i]=(int)l;
			} System.out.print("x=");Tesselation.print(x);
			//if(x.substring(2,3).equals("0"))
			for(int m=0;m<Math.pow(2, sclength);m+=1)  
			{//System.out.println("m="+m);
				int[] y=new int[sclength];
			for(int i=0;i<sclength;i++)
				y[i]=(int)(c%(int)Math.pow(2, i+1))/(int)Math.pow(2, i);
			//	if(y.substring(2,3).equals("0"))
				for(int r=0;r<1/*Math.pow(rot[0], sclength)*/;r++) //to modify for differing rotational options!
				{ 
					int[]z=new int[sclength];
					/*for(int i=0;i<length[0]+length[4];i++)//specially for p1penrose
					{		 
						l= (r%(int)Math.pow(rot[0], i+1));
						l=l/(int)Math.pow(rot[0], i);
						z=l+z; 
					}*/
					for(int i=0;i<sclength;i++)
					{
						int k=0;
						int i0=i;
						while(i0>=length[k])
						{
							i0-=length[k];
							k++;
						} 
						l= (r%(int)Math.pow(rot[k], i0+1));
						l=l/(int)Math.pow(rot[k], i0);
						z[i]=(int)l;
					}	//System.out.println("line 73");z=z+r;
					name=name0+x+","+y+/*","+z+*/",";
					System.out.println(name);
					Tesselation tes=new Tesselation(x,y,z);
					if(tes.nju(done))
					{ins=in.chair(x, y, z); 
					tiles=new ArrayList<Tile>();
					image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
					for(int j=0;j<width;j++)
					{
						for (int k=0;k<height;k++)
						{
							image.setRGB(j, k,new Color(colors[0][0],colors[0][1],colors[0][2]).getRGB());
						}
					}	//	 System.out.println("line 94"+min[0]);

			tiles.add(new Tile(proto[start], color));
			while(n<it)
			{
				iterate();
				System.out.println("n="+n);
				if(/*n==it/2||*/n==it)
				draw();
				else
					emptydraw();
				 
			}	
			n=0;
			}
			}
			}
		}k0++;k2=0;}
		
	}
	

	
	
	public static Tile[][] p1structions(String c,String m,String r)//r nur für penta1&pentagram!
	{
		length=new int[] {6,7,8,3,10,7};
		rot[0]=1;//Rotation not quite thought through...
		double phi=(Math.sqrt(5)+1)/2,sqrt=Math.sqrt(5);
		infl=1/(phi*phi);
		
		Tile[] penta1=new Tile[6];
		int[]pi= {0,1,1,1,1,1};
		Rn[] loc= {new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
		new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2})};
		double[] ang={1,.8,.4,0,-.4,-.8};
		for(int i=0;i<penta1.length;i++)
		{
			penta1[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i,i+1)),m.substring(i,i+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i,i+1))+2*Integer.parseInt(r.substring(i,i+1))/rot[0]),infl);
		}
		int j=penta1.length,k=penta1.length;
		
		Tile[] penta2=new Tile[7];
		pi=new int[] {0,2,1,1,1,2,3};
		loc=new Rn[]{new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}), 
				new Rn(new double[] {0,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		ang=new double[] {1,.4,.4,0,-.4,-.4,0};
		
		for(int i=0;i<penta2.length;i++) 
		{
			penta2[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+j,i+j+1)),m.substring(i+j,i+j+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i+j,i+j+1))),infl);
		}
		
		j=j+penta2.length;
		
		Tile[] penta3=new Tile[8];
		pi=new int[] {0,2,2,1,2,2,3,3};
		loc=new Rn[]{new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}), 
				new Rn(new double[] {phi*phi/4,-Math.sqrt(phi/sqrt)/4}),new Rn(new double[] {-phi*phi/4,-Math.sqrt(phi/sqrt)/4})};
		ang=new double[] {1,-.8,0,0,0,.8,-.4,.4};
		
		for(int i=0;i<penta3.length;i++)
		{
			penta3[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+j,i+j+1)),m.substring(i+j,i+j+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i+j,i+j+1))),infl);
		}
		
		j=j+penta3.length;
		
		Tile[] diamond=new Tile[3];
		pi=new int[] {5,2,4};
		loc=new Rn[]{new Rn(new double[] {0,-Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {0,(2-sqrt)/2*Math.sqrt(phi/sqrt)}),new Rn(new double[] {0,Math.sqrt(phi/sqrt)/2})};
		ang=new double[] {1,0,0};
		for(int i=0;i<diamond.length;i++)
		{
			diamond[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+j,i+j+1)),m.substring(i+j,i+j+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i+j,i+j+1))),infl);
		}
		
		j=j+diamond.length;
		
		Tile[] pentagram=new Tile[10];
		pi=new int[] {/*4,*/2,2,2,2,2,5,5,5,5,5};
		loc=new Rn[] {/*new Rn(new double[] {0,0}),*/new Rn(new double[] {0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[]{0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2}), new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-phi/2,1/(2*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		ang=new double[] {/*1,*/-0.2,-0.6,1,0.6,.2,.8,.4,0,-.4,-.8};
		
		for(int i=0;i<pentagram.length;i++)
		{
			pentagram[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+j,i+j+1)),m.substring(i+j,i+j+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i+j,i+j+1))+2*Integer.parseInt(r.substring(k+i,k+i+1))/rot[0]),infl);
		}
		j=j+pentagram.length;
		
		Tile[] boat=new Tile[7];
		pi=new int[] {4,2,2,2,5,5,5};
		loc=new Rn[] {new Rn(new double[] {0,0}), new Rn(new double[] {.5,1/(2*Math.sqrt(sqrt*phi)*phi)}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-.5,1/(2*phi*Math.sqrt(sqrt*phi))}),new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-(phi)/2,1/(2*Math.sqrt(sqrt*phi))})};
		ang=new double[] {1,-.6,1,.6,.4,0,-.4};
		for(int i=0;i<boat.length;i++)
		{
			boat[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+j,i+j+1)),m.substring(i+j,i+j+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(m.substring(i+j,i+j+1))),infl);
		}
		
		return new Tile[][] {penta1,penta2,penta3,diamond,pentagram,boat};
	}
	
 	
 	
	
	
	
	

	
	
	public static Prototile[] randomproto()
	{
		Random rand=new Random();
		int n=1;//To do: more than one prototile
		length=new int[] {n};
		int cornernumber=rand.nextInt(7)+3;
		Rn[]corners=new Rn[cornernumber];
		for(int i=0;i<cornernumber;i++)
		{
			
		}
		
		return new Prototile[] {};
	}
	
 	
	
	public static Tile[][] instructions(String c,String m,String r)//To be changed for different tesselations
	{
		Tile[]tiles1=new Tile[4];
		double[][]centers=new double[][] {{-0.5,-0.5},{-0.5,0.5},{0.5,0.5},{0.5,-0.5}};
		infl=0.5;
	
	  for(int i=0;i<4;i++)
		{
			tiles1[i]=new Tile(proto[0],Integer.parseInt(c.substring(i, i+1)),m.substring(i,i+1).equals("1"),new Rn(centers[i]),Math.PI/2*Integer.parseInt(r.substring(i,i+1)),0.5);
		}
		
		//table!	
		tiles1[0]=new Tile(proto[0],Integer.parseInt(c.substring(0, 1)),m.substring(0, 1).equals("1"),new Rn(new double[]{-1.5,0}),Math.PI*(0.5+2.0/rot[0]*Integer.parseInt(r.substring(0,1))),0.5);
		tiles1[1]=new Tile(proto[0],Integer.parseInt(c.substring(1, 2)),m.substring(1, 2).equals("1"),new Rn(new double[]{0,0.5}),2*Math.PI/rot[0]*Integer.parseInt(r.substring(1,2)),0.5);
		tiles1[2]=new Tile(proto[0],Integer.parseInt(c.substring(2, 3)), m.substring(2, 3).equals("1"),new Rn(new double[]{0,-0.5}),2*Math.PI/rot[0]*Integer.parseInt(r.substring(2,3)),0.5);
		tiles1[3]=new Tile(proto[0],Integer.parseInt(c.substring(3, 4)),m.substring(3, 4).equals("1"), new Rn(new double[] {1.5,0}),Math.PI*(0.5+2.0/rot[0]*Integer.parseInt(r.substring(3,4))),0.5);
	
	return new Tile[][] {tiles1};
	}
	
	
	
	
	 

	public static void iterate()
	{
		for(int i=tiles.size()-1;i>-1;i--)
		{
			//System.out.println("protocorner :"+proto[0].corners[1].c[0]);
			Tile tile=tiles.get(i);
			for(int j=0;j<proto.length;j++)
			{//System.out.println("iterate"+j);
			if(tile.prototype().equals(proto[j]))
			{
				//System.out.print("Pre");tile.print();
				for(int k=0;k<ins[j].length;k++)
					{//System.out.println("iterate "+k);
						Tile t =ins[j][k].copy();
						//System.out.append("from ");t.print();
						t=t.transform(tile);
					//	System.out.print("to ");t.print();
						//t.translate(tile.shift);
						tiles.add(t);//System.out.print("to "+t.color+", ");
						//t.print();
						//t.checkCorners();
					}
			}
			} 
			tiles.remove(i);
			
		}n++;
	}
	public static void draw()
	{
		int[][] dc=new int[width][height];
		//System.out.println(tiles.size()+" tiles at n="+n);
		for(int i=0;i<tiles.size();i++)
		{
			//System.out.print(".");
			//tiles.get(i).print(); 
			tiles.get(i).draw(image,colors,min,max,dc); 
		} 
		File outputfile = new File(name+n+"."+format);System.out.println("Print:");
		try 
		{
			ImageIO.write(image, format, outputfile);
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace(); 
		}
	}
	public static void emptydraw()
	{
		int[][] dc=new int[width][height];
	//	System.out.println(tiles.size()+" tiles at n="+n);
		for(int i=0;i<tiles.size();i++)
		{
			//System.out.print(".");
			tiles.get(i).draw(image,colors,min,max,dc);
		}
		
	}
}
