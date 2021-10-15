import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class TestDataParsing {
    private val emptyFile = "src/test/resources/EmptyData.txt"
    private val oneLineFile = "src/test/resources/OneNumberAndStringData.txt"
    private val intAndStringFile = "src/test/resources/IntAndStringData.txt"
    private val badIntAndStringFile = "src/test/resources/BadNumberAndString.txt"
    private val numbersFile = "src/test/resources/ThreeNumbers.txt"
    private val badNumbersFile = "src/test/resources/BadNumbers.txt"

    @Test
    fun testParseNumberAndStringData_emptyFile() {
        assertFails {
            parseNumberAndStringData(emptyFile)
        }
    }

    @Test
    fun testParseNumberAndStringData_oneLine() {
        val numberAndStringData = parseNumberAndStringData(oneLineFile)
        assertEquals(1, numberAndStringData.size)
        assertEquals(NumberAndString(50.123f, "string"), numberAndStringData[0])
    }

    @Test
    fun testParseNumberAndStringData() {
        val numberAndStringData = parseNumberAndStringData(intAndStringFile)
        assertEquals(4, numberAndStringData.size)
        assertEquals(NumberAndString(100.0f, "Men"), numberAndStringData[0])
        assertEquals(NumberAndString(100.0f, "Women"), numberAndStringData[1])
        assertEquals(NumberAndString(150.0f, "Kids"), numberAndStringData[2])
        assertEquals(NumberAndString(79.0f, "Animals"), numberAndStringData[3])
    }

    @Test
    fun testParseNumberAndStringData_badData() {
        assertFails {
            parseNumberAndStringData(badIntAndStringFile)
        }
    }

    @Test
    fun testParseNumbersData_emptyFile() {
        assertFails {
            parseNumbersData(emptyFile)
        }
    }

    @Test
    fun testParseNumbersData() {
        val numbersData = parseNumbersData(numbersFile)
        assertEquals(3, numbersData.size)
        assertEquals(-1f, numbersData[0])
        assertEquals(2f, numbersData[1])
        assertEquals(3.0123f, numbersData[2])
    }

    @Test
    fun testParseNumbersData_BadData() {
        assertFails {
            parseNumbersData(badNumbersFile)
        }
    }
}