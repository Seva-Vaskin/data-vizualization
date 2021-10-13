import java.io.File
import java.io.FileNotFoundException

data class ParsedArguments(val diagramType: DiagramTypes, val dataFile: String)

fun argumentsParse(args: Array<String>): ParsedArguments {
    if (args.size != 2) {
        throw IllegalArgumentException("Need 2 arguments, but ${args.size} given")
    }
    val typeString = args[0]
    val dataFileString = args[1]
    if (!File(dataFileString).isFile)
        throw FileNotFoundException("File $dataFileString is not exists")
    val diagramType = when (typeString) {
        "-h", "--histogram" -> DiagramTypes.Histogram
        "-c", "--cycle" -> DiagramTypes.CycleDiagram
        "-d", "--dissipation" -> DiagramTypes.DissipationDiagram
        else -> throw IllegalArgumentException("Undefined type of diagram")
    }
    return ParsedArguments(diagramType, dataFileString)
}
