package co.develhope.email1.students.services;

import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import co.develhope.email1.students.entities.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    private JavaMailSender emailSender;
    static List<Student> students = Arrays.asList(
            new Student("1", "Federico", "Urbani", "urbanifederico5@gmail.com"),
            new Student("2", "Walter", "White", "walter@white.com"),
            new Student("3", "Samuel", "Dungeon", "samuel@dangeon.com"),
            new Student("4", "Ursula", "Von Der Leyen", "ursula@boss.eu")
            );

    public Student getStudentById(String studentId) {
        Optional<Student> studentFromList = students.stream().filter(student -> student.getId().equals(studentId)).findAny();
        return studentFromList.isPresent() ? studentFromList.get() : null;
    }

    public void sendEmailToStudent(String contactId, String subject, String text) {
        Student stud= getStudentById(contactId);
        if (stud!=null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(stud.getEmail());
            message.setSubject(subject);
            message.setText(text);
            message.setReplyTo("exemple@exemple.it");
            message.setFrom("urbanifederico5@gmail.com");
            emailSender.send(message);
        } else {
            throw new IllegalArgumentException("Student not found with contact ID " + contactId);
        }
    }
}
