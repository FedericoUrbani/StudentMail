package co.develhope.email1.api.controllers;

import co.develhope.email1.Mailer.SendGridMailer;
import co.develhope.email1.api.entities.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import co.develhope.email1.students.entities.Student;
import co.develhope.email1.students.services.StudentService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    SendGridMailer sendGridMailer;
    @Autowired
    StudentService studentService;


    @GetMapping("/notification/{id}")
    public ResponseEntity sendGridNotification(@PathVariable String id){
          sendGridMailer.sendMail(id);
        try{
            Student studentToNotify = sendGridMailer.getStudentById(id);
            System.out.println("studentToNotify: " + studentToNotify);
            if (studentToNotify == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("I couldn't find the student :(");
            sendGridMailer.sendMail(studentToNotify.getId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server is broken :(");
        }
    }

}
