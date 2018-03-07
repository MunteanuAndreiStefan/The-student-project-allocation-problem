package Controller;

import Model.*;

public abstract class Solver {
        Problem problem;

        public Solver(Problem problem) {
            this.problem = problem;
        }

        public abstract void execute();
}


