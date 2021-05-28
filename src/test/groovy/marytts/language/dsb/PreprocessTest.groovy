package marytts.language.dsb

import org.apache.commons.csv.CSVFormat
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class PreprocessTest {

    Preprocess preprocess

    @BeforeClass
    void setUp() {
        preprocess = new Preprocess()
    }

    @DataProvider
    Object[][] numberTokens() {
        [
                ['123', 123],
                ['3,14159', 3.14159],
                ['Fnord', null]
        ]
    }

    @Test(dataProvider = 'numberTokens')
    void testParseNumber(String input, Number expected) {
        def actual = preprocess.parseNumber(input)
        assert expected == actual
    }

    @DataProvider
    Object[][] numbers() {
        def stream = this.getClass().getResourceAsStream('numbers.csv')
        def reader = stream.newReader('UTF-8')
        def csvParser = CSVFormat.DEFAULT.parse(reader)
        return csvParser.collect { record ->
            [new BigDecimal(record.get(0)), record.get(1)]
        }
    }

    @Test(dataProvider = 'numbers')
    void testSpelloutNumber(BigDecimal input, String expected) {
        def actual = preprocess.spelloutNumber(input)
        assert expected == actual
    }
}