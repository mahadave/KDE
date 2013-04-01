import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;


public class Main
{


	static int XL;static  int XU;
	public static void main(String g[]) throws Exception
	{

		double x [] = {0.5,0.34,0.67,0.23, 0.34};

		int scale = 100;
		int L=0;
		int U=1;
		XL =L*scale; XU =U*scale; int res = (int) (0.01*scale); // scaled by 100
		int LEN = XU-XL+1;
		int mu = LEN/2;
		double var = 0.8;
		double sigma= var;
		int N = x.length;
		double h_sc[] = new double[LEN]; // scaled by 100
		double x_sc[]= new double[LEN]; // impulse train
		double y_sc[]= new double[LEN]; // impulse train

		double [] mM =getMaxMin(x);
		//normalize(x, mM);
		makeImpulseTrain(x,x_sc, scale);
		makeImpulseResonse(h_sc,mu,sigma);

		printImpulseResponse(h_sc);
//		printImpulseTrain(x_sc);

//		findSystemOutput(x_sc,h_sc,y_sc,mu,N);
//		printSystemOutput(y_sc);

		printDataOutputSum(y_sc);

	}


	private static void printDataOutputSum(double[] y_sc) {
		double s=0;
		for(int i=0;i<y_sc.length;i++)
		{
			s+=y_sc[i];
		}
		System.out.println(" KDE sum = "+s);

	}


	private static void printSystemOutput(double[] y_sc) {
		System.out.println("============SYSTEM OUTPUT=====================");
		for(int i=0;i<y_sc.length;i++)
		{
				System.out.println(y_sc[i]);

		}

	}


	private static void findSystemOutput(double x_sc[],double h_sc[],double y_sc[],int mu,int N) {

		for(int i=0;i<x_sc.length;i++)
		{
			if(x_sc[i]>0)
			{
				for (int k=0;k<h_sc.length;k++)
				{
					int index = k-i+mu;
					if(index>0 && index<h_sc.length)
						y_sc[k] += x_sc[i]*h_sc[index]/N;
				}
			}
		}

	}


	private static void printImpulseTrain(double[] x_sc) {
		System.out.println("============SIGNAL INPUT=====================");
		for(int i=0;i<x_sc.length;i++)
		{
			System.out.println(x_sc[i]);

		}

	}


	private static void printImpulseResponse(double[] h_sc) {

		System.out.println("============IMPULSE RESPONSE=====================");
		for(int i=0;i<h_sc.length;i++)
		{
			if(h_sc[i]>0)
				System.out.println(h_sc[i]);
		}


	}


	private static void makeImpulseResonse(double[] h_sc,int M,double h) {
		// TODO Auto-generated method stub

		int n=h_sc.length;
		for(int i=0;i<n;i++)
		{
			h_sc[i]= Math.exp(-0.5*Math.pow((i-M), 2)/Math.pow(h,2)) / (Math.sqrt(2*Math.PI)*(h));
		}

	}


	private static void makeImpulseTrain(double[] x, double[] x_sc,int scale) {

		for(int i=0;i<x.length;i++)
		{
			x_sc[(int)Math.round(x[i]*scale)]++; // place impulse
		}

	}


	private static void normalize(double[] x,double[] mM) {

		// NORMALIZE
		for(int i=0;i<x.length;i++)
		{
			x[i] = (x[i] - mM[1]) / (mM[0]- mM[1]);

		}

	}


	private static double[] getMaxMin(double[] x) {
		// TODO Auto-generated method stub
		// GET MAX MIN

		double Ma = 0; double m = 100;

		for(int i=0;i<x.length;i++)
		{
			if(Ma<x[i])
				Ma=x[i];
			if(m>x[i])
				m=x[i];
		}
		double mM[] ={Ma,m} ;
		return mM;
	}


}
