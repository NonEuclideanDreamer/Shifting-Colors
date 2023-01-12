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
	static int[][] colors= {new int[] {1*255/x,1*255/x,1*255/x},new int[] {1*255/x,0*x,0*x},new int[] {0*x,1*255/x,1*255/x},new int[] {0*x,1*255/x,0*x},new int[] {0*255/x,0*x,1*255/x}/*,  
				new int[] {2*x,4*x,2*x},new int[] {4*x,2*x,2*x}*/};
	static ArrayList<Tile> tiles;
	static String format="png";
	static Prototile[] proto=pinwheel(); 
	static double infl=0.5;  
	static int start=0,color=4; 
	static Tile[][] ins; 
	static double[] min,max;
	static int width=/*size*150*/2560, height=1440/*size*150*/,it=4,//(int)((Math.log(25*size)/Math.log(2))), 
			n=0;static String name0="apinwheely",name;
	static int[] rot,mir,
			length;
			//rot= rotation symmetry for...
			//length= how many small tiles 
	static BufferedImage image;
	static Random rand=new Random();
	//static Rn[]centers=new Rn[length];
	public static void main(String[] args)  
	{ //Instantiate color group
		Tesselation.colorgroup=Group.ncolors(colors.length);
		
		
		/*for(int i=0;i<length;i++) 
		{
			centers[i]=new Rn(new double[]{rand.nextDouble()*4-2,rand.nextDouble()*2-1});
		}*/
		int sclength=0;  
	//	System.out.print("line 36"); 
		for(int i=0;i<proto.length;i++)    
			sclength+=length[i];int s=0;
		System.out.print("sclength="+sclength); int k0=1,k1=0,k2=1,k3=1,k4=0,st=6*k0+36*k1+217*k2;
		//while(k1<8)//126,30,16250,468750
  {for(long c=130;c<Math.pow(colors.length, sclength)-1;c+=1)
		// if(c!=85)
		{    
		 
			String x="";  
			long l;
			
			for(int i=0;i<sclength;i++)
			{		 
				l=(long) (c%(long)Math.pow(colors.length, i+1));
				l=l/(long)Math.pow(colors.length, i);
				x=l+x; 
			} System.out.println("x="+x);
			//if(x.substring(2,3).equals("0"))
			for(int m=0;m<1/*Math.pow(2, sclength-3)*/;m+=1)  
			{//System.out.println("m="+m);
				String y=Integer.toBinaryString(m);
				
				for(int i=sclength-y.length();i>0;i--)
						y="0"+y;
			//	if(y.substring(2,3).equals("0"))
				for(int r=0;r<Math.pow(rot[0], sclength);r++) //to modify for differing rotational options!
				{ 
					String z="";
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
						z=l+z;
					}	//System.out.println("line 73");z=z+r;
					name=name0+x+/*","+y+","+z+*/",";
					System.out.println(name);
					Tesselation tes=new Tesselation(x,y,z);
					if(tes.nju(done))
					{ins=pinwheelnstructions(x); 
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
		}k1++;k2=0;}
		
	}
	
	public static Prototile[] proto(double width)//rectangular prototile of half height 1 and given half width
	{
		min=new double[]{-1,-1};max=new double[] {1,1};
		//Random rand=new Random();
		
		Rn[] corners= {new Rn(new double[]{-width,-1}),new Rn(new double[]{width,-1}),new Rn(new double[]{width,1}),new Rn(new double[]{-width,1})};
	
		return new Prototile[] {new Prototile(corners)};
	}
	
	public static Prototile[] p1orPenrose()
	{
		min=new double[]{-.3,-.3};max=new double[] {.3,.3};
		double phi=(Math.sqrt(5)+1)/2,sqrt=Math.sqrt(5);
		
		Rn[] pentagon= {new Rn(new double[] {0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2}), new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-phi/2,1/(2*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		Rn[] diamond= {new Rn(new double[] {-1/(2*phi),0}) , new Rn(new double[] {0,-Math.sqrt(2+phi)/2}),
				new Rn(new double[] {1/(2*phi),0}), new Rn(new double[] {0,Math.sqrt(2+phi)/2})};
		Rn[]pentagram= {new Rn(new double[] {0,-1/Math.sqrt(phi*sqrt)}),new Rn(new double[] {phi/2,-(2+sqrt)/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {.5,-1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {(phi+1)/2,Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[] {0.5/phi,Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {0,phi*Math.sqrt(phi/sqrt)}),
				new Rn(new double[] {-0.5/phi,Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {-(phi+1)/2,Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[] {-.5,-1/(2*phi*Math.sqrt(sqrt*phi))}),new Rn(new double[] {-phi/2,-(2+sqrt)/(2*Math.sqrt(sqrt*phi))})};
		Rn[]boat= {new Rn(new double[] {.5,-1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {(phi+1)/2,Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[] {0.5/phi,Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {0,phi*Math.sqrt(phi/sqrt)}),
				new Rn(new double[] {-0.5/phi,Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {-(phi+1)/2,Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {-.5,-1/(2*phi*Math.sqrt(sqrt*phi))})};
		return new Prototile[] {new Prototile(pentagon), new Prototile(pentagon), new Prototile(pentagon), new Prototile(diamond), new Prototile(pentagram), new Prototile(boat)};
	}

	public static Tile[][] p1structions(String c,String m,String r)//r nur für penta1&pentagram!
	{
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
		
		Tile[] pentagram=new Tile[11];
		pi=new int[] {4,2,2,2,2,2,5,5,5,5,5};
		loc=new Rn[] {new Rn(new double[] {0,0}),new Rn(new double[] {0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[]{0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2}), new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-phi/2,1/(2*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		ang=new double[] {1,-0.2,-0.6,1,0.6,.2,.8,.4,0,-.4,-.8};
		
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
	
	public static Prototile[] dragonTrigon()
	{
		double sqrt=Math.sqrt(3);
		infl=1/sqrt;
		min=new double[] {-0.74,-0.45};max=new double[] {0.16,0.45};rot=new int[] {1,3};length=new int[] {3,3};infl=1/sqrt;
		Rn[] dragon= {new Rn(new double[] {-1,0}),new Rn(new double[] {-0.5,-sqrt/2}),new Rn(new double[] {1,0}), new Rn(new double[] {-0.5,sqrt/2})};
		Rn[] trigon= {new Rn(new double[]{-sqrt/3,1}), new Rn(new double[] {-sqrt/3,-1}),new Rn(new double[] {2*sqrt/3,0})};
		
		return new Prototile[] {new Prototile(dragon),new Prototile(trigon)};
	}
 	public static Prototile[] chair()//The chair-tile
	{
		min=new double[] {0,-0.5};max=new double[] {1,0.5};
		Rn[] corners= {new Rn(new double[] {1,0}),new Rn(new double[] {2,1}),new Rn(new double[] {1,2}),
				new Rn(new double[] {-1,0}),new Rn(new double[] {1,-2}),new Rn(new double[] {2,-1})};
		
		return new Prototile[] {new Prototile(corners)};
		
		
	}
 	public static Prototile[] reptrile()
 	{	double sqrt=Math.sqrt(3),d=1;
 		//min=new double[] {2*sqrt/15,-sqrt/10};max=new double[] {sqrt/3,sqrt/10};// boundary zoom
 min=new double[] {-1.25,-1};max=new double[] {0.75,1};//Full picture 
	
 		Rn[] triangle= {new Rn(new double[] {sqrt/3,-1}),new Rn(new double[] {sqrt/3,1}),new Rn(new double[] {-sqrt*2/3,0})};
 		Rn[] sled= {new Rn(new double[] {-0.5*d,-sqrt/2*d}),new Rn(new double[] {-1.5*d,-sqrt/2*d}),new Rn(new double[] {-2*d,0}),new Rn(new double[] {d,0}), new Rn(new double[] {0,-sqrt*d}),new Rn(new double[] {-d,-sqrt*d})};
 	
 		return new Prototile[] {new Prototile(triangle), new Prototile(sled)};
 	}
	 
	public static Prototile[] reptile()
	{
		min=new double[] {-1,-1.5};max=new double[] {0,-0.5};
		double h=Math.sqrt(3)/2;
		Rn[] corners= {new Rn(new double[] {0,0}),new Rn(new double[] {-1,2*h}),new Rn(new double[] {-3,-2*h}),new Rn(new double[] {3,-2*h}),new Rn(new double[] {2,0})};
		
		return new Prototile[] {new Prototile(corners)};
	}
	public static Prototile[] pinwheel()
	{	Tesselation.c=colors.length;
		rot=new int[] {1};mir=new int[] {1};length=new int[] {5};Tesselation.rot=rot;Tesselation.mir=mir;Tesselation.length=length;
		min=new double[] {0,-.125};max =new double[] {4,2.125};
		Rn[] corners= {new Rn(new double[] {0,0}),new Rn(new double[] {4,0}),new Rn(new double[] {0,2})};
		Tesselation.tile=new Prototile[] {new Prototile(corners)};
		return Tesselation.tile;
	}
	public static Tile[][] pinwheelnstructions(String c)
	{
		Tesselation.rotimage=new int[1][5];Tesselation.mirimage=new int[1][5];
		infl=1/Math.sqrt(5);
		double alpha=Math.atan(2),beta=Math.atan(0.5);
		Tile[] out=new Tile[5];
		Rn[] loc= {new Rn(new double[] {0.8,1.6}),new Rn(new double[] {0.4, 0.8}),new Rn(new double[] {0.4,0.8}), new Rn(new double[] {2.4,0.8}), new Rn(new double[] {2.4,0.8})};
		double[] ang= {Math.PI-alpha,beta,beta,beta-Math.PI,beta};
		Tesselation.nullangle=new double[][] {ang};
		boolean[] mir= {true,true,false,false,true};
		int k=0,i=0;
		while(k<5)
		{	Tesselation.rotimage[0][k]=k;Tesselation.mirimage[0][k]=k;
			out[i]=new Tile(proto[0],Integer.parseInt(c.substring(k,k+1)),mir[k],loc[k],ang[k],infl);
			
			i++;
			k++;
			
			while(k==5)//here one can choose whiche tiles not to disect
			{
				k++;
			}
		}
		Tesselation.instr=new Tile[][] {out};
		return Tesselation.instr;
	}
	public static Tile[][]reptristuctions(String c, String m, String r)
	{
		double sqrt= Math.sqrt(3), infl=0.4,d=1;
		Tile[] triangle=new Tile[4];//4
		int[]pi= {1,1,1,0};int doneTriangles=1;
		Rn[] loc= {new Rn(new double[] {sqrt/3,-.6}),new Rn(new double[] {-sqrt*7/15,-0.2}),new Rn(new double[] {sqrt*2/15,.8}),new Rn(new double[]{0,0})};
		double[] ang= {0.5,-10.0/12,-2.0/12,Integer.parseInt(r.substring(0,1))*2.0/3};
		boolean[]mir= {false,false,false,(m.substring(0,1).equals("1"))};
		for(int i=0;i<triangle.length;i++)
		{
			triangle[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i,i+1)),mir[i],loc[i],Math.PI*(ang[i]),infl);
		}
		infl=0.5;
		Tile[]sled=new Tile[7];
		pi=new int[] {0,0,0,0,0,0,0};
		loc=new Rn[] {new Rn(new double[] {-1.5*d,-sqrt*d/6}),new Rn(new double[] {-d,-sqrt*d/3}),new Rn(new double[] {-0.5*d,-sqrt*d/6}),
				new Rn(new double[] {0,-sqrt*d/3}),new Rn(new double[] {0.5*d,-sqrt*d/6}),new Rn(new double[] {0,-sqrt*2*d/3}),new Rn(new double[] {-0.5*d,-sqrt*5*d/6})};
		ang=new double[] {1.0/6,-5.0/6,1.0/6,-5.0/6,1.0/6,1.0/6,-5.0/6};
		int l=triangle.length;
		for(int i=0;i<sled.length;i++)
		{
			//System.out.println("i="+i+", pi[i]="+pi[i]);
			sled[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+l,i+l+1)),m.substring(i+doneTriangles,i+doneTriangles+1).equals("1"),loc[i],Math.PI*(ang[i]+Integer.parseInt(r.substring(i+doneTriangles,i+doneTriangles+1))*2.0/3),infl);
			sled[i].print();
		}
		return new Tile[][] {triangle,sled};
	}
	
	public static Tile[][] draTriStructions(String c, String m,String r)
	{
		double sqrt=Math.sqrt(3);
		Tile[]dragon=new Tile[3];
		int[]pi= {0,  0,  1};
		Rn[] loc= {new Rn(new double[] {-0.5,sqrt/6}),  new Rn(new double[] {-0.5,-sqrt/6}),  new Rn(new double[] {1.0/3,0})  };
		double[] ang= {5.0/6,  -5.0/6,  2.0*Integer.parseInt(r)/3  };
		
		for(int i=0;i<dragon.length;i++)
		{
			dragon[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i,i+1)),m.substring(i,i+1).equals("1"),loc[i],Math.PI*(ang[i]),infl);
		}
				Tile[]trigon=new Tile[3];
		pi=new int[] {0,0,0};
		loc=new Rn[] {new Rn(new double[] {-sqrt/6,0.5}),  new Rn(new double[] {-sqrt/6,-0.5}),  new Rn(new double[] {sqrt/3,0})  };
		ang=new double[] {-2.0/3, 2.0/3, 0};
		int l=dragon.length;
		for(int i=0;i<trigon.length;i++)
		{
			//System.out.println("i="+i+", pi[i]="+pi[i]);
			trigon[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+l,i+l+1)),m.substring(i+l,i+l+1).equals("1"),loc[i],Math.PI*(ang[i]),infl);
			trigon[i].print();
		}
		
		return new Tile[][] {dragon,trigon};
	}
	
	
	public static Tile[][] penroseInstructions(String c,String m,String r)
	{
		min=new double[] {-0.5,-0.5};max=new double[] {0.5,0.5};
		double phi=(1+Math.sqrt(5))/2;
		Tile[]wide=new Tile[3];
		int[]pi= {0/*,0,1*/,0,1};
		Rn[] loc= {new Rn(new double[] {-phi/4,Math.sqrt(3-phi)/4})/*,new Rn(new double[] {-phi/4,-Math.sqrt(3-phi)/4}),
				new Rn(new double[] {1/(4*phi),-Math.sqrt(phi+2)/4})*/, new Rn(new double[] {0.5/phi,0}),
				new Rn(new double[] {1/(4*phi),Math.sqrt(phi+2)/4})};//ToDo
		
		double[] ang= {0.8/*,-0.8,-0.2*/,1,0.2};
		infl=1/phi;
		for(int i=0;i<wide.length;i++)
			{
			wide[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i,i+1)),m.substring(i,i+1).equals("1"),loc[i],Math.PI*(ang[i]+2.0/rot[0]*Integer.parseInt(r.substring(i,i+1))),infl);
		}
		
		Tile[]thin=new Tile[2];
		pi=new int[] {/*0,1,*/1,0};
		loc=new Rn[] {/*new Rn(new double[] {-1/(4*phi),-Math.sqrt(2+phi)/4}), new Rn(new double[] {0.25,-Math.sqrt(phi+2)/(4*phi*phi)}),*/
					new Rn(new double[] {0.25,Math.sqrt(phi+2)/(4*phi*phi)}), new Rn(new double[] {-1/(4*phi),Math.sqrt(2+phi)/4})};
		ang=new double[] {/*-0.6,0.6,*/-0.6,0.6};
		int l=wide.length;
		for(int i=0;i<thin.length;i++)
		{
			thin[i]=new Tile(proto[pi[i]],Integer.parseInt(c.substring(i+l,i+l+1)),m.substring(i+l,i+l+1).equals("1"),loc[i],Math.PI*(ang[i]+2.0/rot[1]*Integer.parseInt(r.substring(i+l,i+l+1))),infl);
		}
		
		return new Tile[][] {wide,thin};
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
	public static Prototile[] fish()
	{
		min=new double[] {-2,-0.5};max=new double[] {-1,0.5};
		double h=Math.sqrt(3);
		Rn[]corners= {new Rn(new double[] {0,0}),new Rn(new double[] {h,-1}),new Rn(new double[] {h,1}),
				new Rn(new double[] {-h,-1}),new Rn(new double[] {-2*h,0}),new Rn(new double[] {-h,1})};
		return new Prototile[] {new Prototile(corners)};
		
	}
 	public static Prototile[] penrose()
	{
		double phi=(1+Math.sqrt(5))/2;
	
		Rn[] widerhomb= {new Rn(new double[] {-phi/2,0}), new Rn(new double[] {0,-Math.sqrt(3-phi)/2}),
						new Rn(new double[] {phi/2,0}), new Rn(new double[] {0,Math.sqrt(3-phi)/2})};
		
		Rn[] thinrhomb= {new Rn(new double[] {-1/(2*phi),0}) , new Rn(new double[] {0,-Math.sqrt(2+phi)/2}),
						new Rn(new double[] {1/(2*phi),0}), new Rn(new double[] {0,Math.sqrt(2+phi)/2})};
		
		return new Prototile[] {new Prototile(widerhomb), new Prototile(thinrhomb)};
	}
	
	public static Tile[][] instructions(String c,String m,String r)//To be changed for different tesselations
	{
		Tile[]tiles1=new Tile[4];
		double[][]centers=new double[][] {{-0.5,-0.5},{-0.5,0.5},{0.5,0.5},{0.5,-0.5}};
		infl=0.5;
		//square
	 /* for(int i=0;i<4;i++)
		{
			tiles1[i]=new Tile(proto[0],Integer.parseInt(c.substring(i, i+1)),m.substring(i,i+1).equals("1"),new Rn(centers[i]),Math.PI/2*Integer.parseInt(r.substring(i,i+1)),0.5);
		}
	*/	
		//table!	
		tiles1[0]=new Tile(proto[0],Integer.parseInt(c.substring(0, 1)),m.substring(0, 1).equals("1"),new Rn(new double[]{-1.5,0}),Math.PI*(0.5+2.0/rot[0]*Integer.parseInt(r.substring(0,1))),0.5);
		tiles1[1]=new Tile(proto[0],Integer.parseInt(c.substring(1, 2)),m.substring(1, 2).equals("1"),new Rn(new double[]{0,0.5}),2*Math.PI/rot[0]*Integer.parseInt(r.substring(1,2)),0.5);
		tiles1[2]=new Tile(proto[0],Integer.parseInt(c.substring(2, 3)), m.substring(2, 3).equals("1"),new Rn(new double[]{0,-0.5}),2*Math.PI/rot[0]*Integer.parseInt(r.substring(2,3)),0.5);
		tiles1[3]=new Tile(proto[0],Integer.parseInt(c.substring(3, 4)),m.substring(3, 4).equals("1"), new Rn(new double[] {1.5,0}),Math.PI*(0.5+2.0/rot[0]*Integer.parseInt(r.substring(3,4))),0.5);
	
	return new Tile[][] {tiles1};
	}
	public static Tile[][] chairstructions(String c,String m, String r)
	{//1==3
		Tile[] tiles1=new Tile[4];
		tiles1[0]=new Tile(proto[0],Integer.parseInt(c.substring(0, 1)),m.substring(0, 1).equals("1"),new Rn(new double[]{-0.5,0}),Math.PI*(2.0/rot[0]*Integer.parseInt(r.substring(0,1))),infl);
		tiles1[1]=new Tile(proto[0],Integer.parseInt(c.substring(1, 2)),m.substring(1, 2).equals("1"),new Rn(new double[]{1,-1.5}),Math.PI*(-0.5+2.0/rot[0]*Integer.parseInt(r.substring(1,2))),infl);
		tiles1[2]=new Tile(proto[0],Integer.parseInt(c.substring(2, 3)),m.substring(2, 3).equals("1"),new Rn(new double[]{0.5,0}),Math.PI*(2.0/rot[0]*Integer.parseInt(r.substring(2,3))),infl);
		tiles1[3]=new Tile(proto[0],Integer.parseInt(c.substring(3, 4)),m.substring(3, 4).equals("1"),new Rn(new double[]{1,1.5}),Math.PI*(0.5+2.0/rot[0]*Integer.parseInt(r.substring(3,4))),infl);

		return new Tile[][] {tiles1};
	}
	public static Tile[][] repStructions(String c)
	{
		double h=Math.sqrt(3)/2;
		double[][] centers= {{-1.5,-h},{1.5,-h},{0.5,-h},{-1,0}};
		boolean[] mir= {true,true,true,false};
		double[] ro= {1,1,0,2.0/3};
		Tile[] tiles=new Tile[4];
		for(int i=0;i<tiles.length;i++)
		tiles[i]=new Tile(proto[0], Integer.parseInt(c.substring(i,i+1)),mir[i],new Rn(centers[i]), Math.PI*ro[i],0.5);

		return new Tile[][] {tiles};
	}
	public static Tile[][] fishStructions(String c,String m)
	{
		double h=Math.sqrt(3)/3;
		double[][] centers= {{-4*h,0},{-4*h,0},{-4*h,0},{-2*h,0},{-2*h,0},{-2*h,0},{2*h,0},{2*h,0},{2*h,0}};
		double[] ro= {0,2.0/3,-2.0/3,1,1.0/3,-1.0/3,0,2.0/3,-2.0/3};
		Tile[] tiles=new Tile[9];
		for(int i=0;i<tiles.length;i++)
			tiles[i]=new Tile(proto[0], Integer.parseInt(c.substring(i,i+1)),m.substring(i, i+1).equals("1"),new Rn(centers[i]), Math.PI*ro[i],1.0/3);

		return new Tile[][] {tiles};
	}
	public static Prototile[] trapezTile()
	{
Rn[] corners= {new Rn(new double[] {0.000,0.000}), new Rn(new double[] {0,2.000}), new Rn(new double[] {4/Math.sqrt(3),2}),new Rn(new double[] {2/Math.sqrt(3),0})};
		
		return new Prototile[] {new Prototile(corners)};
	}
	 
	public static Tile[][] trapezIns(String c)
	{
		min=new double[] {0,0};max=new double[] {1,1};
		double l=1/Math.sqrt(3);
	
		double[][] centers= {{0,1},{0,1},{1.5*l,1.5},{1.5*l,1.5}};
		boolean[]mir= {true,false,true,false};
		double[] ro= {0,0,-1.0/3,2.0/3};
		Tile[] tiles=new Tile[4];
		for(int i=0;i<tiles.length;i++)
			tiles[i]=new Tile(proto[0], Integer.parseInt(c.substring(i,i+1)),mir[i],new Rn(centers[i]), Math.PI*ro[i],0.5);

			return new Tile[][] {tiles};
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
