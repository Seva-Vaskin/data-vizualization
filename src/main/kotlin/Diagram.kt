enum class DiagramType {
    CycleDiagram,
    Histogram,
    DissipationDiagram
}

open class DiagramData {}

data class CycleDiagramData(val data: Map<String, Int>) : DiagramData()
data class HistogramData(val data: Map<String, Int>) : DiagramData()
data class DissipationDiagramData(val data: List<Pair<Int, Int>>) : DiagramData()

