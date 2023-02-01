
public class Instructions 
{
	Prototile[]proto;
	 int[] length,rot,mir;
	int l;
	public Instructions(TileSystems ts)
	{
		proto=ts.proto;
		rot=ts.rot;
		mir=ts.mir;
	}

	public Tile[][] originalPenrose(int[] c,int[] m,int[] r)//r nur für penta1&pentagram!coloring symmetric
	{
		length=new int[] {6,7,8,3,11,7};
		l=27;
		
		double phi=(Math.sqrt(5)+1)/2,sqrt=Math.sqrt(5);
		double infl=1/(phi*phi);
		Tesselation.rotimage=new int[6][11];Tesselation.mirimage=new int[6][11];

		Tile[] penta1=new Tile[6];
		int[]pi= {0,1,1,1,1,1};
		int[]col= {0,1,1,1,1,1};
		Rn[] loc= {new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
		new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2})};
		double[] ang={1,.8,.4,0,-.4,-.8};
		for(int i=0;i<penta1.length;i++)
		{	int j=(i+1)%6;if(j<2)j=1-j;
			int k=i; if(k%3!=0)k=6-k;
			Tesselation.rotimage[0][i]=j;
			Tesselation.mirimage[0][i]=k;
			penta1[i]=new Tile(proto[pi[i]],c[col[i]],m[i]==1,loc[i],Math.PI*(ang[i]+m[col[i]]+2*r[col[i]]/rot[0]),infl);
		}
		int j=penta1.length,k=penta1.length;
		
		Tile[] penta2=new Tile[7];
		pi=new int[] {0,2,1,1,1,2,3};
		col=new int[] {2,3,4,5,4,3,6};
		loc=new Rn[]{new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}), 
				new Rn(new double[] {0,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		ang=new double[] {1,.4,.4,0,-.4,-.4,0};
		
		for(int i=0;i<penta2.length;i++) 
		{
			int l=i;if(l%3!=0)l=6-l;
			Tesselation.rotimage[1][i]=i;Tesselation.mirimage[1][i]=l;
			penta2[i]=new Tile(proto[pi[i]],c[col[i]],m[col[i]]==1,loc[i],Math.PI*(ang[i]+m[col[i]]),infl);
		}
		
		j=j+penta2.length;
		
		Tile[] penta3=new Tile[8];
		pi=new int[] {0,2,2,1,2,2,3,3};
		col=new int[] {7,8,9,10,9,8,11,11};
		loc=new Rn[]{new Rn(new double[] {0,0}),new Rn( new double[]{0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}), 
				new Rn(new double[] {phi*phi/4,-Math.sqrt(phi/sqrt)/4}),new Rn(new double[] {-phi*phi/4,-Math.sqrt(phi/sqrt)/4})};
		ang=new double[] {1,-.8,0,0,0,.8,-.4,.4};
		
		for(int i=0;i<penta3.length;i++)
		{
			int l=i;if(l%3!=0&&l<6)l=6-l; else if(l>5)l=13-l;
			Tesselation.rotimage[2][i]=i;Tesselation.mirimage[2][i]=l;
			penta3[i]=new Tile(proto[pi[i]],c[col[i]],m[i+j]==1,loc[i],Math.PI*(ang[i]+m[col[i]]),infl);
		}
		
		j=j+penta3.length;
		
		Tile[] diamond=new Tile[3];
		pi=new int[] {5,2,4};
		col=new int[] {12,13,14};
		loc=new Rn[]{new Rn(new double[] {0,-Math.sqrt(phi/sqrt)/2}),new Rn(new double[] {0,(2-sqrt)/2*Math.sqrt(phi/sqrt)}),new Rn(new double[] {0,Math.sqrt(phi/sqrt)/2})};
		ang=new double[] {1,0,0};
		for(int i=0;i<diamond.length;i++)
		{
	
			Tesselation.rotimage[3][i]=i;Tesselation.mirimage[3][i]=i;
			diamond[i]=new Tile(proto[pi[i]],c[col[i]],m[c[i]]==1,loc[i],Math.PI*(ang[i]+m[c[i]]),infl);
		}
		
		j=j+diamond.length;
		
		Tile[] pentagram=new Tile[11];
		pi=new int[] {4,2,2,2,2,2,5,5,5,5,5};
		col=new int[] {15,16,17,18,17,16,19,20,21,20,19};

		loc=new Rn[] {new Rn(new double[] {0,0}),new Rn(new double[] {0.5/phi,-Math.sqrt(phi/sqrt)/2}), new Rn(new double[] {0.5,1/(2*phi*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-0.5,1/(2*phi*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5/phi,-Math.sqrt(phi/sqrt)/2}),
				new Rn(new double[]{0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2}), new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-phi/2,1/(2*Math.sqrt(sqrt*phi))}), new Rn(new double[] {-0.5,-Math.sqrt(Math.pow(phi, 3)/sqrt)/2})};
		ang=new double[] {1,-0.2,-0.6,1,0.6,.2,.8,.4,0,-.4,-.8};
		
		for(int i=0;i<pentagram.length;i++)
		{
			int l=(i)%5+(int)Math.signum(i)+(i-1)/5,n=6-(i-1)%5+(i-1)/5*5;if(i==0)n=0;
			Tesselation.rotimage[4][i]=l;Tesselation.mirimage[4][i]=n;
			pentagram[i]=new Tile(proto[pi[i]],c[col[i]],m[col[i]]==1,loc[i],Math.PI*(ang[i]+m[col[i]]+2*r[col[i]]/rot[0]),infl);
		}
		j=j+pentagram.length;
		
		Tile[] boat=new Tile[7];
		pi=new int[] {4,2,2,2,5,5,5};
		col=new int[] {22,23,24,23,25,26,25};

		loc=new Rn[] {new Rn(new double[] {0,0}), new Rn(new double[] {.5,1/(2*Math.sqrt(sqrt*phi)*phi)}),
				new Rn(new double[] {0,1/Math.sqrt(phi*sqrt)}), new Rn(new double[] {-.5,1/(2*phi*Math.sqrt(sqrt*phi))}),new Rn(new double[] {phi/2,1/(2*Math.sqrt(sqrt*phi))}),
				new Rn(new double[] {0,Math.sqrt(phi/sqrt)}), new Rn(new double[] {-(phi)/2,1/(2*Math.sqrt(sqrt*phi))})};
		ang=new double[] {1,-.6,1,.6,.4,0,-.4};
		for(int i=0;i<boat.length;i++)
		{
			Tesselation.rotimage[5][i]=i; Tesselation.mirimage[5][i]=i;
			boat[i]=new Tile(proto[pi[i]],c[col[i]],m[col[i]]==1,loc[i],Math.PI*(ang[i]+m[col[i]]),infl);
		}
		
		return new Tile[][] {penta1,penta2,penta3,diamond,pentagram,boat};
	}

	public Tile[][] pinwheel(int[] c,int[] m,int[] r)
	{
		l=5;
		length=new int[] {5};
		Tesselation.rotimage=new int[1][5];Tesselation.mirimage=new int[1][5];
		double infl=1/Math.sqrt(5);
		double alpha=Math.atan(2),beta=Math.atan(0.5);
		Tile[] out=new Tile[5];
		Rn[] loc= {new Rn(new double[] {0.8,1.6}),new Rn(new double[] {0.4, 0.8}),new Rn(new double[] {0.4,0.8}), new Rn(new double[] {2.4,0.8}), new Rn(new double[] {2.4,0.8})};
		double[] ang= {Math.PI-alpha,beta,beta,beta-Math.PI,beta};
		Tesselation.nullangle=new double[][] {ang};
		boolean[] mir= {true,true,false,false,true};
		int k=0,i=0;
		while(k<5)
		{	Tesselation.rotimage[0][k]=k;Tesselation.mirimage[0][k]=k;
			out[i]=new Tile(proto[0],c[k],mir[k],loc[k],ang[k],infl);
			
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

	public Tile[][] chair(int[] c,int[] m, int[] r)
	{
		l=4;
		length=new int[] {4};
		Tesselation.rotimage=new int [][] {{0,1,2,3}};Tesselation.mirimage=new int[][] {{0,3,2,1}};
		//1==3
		double[]angle= {0,-0.5,0,0.5};
		Rn[] loc= {new Rn(new double[]{-0.5,0}),new Rn(new double[]{1,-1.5}),new Rn(new double[]{0.5,0}),new Rn(new double[]{1,1.5})};
		Tile[] tiles1=new Tile[4];
		for(int i=0;i<4;i++)
		{
		tiles1[i]=new Tile(proto[0],c[i],m[i]==1,loc[i],Math.PI*(angle[i]),0.5);
		}

		return new Tile[][] {tiles1};
	}
	
	public Tile[][]reptrile(int[] c, int[] m, int[] r)
	{
		length=new int[] {4,7};
		l=11;
		double sqrt= Math.sqrt(3), infl=0.4,d=1;
		Tile[] triangle=new Tile[4];//4
		int[]pi= {1,1,1,0};int doneTriangles=4;
		Rn[] loc= {new Rn(new double[] {sqrt/3,-.6}),new Rn(new double[] {-sqrt*7/15,-0.2}),new Rn(new double[] {sqrt*2/15,.8}),new Rn(new double[]{0,0})};
		double[] ang= {0.5,-10.0/12,-2.0/12,r[3]*2.0/3};
		boolean[]mir= {false,false,false,m[3]==1};
		Tesselation.rotimage=new int[][] {{1,2,0,3},{0,1,2,3,4,5,6}};
		Tesselation.mirimage=new int[][] {{0,1,2,3},{0,1,2,3,4,5,6}};
		for(int i=0;i<triangle.length;i++)
		{
			
			triangle[i]=new Tile(proto[pi[i]],c[i],mir[i],loc[i],Math.PI*(ang[i]),infl);
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
			sled[i]=new Tile(proto[pi[i]],c[i+doneTriangles],m[i+doneTriangles]==1,loc[i],Math.PI*(ang[i]+r[i+doneTriangles]*2.0/3),infl);
			//sled[i].print();
		}
		return new Tile[][] {triangle,sled};
	}
	
	public Tile[][] square(int[]c,int[]m, int[]r)
	{
		length=new int[]{4};
		l=4;
		Tesselation.rotimage=new int[][] {{1,2,3,0}};
		Tesselation.mirimage=new int[][] {{1,0,3,2}};
		Tile[]tiles1=new Tile[4];
		double[][]centers=new double[][] {{-0.5,-0.5},{-0.5,0.5},{0.5,0.5},{0.5,-0.5}};
		double infl=0.5;
	
	  for(int i=0;i<4;i++)
		{
			tiles1[i]=new Tile(proto[0],c[i],m[i]==1,new Rn(centers[i]),Math.PI/2*r[i],0.5);
		}
	  return new Tile[][] {tiles1};
	}
	public Tile[][] table(int[]c,int[]m, int[]r)
	{
		length=new int[]{4};
		l=4;
		Tesselation.rotimage=new int[][] {{3,2,1,0}};
		Tesselation.mirimage=new int[][] {{0,2,1,3}};
		Tile[]tiles1=new Tile[4];
		double[][]centers=new double[][] {{-1.5,0},{0,0.5},{0,-0.5},{1.5,0}};
		double[]angle=new double[] {0.5,0,1,-0.5};
		double infl=0.5;
	
	  for(int i=0;i<4;i++)
		{
			tiles1[i]=new Tile(proto[0],c[i],m[i]==1,new Rn(centers[i]),Math.PI*(r[i]+angle[i]),0.5);
		}
	  return new Tile[][] {tiles1};
	}
	public Tile[][] dragonTrigon(int[] c, int[] m,int[] r)
	{
		double infl=1/Math.sqrt(3);
		Tesselation.rotimage=new int[][] {{0,1,2},{1,2,0}};
		Tesselation.mirimage=new int[][] {{1,0,2},{1,0,2}};
		length=new int[] {3,3};
		l=6;
		double sqrt=Math.sqrt(3);
		Tile[]dragon=new Tile[3];
		int[]pi= {0,  0,  1};
		Rn[] loc= {new Rn(new double[] {-0.5,sqrt/6}),  new Rn(new double[] {-0.5,-sqrt/6}),  new Rn(new double[] {1.0/3,0})  };
		double[] ang= {5.0/6,  -5.0/6,  2.0*r[3]/3  };
		
		for(int i=0;i<dragon.length;i++)
		{
			dragon[i]=new Tile(proto[pi[i]],c[i],m[i]==1,loc[i],Math.PI*(ang[i]),infl);
		}
				Tile[]trigon=new Tile[3];
		pi=new int[] {0,0,0};
		loc=new Rn[] {new Rn(new double[] {-sqrt/6,0.5}),  new Rn(new double[] {-sqrt/6,-0.5}),  new Rn(new double[] {sqrt/3,0})  };
		ang=new double[] {-2.0/3, 2.0/3, 0};
		int l=dragon.length;
		for(int i=0;i<trigon.length;i++)
		{
			//System.out.println("i="+i+", pi[i]="+pi[i]);
			trigon[i]=new Tile(proto[pi[i]],c[i+l],m[i+l]==1,loc[i],Math.PI*(ang[i]),infl);
			trigon[i].print();
		}
		
		return new Tile[][] {dragon,trigon};
	}
	
	public Tile[][] penrose(int[] c,int[] m,int[] r)
	{
		length=new int[] {3,2};
		l=5;
		
		double phi=(1+Math.sqrt(5))/2;
		Tesselation.rotimage=new int[][] {{0,1,2},{0,1}};
		Tesselation.mirimage=new int[][] {{0,1,2},{0,1}};
		Tile[]wide=new Tile[3];
		int[]pi= {0/*,0,1*/,0,1};
		Rn[] loc= {new Rn(new double[] {-phi/4,Math.sqrt(3-phi)/4})/*,new Rn(new double[] {-phi/4,-Math.sqrt(3-phi)/4}),
				new Rn(new double[] {1/(4*phi),-Math.sqrt(phi+2)/4})*/, new Rn(new double[] {0.5/phi,0}),
				new Rn(new double[] {1/(4*phi),Math.sqrt(phi+2)/4})};//ToDo
		int[]mi={0,1,0};
		double[] ang= {0.8/*,-0.8,-0.2*/,1,0.2};
		double infl=1/phi;
		for(int i=0;i<wide.length;i++)
			{
			wide[i]=new Tile(proto[pi[i]],c[i],m[i]+mi[i]==1,loc[i],Math.PI*(ang[i]+2.0/rot[0]*r[i]),infl);
		}
		
		Tile[]thin=new Tile[2];
		pi=new int[] {/*0,1,*/1,0};
		loc=new Rn[] {/*new Rn(new double[] {-1/(4*phi),-Math.sqrt(2+phi)/4}), new Rn(new double[] {0.25,-Math.sqrt(phi+2)/(4*phi*phi)}),*/
					new Rn(new double[] {0.25,Math.sqrt(phi+2)/(4*phi*phi)}), new Rn(new double[] {-1/(4*phi),Math.sqrt(2+phi)/4})};
		ang=new double[] {/*-0.6,0.6,*/-0.6,0.6};
		int l=wide.length;
		mi=new int[] {1,0};
		for(int i=0;i<thin.length;i++)
		{
			thin[i]=new Tile(proto[pi[i]],c[i+l],m[i+l]+mi[i]==1,loc[i],Math.PI*(ang[i]+2.0/rot[1]*r[i+l]),infl);
		}
		
		return new Tile[][] {wide,thin};
	}
	
	public Tile[][] sphinx(int[] c,int[] m,int[] r)
	{
		length=new int[] {4};
		l=4;
		Tesselation.mirimage=new int[][] {{0,1,2,3}};
		Tesselation.rotimage=new int[][] {{0,1,2,3}};
		double h=Math.sqrt(3)/2;
		double[][] centers= {{-1.5,-h},{1.5,-h},{0.5,-h},{-1,0}};
		boolean[] mir= {true,true,true,false};
		double[] ro= {1,1,0,2.0/3};
		Tile[] tiles=new Tile[4];
		for(int i=0;i<tiles.length;i++)
		tiles[i]=new Tile(proto[0], c[i],mir[i],new Rn(centers[i]), Math.PI*ro[i],0.5);

		return new Tile[][] {tiles};
	}
	public Tile[][] trapez(int[] c,int[]m,int[]r)
	{
		l=4;
		length=new int[] {4};
		double l=1/Math.sqrt(3);
	
		double[][] centers= {{0,1},{0,1},{1.5*l,1.5},{1.5*l,1.5}};
		boolean[]mir= {true,false,true,false};
		double[] ro= {0,0,-1.0/3,2.0/3};
		Tile[] tiles=new Tile[4];
		for(int i=0;i<tiles.length;i++)
			tiles[i]=new Tile(proto[0], c[i],mir[i],new Rn(centers[i]), Math.PI*ro[i],0.5);

			return new Tile[][] {tiles};
	}
	public Tile[][] fish(int[] c,int[] m, int[] r)
	{
		length=new int[] {3,3};
		l=6;
		double h=Math.sqrt(3)/3;
		double[][] centers= {{-4*h,0},{-2*h,0},{2*h,0}};
		double[] ro= {0,1,0};
		Tile[] fish=new Tile[3];
		for(int i=0;i<3;i++)
			fish[i]=new Tile(proto[1],c[i],m[i]==1,new Rn(centers[i]), Math.PI*(ro[i]+r[i]*2/3.0),1/Math.sqrt(3));
		int k=3;
		Tile[] triangle=new Tile[3];
		centers=new double[][] {{0,0},{0,0},{0,0}};
		 ro=new double[] {0,2.0/3,-2.0/3};
		 for(int i=0;i<3;i++)
				triangle[i]=new Tile(proto[0], c[i+k],m[i+k]==1,new Rn(centers[i]), Math.PI*ro[i],1/Math.sqrt(3));

		return new Tile[][] {fish,triangle};
	}
}
