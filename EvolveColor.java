
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random; 

import javax.imageio.ImageIO;

public class EvolveColor 
{	static ArrayList<Tesselation>done=new ArrayList<Tesselation>();
	static int cr=1;//colorresolution 
	static int[][] colors=circ("oliv",12,60,80); //{ {1*255/cr,0*255/cr,1*255/cr},{0*255/cr,1*255/cr,1*255/cr},{1*255/cr,1*255/cr,0*255/cr},{0*255/cr,0*255/cr,0*255/cr},new int[] {0*255/cr,1*255/cr,0*255/cr}/*,new int[] {192,64,128},new int[] {128,0,128},  
	//new int[] {64,0,64}*/};
static ArrayList<Tile> tiles;
static ArrayList<Double>decimals;
static int change=0,dir=1;//dir:in what direction are we doing the movie , forward or backward in time?
static String format="png";
static Prototile[] proto=Tesselate.p1orPenrose(); 
static double infl=Tesselate.infl;  
static int start=0,color=0, steps=1, no,c=colors.length; 
static Tile[][] ins; static double sqrt=Math.sqrt(3);
static double[] min=new double[] {-16*0.02,-5*0.02},max=new double[] {16*0.02,13*0.02};//min= {6-11*sqrt/3,3-2*sqrt},max= {sqrt/3,2*sqrt-3}; 
static int width=2560, height=1440,it=5, 
n=0;static String name="ColorshiftPenrose5 ",
newx="0001";
static int[] rot= {1},
length= {6,7,8,3,11,7};
//rot= rotation symmetry for...
//length= how many small tiles
static BufferedImage image;
static Random rand=new Random();
static String x, y="000000000000000000000000000000000000000000",z=y;
//static Rn[]centers=new Rn[length];
public static void main(String[] args)
{			Tesselate.colors=colors;Tesselation.c=colors.length;
	//String[] xs= {"0000","0001","0002","0012","0013","0010","0011","0021","0022","0122","0123","0120","0121","0131","0132","1132","1133","1103","1113","1110","2110","2111","2112","2113","2123","2120","2121","2122","2132","2102","2103","2203","2303","2003","3003","3013","3023","3033","3133","3233","3333"};
//int[] changes= {3,3,2,3,3,3,2,3,1,3,3,3,2,3,0,3,2,2,3,0,3,3,3,2,3,3,3,2,2,                                                                     3,1,1,1,0,2,2,2,1,1,1}/*no symmetries*/;
	///*\-symmetry*/ String[] xs= {"00000000110","00000000210","10000000210","11000000210","11100000210","12100000210","12200000210","12000000210"
		//	,"12000000211","22000000211","22100000211","22200000211","02200000211","00200000211","00000000211"}; 
//	int[] changes= {8,0,1,2,1,2,2,10,0,2,2,0,1,2};
	Tesselate.proto=proto;Tesselate.rot=rot;
	Tile.evoColor=true; 
	boolean going=true;
	String[]xdone= {y,"100000000000000000000000000000000000000000"};
	for(int i=0;i<xdone.length;i++)done.add(new Tesselation(xdone[i],y,z).representant(true));
	x=xdone[xdone.length-2];newx=xdone[xdone.length-1];change=0;no=steps*(xdone.length-2);//To change correctly!
	int counter=0;
	while(going&&counter<1000)//for(int a=0;a<changes.length;a++)//for(int a=7;a>-1;a--)//
	{
		counter++;
	//x=xs[a];change=changes[a];
	ins=Tesselate.p1structions(x,y,z);
	infl=Tesselate.infl;
	
	for(int i=0;i<steps;i++) 
	{   
		System.out.print("."); 
		double ratio=1.0*i/steps; 
		Tile.mash=ratio; 
		tiles=new ArrayList<Tile>();
		decimals=new ArrayList<Double>(); 
		image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		for(int j=0;j<width;j++)
		{
			for (int k=0;k<height;k++)
			{
				image.setRGB(j, k,new Color(colors[color][0],colors[color][1],colors[color][2]).getRGB());
			}
		}
		tiles.add(new Tile(proto[start], color));  
		decimals.add(0.0);
		while(n<it)
		{
			iterate(ratio,n==it-1);
			System.out.println("n="+n);
		}	
		print();
		
		n=0;
		boolean colormax=false;
		done.add(new Tesselation(x,y,z).representant(colormax));
	}System.out.println();
	int k=0;
	boolean notfound=true;
	x=newx;	
	int offset=rand.nextInt(x.length());
	//System.out.println("x="+x);
	while(notfound&&k<x.length())
	{
		int i=(k+offset)%x.length();
		newx=x.substring(0,i)+(Integer.parseInt(x.substring(i,i+1))+1)%c+x.substring(i+1,x.length());
		if(new Tesselation(newx,y,z).nju(done))
		{
			notfound=false;
			change=i;
			//System.out.println("change="+change+", ");
		}
		else k++;
	}
if(notfound)going=false;
	}System.out.println("x="+x);
	
}

//Create a color loop
private static int[][] circ(String string, int res, int offset, int height)
{	
	int[][]out=new int[res][3];
	if(string.equals("oliv"))
	{
		int i=0,j=1,k=2,step=4*255/res,loc=offset,stage=0,sign=1,bound=0;
		for(int c=0;c<res;c++)
		{
			out[c][i]=loc;
			out[c][j]=bound;
			out[c][k]=height;
			System.out.print("("+out[c][0]+","+out[c][1]+","+out[c][2]+")");
			loc+=sign*step;
			if(loc>255||loc<0)
			{
				loc=(Math.abs(loc))%255;
				stage++;
				int temp=i;
				i=j;
				j=temp;
				if(stage>1)loc=255-loc;
				if(stage%2==1) {bound=255-bound;}
				else if(stage==2) {sign=-1;}
			}
		}
	}
	return out;
}
public static void iterate(double ratio,boolean last)
{	int inTile=0, instr=change;
	while(instr>=ins[inTile].length)
	{
		instr-=ins[inTile].length;inTile++;
	}
	int old=tiles.size(), aktuell=old;
	for(int j=0;j<proto.length;j++)
	{
		//System.out.println("protocorner :"+proto[0].corners[1].c[0]);
		//System.out.println("Prototile "+j+", Adress:"+proto[j]);
		for(int k=0;k<ins[j].length;k++)
		{
			//System.out.println("subtile "+k);
			
			for(int i=old-1;i>-1;i--)
			{
				
				Tile tile=tiles.get(i);
				
				if(tile.prototype().equals(proto[j]))
				{
					Tile t =ins[j][k].copy();
					
					//System.out.print(".");
			//		System.out.print("!");
			//System.out.print("Pre");
					//tile.print();
			 
					//System.out.append("from ");t.print();
					t=t.transform(tile);
					
					double r=decimals.get(i);
					if(k==instr&&j==inTile)
					{
						r+=ratio;
						if(r>=1)
						{
							r--;
							t.color++;
						}
					}
					t.c2=(t.color+1)%colors.length;
					tiles.add(t);decimals.add(r);
				//	System.out.print("to ");t.print();
					//t.translate(tile.shift);
					//System.out.println("Added tile of type "+t.type);//System.out.print("to "+t.color+", ");
					//t.print();System.out.println(t.color+"+"+r+" ("+t.c2+") ");
					//t.checkCorners();
				} 
			}
			
			
		}
		
	}n++;if(last)emptydraw(aktuell,ratio);
			aktuell=tiles.size();
	for(int i=old-1;i>=0;i--)
	{
		tiles.remove(i);
		decimals.remove(i); 
	}
}
public static void emptydraw(int old,double ratio) 
{
	//System.out.println(tiles.size()+" tiles at n="+n);
	//System.out.println(old+" old tiles at n="+n); 
	if(old<tiles.size())
	{int[][] dc=new int[width][height];
	for(int i=old;i<tiles.size();i++)  
	{ 
		//System.out.println(tiles.get(i).c2);
		tiles.get(i).draw(image,colors,min,max,decimals.get(i));
	}
	}
	
}
public static void print()
{
	File outputfile = new File(name+no+"."+format);
	try 
	{
		ImageIO.write(image, format, outputfile);
	} catch (IOException e) 
	{
		System.out.println("IOException");
		e.printStackTrace();
	}
	
	no+=dir;
}
}
