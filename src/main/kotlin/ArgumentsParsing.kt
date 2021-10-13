data class ParsedArguments(val diagramType: DiagramType, val dataFile: String)

fun argumentsParse(args: Array<String>): ParsedArguments {
    if (args.size != 2) {
        throw IllegalArgumentException("Need 2 arguments, but ${args.size} given")
    }
    val typeString = args[0]
    val dataFileString = args[1]
    val diagramType = when (typeString) {
        "-h", "--histogram" -> DiagramType.Histogram
        "-c", "--cycle" -> DiagramType.CycleDiagram
        "-d", "--dissipation" -> DiagramType.DissipationDiagram
        else -> throw IllegalArgumentException("Undefined type of diagram")
    }
    return ParsedArguments(diagramType, dataFileString)
}
