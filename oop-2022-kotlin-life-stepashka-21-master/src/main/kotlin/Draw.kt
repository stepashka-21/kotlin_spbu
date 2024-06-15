import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel
class Draw(var board: Board, var gridlines: Boolean = false) : JPanel() {
    override fun paint(g: Graphics) {
        g.color = Color.PINK
        foreground.blue
        val size = height / (board.size)
        if (gridlines) {
            for (i in 0  until height step size) {
                g.drawLine(height, i, 0, i)
                g.drawLine(i, height, i, 0)
            }
        }
        for (coordinate in board.coordinates) {
            g.fillRect(coordinate.first * size, coordinate.second * size, size, size)
        }
    }
}