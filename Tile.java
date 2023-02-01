import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tile 
{
	int rotcount;
	Rn[] corners;
	int color,c2,black=Color.black.getRGB(), linethickness=5;
	boolean mirrored;
	Rn shift;
	double hangle, size;
	Prototile type;//1. corner corresponds to 1.corner of prototype etc
	static int dim=2;
	static double mash,//For evolveColor
			w=0.5;//For Line Fractals: How much is a straight line?
	static int exactness=1;
	static boolean evoColor=false,
			firstTile=true;
	static int[][] nghb=new int[2][2];
	public Tile(Prototile pr, int col)
	{
		corners=copy(pr.corners);
		color=col;
		c2=col+1;
		type=pr;
		mirrored=false;
		shift=Rn.zero(dim);
		hangle=0;
		size=1;
		rotcount=0;
	}
	public Tile(Prototile pr, int col, boolean mir, Rn vector,double phi, double scale)
	{
		//System.out.println("scale="+scale);
		color=col;c2=col+1;
		mirrored=mir;
		shift=vector;
		hangle=phi; 
		size=scale;
		type=pr;
		rotcount=0;
		corners=copy(pr.corners);Matrix matrix=tmatrix(hangle,size, mirrored);
		//matrix.print();vector.print();
		for(int i=0;i<corners.length;i++)
		{
			//System.out.print("From "); pr.corners[i].print();
			corners[i]=matrix.times(pr.corners[i]);
			//System.out.print(" to ");corners[i].print();
			corners[i]=corners[i].add(shift);
			//System.out.print(" to ");corners[i].print();System.out.println();
		}
	}
	public Tile copy()
	{
		Tile out=new Tile(type,color,mirrored,shift.copy(),hangle,size);
		out.rotcount=rotcount;
		return out;
	}
	public int getV()
	{
		return corners.length;
	}
	public void setColor(int n,int l)
	{
		color=n;
		c2=(n+1)%l;
	}
	public Prototile prototype()
	{
		return type;
	}
	public int getColor()
	{
		return color;
	}
	public Rn getCorner(int i)
	{
		return corners[i];
	}
	//translates, rotates and shrinks the tile s.t. the first 2 vertices land on pos Will not currently work for more than 2 dimensions!
	public void transform(Rn[] pos)
	{
		translate(pos[0].substract(corners[0]));
		hrotate(corners[0],corners[1], pos[1]);
		stretch(corners[0],corners[0].distance(pos[1])/corners[0].distance(corners[1]));
	}
	public void transform(Matrix a)
	{
		
	}
	public void hrotate(Rn center,Rn from,Rn to)
	{
		if(center.get(0)==to.get(0)&&center.get(1)==to.get(1))
		{
			return;
		}
		else
		{
			Rn[] cor=new Rn[getV()];
			double[][]rot=new double[dim][dim];
			Rn vec0=from.add(center.times(-1));
			Rn vec1=to.add(center.times(-1));
			double theta=vec1.hangle(0,1)-vec0.hangle(0,1);//System.out.print(theta);
			rot[0][0]=Math.cos(theta);rot[0][1]=-Math.sin(theta);
			rot[1][0]=Math.sin(theta);rot[1][1]=Math.cos(theta);
			if(dim==3)
				rot[2][2]=1;
			for(int i=0;i<getV();i++)
			{
				cor[i]=corners[i].substract(center).transform(rot).add(center);
			}
			corners=cor;
		}
	}

	public void draw(BufferedImage image, int[][]colors, double[]min,double[]max,int[][]doublecheck)//to be reconsidered for concave tiles
	{
		int width=image.getWidth(),height=image.getHeight();
		boolean[][] newcol=new boolean[width][height];
		int c=(color%colors.length+colors.length)%colors.length;
			
		c= new Color(colors[c][0],colors[c][1],colors[c][2]).getRGB();//System.out.println("true corner "+corners[0].c[0]+", "+corners[0].c[1]);
		int[] one=corners[0].toGrid(min,max,image.getWidth(),image.getHeight()),two,three;
		//System.out.println("corner"+one[0]+", "+one[1]);
		//System.out.println("color nr. "+c);
		for (int i=1;i<corners.length-1;i++)
		{
			two=corners[i].toGrid(min,max,image.getWidth(),image.getHeight());
			three=corners[i+1].toGrid(min,max,image.getWidth(),image.getHeight());
			if(evoColor)
			{
				drawTriangle(one,two,three,image,
						mash(c2,color,mash),doublecheck,newcol);
			}
			else
			drawTriangle(one,two,three,image,c,doublecheck,newcol);
			//int xmin=Math.min(one[0],Math.min(two[0], three[0]));
			/*int hmax=Math.max(Math.abs(two[0]-one[0]),Math.abs( two[1]-one[1]));
			for(int h=0;h<hmax;h++)
			{
				int jmax=Math.max(Math.abs(h*(two[0]-three[0])/hmax), Math.abs(h*(two[1]-three[1])/hmax))*exactness;
				for(int j=0;j<jmax;j++)
				{
					int x=one[0]+h*(j*(three[0]-two[0])/jmax+two[0]-one[0])/hmax,y= one[1]+h*(j*(three[1]-two[1])/jmax+two[1]-one[1])/hmax;
					
					try{image.setRGB(x,y,c);
					if(!newcol[x][y])doublecheck[x][y]++;
					newcol[x][y]=true;}
					catch(ArrayIndexOutOfBoundsException e)
					{
						//System.out.println("outofbounds");
					}
				}
			}*/
			
		}
		
	}
	public void draw(BufferedImage image, int[][] colors, double[]min,double[]max, double mashfactor)//to be reconsidered for concave tiles
	{
		//print();
		int c=(color%colors.length+colors.length)%colors.length;
		c=new Color(colors[c][0],colors[c][1],colors[c][2]).getRGB();//
		
		//System.out.println("true corner "+corners[0].c[0]+", "+corners[0].c[1]);
		int[] one=corners[0].toGrid(min,max,image.getWidth(),image.getHeight()),two,three;
		//System.out.println("corner"+one[0]+", "+one[1]);
		//System.out.println("color nr. "+c);
		for (int i=1;i<corners.length-1;i++)
		{
			two=corners[i].toGrid(min,max,image.getWidth(),image.getHeight());
			three=corners[i+1].toGrid(min,max,image.getWidth(),image.getHeight());
			boolean nondeg=false;
			
			drawTriangle(one,two,three,image,c,mashfactor,nondeg);
			//if (!nondeg){drawTriangle(two,three,one,image,c,mashfactor,nondeg);if(!nondeg)	drawTriangle(three,one,two,image,c,mashfactor,nondeg);}


			/*int hmax=Math.max(Math.abs(two[0]-one[0]),Math.abs( two[1]-one[1]));
			for(int h=0;h<hmax;h++)
			{
				int jmax=Math.max(Math.abs(h*(two[0]-three[0])/hmax), Math.abs(h*(two[1]-three[1])/hmax));
				for(int j=0;j<jmax;j++)
				{
					int x=one[0]+h*(j*(three[0]-two[0])/jmax+two[0]-one[0])/hmax, y=one[1]+h*(j*(three[1]-two[1])/jmax+two[1]-one[1])/hmax;
					try{image.setRGB(x,y ,mash(c,image.getRGB(x,y),mashfactor));}
					catch(ArrayIndexOutOfBoundsException e)
					{
						//System.out.println("outofbounds");
					}
				}
			}*/
		}
		if(corners.length==2)
		{
			two=corners[1].toGrid(min,max,image.getWidth(),image.getHeight());
			System.out.println("rotcount="+rotcount);
			if(rotcount%2==1)
			{
				int[]temp=one.clone();
				one=two.clone();
				two=temp;
			}
			//System.out.println("true corner "+corners[1].c[0]+", "+corners[1].c[1]);
			//System.out.println("("+one[0]+", "+one[1]+"), ("+two[0]+", "+two[1]+")");
			drawLine(one,two,image,c,mashfactor);
		}
	}

	public static int mash(int njucolor, int oldcolor, double mashfactor)
	{
		Color old=new Color(oldcolor),nju=new Color(njucolor);
		int[]nc=new int[] {nju.getRed(),nju.getGreen(),nju.getBlue()},
				oc=new int[] {old.getRed(),old.getGreen(),old.getBlue()};
		int red=(int)Math.round(nc[0]*mashfactor+oc[0]*(1.0-mashfactor));
		int green=(int)Math.round(nc[1]*mashfactor+oc[1]*(1.0-mashfactor));
		int blue=(int)Math.round(nc[2]*mashfactor+oc[2]*(1.0-mashfactor));
		
		return new Color(red,green,blue).getRGB();
	}
	public void translate(Rn vector)
	{
		for(int i=0;i<getV();i++)
		{
			corners[i]=corners[i].add(vector);
		}
	}
	public void stretch(Rn center,double factor)
	{
		for(int i=0;i<getV();i++)
		{
			corners[i]=corners[i].substract(center).times(factor).add(center);
		}
	}
	//With what matrix do I have to transform the prototile to get the tile with these parameters?
	public static Matrix tmatrix(double hangle, double size, boolean mirrored)
	{
		
		Matrix matrix=Matrix.idmatrix(dim).copy();
		
		
		matrix=Matrix.hrotate(hangle, 1, 0, dim);
		if(mirrored)
		{
			matrix.mirror(1);
		}
		
		matrix=matrix.times(size);
		//System.out.print("size: "+size);matrix.print();
		
		//System.out.print("rot");rot.print();
		
	//	matrix.print();
		return matrix;
	}
	
	//Transform according to prototile->tile
	public Tile transform(Tile tile)
	{
		Matrix matrix= tmatrix(tile.hangle,tile.size,tile.mirrored);
		
		int col=color+tile.color;
		boolean mirr=!((mirrored&&tile.mirrored)||!(mirrored||tile.mirrored));
		//System.out.println("Pretile mirrored="+tile.mirrored+", tile mirorred="+mirrored+"-->mirrored="+mirr);
		int m=0;if(tile.mirrored)m=1;
		double hang=hangle*Math.pow(-1, m)+tile.hangle;
		double siz=size*tile.size;
		Rn shif=matrix.times(shift).add(tile.shift);
		Tile out= new Tile(type,col,mirr,shif,hang,siz);
		out.rotcount=rotcount+tile.rotcount;
		return out;
	}
	public Rn[] copy(Rn[] vectors)
	{
		Rn[] out=new Rn[vectors.length];
		for(int i=0;i<vectors.length;i++)
		{
			out[i]=vectors[i].copy();
		}
		return out;
	}
	public void print()
	{
		int n=corners.length;
		Rn[] v=corners;
		System.out.print("Tile: (");
		String[] text=new String[n];
		for(int i=0;i<n;i++)
		{
			text[i]="{";
			for(int j=0;j<2;j++)
			{
				
					text[i]=text[i]+v[i].c[j];
					if(j==1)
						text[i]+="}";
					else
						text[i]+=", ";
			}
			System.out.print(text[i]);
			if(i==n-1)
				System.out.print(") ");
			else
				System.out.print(", ");
		}
		if(mirrored) System.out.println("mirrored");
		System.out.println();
	}
	
	public void drawTriangle(int[]v1, int[]v2,int[]v3,BufferedImage image, int color, double mashfactor,boolean nondeg)
	{
		int[]one=v1,two=v2,three=v3;
		if(v1[1]>v2[1])
		{
			if(v1[1]<v3[1])
			{
				two=v1;one=v2;
			}
			else if(v3[1]<v2[1])
			{
				one=v3;three=v1;
			}
			else
			{
				one=v2;two=v3;three=v1;
			}
		}
		else if(v1[1]>v3[1])
		{
			one=v3;two=v1;three=v2;
		}
		else if(v2[1]>v3[1])
		{
			two=v3;three=v2;
		}
		int sign=(int)Math.signum((two[0]-one[0])*(three[1]-one[1])-(three[0]-one[0])*(two[1]-one[1]));//System.out.print("drawTriangle");
		//System.out.println(one[0]+","+two[0]+","+three[0]);
		//if(two[0]<three[0])sign=-1;
		if(sign==0) {}//System.out.println("sign==0");
		else
		{
		if(evoColor) {int col2=new Color(EvolveColor.colors[c2][0],EvolveColor.colors[c2][1],EvolveColor.colors[c2][2]).getRGB();
		color=mash(col2,color,mashfactor);mashfactor=1;}
		for(int y=one[1];y<two[1];y++)
		{
			int x1=one[0]+(three[0]-one[0])*(y-one[1])/(three[1]-one[1]),x2=one[0]+(two[0]-one[0])*(y-one[1])/(two[1]-one[1]);
			//sign=(int) Math.signum(x2-x1);//if(two[1]==three[1])//System.out.println(x1+"<"+x2);
			for(int x=x1;sign*x<sign*(x2)+1;x=x+sign)
			{
				nondeg=false;
				//if(two[1]==three[1])System.out.print(".");
				try{
					
					image.setRGB(x, y, mash(color,image.getRGB(x, y),mashfactor));//System.out.print(",");
				}
				catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		
		for(int y=two[1];y<three[1];y++)
		{
			int x1=one[0]+(three[0]-one[0])*(y-one[1])/(three[1]-one[1]), x2=two[0]+(three[0]-two[0])*(y-two[1])/(three[1]-two[1]);//sign=(int) Math.signum(x2-x1);
			for(int x=x1;sign*x<sign*(x2)+1;x=x+sign)
			{
				nondeg=true;
				try{image.setRGB(x, y,  mash(color,image.getRGB(x, y),mashfactor));}catch(ArrayIndexOutOfBoundsException e){}
			}
		}}//System.out.println();
	}
	
	public void drawTriangle(int[]v1,int[]v2, int[] v3, BufferedImage image, int color,int[][]doublecheck, boolean[][]newcol)
	{
		int[]one=v1,two=v2,three=v3;
		if(v1[1]>v2[1])
		{
			if(v1[1]<v3[1])
			{
				two=v1;one=v2;
			}
			else if(v3[1]<v2[1])
			{
				one=v3;three=v1;
			}
			else
			{
				one=v2;two=v3;three=v1;
			}
		}
		else if(v1[1]>v3[1])
		{
			one=v3;two=v1;three=v2;
		}
		else if(v2[1]>v3[1])
		{
			two=v3;three=v2;
		}
		int sign=(int)Math.signum((two[0]-one[0])*(three[1]-one[1])-(three[0]-one[0])*(two[1]-one[1]));//System.out.print("drawTriangle");
		//System.out.println(one[0]+","+two[0]+","+three[0]);
		//if(two[0]<three[0])sign=-1;
		if(sign==0) {}//System.out.println("sign==0");
		else
		{for(int y=one[1];y<two[1];y++)
		{
			int x1=one[0]+(three[0]-one[0])*(y-one[1])/(three[1]-one[1]),x2=one[0]+(two[0]-one[0])*(y-one[1])/(two[1]-one[1]);
			//sign=(int) Math.signum(x2-x1);//if(two[1]==three[1])//System.out.println(x1+"<"+x2);
			for(int x=x1;sign*x<sign*(x2)+1;x=x+sign)
			{
				//if(two[1]==three[1])System.out.print(".");
				try{image.setRGB(x, y, color);
				if(!newcol[x][y]) {doublecheck[x][y]++;
				newcol[x][y]=true;}}
				catch(ArrayIndexOutOfBoundsException e){}
			}
		}//System.out.println();
		//sign=1;if(one[0]>two[0])sign=-1;
		for(int y=two[1];y<three[1];y++)
		{
			int x1=one[0]+(three[0]-one[0])*(y-one[1])/(three[1]-one[1]), x2=two[0]+(three[0]-two[0])*(y-two[1])/(three[1]-two[1]);//sign=(int) Math.signum(x2-x1);
			for(int x=x1;sign*x<sign*(x2)+1;x=x+sign)
			{
				try{image.setRGB(x, y, color);	if(!newcol[x][y]) {doublecheck[x][y]++;
				newcol[x][y]=true;}}catch(ArrayIndexOutOfBoundsException e){}
			}
		}}
	}

	public void drawLine(int[] one, int[] two, BufferedImage image, int c, double mashfactor) 
	{
		//System.out.println("color nr. "+color+" is"+c);
		int[] dist= {Math.abs(two[0]-one[0]),Math.abs(two[1]-one[1])};
		int i=0;if(dist[1]>dist[0])i=1;
		
		//straight part:
		for(int j=(int) (dist[i]/2*(1-w));j<dist[i]/2*(1+w);j++)
		{
			int[]coordinates=new int[2];
			coordinates[i]=one[i]+j*(two[i]-one[i])/dist[i];
			coordinates[1-i]=one[1-i]+j*(two[1-i]-one[1-i])/dist[i];
		//	System.out.print(", Printing "+coordinates[0]+","+coordinates[1]+", color:"+color);
			try 
			{
				for(int k=-linethickness;k<linethickness;k++)
				{
					int l0=(int)Math.sqrt(linethickness*linethickness-k*k);
					for(int l=-l0;l<l0;l++)
						image.setRGB(coordinates[0]+k,coordinates[1]+l,c);
				}
					
			}catch(ArrayIndexOutOfBoundsException e) {/*System.out.println("couldn't print at"+coordinates[0]+","+coordinates[1]);*/}
		}
		if(!firstTile) {
		//curved part:
		double r2=0;double[]angle=new double[2];
		int[]point1=new int[2],point2=new int[2],center=new int[2],dir=new int[2];
		double d=Math.sqrt(Math.pow(one[0]-point2[0],2)+Math.pow(one[1]-two[1], 2)),
				r=d*Math.tan(0.5*Math.acos(((point2[0]-one[0])*(point1[0]-one[0])+(point2[1]-one[1])*(point1[1]-one[1]))/(d*d)));
		for(int k=0;k<2;k++)
		{
			point1[k]=(int) (one[k]*(w+1)/2+nghb[0][k]*(1-w)/2);
			point2[k]=(int) (one[k]*(w+1)/2+two[k]*(1-w)/2);
			dir[k]=(point1[k]+point2[k]-2*one[k]);
		}
		for(int k=0;k<2;k++)
			center[k]=(int) (one[k]+dir[k]/Math.sqrt(dir[0]*dir[0]+dir[1]*dir[1])*r);
		angle[0]=Math.atan2(point1[1]-center[1], point1[0]-center[0]);
		angle[1]=Math.atan2(point2[1]-center[1], point2[0]-center[0]);System.out.println("angle: "+angle[1]);
		double ang=Math.atan2(one[1]-center[1], one[0]-center[0]);
		int sign;double start,end,step=0.5/r;
		if ((ang-angle[0]<=Math.PI/2&&(ang-angle[0])>=0)||((ang-angle[0])<=-Math.PI&&(ang-angle[0])>=-3*Math.PI/2))
		{start=angle[0]; end=angle[1];}
		else {start=angle[1];end=angle[0];}
		if(end-start<0)end+=2*Math.PI;
		System.out.println("center=("+center[0]+", "+center[1]+"), r="+r+", start="+start+", end="+end);
		for(double k=start;k<end;k+=step)
		{
			try 
			{
				for(int m=-linethickness;m<linethickness;m++)
				{
					int l0=(int)Math.sqrt(linethickness*linethickness-m*m);
					for(int l=-l0;l<l0;l++)
						image.setRGB((int)(center[0]+r*Math.cos(k))+m,(int)(center[1]+r*Math.sin(k))+l,c);
				}
					
			}catch(ArrayIndexOutOfBoundsException e) {/*System.out.println("couldn't print at"+coordinates[0]+","+coordinates[1]);*/}
		
		}}
		
	}
	private void print(int[] point) {
		System.out.print("grid point: ("+point[0]);
		for(int i=1;i<point.length;i++)
		{
			System.out.print(", "+point[i]);
		}
		System.out.println(")");
	}
	public double[][] minmax() 
	{
		double[] outx= {corners[0].c[0],corners[0].c[0]},
				outy={corners[0].c[1],corners[0].c[1]};
		for(int i=1;i<corners.length;i++)
		{
			outx[0]=Math.min(outx[0],corners[i].c[0]);
			outx[1]=Math.max(outx[1],corners[i].c[0]);
			outy[0]=Math.min(outy[0],corners[i].c[1]);
			outy[1]=Math.max(outy[1],corners[i].c[1]);
		}
		return new double[][] {outx,outy};
	}
	
	public static void print(Tile[]tiles)
	{
		for(int i=0;i<tiles.length;i++)
		{
			tiles[i].shift.print();
			System.out.println(","+tiles[i].hangle);
		}
	}
}
