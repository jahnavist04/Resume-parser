import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeParser {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("(?:\\+?\\d{1,3}[\\s.-]?)?(?:\\d[\\s.-]?){9,12}\\d");

    private static final String[] SKILLS = {
        "Java", "Python", "C", "C++", "JavaScript", "HTML", "CSS",
        "SQL", "MySQL", "Machine Learning", "Data Science", "React",
        "Node.js", "Git", "Power BI"
    };

    public static void main(String[] args) {
        Path resumePath = Path.of("resumes", "sample.txt");

        try {
            String resumeText = Files.readString(resumePath);

            System.out.println("===== RESUME PARSER =====");
            System.out.println("Name: " + extractName(resumeText));
            System.out.println("Email: " + extractFirst(EMAIL_PATTERN, resumeText));
            System.out.println("Phone: " + extractFirst(PHONE_PATTERN, resumeText));
            System.out.println("Skills: " + extractSkills(resumeText));
            System.out.println("Education: " + extractLine(resumeText, "Education:"));
        } catch (IOException e) {
            System.out.println("Could not read " + resumePath);
            System.out.println("Make sure resumes/sample.txt exists and run from the project folder.");
        }
    }

    private static String extractName(String text) {
        for (String line : text.split("\\R")) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty() && !trimmed.contains("@")
                    && !trimmed.toLowerCase().startsWith("phone:")
                    && !trimmed.toLowerCase().startsWith("skills:")
                    && !trimmed.toLowerCase().startsWith("education:")) {
                return trimmed.replaceFirst("(?i)^name:\\s*", "");
            }
        }
        return "Not found";
    }

    private static String extractFirst(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group().trim() : "Not found";
    }

    private static String extractSkills(String text) {
        Set<String> found = new LinkedHashSet<>();
        for (String skill : SKILLS) {
            Pattern pattern = Pattern.compile(
                "(?i)(?<![A-Za-z0-9+#.])" + Pattern.quote(skill)
                + "(?![A-Za-z0-9+#.])");
            if (pattern.matcher(text).find()) {
                found.add(skill);
            }
        }
        return found.isEmpty() ? "None found" : String.join(", ", found);
    }

    private static String extractLine(String text, String label) {
        for (String line : text.split("\\R")) {
            if (line.trim().toLowerCase().startsWith(label.toLowerCase())) {
                return line.substring(label.length()).trim();
            }
        }
        return "Not found";
    }
}
