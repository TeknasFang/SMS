package com.crudmaster.authentication.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudmaster.authentication.application.entity.Parent;
import com.crudmaster.authentication.application.entity.Standard;
import com.crudmaster.authentication.application.entity.Student;
import com.crudmaster.authentication.application.entity.StudentInformation;
import com.crudmaster.authentication.application.entity.Subject;
import com.crudmaster.authentication.application.entity.Teacher;
import com.crudmaster.authentication.application.entity.TeacherInformation;
import com.crudmaster.authentication.application.entity.dto.StandardDTO;
import com.crudmaster.authentication.application.repository.ParentRepository;
import com.crudmaster.authentication.application.repository.StandardRepository;
import com.crudmaster.authentication.application.repository.StudentRepository;
import com.crudmaster.authentication.application.repository.SubjectRepository;

@CrossOrigin(origins = "http://localhost:4200")
@Configuration
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/hello")
    public String hello() {
	return "Hello from admin";
    }
    
    @PostMapping("/student")
    public ResponseEntity<?> add(@RequestBody StudentInformation studentInfo){
	
	System.out.println(studentInfo);
	UUID uuid=UUID.randomUUID(); 
	
	String password=uuid.toString();
	
	Student student = Student.builder().enroll_id(studentInfo.getId())
		.email(studentInfo.getStudentEmail())
		.adress(studentInfo.getStudentAdress())
		.dateOfJoin(new Date())
		.dob(studentInfo.getStudentDOB())
		.name(studentInfo.getStudentName())
		.password(password)
		.phone(studentInfo.getStudentPhone())
		.sex(studentInfo.getStudentSex()).build();
	
	Parent father = Parent.builder().email(studentInfo.getFatherEmail())
		.phone(studentInfo.getFatherPhone())
		.name(studentInfo.getFatherName()).build();
	
	Parent mother = Parent.builder().email(studentInfo.getMotherEmail())
		.phone(studentInfo.getMotherPhone())
		.name(studentInfo.getMotherName()).build();;
	
	Integer stdId = Integer.parseInt(studentInfo.getStudentStandard());
	
	Standard standard = standardRepository.findById(stdId.intValue()).orElseThrow();
	List<Parent> parentList = new ArrayList<>();
	parentList.add(mother);
	parentList.add(father);
	
	student.setStandard(standard);
	father.setStudent(student);
	mother.setStudent(student);
	parentRepository.save(father);
	parentRepository.save(mother);
	
	return ResponseEntity.ok("hi");

    }
    
    @GetMapping("/student/standard/{std}")
    public String getStudent(@PathVariable String std) {
	
	Integer id=Integer.parseInt(std);
	Standard standard=standardRepository.findById(id.intValue()).orElseThrow();
	List<Student> students =  standard.getStudentList();
	System.out.println(students);
	return "students";
	
    }
    
    @PostMapping("/subject")
    public String addSubject(@RequestBody StandardDTO standardDTO) {
	System.out.println(standardDTO);
	Integer id=standardDTO.getStandard();
	Standard standard = standardRepository.findById(id).orElseThrow();
	Subject subject = Subject.builder().subject_name(standardDTO.getSubject()).standard(standard).build();
	subjectRepository.save(subject);
	
	return "Subject added"+ standardDTO.getStandard()+" successfully ";
    }
    
    @PostMapping("/teacher")
    public String addTeacher(@RequestBody TeacherInformation teacherInformation) {
	
	System.out.println(teacherInformation);
	UUID uuid=UUID.randomUUID(); 
	String password=uuid.toString();
	
	Teacher teacher = Teacher.builder()
		.adress(teacherInformation.getTeacherAdress())
		.dateOfJoin(new Date())
		.dob(teacherInformation.getTeacherDOB())
		.email(teacherInformation.getTeacherEmail())
		.name(teacherInformation.getTeacherName())
		.password(password)
		.phone(teacherInformation.getTeacherPhone())
		.sex(teacherInformation.getTeacherSex()).build();
		
	
	Integer std = Integer.parseInt(teacherInformation.getTeacherStandard());
	Standard standard = standardRepository.findById(std).orElseThrow();
	String subName = teacherInformation.getTeacherSubject();
	
	List<Subject> subList =standard.getSubjectList();
	Stream<Subject> subStream = subList.stream();
	List<Subject> filteredSubList = subStream.filter(sub->sub.getSubject_name().equals(subName)).toList();
	Subject filteredSubject = filteredSubList.get(0);
	
	if(filteredSubject.getTeacher()!=null) {
	    return "teacher already exists";
	}
	filteredSubject.setTeacher(teacher);
	subjectRepository.save(filteredSubject);
	
	System.out.println(filteredSubList);
	
	
	return "teacher added succesfully";
    }
    
    @GetMapping("/subjectList/{xd}")
    public List<String> getSubjectList(@PathVariable String xd) {
	
	Integer std = Integer.parseInt(xd);
	Standard standard = standardRepository.findById(std).orElseThrow();
	System.out.println("hi");
	List<Subject> subList =standard.getSubjectList();
	Stream<Subject> subStream = subList.stream();
	List<String> subjectList=new ArrayList<>();
	subStream.forEach(sub->subjectList.add(sub.getSubject_name()));
	return subjectList;
	
    }
    
}
