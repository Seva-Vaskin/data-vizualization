
fun main(args: Array<String>) {
    val parsedArguments = argumentsParse(args)
    when (parsedArguments.diagramType) {
        DiagramType.CycleDiagram -> {
            val data = parseCycleDiagramData(parsedArguments.dataFile)
            createCycleDiagramWindow("Cycle Diagram", data)
        }
        DiagramType.Histogram -> {
            val data = parseHistogramData(parsedArguments.dataFile)
            createHistogramWindow("Histogram", data)
        }
        DiagramType.DissipationDiagram -> TODO()
    }
}