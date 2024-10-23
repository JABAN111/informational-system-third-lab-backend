package is.fistlab;

import com.google.gson.Gson;
import is.fistlab.database.entities.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PersonList {

    public class Person1{
        private String name;
        private int age;
        public Person1(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void PersonListToJson(){
        List<Person1> personList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person1 p = new Person1("name"+i,i);
            personList.add(p);
        }

        Gson gson = new Gson();
        String s = gson.toJson(personList);
        System.out.println(s);
    }
}
