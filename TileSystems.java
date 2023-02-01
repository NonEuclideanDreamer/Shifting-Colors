
public class TileSystems 
{
	Prototile[] proto;
	double[]min=new double[2],max=new double[2];
	int[] rot,mir;
	int l;
	public TileSystems(Prototile[] prototiles, double[] min2, double[] max2, int[] mir2, int[] rot2,int l0) 
	{
		proto=prototiles;
		min=min2;
		max=max2;
		rot=rot2;
		mir=mir2;
		Tesselation.rot=rot;Tesselation.mir=mir;
		l=l0;
	}
	public static TileSystems square()//rectangular prototile of half height 1 and given half width
	{
		double[] min=new double[]{-1,-1},max=new double[] {1,1};
		int[] mir=new int[] {2,2},
		rot=new int[] {4};
		return new TileSystems(proto(1),min,max,mir,rot,4);
	}
	public static TileSystems table()
	{
		double[] //min=new double[]{-2,-1},max=new double[] {2,1};
		min=new double[] {-1,-1},max=new double[] {1,1};
		int[]mir=new int[] {2},
		rot=new int[] {2};
		return new TileSystems(proto(2),min,max,mir,rot,4);

	}
	public static Prototile[] proto(int width)//rectangular prototile of half height 1 and given half width
	{
	
		//Random rand=new Random();
		
		Rn[] corners= {new Rn(new double[]{-width,-1}),new Rn(new double[]{width,-1}),new Rn(new double[]{width,1}),new Rn(new double[]{-width,1})};
	
		return new Prototile[] {new Prototile(corners)};
	}
	public static TileSystems dragonTrigon()
	{
		double sqrt=Math.sqrt(3);
		
		double [] min=new double[] {-0.74,-0.45},max=new double[] {0.16,0.45};
		int[] rot=new int[] {1,3},
		mir=new int[] {2,2};
		
		Rn[] dragon= {new Rn(new double[] {-1,0}),new Rn(new double[] {-0.5,-sqrt/2}),new Rn(new double[] {1,0}), new Rn(new double[] {-0.5,sqrt/2})};
		Rn[] trigon= {new Rn(new double[]{-sqrt/3,1}), new Rn(new double[] {-sqrt/3,-1}),new Rn(new double[] {2*sqrt/3,0})};
		
		return new TileSystems(new Prototile[] {new Prototile(dragon),new Prototile(trigon)},min,max,mir,rot,6);
	}
	public static TileSystems originalPenrose()
	{
		double[] min=new double[] {-9*0.02,-5*0.02},max=new double[] {9*0.02,13*0.02};
		double phi=(Math.sqrt(5)+1)/2,sqrt=Math.sqrt(5);
	
		int[]rot=new int[] {5,1,1,1,5,1},
		mir=new int[] {2,2,2,2,2,2};
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
		return new TileSystems(new Prototile[] {new Prototile(pentagon), new Prototile(pentagon), new Prototile(pentagon), new Prototile(diamond), new Prototile(pentagram), new Prototile(boat)},
				min,max,mir,rot,27);
	}
	public static TileSystems chair()//The chair-tile
	{
		double[] min=new double[] {-0,-0.5},max=new double[] {1,0.5};
		int[]rot=new int[] {1},
		mir=new int[] {2};
		Rn[] corners= {new Rn(new double[] {1,0}),new Rn(new double[] {2,1}),new Rn(new double[] {1,2}),
				new Rn(new double[] {-1,0}),new Rn(new double[] {1,-2}),new Rn(new double[] {2,-1})};
		
		return new TileSystems(new Prototile[] {new Prototile(corners)},min,max,mir,rot,4);
	}
	public static TileSystems reptrile()
 	{	
		double sqrt=Math.sqrt(3),d=1;
		int[]rot=new int[] {3,1},
		mir=new int[] {2,1};
 		double[]min={2*sqrt/15,-sqrt/10},max={sqrt/3,sqrt/10};// boundary zoom
		//min= {-1.25,-1},max= {0.75,1};//Full picture 
	
 		Rn[] triangle= {new Rn(new double[] {sqrt/3,-1}),new Rn(new double[] {sqrt/3,1}),new Rn(new double[] {-sqrt*2/3,0})};
 		Rn[] sled= {new Rn(new double[] {-0.5*d,-sqrt/2*d}),new Rn(new double[] {-1.5*d,-sqrt/2*d}),new Rn(new double[] {-2*d,0}),new Rn(new double[] {d,0}), new Rn(new double[] {0,-sqrt*d}),new Rn(new double[] {-d,-sqrt*d})};
 	
 		return new TileSystems(new Prototile[] {new Prototile(triangle), new Prototile(sled)},min,max,mir,rot,11);
 	}
	public static TileSystems sphinx()
	{
		double h=Math.sqrt(3)/2;
	
		double[] //min= {-3,-3};double[]max= {3,3};
				min= {-1,-2*h},max= {-1+2*h,0};
		int[]rot=new int[] {1},

		mir=rot;
		
		Rn[] corners= {new Rn(new double[] {0,0}),new Rn(new double[] {-1,2*h}),new Rn(new double[] {-3,-2*h}),new Rn(new double[] {3,-2*h}),new Rn(new double[] {2,0})};
		
		return new TileSystems(new Prototile[] {new Prototile(corners)}, min,max,mir,rot,4);
	}
	public static TileSystems pinwheel()
	{	
		int[]rot= {1},
		mir= {1};
		//min=new double[] {0,-.125};max =new double[] {4,2.125};//whole visible
		double[] min={0,0},max= {4.0/3,4.0/3};//
		Rn[] corners= {new Rn(new double[] {0,0}),new Rn(new double[] {4,0}),new Rn(new double[] {0,2})};
		Tesselation.tile=new Prototile[] {new Prototile(corners)};
		return new TileSystems(Tesselation.tile,min,max,mir,rot,5);
	}
	public static TileSystems fish()
	{
		double h=Math.sqrt(3),b=h/(h+1);
		double[]min={-h-b,-b},max= {-h+b,b};
				//min= {-2*h,-1.5*h},max= {h,1.5*h};
		int[]rot=new int[] {1,3},
		mir= {2,2};
		
		Rn[]corners= {new Rn(new double[] {0,0}),new Rn(new double[] {h,-1}),new Rn(new double[] {h,1}),
				new Rn(new double[] {-h,-1}),new Rn(new double[] {-2*h,0}),new Rn(new double[] {-h,1})};
		Rn[]triangle= {new Rn(new double[] {-2,0}),new Rn(new double[] {1,-h}),new Rn(new double[] {1,h})};
		return new TileSystems(new Prototile[] {new Prototile(corners), new Prototile(triangle)}, min,max,mir,rot,6);
	}
	public static TileSystems penrose()
	{
		
		
		int[] rot= {1,1},mir= {1,1};
		
		double phi=(1+Math.sqrt(5))/2;
		
		double[] min={-0,0},max= {0.5,0.5};
		
		Rn[] widerhomb= {new Rn(new double[] {-phi/2,0}), new Rn(new double[] {0,-Math.sqrt(3-phi)/2}),
						new Rn(new double[] {phi/2,0}), new Rn(new double[] {0,Math.sqrt(3-phi)/2})};
		
		Rn[] thinrhomb= {new Rn(new double[] {-1/(2*phi),0}) , new Rn(new double[] {0,-Math.sqrt(2+phi)/2}),
						new Rn(new double[] {1/(2*phi),0}), new Rn(new double[] {0,Math.sqrt(2+phi)/2})};
		
		return new TileSystems(new Prototile[] {new Prototile(widerhomb), new Prototile(thinrhomb)},
				min,max,mir,rot,5);
	}
	public static TileSystems trapez()
	{
		
		double[] min= {0,2-4/(1+Math.sqrt(3))},max={4/(1+Math.sqrt(3)),2};
		int[]rot= {1},
		mir=rot;
		Rn[] corners= {new Rn(new double[] {0.000,0.000}), new Rn(new double[] {0,2.000}), new Rn(new double[] {4/Math.sqrt(3),2}),new Rn(new double[] {2/Math.sqrt(3),0})};
		
		return new TileSystems(new Prototile[] {new Prototile(corners)},min,max,mir,rot,4);
	}
}
