package system;

import enums.Language;
import java.util.HashMap;
import java.util.Map;

public class Localization {
    private static final Map<String, Map<Language, String>> t = new HashMap<>();

    static {
        add("login.title",       "Login",            "Войти",              "Кіру");
        add("login.exit",        "Exit",             "Выйти",              "Шығу");
        add("login.username",    "Username: ",       "Имя пользователя: ", "Пайдаланушы аты: ");
        add("login.password",    "Password: ",       "Пароль: ",           "Құпия сөз: ");
        add("login.welcome",     "Welcome, ",        "Добро пожаловать, ", "Қош келдіңіз, ");
        add("login.invalid",     "Invalid choice.",  "Неверный выбор.",    "Қате таңдау.");
        add("login.goodbye",     "Goodbye!",         "До свидания!",       "Сау болыңыз!");

        add("menu.change_lang",  "Change language",  "Сменить язык",       "Тілді өзгерту");
        add("menu.logout",       "Logout",           "Выйти",              "Шығу");
        add("menu.choice",       "Choice: ",         "Выбор: ",            "Таңдау: ");

        
        add("student.menu",           "STUDENT MENU",              "МЕНЮ СТУДЕНТА",              "СТУДЕНТ МӘЗІРІ");
        add("student.view_courses",   "View available courses",    "Доступные курсы",            "Қол жетімді курстар");
        add("student.register",       "Register for course",       "Записаться на курс",         "Курсқа тіркелу");
        add("student.drop",           "Drop course",               "Отчислиться с курса",        "Курстан шығу");
        add("student.marks",          "View my marks",             "Мои оценки",                 "Бағаларым");
        add("student.transcript",     "View transcript",           "Просмотр транскрипта",       "Транскриптті көру");
        add("student.attendance",     "View attendance",           "Посещаемость",               "Қатысуды көру");
        add("student.rate_teacher",   "Rate a teacher",            "Оценить преподавателя",      "Оқытушыны бағалау");
        add("student.news",           "View news",                 "Новости",                    "Жаңалықтар");
        add("student.no_marks",       "No marks yet.",             "Оценок пока нет.",           "Әзірше баға жоқ.");
        add("student.no_courses",     "Not registered in any course.", "Не записан ни на один курс.", "Ешбір курсқа тіркелмеген.");
        add("student.no_sections",    "No sections available.",    "Нет доступных секций.",      "Қол жетімді секциялар жоқ.");
        add("student.available",      "Available sections:",       "Доступные секции:",          "Қол жетімді секциялар:");

       
        add("teacher.menu",           "TEACHER MENU",              "МЕНЮ ПРЕПОДАВАТЕЛЯ",         "ОҚЫТУШЫ МӘЗІРІ");
        add("teacher.courses",        "View my courses",           "Мои курсы",                  "Менің курстарым");
        add("teacher.put_mark",       "Put mark for student",      "Поставить оценку",           "Баға қою");
        add("teacher.attendance",     "Mark attendance",           "Отметить посещаемость",      "Қатысуды белгілеу");
        add("teacher.complaint",      "Send complaint",            "Отправить жалобу",           "Шағым жіберу");
        add("teacher.report",         "Generate mark report",      "Отчёт по оценкам",           "Баға есебі");
        add("teacher.letter",         "Generate recommendation letter", "Рекомендательное письмо", "Ұсыныс хаты");
        add("teacher.hindex",         "View my h-index",           "Мой h-индекс",               "Менің h-индексім");
        add("teacher.papers",         "Print my papers",           "Мои статьи",                 "Менің мақалаларым");
        add("teacher.message",        "Send message to user",      "Отправить сообщение",        "Хабарлама жіберу");
        add("teacher.no_courses",     "No courses.",               "Курсов нет.",                "Курстар жоқ.");

        // Admin menu
        add("admin.menu",             "ADMIN MENU",                "МЕНЮ АДМИНИСТРАТОРА",        "ӘКІМШІ МӘЗІРІ");
        add("admin.view_users",       "View all users",            "Все пользователи",           "Барлық пайдаланушылар");
        add("admin.create_user",      "Create user",               "Создать пользователя",       "Пайдаланушы жасау");
        add("admin.delete_user",      "Delete user",               "Удалить пользователя",       "Пайдаланушыны жою");
        add("admin.logs",             "View logs",                 "Просмотр логов",             "Журналды көру");
        add("admin.papers",           "Print all research papers", "Все научные статьи",         "Барлық ғылыми мақалалар");

        // Manager menu
        add("manager.menu",           "MANAGER MENU",              "МЕНЮ МЕНЕДЖЕРА",             "МЕНЕДЖЕР МӘЗІРІ");
        add("manager.assign",         "Assign teacher to section", "Назначить преподавателя",    "Оқытушыны тағайындау");
        add("manager.students",       "View students sorted",      "Студенты (сортировка)",      "Студенттер (сұрыптау)");
        add("manager.report",         "Generate academic report",  "Академический отчёт",        "Академиялық есеп");
        add("manager.news",           "Publish news",              "Опубликовать новость",       "Жаңалық жариялау");
        add("manager.courses",        "View all courses",          "Все курсы",                  "Барлық курстар");

        // Grad menu
        add("grad.menu",              "GRADUATE STUDENT MENU",     "МЕНЮ АСПИРАНТА",             "МАГИСТРАНТ МӘЗІРІ");
        add("grad.student_options",   "All student options",       "Меню студента",              "Студент мәзірі");
        add("grad.supervisor",        "Set supervisor",            "Назначить научрука",         "Жетекшіні белгілеу");
        add("grad.hindex",            "View my h-index",           "Мой h-индекс",               "Менің h-индексім");
        add("grad.diploma",           "View diploma papers",       "Дипломные работы",           "Диплом жұмыстары");

        // Tech menu
        add("tech.menu",              "TECH SUPPORT MENU",         "МЕНЮ ТЕХПОДДЕРЖКИ",          "ТЕХҚОЛДАУ МӘЗІРІ");
        add("tech.view",              "View new requests",         "Новые заявки",               "Жаңа өтінімдер");
        add("tech.accept",            "Accept  request",      "Принять заявку",             "Өтінімді қабылдау");
        add("tech.reject",            "Reject  request",      "Отклонить заявку",           "Өтінімді қабылдамау");
        add("tech.done",              "Mark  request done",   "Завершить заявку",           "Өтінімді аяқтау");
        add("tech.no_requests",       "No new requests.",          "Новых заявок нет.",          "Жаңа өтінімдер жоқ.");
        add("tech.no_requests_all",   "No requests.",              "Заявок нет.",                "Өтінімдер жоқ.");

        add("error.invalid",          "Invalid choice.",           "Неверный выбор.",            "Қате таңдау.");
        add("logout.bye",             "Logged out. Goodbye, ",     "Выход. До свидания, ",       "Шықты. Сау болыңыз, ");
        add("teacher.news", "View & comment news", "Новости и комментарии", "Жаңалықтар және пікірлер");
        add("news.comment_prompt", "Enter news number to comment (0 to skip): ", "Номер новости для комментария (0 - пропустить): ", "Пікір қалдыру үшін жаңалық нөмірі (0 - өткізіп жіберу): ");
        add("student.orgs", "Student organizations", "Студенческие организации", "Студенттік ұйымдар");
        add("student.journal", "Subscribe to journal", "Подписка на журнал", "Журналға жазылу");
        add("teacher.publish_paper", "Publish paper to journal", "Опубликовать статью в журнале", "Мақаланы журналға жариялау");
        add("grad.add_paper", "Add new research paper", "Добавить новую статью", "Жаңа мақала қосу");
        add("teacher.add_paper", "Add new research paper", "Добавить новую статью", "Жаңа мақала қосу");
        add("menu.messages", "View messages", "Мои сообщения", "Хабарламаларым");
        add("student.message", "Send message", "Отправить сообщение", "Хабарлама жіберу");
        add("student.tech_request", "Send tech support request", "Заявка в техподдержку", "Техқолдауға өтінім");
        add("student.my_requests", "View my tech requests", "Мои заявки в техподдержку", "Менің өтінімдерім");
        add("admin.update_user", "Update user", "Обновить пользователя", "Пайдаланушыны жаңарту");
        add("student.teacher_info", "View teacher info by course", "Информация о преподавателях", "Оқытушылар туралы ақпарат");
        add("teacher.startup", "Manage Startup", "Управление стартапом", "Стартапты басқару");
        add("student.schedule", "My schedule", "Моё расписание", "Менің кестем");
        add("teacher.startup", "Manage Startup", "Управление стартапом", "Стартапты басқару");
        add("manager.approve", "Approve student registrations", "Утверждение регистрации", "Тіркелуді бекіту");
    }
    
    private static void add(String key, String en, String ru, String kz) {
        Map<Language, String> map = new HashMap<>();
        map.put(Language.EN, en);
        map.put(Language.RU, ru);
        map.put(Language.KZ, kz);
        t.put(key, map);
    }

    public static String get(String key, Language lang) {
        Map<Language, String> map = t.get(key);
        if (map == null) return key;
        return map.getOrDefault(lang, key);
    };
    
 
}