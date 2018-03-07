
package Controller;

import Model.*;

public class StableMatchingSolver extends Solver {

    /**
     *Class constructor
     * @param problem problem described to be solved
     */
    public StableMatchingSolver(Problem problem) {
        super(problem);
    }

    /**
     * Stable Matching Algorithm.
     */
    public void execute() {
        while (problem.thereIsAFreeStudentWithNoEmptyList()) {
            Student studentWithNoEmptyList = problem.getFreeStudentWithNoEmptyList();

            Project project = studentWithNoEmptyList.getFirstProject();
            Teacher teacher = problem.getProjectTeacher(project);

            studentWithNoEmptyList.setProjectAssigned(project);
            studentWithNoEmptyList.setProjectTeacher(teacher);

            project.subscribeStudent(studentWithNoEmptyList);
            teacher.subscribeStudent(studentWithNoEmptyList);
            if (project.isOverSubscribed()) {

                Student studentResolve = teacher.getWorstStudent(project);
                studentResolve.setProjectAssigned(null);
                studentResolve.setProjectTeacher(null);
                project.unSubscribeStudent(studentResolve);
                teacher.unSubscribeStudent(studentResolve);

            } else if (teacher.isOverSubscribed()) {

                Student studentResolve = teacher.getWorstStudent();
                Project pt = studentResolve.getProjectAssigned();
                studentResolve.setProjectAssigned(null);
                studentResolve.setProjectTeacher(null);
                pt.unSubscribeStudent(studentResolve);
                teacher.unSubscribeStudent(studentResolve);

            }
            if (project.isFull()) {
                Student studentResolve = teacher.getWorstStudent(project);
                teacher.deleteSuccessors(studentResolve, project);
            }
            if (teacher.isFull()) {
                Student studentResolve = teacher.getWorstStudent();
                teacher.deleteUnwantedStudents(studentResolve);
            }
        }
        problem.printSolution();
        problem.printStudentSolutionAnalysis();
        problem.printTeacherSolutionAnalysis();
        problem.printMatchingSolutionAnalysis();
    }
}