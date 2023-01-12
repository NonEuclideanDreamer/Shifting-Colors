import java.util.ArrayList;

public class Group 
{	static String[] numerals= {"0","1","2","3","4","5","6","7"};
	String[] elements;
	int order;
	int[][]action;
	public Group(String[]e,int[][]a)
	{
		elements=e;
		action=a;
	}
/*	public int[][]ordernelements()
	{
		int[][]out=new int[c][c];
		for(int i=0;i<n;i++)
		{
			out[]
		}
	}*/
	public Group Cube()//SymmetryGroup of the Cube
	{
		
	}
	public int[] ElementsOfOrder(int n)
	{
		ArrayList<Integer> ls=new ArrayList<Integer>();
		for(int i=0;i<order;i++)
		{
			if(order(i)==n)ls.add(i);
		}
		int[]out=new int[ls.size()];
		for(int i=0;i<out.length;i++)
			out[i]=ls.get(i);
		return out;
	}
	public int times(int a, int b)
	{
		return action[a][b];
	}
	public int order(int a)
	{
		int e=0, i=0;
		do{
			e=times(e,a);
			i++;
		}while(e!=0);
		return i;
	}
	public static Group ncolors(int n)
	{
		String[]el=new String[n];
		int[][]a=new int[n][n];
		for(int i=0;i<n;i++)
		{
			el[0]=numerals[i];
			for(int j=0;j<n;j++)
			{
				a[i][j]=(i+j)%n;
			}
		}
		return new Group(el,a);
	}
}
