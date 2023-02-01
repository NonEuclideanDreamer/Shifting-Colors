
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random; 
import javax.imageio.ImageIO;
import javax.swing.*;

import java.lang.reflect.*;
import java.text.DecimalFormat;

public class EvolveColor 
{
	static String title="ShiftingColors by Non-Euclidean Dreamer";
	static JFrame frame=new JFrame(title);
	static DecimalFormat df=new DecimalFormat("00");
	static ArrayList<Tesselation>done;
	static int cr=1;//colorresolution 
	//List of colors as rgb
	static int[][] colors=circ("oliv",19,13,154);
//{ {1*255/cr,0*255/cr,1*255/cr},{0*255/cr,1*255/cr,1*255/cr},{1*255/cr,1*255/cr,0*255/cr},{0*255/cr,0*255/cr,0*255/cr},new int[] {0*255/cr,1*255/cr,0*255/cr}};
static ArrayList<Tile> tiles;
static ArrayList<Integer[]>decimals;
static int change=0,dir=1;//dir:in what direction are we doing the movie , forward or backward in time?
static String format="png";
static TileSystems ts=TileSystems.sphinx(); 
static Prototile[] proto=ts.proto;
static double infl=Tesselate.infl;   
static int start=0,color=0, steps=4, no,c; 
static Tile[][] ins; static double sqrt=Math.sqrt(3);
static double[] min=ts.min,max=ts.max;//{-16*0.02,-5*0.02},max=new double[] {16*0.02,13*0.02};//min= {6-11*sqrt/3,3-2*sqrt},max= {sqrt/3,2*sqrt-3}; 
static int width=1080, height=1080,it=4, 
n=0;static String name="Colorshift";
static Method insMethod;
static boolean print=false;
 
static int[] rot= {1},
			newx,
			length;
static int minitiles=4;
//rot= rotation symmetry for...
//length= how many small tiles
static BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
static JLabel label=new JLabel(new ImageIcon(image));

static Random rand=new Random();
static int[] x,y,z;//x=color instruction, y=mirror instruction,z=rotation instruction
//static Rn[]centers=new Rn[length];
public static void main(String[] args)
{		
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(1080,1080);
	frame.setResizable(false);
	frame.getContentPane().add(label);
	frame.pack();
	frame.setVisible(true);
	Instructions tilesystem=new Instructions(ts);
	try
	{
		Class<?> tileSys=Class.forName("TileSystems"),
				instr=Class.forName("Instructions");
		
		File config=new File("config.txt");
		FileReader fr=new FileReader(config);
		BufferedReader read=new BufferedReader(fr);
		
		String line="";
		while(!line.contains("col"))
		line=read.readLine();
		System.out.print(line);
		int c=line.length()-line.replace("(", "").length();
		colors=new int[c][3];
		for(int i=0;i<c;i++)
		{
			line=line.substring(line.indexOf("(")+1);
			for(int j=0;j<3;j++)
			{
				int numberlength=Math.min(line.indexOf(")"), line.indexOf(","));
				if(numberlength==-1)numberlength=line.indexOf(")");
				colors[i][j]=Integer.parseInt(line.substring(0,numberlength));
				line=line.substring(line.indexOf(",")+1);
			}
		}
		line="";
		while(!line.contains("tes"))
		{line=read.readLine();}
		System.out.println();
		System.out.println(line);
		line=line.substring(line.indexOf("=")+1);
		while(line.substring(0,1).equals(" "))line=line.substring(1);
		while(line.substring(line.length()-1).equals(" "))line=line.substring(0,line.length()-1);

		name+=line;
		name+=" ";
		Class[] parameterType = null; 
		Method tileMethod = tileSys.getDeclaredMethod(line,parameterType);
				insMethod=instr.getDeclaredMethod(line, int[].class,int[].class,int[].class);
		ts=(TileSystems) tileMethod.invoke(null);
		x=new int[ts.l];y=new int[ts.l];z=new int[ts.l];

		tilesystem=new Instructions(ts);

	//	ins=(Tile[][])insMethod.invoke(tilesystem, x, y,z);
		proto=ts.proto;
		
		min=ts.min;
		max=ts.max;
		//StringBuffer sb
		line="";
		while(!line.contains("iter"))
			line=read.readLine();
		line=line.substring(line.indexOf("=")+1);
		while(line.substring(0,1).equals(" "))line=line.substring(1);
		while(line.substring(line.length()-1).equals(" "))line=line.substring(0,line.length()-1);

		it=Integer.parseInt(line);
				
		
		line="";
		while(!line.contains("inter"))
			line=read.readLine();
		line=line.substring(line.indexOf("=")+1);
		while(line.substring(0,1).equals(" "))line=line.substring(1);
		while(line.substring(line.length()-1).equals(" "))line=line.substring(0,line.length()-1);
		steps=Integer.parseInt(line);
		
		
		line="";
		while(!line.contains("pngs"))
			line=read.readLine();
		line=line.substring(line.indexOf("=")+1);
		
		print=line.contains("y");
	}
	catch(IOException e){System.out.println("IOException");} 
	catch (ClassNotFoundException e) {System.out.println("class not found.");} 
	catch (NoSuchMethodException e) {System.out.println("Couldn't find method");	e.printStackTrace();} 
	catch (SecurityException e) {System.out.println("No idea what the problem is");e.printStackTrace();} 
	catch (IllegalAccessException e) {e.printStackTrace();System.out.println("Couldn't Access method");} 
	catch (InvocationTargetException e) {e.printStackTrace(); System.out.println("Couldn't invoke method");}
	
	c=colors.length;
	Tesselate.colors=colors;
	Tesselation.setC(colors.length); 
	done=new ArrayList<Tesselation>();
	Tesselate.proto=proto;Tesselate.rot=rot;
	Tile.evoColor=true; 
	boolean going=true;
	int[][]xdone= {x.clone(),x.clone()}; 
	xdone[1][0]=1;
	for(int i=0;i<xdone.length;i++)done.add(new Tesselation(xdone[i],y,z).representant(true));
	x=xdone[xdone.length-2];newx=xdone[xdone.length-1];change=0;no=steps*(xdone.length-2);//change= which digit was changed last in xdone?
	int counter=0;
	done.get(0).print();
	done.get(1).print();

	try
	{
		ins=(Tile[][])insMethod.invoke(tilesystem, x, y,z);
	} catch (IllegalAccessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (InvocationTargetException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	length=tilesystem.length;
	minitiles=sum(length);
	decimals=new ArrayList<Integer[]>(); 
	tiles=new ArrayList<Tile>();
	tiles.add(new Tile(proto[start], color)); 
	Integer[]d=new Integer[minitiles];//To generalize
	for(int i=0;i<minitiles;i++)d[i]=0;
	decimals.add(d);
	while(n<it)
	{
		iterate(n==it-1);
		System.out.println("n="+n);
	}
	while(going&&counter<1000)
	{
		counter++;
	//x=xs[a];change=changes[a];
	try 
	{
		ins=(Tile[][])insMethod.invoke(tilesystem,x, y,z);
	} 
	catch (IllegalAccessException e) {e.printStackTrace();System.out.println("Can't acces instructionMethod");} 
	catch (InvocationTargetException e) {e.printStackTrace();System.out.println("Can't invoke instroctionMethod");}
	
	
	
	for(int i=0;i<steps;i++) 
	{   
		System.out.print("."); 
		double ratio=1.0*i/steps; 
		Tile.mash=ratio; 
		image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		for(int j=0;j<width;j++)
		{
			for (int k=0;k<height;k++)
			{
				image.setRGB(j, k,new Color(colors[color][0],colors[color][1],colors[color][2]).getRGB());
			}
		}
	//System.out.print("draw");
		emptydraw(ratio);
	//	System.out.println("n.");
		if(print)print();
		
		label.setIcon(new ImageIcon(image));
	
		n=0;
		boolean colormax=false;
		done.add(new Tesselation(x,y,z).representant(colormax));
	}System.out.println();
	int k=0;
	boolean notfound=true;
	x=newx;	
	int offset=rand.nextInt(x.length);
	//System.out.println("x="+x);
	while(notfound&&k<x.length)
	{
		int i=(k+offset)%x.length;
		newx=x.clone();
		newx[i]=(x[i]+1)%c;
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

private static int sum(int[] vec) 
{
	int out=0;
	for(int i=0;i<vec.length;i++)
	{
		out+=vec[i];
	}
	return out;
}

//Create a color loop
static int[][] circ(String string, int res, int offset, int height)
{	
	int[][]out=new int[res][3];
	if(string.equals("navy")||string.equals("lime"))//height between -255&255
	{
		int i=2,j=0,k=1,sign=1;
		if(string.equals("navy")) {System.out.println("navy");i=2;j=0;k=1;sign=-1;}
		for(int c=0;c<res;c++)
		{
			
			double[]raw=new double[3];
			raw[i]=Math.cos(c*2*Math.PI/res+Math.PI*offset/510);
			raw[j]=Math.sin(c*2*Math.PI/res+Math.PI*offset/510)+height/128.0;
			raw[k]=sign*(Math.sin(c*2*Math.PI/res+Math.PI*offset/510)-height/128.0);
			double max=Math.max(Math.abs(raw[i]),Math.max(Math.abs(raw[j]), Math.abs(raw[k])));
			for(int l=0;l<3;l++)
				out[c][l]=(int)(128+127.999*raw[l]/max);
			System.out.print("{"+out[c][0]+","+out[c][1]+","+out[c][2]+"},");

		}	
		
	}
	else if(string.equals("oliv")||string.equals("petrol"))//face
	{
		int i=2,j=0,k=1,step=4*255/res,loc=offset,stage=0,sign=1,bound=0;
		if(string.equals("oliv")){ i=0;j=1;k=2;}
		else if(string.equals("petrol")) {i=1;j=2;k=0;} 
		for(int c=0;c<res;c++)
		{
			out[c][i]=loc;
			out[c][j]=bound;
			out[c][k]=height;
			System.out.print("{"+out[c][0]+","+out[c][1]+","+out[c][2]+"},");
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
public static void iterate(boolean last)
{	
	int old=tiles.size(), aktuell=old;
	int tilecount=0;
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
					
					Integer[] r=decimals.get(i).clone();
					//System.out.println("tilecount"+tilecount);
					r[tilecount+k]++;
					
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
		tilecount+=length[j]; 
	}n++;
	for(int i=old-1;i>=0;i--)
	{
		tiles.remove(i);
		decimals.remove(i); 
	}
	//System.out.print(tiles.size()+ " tiles. ");
}
public static void emptydraw(double ratio) 
{
	int inTile=0, instr=change;
	while(instr>=ins[inTile].length)
	{
		instr-=ins[inTile].length;inTile++;
	}
	//System.out.println(tiles.size()+" tiles at n="+n);
	//System.out.println(old+" old tiles at n="+n); 
	if(0<tiles.size())
	{int[][] dc=new int[width][height];
	for(int i=0;i<tiles.size();i++) 
	{ //System.out.print(".");
		double col=color;//System.out.print(col);
		int tilecount=0;
		for(int j=0;j<proto.length;j++)
		{	for(int k=0;k<length[j];k++)
			{	
				double d=0; if(change==tilecount+k) {d=ratio;}
				col+=(ins[j][k].color+d)*decimals.get(i)[tilecount+k];
				//System.out.print("+"+(ins[j][k].color+d)+"*"+decimals.get(i)[tilecount+k]);
			}
			tilecount+=length[j];
		}//System.out.print("="+col+"=");
		tiles.get(i).setColor((int)(col%colors.length),colors.length);
		double r=col%1;
		//System.out.println(tiles.get(i).color+"+"+r);
		tiles.get(i).draw(image,colors,min,max,r);
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
