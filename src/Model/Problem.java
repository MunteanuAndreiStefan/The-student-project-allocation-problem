package Model;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class Problem {
    LinkedHashMap<String,Student> students;
    LinkedHashMap<String,Teacher> teachers;
    LinkedHashMap<String,Project> projects;

    /**
     * Default problem constructor.
     */
    public Problem() {
        initialize();
    }

    /**
     * Initialize empty Problem.
     */
    public void initialize() {
        students = new LinkedHashMap<String,Student>();
        teachers = new LinkedHashMap<String,Teacher>();
        projects = new LinkedHashMap<String,Project>();
        setStudentsData();
        setTeachersData();
        setProjectsData();
        setStudentPreferences();
        setTeacherPreferences();
        setAvailableProjects();
    }

    /**
     * Getter for students list of the project.
     * @return - returns a list of students.
     */
    public List<Student> getStudents() {
        return students.values().stream().collect(Collectors.toList());
    }

    /**
     * Getter for teachers list of the project.
     * @return - returns a list of teachers.
     */
    public List<Teacher> getTeachers() {
        return teachers.values().stream().collect(Collectors.toList());
    }

    /**
     * Getter for projects list of the project.
     * @return - returns a list of projects.
     */
    public List<Project> getProjects() {
        return projects.values().stream().collect(Collectors.toList());
    }

    /**
     * Getter for the project name.
     * @param projectName Get the a Project object from list of projects inside the problem via it's name.
     * @return - returns The project with name: projectName
     */
    Project getProjectByName(String projectName) {
        if(projects.containsKey(projectName))
            return projects.get(projectName);
        return null;
    }

    /**
     * Getter for student by name.
     * Get a Student object from the list of students inside the problem via it's name
     * @param studentName
     * @return - returns the student with name: studentName
     */
    Student getStudentByName(String studentName) {
        if(students.containsKey(studentName))
            return students.get(studentName);
        return null;
    }

    /**
     * @return - returns a bool value if in students exists a student that has no preferredProjects but isFree
     */
    public boolean thereIsAFreeStudentWithNoEmptyList() {
        for (Student student : students.values()) {
            if (student.isFree() && student.preferredProjects.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return - returns the student that has no preferred projects but isFree ( first finded )
     */
    public Student getFreeStudentWithNoEmptyList() {
        for (Student student : students.values()) {
            if (student.isFree() && student.preferredProjects.size() > 0) {
                return student;
            }
        }
        return null;
    }

    public Teacher getProjectTeacher(Project project) {
        return project.projectTeacher;
    }

    /**
     * Set students data from the file students.in from Input folder.
     */
    public void setStudentsData() {
        String studentName, studentEmail;
        try {
            Scanner sc = new Scanner(new File("Input/students.in"));
            while (sc.hasNextLine()) {
                studentName = sc.next();
                studentEmail = sc.next();
                Student student = new Student(studentEmail, studentName);
                this.students.put(student.getName(),student);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set projects data from the file projects.in from Input folder.
     */
    public void setProjectsData() {
        String projectName;
        int projectCapacity;
        try {
            Scanner sc = new Scanner(new File("Input/projects.in"));
            while (sc.hasNextLine()) {
                projectName = sc.next();
                projectCapacity = sc.nextInt();
                Project project = new Project(projectName, projectCapacity);
                this.projects.put(project.projectName,project);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set teachers data from the file teachers.in from Input folder.
     */
    public void setTeachersData() {
        String teacherName, teacherEmail;
        int teacherCapacity;
        try {
            Scanner sc = new Scanner(new File("Input/teachers.in"));
            while (sc.hasNextLine()) {
                teacherName = sc.next();
                teacherEmail = sc.next();
                teacherCapacity = sc.nextInt();
                Teacher teacher = new Teacher(teacherEmail, teacherName, teacherCapacity);
                this.teachers.put(teacher.getName(),teacher);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set student preferences from the file studentPreferences.in from Input folder.
     */
    public void setStudentPreferences() {
        String line;
        try {
            Scanner sc = new Scanner(new File("Input/studentPreferences.in"));
            for (Student student : students.values()) {
                line = sc.nextLine();
                String preferences[] = line.split("\\s+");
                for (String projectName : preferences) {
                    Project preferredProject = getProjectByName(projectName);
                    student.addPreferredProject(preferredProject);
                }
                student.setPreferredProjectsCopy();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set teacher preferences from the file teacherPreferences.in from Input folder.
     */
    public void setTeacherPreferences() {
        String line;
        try {
            Scanner sc = new Scanner(new File("Input/teacherPreferences.in"));
            for (Teacher teacher : teachers.values()) {
                line = sc.nextLine();
                String preferences[] = line.split("\\s+");
                for (String studentName : preferences) {
                    Student preferredStudent = getStudentByName(studentName);
                    teacher.addPreferredStudent(preferredStudent);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set available projects from the file availableProjects.in from Input folder.
     */
    public void setAvailableProjects() {
        String line;
        try {
            Scanner sc = new Scanner(new File("Input/availableProjects.in"));
            for (Teacher teacher : teachers.values()) {
                line = sc.nextLine();
                String availableProjects[] = line.split("\\s+");
                for (String projectName : availableProjects) {
                    Project availableProject = getProjectByName(projectName);
                    availableProject.setTeacher(teacher);
                    teacher.addAvailableProject(availableProject);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to find the table row size, depending on the column.
     *
     * @param persons   List of teachers or students.
     * @param maxLength Current header length.
     * @param header    Column used to find the row size.
     * @return - returns the maximum length of the header.
     */
    public int getMaxHeaderLength(List<? extends Person> persons, int maxLength, String header) {
        for (Person person : persons) {
            if (person.getHeaderLength(header) > maxLength) {
                maxLength = person.getHeaderLength(header);
            }
        }
        return maxLength;
    }

    /**
     * Adds spaces to the header.
     *
     * @param header
     * @param maxLength
     * @return - returns the header padded with spaces.
     */
    String getPaddedHeader(String header, int maxLength) {
        int spacesRequired = maxLength - header.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spacesRequired / 2; ++i) {
            sb.append(" ");
        }
        sb.append(header);
        for (int i = spacesRequired / 2; i < spacesRequired; ++i) {
            sb.append(" ");
        }
        return String.valueOf(sb);
    }

    String getTableRow(String row, int maxLength) {
        StringBuilder paddedRow = new StringBuilder();
        if (row.length() == 0) {
            for (int i = 0; i < maxLength; ++i) {
                paddedRow.append(" ");
            }
        } else {
            paddedRow.append(row);
            for (int i = paddedRow.length(); i < maxLength; ++i) {
                paddedRow.append(" ");
            }
        }
        return String.valueOf(paddedRow);
    }

    public void printSolution() {
        for (Student student : students.values()) {
            student.printProjectAssigned();
        }
    }

    LinkedHashMap <Student, Integer> projectsAnalysis = new LinkedHashMap<Student, Integer>();

    /**
     * Print student solution Analysis
     */
    public void printStudentSolutionAnalysis() {
        System.out.println("Students solution analysis\n");
        int satisfaction;
        for (Student student : students.values()) {
            satisfaction = 0;
            List<Project> studProjects = student.preferredProjectsCopy;
            for (int j = 0; j < studProjects.size(); ++j) {
                if (studProjects.get(j).equals(student.projectAssigned)) {
                    satisfaction = (int) ((double) (studProjects.size() - j) / studProjects.size() * 100);
                    break;
                }
            }
            projectsAnalysis.put(student, satisfaction);
        }
        int sum = 0;
        for (Map.Entry<Student, Integer> entry : projectsAnalysis.entrySet()) {
            String name = entry.getKey().getName();
            System.out.println(name + " : " + entry.getValue() + "% pleased.");
            sum += entry.getValue();
        }
        sum /= projectsAnalysis.size();
        System.out.println("Overall : " + sum + "% pleased.");
    }

    HashMap<Student, Integer> teacherSatisfactions = new HashMap<Student, Integer>();

    /**
     * Print teacher solution Analysis
     */
    public void printTeacherSolutionAnalysis() {
        System.out.println("Teachers solution analysis \n");
        LinkedHashMap <Teacher, Integer>  projectsAnalysis = new LinkedHashMap<Teacher, Integer>();
        int satisfaction;
        int totalSatisfaction;
        for (Teacher teacher : teachers.values()) {
            Iterator<Student> iterator = teacher.subscribedStudents.iterator();
            for (; iterator.hasNext(); ) {
                Student currentStudent = iterator.next();
                if (currentStudent == null) {
                    iterator.remove();
                }
            }
        }


         for (Teacher teacher : teachers.values()) {
            totalSatisfaction = 0;
            List<Student> preferredStuds = teacher.preferredStudents;
            List<Student> subscribedStuds = teacher.subscribedStudents;
            int foundBefore = 0;
            for (int k = 0; k < preferredStuds.size(); ++k) {
                satisfaction = 0;
                for (int j = 0; j < subscribedStuds.size(); ++j) {
                    if (preferredStuds.get(k).equals(subscribedStuds.get(j))) {
                        satisfaction = (int) ((double) (preferredStuds.size() - k) / (preferredStuds.size() - foundBefore) * 100);
                        foundBefore++;
                        break;
                    }
                }
                totalSatisfaction += satisfaction;

                Student index = preferredStuds.get(k);
                if(teacherSatisfactions.containsKey(index)) {
                    if (teacherSatisfactions.get(index) == 0)
                        teacherSatisfactions.put(index, satisfaction);
                }
                else
                {
                    teacherSatisfactions.put(index, satisfaction);
                }
            }
            if (subscribedStuds.size() > 0) {
                totalSatisfaction /= subscribedStuds.size();
            }
            projectsAnalysis.put(teacher, totalSatisfaction);
        }
        int sum = 0;
        for (Map.Entry<Teacher, Integer> entry : projectsAnalysis.entrySet()) {
            String name = entry.getKey().getName();
            System.out.println(name + " : " + entry.getValue() + "% pleased.");
            sum += entry.getValue();
        }
        sum /= projectsAnalysis.size();
        System.out.println("Overall : " + sum + "% pleased.");
    }

    /**
     * Average between student satisfaction and teacher satisfaction for that student.
     */
    public void printMatchingSolutionAnalysis() {
        System.out.println("Matching Solution Analysis\n");
        int count = 0;
        int sum = 0;
        for (Student student : students.values()) {
            if (projectsAnalysis.get(student) > 0) {
                System.out.println(student.getName() + " - " + student.projectTeacher.getName() + " : "
                        + (projectsAnalysis.get(student) + teacherSatisfactions.get(student)) / 2 + "% pleased.");
                sum += ((projectsAnalysis.get(student) + teacherSatisfactions.get(student)) / 2);
                count++;
            }
        }
        System.out.println("Overall : " + sum / count + "% pleased.");
    }

    /**
     * Overridden function for toString() method
     */
    public String toString() {
        List<Student> students = getStudents();
        List<Teacher> teachers = getTeachers();
        String studentsHeader = "   Student preferences   ";
        int maxLengthOfStudentsHeader = studentsHeader.length();
        maxLengthOfStudentsHeader = getMaxHeaderLength(students, maxLengthOfStudentsHeader, "students");
        String teachersHeader = "   Teacher preferences   ";
        int maxLengthOfTeacherHeader = teachersHeader.length();
        maxLengthOfTeacherHeader = getMaxHeaderLength(teachers, maxLengthOfTeacherHeader, "students");
        String projectsHeader = " Available projects ";
        int maxLengthOfProjectsHeader = projectsHeader.length();
        maxLengthOfProjectsHeader = getMaxHeaderLength(teachers, maxLengthOfProjectsHeader, "projects");

        int totalHeaderLength = maxLengthOfStudentsHeader + maxLengthOfTeacherHeader + maxLengthOfProjectsHeader;

        StringBuilder lineSb = new StringBuilder();
        for (int i = 0; i < totalHeaderLength + 4; ++i) {
            lineSb.append("-");
        }
        String line = String.valueOf(lineSb);

        StringBuilder table = new StringBuilder(line);
        table.append("\n");
        table.append("|");
        table.append(getPaddedHeader(studentsHeader, maxLengthOfStudentsHeader));
        table.append("|");
        table.append(getPaddedHeader(teachersHeader, maxLengthOfTeacherHeader));
        table.append("|");
        table.append(getPaddedHeader(projectsHeader, maxLengthOfProjectsHeader));
        table.append("|\n");
        table.append(line);
        table.append("\n");

        int currentStudent = 0;
        int currentTeacher = 0;

        while (currentStudent < students.size() || currentTeacher < teachers.size()) {
            table.append("|");
            String currentRow = currentStudent < students.size() ? students.get(currentStudent).toString() : "";
            table.append(getTableRow(currentRow, maxLengthOfStudentsHeader));
            table.append("|");
            currentRow = currentTeacher < teachers.size() ? teachers.get(currentTeacher).toString() : "";
            table.append(getTableRow(currentRow, maxLengthOfTeacherHeader));
            table.append("|");
            currentRow = currentTeacher < teachers.size() ? teachers.get(currentTeacher).availableProjectsToString() : "";
            table.append(getTableRow(currentRow, maxLengthOfProjectsHeader));
            table.append("|\n");
            table.append(line);
            table.append("\n");

            currentStudent++;
            currentTeacher++;
        }

        return String.valueOf(table);
    }
}