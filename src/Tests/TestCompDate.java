package Tests;

import Utilis.CompDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCompDate {

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2012, 2, 1, 2012, -1",
            "3, 10, 2010, 3, 10, 2012, -1",
            "2, 3, 2014, 2, 4, 2014, -1",
            "2, 3, 2014, 2, 3, 2014, 0",
            "4, 4, 2015, 3, 4, 2015, 1",
            "4, 4, 2016, 4, 4, 2015, 1",
            "4, 5, 2015, 4, 4, 2015, 1"
    })
    public void test_compareTo(int day1, int month1, int year1, int day2, int month2, int year2, int expected){
        assertEquals(expected, (new CompDate(day1, month1, year1)).compareTo(new CompDate(day2, month2, year2)));
    }

}
