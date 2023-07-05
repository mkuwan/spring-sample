package dev.mkuwan.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor2 implements ItemProcessor<Person, Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonItemProcessor2.class);


    @Override
    public Person process(Person person) throws Exception {
        final String firstName = person.getFirstName() + "をさらに変換";
        final String lastName = person.getLastName() + "をさらに変換";
        final Person transformedPerson = new Person(firstName, lastName);

        logger.info("Converging (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
