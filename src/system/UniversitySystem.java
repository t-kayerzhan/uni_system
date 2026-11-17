package system;

import academic.Course;
import enums.NewsTopic;
import research.*;
import users.User;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import academic.CourseSection;
import academic.CourseRegistrationRequest;
import enums.CitationFormat;

public class UniversitySystem implements Serializable {
	private static UniversitySystem instance;

	private List<User> users;
	private List<Course> courses;
	private List<research.News> news;
	private List<Journal> journals;
	private List<academic.Room> rooms;
	private List<users.StudentOrganization> organizations;
	private List<String> logs;
	private List<CourseSection> sections = new ArrayList<>();
	private List<CourseRegistrationRequest> pendingRegistrations = new ArrayList<>();
	private List<Startup> startups = new ArrayList<>();
	private List<users.Complaint> complaints = new ArrayList<>();
	private List<users.OfficialRequest> officialRequests = new ArrayList<>();
	private List<ResearchProject> researchProjects = new ArrayList<>();

	private UniversitySystem() {
		users = new ArrayList<>();
		courses = new ArrayList<>();
		news = new ArrayList<>();
		journals = new ArrayList<>();
		rooms = new ArrayList<>();
		organizations = new ArrayList<>();
		logs = new ArrayList<>();
	}

	public static UniversitySystem getInstance() {
		if (instance == null)
			instance = new UniversitySystem();
		return instance;
	}

