import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class TestArgumentsParsing {
    private val directoryPath = "src/test/resources/"
    private val filePath = "src/test/resources/EmptyData.txt"
    private val notExistingPath = "src/test/resources/ThisFileDoesNotExist.txt"

    @Test
    fun testCheckPathIsFile_NotFile() {
        assertFails {
            checkPathIsFile(directoryPath)
        }
    }

    @Test
    fun testCheckPathIsFile_File() {
        checkPathIsFile(filePath)
    }

    @Test
    fun testCheckPathIsNotExists_Exists() {
        assertFails {
            checkPathIsNotExists(filePath)
        }
    }

    @Test
    fun testCheckPathIsNotExists_NotExists() {
        checkPathIsNotExists(notExistingPath)
    }

    @Test
    fun testArgumentsParse_EmptyArgs() {
        assertFails {
            argumentsParse(arrayOf())
        }
    }

    @Test
    fun testArgumentsParse_OneArgument() {
        assertFails {
            argumentsParse(arrayOf("-h"))
        }
    }

    @Test
    fun testArgumentsParse_BadDiagramType() {
        assertFails {
            argumentsParse(arrayOf("--NotDiagramType"))
        }
    }

    @Test
    fun testArgumentsParse_CycleDiagramShort() {
        val parsedArguments = argumentsParse(arrayOf("-c", filePath))
        assertEquals(ParsedArguments(DiagramTypes.CycleDiagram, filePath), parsedArguments)
    }

    @Test
    fun testArgumentsParse_CycleDiagramLong() {
        val parsedArguments = argumentsParse(arrayOf("--cycle", filePath))
        assertEquals(ParsedArguments(DiagramTypes.CycleDiagram, filePath), parsedArguments)
    }

    @Test
    fun testArgumentsParse_HistogramShort() {
        val parsedArguments = argumentsParse(arrayOf("-h", filePath, "--columns", "2"))
        assertEquals(ParsedArguments(DiagramTypes.Histogram, filePath, columnsNumber = 2), parsedArguments)
    }

    @Test
    fun testArgumentsParse_HistogramLong() {
        val parsedArguments = argumentsParse(arrayOf("--histogram", filePath, "--columns", "2"))
        assertEquals(ParsedArguments(DiagramTypes.Histogram, filePath, columnsNumber = 2), parsedArguments)
    }

    @Test
    fun testArgumentsParse_BadDataFile() {
        assertFails {
            argumentsParse(arrayOf("-c", notExistingPath))
        }
    }

    @Test
    fun testArgumentsParse_PngShort() {
        val parsedArguments = argumentsParse(arrayOf("-c", filePath, "-p", notExistingPath))
        assertEquals(
            ParsedArguments(DiagramTypes.CycleDiagram, filePath, pngMode = true, pngFile = notExistingPath),
            parsedArguments
        )
    }

    @Test
    fun testArgumentsParse_PngLong() {
        val parsedArguments = argumentsParse(arrayOf("-c", filePath, "--png", notExistingPath))
        assertEquals(
            ParsedArguments(DiagramTypes.CycleDiagram, filePath, pngMode = true, pngFile = notExistingPath),
            parsedArguments
        )
    }

    @Test
    fun testArgumentsParse_PngBadFile() {
        assertFails {
            argumentsParse(arrayOf("-c", filePath, "-p", filePath))
        }
    }

    @Test
    fun testArgumentsParse_HistogramBadColumns() {
        assertFails {
            argumentsParse(arrayOf("-h", filePath, "--columns", "10q2"))
        }
    }

    @Test
    fun testArgumentsParse_HistogramNoColumns() {
        assertFails {
            argumentsParse(arrayOf("--histogram", filePath))
        }
        assertFails {
            argumentsParse(arrayOf("-h", filePath, "--columns"))
        }
    }
}