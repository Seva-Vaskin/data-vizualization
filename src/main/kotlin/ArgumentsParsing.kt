import java.io.File
import java.io.FileNotFoundException

data class ParsedArguments(val diagramType: DiagramTypes, val dataFile: String, val columnsNumber: Int = -1)

fun checkFilePath(path: String) {
    if (!File(path).isFile)
        throw FileNotFoundException("File $path is not exists")
}

fun argumentsParse(args: Array<String>): ParsedArguments {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Need at least one argument")
    }
    val diagramTypeString = args[0]
    val diagramType = when (diagramTypeString) {
        "-h", "--histogram" -> DiagramTypes.Histogram
        "-c", "--cycle" -> DiagramTypes.CycleDiagram
        "-s", "--scatter" -> DiagramTypes.ScatterPlot
        else -> throw IllegalArgumentException("Undefined type of diagram")
    }
    when (diagramType) {
        DiagramTypes.CycleDiagram -> {
            if (args.size < 2) {
                throw Exception("Cycle diagram needs at least 2 arguments")
            }
            val dataFileString = args[1]
            checkFilePath(dataFileString)
            return ParsedArguments(diagramType, dataFileString)
        }
        DiagramTypes.Histogram -> {
            val columnsNumber = args[1].toIntOrNull()
                ?: throw Exception("2nd argument in histogram should be a number, but ${args[1]} given")
            val dataFileString = args[2]
            checkFilePath(dataFileString)
            return ParsedArguments(diagramType, dataFileString, columnsNumber)
        }
        DiagramTypes.ScatterPlot -> {
            TODO()
        }
    }
}
