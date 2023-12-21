package Tests.UnitTesting;

import Mocks.LibrarianMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class LibrarianTest {
    static LibrarianMock lib;
    @BeforeAll
    static void setUp(){
        try {
            lib = new LibrarianMock("");
        } catch (Exception e) {
            fail();
        }
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
            "23424 25/02/2005" +
            ",2",
            "1313 15/08/2003" +
                    ",1",
    })
    void testNrOfBills(String str, int number) {
        lib.translate(str);
        assertEquals(number,lib.nrOfBills());
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",25/02/2005,1",
            "1313 15/08/2003" +
                    ",16/08/2003,0",
    })
    void testNrOfBills(String str, String date, int number) {
        lib.translate(str);
        assertEquals(number,lib.nrOfBills(lib.dateCon(date)));
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/1990,24/02/2005,1",
            "1313 15/08/2003" +
                    ",1/1/1990,24/02/2003,0",
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/2001,24/02/2010,1",
    })
    void testNrOfBills(String str, String start, String end, int number) {
        lib.translate(str);
        assertEquals(number,lib.nrOfBills(lib.dateCon(start),lib.dateCon(end)));
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",25/02/2005,5",
            "1313 15/08/2003" +
                    ",16/08/2003,0",
    })
    void testNrOfBooks(String str, String date, int number) {
        lib.translate(str);
        assertEquals(number,lib.nrOfBooks(lib.dateCon(date)));
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/1990,24/02/2005,4",
            "1313 15/08/2003" +
                    ",1/1/1990,24/02/2003,0",
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/2001,24/02/2010,5",
    })
    void testNrOfBooks(String str, String start, String end, int number) {
        lib.translate(str);
        assertEquals(number,lib.nrOfBooks(lib.dateCon(start),lib.dateCon(end)));
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",25/02/2005,15000",
            "1313 15/08/2003" +
                    ",16/08/2003,0",
    })
    void testMoneyMade(String str, String date, int number) {
        lib.translate(str);
        assertEquals(number,lib.moneyMade(lib.dateCon(date)));
    }
    @ParameterizedTest
    @CsvSource({
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/1990,24/02/2005,10000",
            "1313 15/08/2003" +
                    ",1/1/1990,24/02/2003,0",
            "1234 12/12/2000 " +
                    "23424 25/02/2005" +
                    ",1/1/2001,24/02/2010,15000",
    })
    void testMoneyMade(String str, String start, String end, int number) {
        lib.translate(str);
        assertEquals(number,lib.moneyMade(lib.dateCon(start),lib.dateCon(end)));
    }
}