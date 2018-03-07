import Model.Problem;
import Controller.*;


/**
 * Main class of the project and it's start function.
 */
public class Main {

    public static void main(String args[]) {
        Problem problem = new Problem();
        System.out.println(problem.toString());
        problem.initialize();
        StableMatchingSolver stableMatchingSolver = new StableMatchingSolver(problem);
        stableMatchingSolver.execute();
    }

}