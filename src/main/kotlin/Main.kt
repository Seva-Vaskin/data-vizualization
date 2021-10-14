
fun main(args: Array<String>) {
    try {
        val parsedArguments = argumentsParse(args)
        when (parsedArguments.diagramType) {
            DiagramTypes.CycleDiagram -> {
                val numberAndStringData = parseNumberAndStringData(parsedArguments.dataFile)
                val cycleDiagramData = CycleDiagramData(numberAndStringData)
                createCycleDiagramWindow("Cycle Diagram", cycleDiagramData)
            }
            DiagramTypes.Histogram -> {
                val numbersData = parseNumbersData(parsedArguments.dataFile)
                val histogramData = HistogramData(parsedArguments.columnsNumber, numbersData)
                createHistogramWindow("Histogram", histogramData)
            }
            DiagramTypes.ScatterPlot -> {
                TODO()
            }
        }
    } catch (e: Exception) {
        System.err.println("Something went wrong :)")
        System.err.println(e.message)
    }
}