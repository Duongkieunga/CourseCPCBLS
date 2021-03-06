package binbacking2D;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class Binbacking2D_Choco {
	public int N = 3, W = 4, H = 6;
	public int[] h = {2, 4, 6};
	public int[] w = {3, 3, 1};
	
	public void solver() {
		Model m = new Model();
		IntVar[] X = new IntVar[N];
		IntVar[] Y = new IntVar[N];
		
		IntVar[] xoay = m.intVarArray(N, 0, 1);
		for(int i=0; i<N; i++) {
			X[i] = m.intVar(0, W-1);
			Y[i] = m.intVar(0, H-1);
		}
		
		
		for(int i=0; i<N; i++) {
			Constraint c1 = m.arithm(m.intOffsetView(X[i], w[i]), "<=", W);
			Constraint c2 = m.arithm(m.intOffsetView(Y[i], h[i]), "<=", H);
			Constraint c3 = m.arithm(m.intOffsetView(X[i], h[i]), "<=", W);
			Constraint c4 = m.arithm(m.intOffsetView(Y[i], w[i]), "<=", H);
			m.ifThenElse(
					m.arithm(xoay[i], "=", 0),
					m.and(c1, c2),
					m.and(c3, c4)
			);
		}
		
		for(int i=0; i<N-1; i++)
			for(int j=i+1; j<N; j++) {
				Constraint c11 = m.arithm(m.intOffsetView(X[i], w[i]), "<=", X[j]);
				Constraint c12 = m.arithm(m.intOffsetView(X[j], w[j]), "<=", X[i]);
				Constraint c13 = m.arithm(m.intOffsetView(Y[i], h[i]), "<=", Y[j]);
				Constraint c14 = m.arithm(m.intOffsetView(Y[j], h[j]), "<=", Y[i]);
				m.ifThen(
						m.and(m.arithm(xoay[i], "=", 0), m.arithm(xoay[j], "=", 0)),
						m.or(c11, c12, c13, c14)
				);
				
				c11 = m.arithm(m.intOffsetView(X[i], w[i]), "<=", X[j]);
				c12 = m.arithm(m.intOffsetView(X[j], h[j]), "<=", X[i]);
				c13 = m.arithm(m.intOffsetView(Y[i], h[i]), "<=", Y[j]);
				c14 = m.arithm(m.intOffsetView(Y[j], w[j]), "<=", Y[i]);
				m.ifThen(
						m.and(m.arithm(xoay[i], "=", 0), m.arithm(xoay[j], "=", 1)),
						m.or(c11, c12, c13, c14)
				);
				
				c11 = m.arithm(m.intOffsetView(X[i], h[i]), "<=", X[j]);
				c12 = m.arithm(m.intOffsetView(X[j], w[j]), "<=", X[i]);
				c13 = m.arithm(m.intOffsetView(Y[i], w[i]), "<=", Y[j]);
				c14 = m.arithm(m.intOffsetView(Y[j], h[j]), "<=", Y[i]);
				m.ifThen(
						m.and(m.arithm(xoay[i], "=", 1), m.arithm(xoay[j], "=", 0)),
						m.or(c11, c12, c13, c14)
				);
				
				c11 = m.arithm(m.intOffsetView(X[i], h[i]), "<=", X[j]);
				c12 = m.arithm(m.intOffsetView(X[j], h[j]), "<=", X[i]);
				c13 = m.arithm(m.intOffsetView(Y[i], w[i]), "<=", Y[j]);
				c14 = m.arithm(m.intOffsetView(Y[j], w[j]), "<=", Y[i]);
				m.ifThen(
						m.and(m.arithm(xoay[i], "=", 1), m.arithm(xoay[j], "=", 1)),
						m.or(c11, c12, c13, c14)
				);
			}
		
		Solver s = m.getSolver();
		while(s.solve()) {
			System.out.println("\n"+"Toa do cac toa hang: ");
			for(int i=0; i<N; i++) {
				System.out.print(X[i].getValue() + " " + Y[i].getValue() + " ");
				if(xoay[i].getValue() == 1)
					System.out.println("Xoay 90 do");
				else
					System.out.println();
			}
				
		}
		
	}

	public static void main(String[] args) {
		Binbacking2D_Choco run = new Binbacking2D_Choco();
		run.solver();

	}

}