	public void addUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
			logAction(user.getId(), "User created: " + user.getFullName());
		}
	}

	public void removeUser(String userId) {
		boolean removed = users.removeIf(u -> u.getId().equals(userId));
		if (removed) {
			logAction(userId, "User removed: #" + userId);
		}
	}

	public User getUserById(String id) {
		return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
	}

	public void addCourse(Course course) {
		if (!courses.contains(course)) {
			courses.add(course);
		}
	}

	public void addJournal(Journal journal) {
		if (!journals.contains(journal)) {
			journals.add(journal);
		}
	}

	public void addRoom(academic.Room room) {
		if (!rooms.contains(room)) {
			rooms.add(room);
		}
	}

	public void addOrganization(users.StudentOrganization org) {
		organizations.add(org);
	}

	public void addNews(research.News n) {
		news.add(n);
	}

	public List<research.News> getNewsSortedByPriority() {
		return news.stream().sorted((a, b) -> Boolean.compare(b.isPinned(), a.isPinned())).collect(Collectors.toList());
	}

	public Researcher getTopCitedResearcher() {
		return getAllResearchers().stream()
				.max(Comparator.comparingInt(r -> r.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum()))
				.orElse(null);
	}

	public Researcher getTopCitedResearcherByYear(int year) {
		return getAllResearchers().stream().max(Comparator.comparingInt(r -> r.getPapers().stream()
				.filter(p -> p.getPublishDate().getYear() + 1900 == year).mapToInt(ResearchPaper::getCitations).sum()))
				.orElse(null);
	}

	public void printAllPapers(Comparator<ResearchPaper> comp) {
		Set<String> seen = new HashSet<>();
		List<ResearchPaper> all = new ArrayList<>();
		for (User u : users) {
			if (u instanceof Researcher) {
				for (ResearchPaper p : ((Researcher) u).getPapers()) {
					if (seen.add(p.getDoi())) {
						all.add(p);
					}
				}
			}
		}
		all.sort(comp);
		System.out.println("===== ALL RESEARCH PAPERS =====");
		for (int i = 0; i < all.size(); i++)
			System.out.println(all.get(i));
	}

	public research.News generateResearchAnnouncementNews(ResearchPaper paper, User author) {

		for (research.News n : news) {
			if (n.getTitle().equals("New Research Paper Published: " + paper.getTitle())) {
				return n;
			}
		}
		research.News n = new research.News("New Research Paper Published: " + paper.getTitle(),
				"A new paper has been published: " + paper.getCitation(enums.CitationFormat.PLAIN_TEXT),
				NewsTopic.RESEARCH, author);
		news.add(n);
		return n;
	}

	public research.News generateTopResearcherNews(User systemUser) {
		Researcher top = getTopCitedResearcher();
		if (top == null)
			return null;
		int totalCitations = top.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum();
		research.News n = new research.News("Top Cited Researcher of the University",
				"Congratulations! Total citations: " + totalCitations, NewsTopic.RESEARCH, systemUser);
		news.add(n);
		return n;
	}

	public Researcher getTopCitedResearcherBySchool(String school) {
		if (school == null)
			return null;
		return getAllResearchers().stream().filter(r -> r instanceof users.Employee)
				.filter(r -> ((users.Employee) r).getDepartment() != null
						&& ((users.Employee) r).getDepartment().equalsIgnoreCase(school))
				.max(Comparator.comparingInt(r -> r.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum()))
				.orElse(null);
	}

	public int getTotalCitations(Researcher researcher) {
		if (researcher == null)
			return 0;
		return researcher.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum();
	}

	public List<Researcher> getAllResearchers() {
		List<Researcher> researchers = new ArrayList<>();
		for (User u : users) {
			if (u instanceof Researcher)
				researchers.add((Researcher) u);
		}
		return researchers;
	}

	public void logAction(String userId, String action) {
		String entry = "[" + new Date() + "] User#" + userId + " - " + action;
		logs.add(entry);
	}

	public void addRegistrationRequest(CourseRegistrationRequest req) {
		pendingRegistrations.add(req);
	}

	public List<CourseRegistrationRequest> getPendingRegistrations() {
		return pendingRegistrations;
	}

	public void addStartup(Startup s) {
		startups.add(s);
	}

	public List<Startup> getStartups() {
		return startups;
	}

	public void addComplaint(users.Complaint complaint) {
		complaints.add(complaint);
		logAction(complaint.getSender().getId(),
				"Complaint created about " + complaint.getTargetStudent().getFullName());
	}

	public List<users.Complaint> getComplaints() {
		return complaints;
	}

	public void addOfficialRequest(users.OfficialRequest request) {
		officialRequests.add(request);
		logAction(request.getCreatedBy().getId(), "Official request created: " + request.getTitle());
	}

	public List<users.OfficialRequest> getOfficialRequests() {
		return officialRequests;
	}

	public void addResearchProject(ResearchProject project) {
		if (!researchProjects.contains(project))
			researchProjects.add(project);
	}

	public List<ResearchProject> getResearchProjects() {
		return researchProjects;
	}

	public research.News generateResearchAnnouncementNewsWithTopic(ResearchPaper paper, User author, NewsTopic topic) {
		for (research.News n : news) {
			if (n.getTitle().equals("New Research Paper Published: " + paper.getTitle())) {
				return n;
			}
		}
		research.News n = new research.News("New Research Paper Published: " + paper.getTitle(),
				"A new paper has been published: " + paper.getCitation(enums.CitationFormat.PLAIN_TEXT), topic, author);
		news.add(n);
		return n;
	}

	public List<users.Teacher> getTeachersSortedByRating() {
		List<users.Teacher> teachers = new ArrayList<>();
		for (users.User u : users) {
			if (u instanceof users.Teacher) {
				teachers.add((users.Teacher) u);
			}
		}

		teachers.sort(UniversityComparators.BY_RATING_DESC);
		return teachers;
	}

	public List<users.Teacher> getTeachersSortedByHIndex() {
		List<users.Teacher> teachers = new ArrayList<>();
		for (users.User u : users) {
			if (u instanceof users.Teacher) {
				teachers.add((users.Teacher) u);
			}
		}
		teachers.sort(UniversityComparators.BY_HINDEX_DESC);
		return teachers;
	}

	public List<String> getLogs() {
		return logs;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<Journal> getJournals() {
		return journals;
	}

	public List<academic.Room> getRooms() {
		return rooms;
	}

	public List<research.News> getNews() {
		return news;
	}

	public List<users.StudentOrganization> getOrganizations() {
		return organizations;
	}

	public void addSection(CourseSection section) {
		sections.add(section);
	}

	public List<CourseSection> getSections() {
		return sections;
	}
}
