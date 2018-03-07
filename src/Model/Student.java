package Model;

import java.util.*;

public class Student extends Person {

    /**
     * List of projects preferred by this student, in ascending order.
     */
    List<Project> preferredProjects;
    List<Project> preferredProjectsCopy;
    Project projectAssigned;
    Teacher projectTeacher;

    /**
     * Constructor for Student.
     * @param email - the email of the student.
     * @param name - the name of the student.
     */
    public Student(String email, String name) {
        super(email, name);
        this.preferredProjects = new ArrayList<Project>();
    }

    /**
     * Getter for preferred student projects.
     * @return - returns a list of projects.
     */
    public List<Project> getPreferredProjects() {
        return preferredProjects;
    }

    /**
     * Returns an int that represents if the student has alray a project or not.
     * @return - represent the availability of a student to project.
     */
    public boolean isFree() {
        return projectAssigned == null;
    }

    /**
     * @param header Get serialization length
     */
    public int getHeaderLength(String header) {

        return toString().length();
    }

    /**
     * @param preferredProject Variable number of Model.Student's preferred projects
     */
    public void addPreferredProject(Project preferredProject) {

        this.preferredProjects.add(preferredProject);
    }

    /**
     * setPreferredProjectsCopy to be equal to preferredProjects
     */
    public void setPreferredProjectsCopy() {

        this.preferredProjectsCopy = new ArrayList<>(preferredProjects);
    }

    public Project getFirstProject() {
        assert preferredProjects.size() > 0 : "Model.Student has no remaining acceptable projects!";
        return preferredProjects.get(0);
    }

    public Project getProjectAssigned() {
        return projectAssigned;
    }

    /**
     * @param projectAssigned Set for projectAssigned
     */
    public void setProjectAssigned(Project projectAssigned) {
        this.projectAssigned = projectAssigned;
    }

    /**
     * @param projectTeacher Set for projectTeacher
     */
    public void setProjectTeacher(Teacher projectTeacher) {
        this.projectTeacher = projectTeacher;
    }

    /**
     * @param project Remove project from preferredProjects
     */
    public void removeProject(Project project) {
        preferredProjects.remove(project);
    }

    /**
     * Print for projectAssigned
     */
    public void printProjectAssigned() {
        if (projectAssigned != null) {
            System.out.println(this.name + " : " + projectAssigned.getProjectName());
        } else {
            System.out.println(this.name + " : No project.");
        }
    }

    /**
     * @param obj Equals overridden
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        Student student = (Student) obj;
        return (this.name.equals(student.name));
    }

    /**
     * toString overridden
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" : ");
        for (Project project : preferredProjects) {
            sb.append(project.getProjectName());
            sb.append(" ");
        }
        return String.valueOf(sb);
    }

}