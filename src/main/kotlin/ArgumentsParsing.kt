import java.io.File
import java.io.FileNotFoundException

data class ParsedArguments(
    val diagramType: DiagramTypes,
    val dataFile: String,
    val columnsNumber: Int = -1,
    val pngMode: Boolean = false,
    val pngFile: String = ""
)

fun checkPathIsFile(path: String) {
    if (!File(path).isFile)
        throw FileNotFoundException("File $path is not exists")
}

fun checkPathIsNotExists(path: String) {
    if (File(path).exists())
        throw Exception("File $path already exists")
}

fun argumentsParse(args: Array<String>): ParsedArguments {
    if (args.size < 2) {
        throw IllegalArgumentException("Need at least 2 argument")
    }
    val diagramTypeString = args[0]
    val diagramType = when (diagramTypeString) {
        "-h", "--histogram" -> DiagramTypes.Histogram
        "-c", "--cycle" -> DiagramTypes.CycleDiagram
        "-s", "--scatter" -> DiagramTypes.ScatterPlot
        else -> throw IllegalArgumentException("Undefined type of diagram")
    }

    val dataFile = args[1]
    checkPathIsFile(dataFile)

    var isWaitingColumnsNumber = false
    var columnsNumber = -1
    var isWaitingPngFile = false
    var pngMode = false
    var pngFile = ""
    for (i in 2 until args.size) {
        when {
            args[i] == "-p" || args[i] == "--png" -> isWaitingPngFile = true
            isWaitingPngFile -> {
                isWaitingPngFile = false
                pngFile = args[i]
                checkPathIsNotExists(pngFile)
                pngMode = true
            }
            args[i] == "--columns" -> isWaitingColumnsNumber = true
            isWaitingColumnsNumber -> {
                isWaitingColumnsNumber = false
                columnsNumber = args[i].toIntOrNull()
                    ?: throw Exception("columns should be and integer number, but ${args[i]} given")
            }
        }
    }

    if (isWaitingPngFile) {
        throw Exception("Path to png file is not given")
    }
    if (isWaitingColumnsNumber) {
        throw Exception("Columns count is not given")
    }
    if (columnsNumber == -1 && diagramType == DiagramTypes.Histogram) {
        throw Exception("Histogram need number of columns to generate")
    }
    return ParsedArguments(diagramType, dataFile, columnsNumber, pngMode, pngFile)
}
