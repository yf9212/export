package com.yf;

import java.util.ArrayList;
import java.util.List;

import com.yf.model.Student;
import com.yf.utils.ExportUtils;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<Student>  students=new ArrayList<Student>();
        for (int i = 0; i < 100; i++) {
			Student  s=new Student();
			s.setAge(i%9+10);
			s.setId(""+i+1+"");
			s.setName("test"+i);
			s.setSex(i%2==0?"男":"女");
			s.setScore1(Math.round(100));
			s.setScore2(Math.round(100));
			s.setSocre3(Math.round(100));
			students.add(s);
		}
        ExportUtils.doExportExcel(students, "d:/export", "学生列表",20);
    }
}
