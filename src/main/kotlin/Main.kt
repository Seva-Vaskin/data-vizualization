
fun main(args: Array<String>) {
    try {
        val parsedArguments = argumentsParse(args)
        when (parsedArguments.diagramType) {
            DiagramTypes.CycleDiagram -> {
                val data = parseNumberAndStringData(parsedArguments.dataFile)
                createCycleDiagramWindow("Cycle Diagram", data)
            }
            DiagramTypes.Histogram -> {
                val data = parseNumberAndStringData(parsedArguments.dataFile)
                createHistogramWindow("Histogram", data)
            }
            DiagramTypes.DissipationDiagram -> {
                TODO()
            }
        }
    } catch (e: Exception) {
        System.err.println("Something went wrong :)")
        System.err.println(e.message)
    }
}