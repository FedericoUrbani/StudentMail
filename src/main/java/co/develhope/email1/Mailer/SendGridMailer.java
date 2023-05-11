package co.develhope.email1.Mailer;

import co.develhope.email1.api.entities.NotificationDTO;
import co.develhope.email1.students.entities.Student;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SendGridMailer {
    @Autowired
    private SendGridAPI sendGridAPI;

    static List<Student> students = Arrays.asList(
            new Student("1", "Federico", "Urbani", "federico-urbani@hotmail.it"),
            new Student("2", "Walter", "White", "walter@white.com"),
            new Student("3", "Samuel", "Dungeon", "samuel@dangeon.com"),
            new Student("4", "Ursula", "Von Der Leyen", "ursula@boss.eu")
    );

    public Student getStudentById(String studentId) {
        Optional<Student> studentFromList = students.stream().filter(student -> student.getId().equals(studentId)).findAny();
        return studentFromList.isPresent() ? studentFromList.get() : null;
    }


    public void sendMail(String id) {
        Student stud = getStudentById(id);
        NotificationDTO nDTO = new NotificationDTO();
        nDTO.setText("mail content");
        nDTO.setTitle("mail subject");
        nDTO.setContactId(id);
        Email from = new Email("urbanifederico5@gmail.com", "il mandante della mail!");
        String subject = nDTO.getTitle();
        Email to = new Email(stud.getEmail());
        Content content = new Content("text/plain", nDTO.getText());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridAPI.api(request);
            System.out.println(response.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

