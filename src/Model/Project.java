package Model;

import java.util.ArrayList;
import java.util.List;


public class Project {
    String projectName;
    int projectCapacity;
    int numberOfStudentsSubscribed;

    Teacher projectTeacher;
    List<Student> subscribedStudents;

    /**
     * Constructor.
     * @param projectName - a string that represents the project name.
     * @param projectCapacity - a int that represents number of maximum students on the project.
     */
    public Project(String projectName, int projectCapacity) {
        this.projectName = projectName;
        this.projectCapacity = projectCapacity;
        this.subscribedStudents = new ArrayList<Student>();
        this.numberOfStudentsSubscribed = 0;
    }

    /**
     * Getter for project name.
     * @return - returns a string that represents the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Setter for project teacher.
     * @param teacher - teacher of the project.
     */
    public void setTeacher(Teacher teacher) {
        this.projectTeacher = teacher;
    }

    /**
     * Getter for project teacher.
     * @return - returns a string that represents teacher of the project name.
     */
    public Teacher getProjectTeacher() {
        return projectTeacher;
    }

    /**
     * Subscribe a student to project.
     * @param student - student to be subscribed to the project.
     */
    public void subscribeStudent(Student student) {
        this.subscribedStudents.add(student);
        this.numberOfStudentsSubscribed++;
    }

    /**
     * Unsuscribe a student the project.
     * @param student - student to be unsuscribed from a project.
     */
    public void unSubscribeStudent(Student student) {
        subscribedStudents.remove(student);
        numberOfStudentsSubscribed--;
    }

    /**
     * Checks if the project hasn't any need for more students.
     * @return - returns a bool value that represents the "isFull" functionality.
     */
    public boolean isFull() {
        return (projectCapacity - numberOfStudentsSubscribed) == 0;
    }

    /**
     * If a project hase more students then project capacity.
     * @return - returns a bool if project is overpopulated with students.
     */
    public boolean isOverSubscribed() {
        return (projectCapacity - numberOfStudentsSubscribed) < 0;
    }

    /**
     * Check if projectName equals other project name.
     * @param obj Overridden function for Equals(object) method.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Project)) {
            return false;
        }
        Project project = (Project) obj;
        return (this.projectName.equals(project.projectName));
    }
}