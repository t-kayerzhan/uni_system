package system;

import academic.*;
import enums.*;
import exceptions.*;
import research.*;
import users.*;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import static system.AppContext.*;
import static system.CommonConsoleActions.*;

public class ConsoleApp {
	public static void run() {
		System.out.println("=======================================");
		System.out.println("    Research University System v1.0   ");
		System.out.println("=======================================");
		DataStorage storage = DataStorage.getInstance();
		UniversitySystem loaded = storage.loadAll();
		if (loaded != null && !loaded.getUsers().isEmpty()) {
			sys = loaded;
			System.out.println(" √ Data restored: " + sys.getUsers().size() + " users.");
		} else {
			ConsoleApp.initSampleData();
		}
		while (true) {
			if (currentUser == null)
				ConsoleApp.showLoginMenu();
			else
				MenuRouter.showUserMenu();
		}

	}

	public static void initSampleData() {
		Admin admin = UserFactory.createAdmin("1", "admin", "admin123", "Assel", "Admin", "admin@uni.edu", 500000,
				"Administration");

		Teacher teacher = (Teacher) UserFactory.createTeacher("2", "teacher1", "pass123", "Bob", "Smith", "bob@uni.edu",
				400000, "Computer Science", TeacherPosition.PROFESSOR);

		Teacher dbTeacher = (Teacher) UserFactory.createTeacher("7", "teacher2", "pass123", "Mosavi", "Database",
				"john@uni.edu", 380000, "Computer Science", TeacherPosition.SENIOR_LECTURER);

		Student student = UserFactory.createStudent("3", "student1", "pass123", "Cerik", "Kuanyshuly", "cerik@uni.edu",
				"SE2024001", 3, "Software Engineering");
		student.setSchool("Computer Science");
		Student student2 = UserFactory.createStudent("8", "student2", "pass123", "Emma", "White", "emma@uni.edu",
				"SE2024002", 2, "Software Engineering");
		student2.setSchool("Computer Science");

		Student student3 = UserFactory.createStudent("9", "student3", "pass123", "Mike", "Green", "mike@uni.edu",
				"SE2024003", 1, "Information Systems");
		student3.setSchool("Computer Science");

		GraduateStudent grad = (GraduateStudent) UserFactory.createGraduate("4", "grad1", "pass123", "Nurzhan",
				"malikov", "nurik@uni.edu", "MS2024001", 1, "Computer Science", GraduateType.MASTER);
		grad.setSchool("Computer Science");

		Manager manager = UserFactory.createManager("5", "manager1", "pass123", "aruzhan", "alimova", "aru@uni.edu",
				450000, "Registration Office", ManagerType.OR);

		TechSupportSpecialist tech = UserFactory.createTechSupport("6", "tech1", "pass123", "Dyadya Borya", "Tech",
				"boris@uni.edu", 300000, "IT Support");

		sys.addUser(admin);
		sys.addUser(teacher);
		sys.addUser(student);
		sys.addUser(grad);
		sys.addUser(manager);
		sys.addUser(tech);
		sys.addUser(dbTeacher);
		sys.addUser(student2);
		sys.addUser(student3);
		users.StudentOrganization org = new users.StudentOrganization("ACM Club", "Programming club");
		sys.addOrganization(org);
		try {
			student.joinOrganization(org, OrganizationRole.HEAD);
		} catch (Exception e) {
		}

		Course oop = new Course("CS101", "Object-Oriented Programming", "OOP fundamentals", 5);
		oop.setTargetMajor("Software Engineering");
		oop.setSchool("Computer Science");
		Course db = new Course("CS201", "Databases", "SQL and NoSQL", 4);
		db.setTargetMajor("Software Engineering");
		db.setSchool("Computer Science");
		sys.addCourse(oop);
		sys.addCourse(db);

		oopSection = new CourseSection(oop, 30, Semester.SPRING, 2026);
		dbSection = new CourseSection(db, 25, Semester.SPRING, 2026);

		oopSection.assignTeacher(teacher, LessonType.LECTURE);
		oopSection.assignTeacher(teacher, LessonType.PRACTICE);
		sys.addSection(oopSection);

		dbSection.assignTeacher(dbTeacher, LessonType.LECTURE);
		dbSection.assignTeacher(dbTeacher, LessonType.PRACTICE);

		sys.addSection(dbSection);

		
		Course heavyCourse = new Course("CS301", "Advanced Algorithms", "Heavy course", 20);
		heavyCourse.setTargetMajor("Software Engineering");
		heavyCourse.setSchool("Computer Science");
		sys.addCourse(heavyCourse);

		CourseSection heavySection = new CourseSection(heavyCourse, 30, Semester.FALL, 2026);
		heavySection.assignTeacher(teacher, LessonType.LECTURE);
		heavySection.assignTeacher(teacher, LessonType.PRACTICE);
		sys.addSection(heavySection);

		try {
			student.registerForCourse(oopSection);
		} catch (Exception e) {
		}
		try {
			grad.registerForCourse(oopSection);
		} catch (Exception e) {
		}
		try {
			student2.registerForCourse(dbSection);
		} catch (Exception e) {
		}
		try {
			student3.registerForCourse(dbSection);
		} catch (Exception e) {
		}
		try {
			student2.registerForCourse(oopSection);
		} catch (Exception e) {
		}

		Room room1 = new Room("101", "Main Building", 1, 100, RoomType.LECTURE_HALL);
		Room room2 = new Room("201", "Tech Building", 2, 30, RoomType.COMPUTER_LAB);
		sys.addRoom(room1);
		sys.addRoom(room2);

		Lesson lesson1 = new Lesson("Intro to OOP", new Date(), "09:00", "10:30", LessonType.LECTURE, room1);
		oopSection.addLesson(lesson1);
		Lesson lesson2 = new Lesson("Introduction to SQL", new Date(), "11:00", "12:30", LessonType.LECTURE, room1);
		dbSection.addLesson(lesson2);

		Lesson lesson3 = new Lesson("SQL Practice", new Date(), "13:00", "14:30", LessonType.PRACTICE, room2);
		dbSection.addLesson(lesson3);
		room2.book("SQL Practice", dbSection.getEnrolledStudents().size());

		ResearchPaper paper2 = new ResearchPaper("Advanced Databases", Arrays.asList("Bob Smith"), "Springer", 15,
				new Date(), "10.1000/DB2024.002");
		paper2.setCitations(4);

		ResearchPaper paper3 = new ResearchPaper("Java OOP Patterns", Arrays.asList("Bob Smith"), "ACM Journal", 20,
				new Date(), "10.1000/JAVA2024.003");
		paper3.setCitations(3);

		teacher.addPaper(paper2);
		teacher.addPaper(paper3);

		ResearchPaper paper = new ResearchPaper("Machine Learning in Education",
				Arrays.asList("Bob Smith", "Dave Brown"), "IEEE Transactions", 12, new Date(), "10.1109/ML2024.001");
		paper.setCitations(5);
		teacher.addPaper(paper);
		grad.addPaper(paper);
		ResearchProject project = new ResearchProject("AI in Education", "Applying ML to university systems");
		sys.addResearchProject(project);
		try {
			project.addParticipant(teacher);
			project.addParticipant(grad);
		} catch (Exception e) {
		}
		project.addPaper(paper);

		Startup startup = new Startup("EduAI", "AI-powered education platform", project);
		startup.addFounder(teacher);
		startup.addFounder((User) grad);
		sys.addStartup(startup);

		Journal journal = new Journal("University Research Journal", "1234-5678", "Flagship journal");
		sys.addJournal(journal);
		journal.publishPaper(paper2);
		journal.subscribe(student);

		TechSupportRequest request = new TechSupportRequest("Projector broken", "Room 101 projector not working",
				student);
		tech.addRequest(request);

		News news1 = new News("Welcome to RU System", "Classes start next Monday.", NewsTopic.GENERAL, admin);

		News news2 = new News("Research Conference", "AI conference will be held this Friday.", NewsTopic.RESEARCH,
				teacher);

		News news3 = new News("Database Midterm", "Database midterm exam on May 25.", NewsTopic.ACADEMIC, dbTeacher);

		sys.addNews(news1);
		sys.addNews(news2);
		sys.addNews(news3);

		sys.generateTopResearcherNews(admin);

		room1.book("OOP Lecture", oopSection.getEnrolledStudents().size());

		

		Teacher mathTeacher = (Teacher) UserFactory.createTeacher("10", "teacher3", "pass123", "Sarah", "Johnson",
				"sarah@uni.edu", 390000, "Mathematics", TeacherPosition.LECTURER);

		Teacher aiTeacher = (Teacher) UserFactory.createTeacher("11", "teacher4", "pass123", "Mohamed", "khali",
				"mochamed@uni.edu", 450000, "Computer Science", TeacherPosition.PROFESSOR);

		Teacher physicsTeacher = (Teacher) UserFactory.createTeacher("12", "teacher5", "pass123", "Emily", "Clark",
				"emily@uni.edu", 410000, "Physics", TeacherPosition.SENIOR_LECTURER);

		sys.addUser(mathTeacher);
		sys.addUser(aiTeacher);
		sys.addUser(physicsTeacher);

		

		Room room3 = new Room("301", "Math Building", 3, 80, RoomType.LECTURE_HALL);

		Room room4 = new Room("401", "AI Center", 4, 25, RoomType.COMPUTER_LAB);

		Room room5 = new Room("105", "Physics Building", 1, 60, RoomType.LECTURE_HALL);
		Room room6 = new Room("151", "practical lesson", 1, 60, RoomType.SEMINAR_ROOM);
		

		sys.addRoom(room3);
		sys.addRoom(room4);
		sys.addRoom(room5);
		sys.addRoom(room6);

		


		Course calculus = new Course("MATH101", "Calculus", "Limits, derivatives, integrals", 5);

		calculus.setTargetMajor("Software Engineering");
		calculus.setSchool("Mathematics");

		Course ai = new Course("CS305", "Artificial Intelligence", "Introduction to AI and ML", 6);

		ai.setTargetMajor("Software Engineering");
		ai.setSchool("Computer Science");

		

		Course physics = new Course("PH101", "Physics", "General physics course", 4);

		physics.setSchool("Physics");

		Course publicSpeaking = new Course("HUM201", "Public Speaking", "Communication skills", 3);

		publicSpeaking.setSchool("Humanities");

		sys.addCourse(calculus);
		sys.addCourse(ai);
		sys.addCourse(physics);
		sys.addCourse(publicSpeaking);

		

		CourseSection calcSection = new CourseSection(calculus, 35, Semester.SPRING, 2026);

		CourseSection aiSection = new CourseSection(ai, 20, Semester.SPRING, 2026);

		CourseSection physicsSection = new CourseSection(physics, 40, Semester.SPRING, 2026);

		CourseSection speakingSection = new CourseSection(publicSpeaking, 50, Semester.SPRING, 2026);

		

		calcSection.assignTeacher(mathTeacher, LessonType.LECTURE);
		calcSection.assignTeacher(mathTeacher, LessonType.PRACTICE);

		aiSection.assignTeacher(aiTeacher, LessonType.LECTURE);
		aiSection.assignTeacher(aiTeacher, LessonType.PRACTICE);

		physicsSection.assignTeacher(physicsTeacher, LessonType.LECTURE);

		speakingSection.assignTeacher(teacher, LessonType.PRACTICE);

		

		sys.addSection(calcSection);
		sys.addSection(aiSection);
		sys.addSection(physicsSection);
		sys.addSection(speakingSection);

		

		Lesson calcLesson = new Lesson("Derivatives", new Date(), "10:00", "11:30", LessonType.LECTURE, room3);

		Lesson calcPractice = new Lesson("Integrals Practice", new Date(), "12:00", "13:30", LessonType.PRACTICE,
				room3);

		Lesson aiLesson = new Lesson("Neural Networks", new Date(), "15:00", "16:30", LessonType.LECTURE, room4);

		Lesson physicsLesson = new Lesson("Newton Laws", new Date(), "09:00", "10:30", LessonType.LECTURE, room5);

		Lesson speakingLesson = new Lesson("Presentation Skills", new Date(), "14:00", "15:30", LessonType.PRACTICE,
				room1);


		calcSection.addLesson(calcLesson);
		calcSection.addLesson(calcPractice);

		aiSection.addLesson(aiLesson);

		physicsSection.addLesson(physicsLesson);

		speakingSection.addLesson(speakingLesson);

		

		room3.book("Calculus Lecture", 35);
		room4.book("AI Lecture", 20);
		room5.book("Physics Lecture", 40);

		Teacher unassignedTeacher = (Teacher) UserFactory.createTeacher("13", "teacher6", "pass123", "David", "Miller",
				"david@uni.edu", 370000, "Computer Science", TeacherPosition.TUTOR);

		sys.addUser(unassignedTeacher);

		Room emptyRoom = new Room("501", "New Building", 5, 50, RoomType.LECTURE_HALL);

		sys.addRoom(emptyRoom);

		System.out.println("\n Sample data initialized. " + sys.getUsers().size() + " users loaded.\n");
	}

