package MiniProject;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class GraphPartitioningCP {

    private int N = 6;
    private int[][] E = {
        {0 , 2 , 1},
        {0 , 4 , 3},
        {0 , 5 , 2},
        {1 , 4 , 4},
        {1 , 5 , 3},
        {2 , 3 , 5},
        {2 , 4 , 1},
        {4 , 5 , 5}
    };

    public void solve() {
        int[] c = new int[E.length];
        int C = 0;
        for (int i = 0; i < E.length; i++) {
            c[i] = E[i][2];
            C += c[i];
        }

        int K = 3; 
        
        Model model = new Model("graph partitioning");
        IntVar[] z = new IntVar[E.length];
        IntVar[] x = new IntVar[N];

        IntVar obj = model.intVar("obj", 0, C);
        for (int k = 0; k < E.length; k++) {
            z[k] = model.intVar("z[" + k + "]", 0 , 1);
        }
        for (int i = 0; i < N; i++) {
            x[i] = model.intVar("x[" + i + "]", 0 , K - 1);
        }
        for (int k = 0; k < E.length; k++) {
            model.ifThen(model.arithm(z[k], "=", 1), model.arithm(x[E[k][0]], "-", x[E[k][1]], "!=", 0));
            model.ifThen(model.arithm(x[E[k][0]], "-", x[E[k][1]], "!=", 0), model.arithm(z[k], "=", 1));
        }
        int[] one = new int[N];
        for (int i = 0; i < N; i++) {
            one[i] = 1;
        }
        model.scalar(x, one, "=", N * (K - 1) / 2).post();

        model.scalar(z, c, "=", obj).post();

        model.setObjective(model.MAXIMIZE, obj);
        model.getSolver().solve();
        model.getSolver().printStatistics();
        
        for (int i = 0; i < N; i++) {
            System.out.println("x[" + i + "] = " + x[i].getValue());
        }
        for (int k = 0; k < E.length; k++) {
            System.out.println("z[" + E[k][0] + "," + E[k][1] + "] = " + z[k].getValue());
        }
        
        System.out.println("obj = " + obj.getValue());
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        GraphPartitioningCP app = new GraphPartitioningCP();
        app.solve();
    }

}