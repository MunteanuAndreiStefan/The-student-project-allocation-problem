package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Teacher extends Person {
    
    /**
     * Maximum number of students that the teacher can accept.
     */
    int teacherCapacity;
    int numberOfSubscribedStudents;

    /**
     * List of students preferred by this teacher, in ascending order.
     */
    List<Student> preferredStudents;

    List<Student> subscribedStudents;

    /**
     * List of projects offered by this teacher.
     */
    List<Project> availableProjects;

    public Teacher(String email, String name, int capacity) {
        super(email, name);
        this.numberOfSubscribedStudents = 0;
        this.teacherCapacity = capacity;
        this.preferredStudents = new ArrayList<Student>();
        this.subscribedStudents = new ArrayList<Student>();
        this.availableProjects = new ArrayList<Project>();
    }

    /**
     * Used to tell if a Model.Teacher hasn't reached his capacity yet.
     *
     * @return - returns true if the teacher can accept more students, otherwise false.
     */
    public boolean isFree() {

        return (teacherCapacity - numberOfSubscribedStudents) > 0;
    }

    /**
     *
     * @return - returns if the teacherCapacity is full or not.
     */
    public boolean isFull() {

        return (teacherCapacity - numberOfSubscribedStudents) == 0;
    }

    /**
     *
     * @return - returns if all teacherCapacity is occupied.
     */
    public boolean isOverSubscribed() {

        return (teacherCapacity - numberOfSubscribedStudents) < 0;
    }

    /**
     * @param student - adds a student to subscribed list of the teacher.
     */
    public void subscribeStudent(Student student) {
        this.subscribedStudents.add(student);
        this.numberOfSubscribedStudents++;
    }

    /**
     * Method used in the Stable Matching Algorithm.
     * Removes projects that had been already assigned to a better student.
     *
     * @param student Model.Student that has just been assigned a project.
     */
    public void deleteUnwantedStudents(Student student) {
        Iterator<Student> iterator = preferredStudents.iterator();
        for (; iterator.hasNext(); ) {
            Student currentStudent = iterator.next();
            if (currentStudent.equals(student)) {
                break;
            }
        }
        for (; iterator.hasNext(); ) {
            Student currentStudent = iterator.next();
            for (Project project : availableProjects) {
                currentStudent.removeProject(project);
            }
        }
    }

    /**
     * @param student - remove student from subscribedStudents teacher list.
     */
    public void unSubscribeStudent(Student student) {
        subscribedStudents.remove(student);
        numberOfSubscribedStudents--;
    }

    public int getHeaderLength(String header) {
        switch (header) {
            case "students":
                return toString().length();
            case "projects":
                return String.valueOf(availableProjectsToString()).length();
        }
        return 0;
    }

    public void deleteSuccessors(Student std, Project project) {
        boolean canDelete = false;
        for (Student student : preferredStudents) {
            if (canDelete) {
                student.removeProject(project);
            }
            if (student.equals(std)) {
                canDelete = true;
            }
        }
    }

    /**
     *
     * @param project to check that is assigned to this student.
     * @return - returns a student that is preferred student and is assigned to this teacher.
     */
    public Student getWorstStudent(Project project) {
        Student worstStudent = null;
        for (Student student : preferredStudents) {
            if (this.equals(student.projectTeacher) && project.equals(student.projectAssigned)) {
                worstStudent = student;
            }
        }
        return worstStudent;
    }

    /**
     * @return - returns a student that is preferred student and is assigned to this teacher.
     */
    public Student getWorstStudent() {
        Student worstStudent = null;
        for (Student student : preferredStudents) {
            if (this.equals(student.projectTeacher)) {
                worstStudent = student;
            }
        }
        return worstStudent;
    }

    /**
     * @param student Preferred student to be added in teacher's list
     */
    public void addPreferredStudent(Student student) {
        this.preferredStudents.add(student);
    }

    /**
     * @param project Available project to be added in teacher's list
     */
    public void addAvailableProject(Project project) {

        this.availableProjects.add(project);
    }

    public String availableProjectsToString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" offers ");
        int i;
        for (i = 0; i < availableProjects.size() - 1; ++i) {
            sb.append(availableProjects.get(i).getProjectName());
            sb.append(", ");
        }
        sb.append(availableProjects.get(i).getProjectName());
        return String.valueOf(sb);
    }

    /**
     * Overridden toString Function.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" : ");
        for (Student student : preferredStudents) {
            sb.append(student.getName());
            sb.append(" ");
        }
        return String.valueOf(sb);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        Teacher teacher = (Teacher) obj;
        return (this.name.equals(teacher.name));
    }
}