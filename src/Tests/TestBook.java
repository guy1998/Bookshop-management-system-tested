package Tests;

import Products.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBook {

    @ParameterizedTest
    @CsvSource({
            "''", //Empty ISBN
            "'132-214-421'", //3 chars entered where 4 are needed
            "'132-21441-421'", //5 chars entered where 5 are needed
            "'13-2142-421'", //2 chars entered where 3 are needed (1st position)
            "'132-2142-41'", //2 chars entered where 3 are needed (2nd position)
            "'132-214a-421'", //non numeric elements are entered
    })
    public void checkIsbnValidityWrongISBN(String ISBN){
        assertFalse(Book.checkIsbnValidity(ISBN));
    }

    @Test
    public void checkIsbnValidityRightIsbn(){
        assertTrue(Book.checkIsbnValidity("132-2141-421"));
    }

}
