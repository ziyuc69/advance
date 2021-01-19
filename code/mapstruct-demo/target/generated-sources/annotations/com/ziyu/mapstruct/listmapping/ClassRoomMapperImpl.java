package com.ziyu.mapstruct.listmapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T14:40:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class ClassRoomMapperImpl implements ClassRoomMapper {

    @Override
    public ClassRoom classRoomToClassRoomDto(ClassRoom classRoom) {
        if ( classRoom == null ) {
            return null;
        }

        List<Student> studentList = null;

        studentList = studentListToStudentList( classRoom.getStudentList() );

        ClassRoom classRoom1 = new ClassRoom( studentList );

        return classRoom1;
    }

    protected Student studentToStudent(Student student) {
        if ( student == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = student.getId();
        name = student.getName();

        Student student1 = new Student( id, name );

        return student1;
    }

    protected List<Student> studentListToStudentList(List<Student> list) {
        if ( list == null ) {
            return null;
        }

        List<Student> list1 = new ArrayList<Student>( list.size() );
        for ( Student student : list ) {
            list1.add( studentToStudent( student ) );
        }

        return list1;
    }
}
