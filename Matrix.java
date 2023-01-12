
public class Matrix 
{
	public static double ex=0.00000001;//Exactness when checking wether smth is =0)
	public int m, n;
	public double[][] entry;
	
	public Matrix(double[][] c)
	{
		m=c.length;
		n=c[0].length;
		entry=c;
	}
	
	public Matrix(int x, int y)
	{
		m=x;
		n=y;
		entry=new double[x][y];
	}
	
	public static Matrix idmatrix(int n)
	{
		double[][]out =new double[n][n];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(i==j)out[i][j]=1;
			}
		}
		return new Matrix(out);
	}
	public static Matrix project(int n,int i, int j, int k)
	{
		double[][] entries=new double[n][n];
		entries[i][i]=1;entries[j][j]=1;entries[k][k]=1;
		return new Matrix(entries);
	}
	public static Matrix hrotate(double angle, int x, int y,int dim)//Rotation matrix for rotation by angle in ex-ey-plane
	{
		Matrix matrix=idmatrix(dim);
		
		matrix.entry[x][x]=Math.cos(angle);
		matrix.entry[x][y]=-Math.sin(angle);
		matrix.entry[y][x]=Math.sin(angle);
		matrix.entry[y][y]=Math.cos(angle);

		return matrix;
	}
	//Rotates the ex-(ey+ez)-plane by angle
	public static Matrix drotate(double angle, int x, int y, int z, int dim)
	{
		Matrix matrix=idmatrix(dim);//ToDo
		
		matrix.entry[x][x]=Math.cos(angle);
		matrix.entry[x][y]=-Math.sin(angle)/Math.sqrt(2);
		matrix.entry[x][z]=matrix.entry[x][y];
		matrix.entry[y][x]=-matrix.entry[x][y];
		matrix.entry[z][x]=matrix.entry[y][x];
		matrix.entry[y][y]=(Math.cos(angle)+1)/2;
		matrix.entry[z][z]=matrix.entry[y][y];
		matrix.entry[y][z]=matrix.entry[y][y]-1;
		matrix.entry[z][y]=matrix.entry[y][z];
		
		return matrix;
	}
	public void mirror(int j)
	{
		for(int i=0;i<m;i++)
			entry[i][j]*=-1;
	}
	public Matrix times(Matrix matrix)
	{
		double[][] out=new double[m][matrix.n];
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				for(int k=0;k<matrix.n;k++)
				{
					out[i][k]=out[i][k]+entry[i][j]*matrix.entry[j][k];
				}
			}
		}
		return new Matrix(out);
	}
	public Rn times(Rn vector)
	{
		double[] c=new double[m];
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				c[i]=c[i]+entry[i][j]*vector.c[j];
			}
		}
		return new Rn(c);
	}
	public Matrix times(double scalar)
	{
		double[][] nentry=new double[m][n];
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				nentry[i][j]=entry[i][j]*scalar;
			}
		}
		return new Matrix(nentry);
	}
	public void print()
	{
		System.out.println("Matrix:");
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(entry[i][j]+"   ");
			}
			System.out.println();
		}
		
	}
	public Matrix copy()
	{
		double[][] out=new double[m][n];
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				out[i][j]=entry[i][j];
			}
		}
		return new Matrix(out);
	}
	public Rn[] nullspace( )//actually left nullspace
	{
		
		Rn res=Rn.zero(m);
		Matrix out=new Matrix(m,m+n);
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				out.entry[i][j]=entry[i][j];
			}
			out.entry[i][i+n]=1;
		}
	
		int i=0,j=0;
		while(i<m&&j<n)
		{while(Math.abs(out.entry[i][j])<=ex)
		{
			int k=i+1;boolean problemsolved=false;
			while(k<m&& !problemsolved)
			{
				if(Math.abs(out.entry[k][j])>ex)problemsolved=true;
				else k++;
			}
			if(problemsolved)
			{
				double[] s=out.entry[i];
				out.entry[i]=out.entry[k];
				out.entry[k]=s;
			
			}
			else{j++;}
		}
		double f=out.entry[i][j];
		
		for(int h=0;h<n+m;h++)
		{
			out.entry[i][h]=out.entry[i][h]/f;
		}	
		for(int l=0;l<m;l++)
		{	
			double a=out.entry[l][j];
			if(l!=i)
			for(int h=0;h<n+m;h++)
			{
					out.entry[l][h]-=a*out.entry[i][h];
						
			}
		}	
				{i++;j++;}	//out.print();
		}
		boolean iszero=false;i=-1;
		System.out.print("Hessematrix:");
		out.print();
		while(!iszero&&i<m-1)
		{
			i++;
			boolean maybe=true;
			for(j=0;j<n;j++)
			{
				if(Math.abs(out.entry[i][j])>ex)maybe=false;
			}
			if(maybe)iszero=true;
		}
		Rn[]nullsp=new Rn[n-i];
		for(int k=i;k<n;k++)
		{
			double[] e=new double[m];
			for(int l=0;l<m;l++)
			{
				e[l]=out.entry[k][n+l];
			}
			nullsp[k-i]=new Rn(e);
		}
		return nullsp;
	}

	public Matrix subtract(Matrix subtractor) 
	{
		return add(subtractor.times(-1));
	}

	public Matrix add(Matrix summand)
	{
		double[][]out=new double[m][n];
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				out[i][j]=entry[i][j]+summand.entry[i][j];
			}
		}
		return new Matrix(out);
	}

	public Rn getRow(int i) 
	{
		double[] out=entry[i];
		return new Rn(out);
	}

	public Matrix transpone() 
	{
		double[][]out =new double[n][m];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				out[j][i]=entry[i][j];
		return new Matrix(out);
	}
	
}