	public static void showLoginMenu() {
		System.out.println("\n============================");
		System.out.println("  1. Login");
		System.out.println("  0. Exit");
		System.out.println("============================");
		System.out.print("Choice: ");
		String choice = scanner.nextLine();
		switch (choice) {
		case "1":
			System.out.print("Username: ");
			String username = scanner.nextLine();
			System.out.print("Password: ");
			String password = scanner.nextLine();
			currentUser = auth.authenticate(username, password, sys.getUsers());

			if (currentUser != null) {
				sessionId = auth.generateSession(currentUser);
				System.out.println("\nWelcome, " + currentUser.getFullName() + "!");

				List<research.News> pinnedNews = sys.getNews().stream()
						.filter(n -> n.isPinned() && n.getTopic() == NewsTopic.RESEARCH)
						.collect(java.util.stream.Collectors.toList());
				if (!pinnedNews.isEmpty()) {
					System.out.println(" Pinned announcements:");
					for (research.News n : pinnedNews) {
						System.out.println("  • " + n.getTitle() + " | " + n.getPublishedAt());
					}
				}

				
				List<Message> journalNotifs = new ArrayList<>();
				for (Message m : currentUser.getUnreadMessages()) {
					if (m.getSubject().startsWith("[JOURNAL]")) {
						journalNotifs.add(m);
						m.markAsRead();
					}
				}
				if (!journalNotifs.isEmpty()) {
					System.out.println(" Journal notifications:");
					for (Message m : journalNotifs) {
						System.out.println("  • " + m.getSubject() + ": " + m.getText());
					}
				}

			} else {
				
				System.out.println("Login failed! Invalid username or password.");
			}

			break; 
		case "0":
			System.out.println("Goodbye!");
			System.exit(0);
		default:
			System.out.println("Invalid choice.");
		}
	}
}
