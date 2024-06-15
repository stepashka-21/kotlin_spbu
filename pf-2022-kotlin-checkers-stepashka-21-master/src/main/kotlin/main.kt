import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.border.BevelBorder
import kotlin.math.abs
import kotlin.math.min

enum class Figure {
    EMPTY,
    WHITE,
    WHITE_QUEEN,
    BLACK,
    BLACK_QUEEN,
}

class Checkers() {
    var board: Array<Array<Figure>> = Array(8) { Array(8) { Figure.EMPTY } }
    init {
        for (row in 0..7) {
            for (col in 0..7) {
                if (row < 3 && row % 2 == col % 2) {
                    board[row][col] = Figure.BLACK
                } else if (row > 4 && row % 2 == col % 2) {
                    board[row][col] = Figure.WHITE
                } else {
                    board[row][col] = Figure.EMPTY
                }
            }
        }
    }
}

internal class JavaPaintUI : JFrame() {
    val checkers = Checkers()
    private val tool = 1
    var currentX = 0
    var currentY = 0
    var state1 = 0
    var takedrow = 0
    var takedcol = 0
    var play = 0

    private fun initComponents() {
        jPanel2 = Panel2()
        jPanel2!!.background = Color(255, 255, 255)
        jPanel2!!.border = BorderFactory.createBevelBorder(BevelBorder.RAISED)
        this.contentPane = jPanel2
        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        jPanel2!!.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(evt: MouseEvent) {
                MousePressed(evt)
            }

            override fun mouseReleased(evt: MouseEvent) {
                MouseReleased(evt)
            }
        })

    }

    private fun MousePressed(evt: MouseEvent) {
        val col = (evt.x - 2) / 50
        val row = (evt.y - 2) / 50
        if (state1 == 0) {
            var ok = 0
            var found = 0

            for (row1 in 0..7) {
                for (col1 in 0..7) {
                    for (row2 in 0..7) {
                        for (col2 in 0..7) {
                            for (row3 in 0..7) {
                                for (col3 in 0..7) {
                                    val player = checkers.board[row1][col1]
                                    if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        found = 1
                                    }
                                    if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        found = 1
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (i in 0..7) {
                for (j in 0..7) {
                    if (i != row || j != col) {
                        if ((play == 0 && (checkers.board[row][col] == Figure.WHITE_QUEEN || checkers.board[row][col] == Figure.WHITE)) ||
                            (play == 1 && (checkers.board[row][col] == Figure.BLACK_QUEEN || checkers.board[row][col] == Figure.BLACK))
                            && possJumps(checkers.board[row][col], row, col, min(row, i) + 1, min(col, j) + 1, i, j)) {
                            ok = 1
                        }
                        if ((play == 0 && (checkers.board[row][col] == Figure.WHITE_QUEEN || checkers.board[row][col] == Figure.WHITE)) ||
                            (play == 1 && (checkers.board[row][col] == Figure.BLACK_QUEEN || checkers.board[row][col] == Figure.BLACK))
                            && possMoves(checkers.board[row][col], row, col, i, j) && found != 1 && ok != 1) {
                            ok = 2
                        }
                    }
                }
            }
            if (ok == 0) {
                return
            }
            takedrow = row
            takedcol = col
            state1 = 1
        } else {
            var qw = 0
            var found4 = 0
            var tt = 0
            for (row1 in 0..7) {
                for (col1 in 0..7) {
                    for (row2 in 0..7) {
                        for (col2 in 0..7) {
                            for (row3 in 0..7) {
                                for (col3 in 0..7) {
                                    val player = checkers.board[row1][col1]
                                    if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        found4 = 1
                                        if (row1 == takedrow && col1 == takedcol) {
                                            tt = 1
                                        }
                                    }
                                    if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        found4 = 1
                                        if (row1 == takedrow && col1 == takedcol) {
                                            tt = 1
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (found4 == 1 && tt == 0) {
                state1 = 0
                return
            }
            if ((checkers.board[row][col] == Figure.WHITE && checkers.board[takedrow][takedcol] == Figure.WHITE_QUEEN) ||
                (checkers.board[row][col] == Figure.WHITE && checkers.board[takedrow][takedcol] == Figure.WHITE) ||
                (checkers.board[row][col] == Figure.WHITE_QUEEN && checkers.board[takedrow][takedcol] == Figure.WHITE) ||
                (checkers.board[row][col] == Figure.WHITE_QUEEN && checkers.board[takedrow][takedcol] == Figure.WHITE_QUEEN) ||
                (checkers.board[row][col] == Figure.BLACK && checkers.board[takedrow][takedcol] == Figure.BLACK_QUEEN) ||
                (checkers.board[row][col] == Figure.BLACK && checkers.board[takedrow][takedcol] == Figure.BLACK) ||
                (checkers.board[row][col] == Figure.BLACK_QUEEN && checkers.board[takedrow][takedcol] == Figure.BLACK) ||
                (checkers.board[row][col] == Figure.BLACK_QUEEN && checkers.board[takedrow][takedcol] == Figure.BLACK_QUEEN)) {
                var g = 1
                var found = 0
                for (row1 in takedrow..takedrow) {
                    for (col1 in takedcol..takedcol) {
                        for (row2 in 0..7) {
                            for (col2 in 0..7) {
                                for (row3 in 0..7) {
                                    for (col3 in 0..7) {
                                        val player = checkers.board[row1][col1]
                                        if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                            found = 1
                                        }
                                        if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                            found = 1
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var found2 = 0
                for (row1 in row..row) {
                    for (col1 in col..col) {
                        for (row2 in 0..7) {
                            for (col2 in 0..7) {
                                for (row3 in 0..7) {
                                    for (col3 in 0..7) {
                                        val player = checkers.board[row1][col1]
                                        if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                            found2 = 1
                                        }
                                        if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                            found2 = 1
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (found == 1) {
                    if (found2 == 1) {
                        takedrow = row
                        takedcol = col
                    }
                } else {
                    takedrow = row
                    takedcol = col
                }
            }
            if ((((checkers.board[takedrow][takedcol] == Figure.BLACK || checkers.board[takedrow][takedcol] == Figure.BLACK_QUEEN) && play == 1) ||
                        ((checkers.board[takedrow][takedcol] == Figure.WHITE || checkers.board[takedrow][takedcol] == Figure.WHITE_QUEEN) && play == 0))
                && possJumps(checkers.board[takedrow][takedcol], takedrow, takedcol, min(row, takedrow) + 1, min(col, takedcol) + 1, row, col) && found4 == 1) {
                checkers.board[row][col] = checkers.board[takedrow][takedcol]
                checkers.board[takedrow][takedcol] = Figure.EMPTY
                checkers.board[min(row, takedrow) + 1][min(col, takedcol) + 1] = Figure.EMPTY
                qw = 1
                var found3 = 0
                for (row2 in 0..7) {
                    for (col2 in 0..7) {
                        for (row3 in 0..7) {
                            for (col3 in 0..7) {
                                val player = checkers.board[row][col]
                                if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row, col, row2, col2, row3, col3) &&
                                    (checkers.board[row2][col2] == Figure.BLACK || checkers.board[row2][col2] == Figure.BLACK_QUEEN) &&
                                    checkers.board[row3][col3] == Figure.EMPTY &&
                                    abs(row - row2) == 1 && abs (col - col2) == 1 && abs(row2 - row3) == 1 && abs (col2 - col3) == 1 && abs(col - col3) == 2 && abs(row - row3) == 2) {
                                    found3 = 1
                                }
                                if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row, col, row2, col2, row3, col3) &&
                                    (checkers.board[row2][col2] == Figure.WHITE || checkers.board[row2][col2] == Figure.WHITE_QUEEN) &&
                                    checkers.board[row3][col3] == Figure.EMPTY &&
                                    abs(row - row2) == 1 && abs (col - col2) == 1 && abs(row2 - row3) == 1 && abs (col2 - col3) == 1 && abs(col - col3) == 2 && abs(row - row3) == 2) {
                                    found3 = 1
                                }
                            }
                        }
                    }
                }
                if (found3 == 1) {
                    play = 1 - play
                    takedrow = row
                    takedcol = col

                }
                state1 = 0
                play = 1 - play
            }
            if (qw == 0) {
                if ((((checkers.board[takedrow][takedcol] == Figure.BLACK || checkers.board[takedrow][takedcol] == Figure.BLACK_QUEEN) && play == 1) ||
                            ((checkers.board[takedrow][takedcol] == Figure.WHITE || checkers.board[takedrow][takedcol] == Figure.WHITE_QUEEN) && play == 0)) &&
                    possMoves(checkers.board[takedrow][takedcol], takedrow, takedcol, row, col) && found4 == 0) {
                    checkers.board[row][col] = checkers.board[takedrow][takedcol]
                    checkers.board[takedrow][takedcol] = Figure.EMPTY
                    state1 = 0
                    play = 1 - play
                }
            }
            if (checkers.board[row][col] == Figure.WHITE && row == 0) {
                checkers.board[row][col] = Figure.WHITE_QUEEN
            }
            if (checkers.board[row][col] == Figure.BLACK && row == 7) {
                checkers.board[row][col] = Figure.BLACK_QUEEN
            }

        }
        repaint()
    }

    private fun MouseReleased(evt: MouseEvent) {
        if (tool == 2) {
            currentX = evt.x
            currentY = evt.y
        }
    }

    private fun possMoves(player: Figure, rowP: Int, colP: Int, row2: Int, col2: Int) : Boolean {
        if (rowP < 0 || rowP >= 8 || colP < 0 || colP >= 8) {
            return false
        }
        if (player == Figure.EMPTY)
            return false
        else if (player == Figure.BLACK) {
            if (row2 - rowP == 1 && abs(col2 - colP) == 1 && checkers.board[rowP][colP] == Figure.BLACK && checkers.board[row2][col2] == Figure.EMPTY)
                return true
        } else if (player == Figure.WHITE) {
            if (rowP - row2 == 1 && abs(col2 - colP) == 1 && checkers.board[rowP][colP] == Figure.WHITE && checkers.board[row2][col2] == Figure.EMPTY)
                return true
        } else if (player == Figure.BLACK_QUEEN) {
            if (abs(row2 - rowP) == abs(col2 - colP) && checkers.board[rowP][colP] == Figure.BLACK_QUEEN && checkers.board[row2][col2] == Figure.EMPTY)
                return true
        } else {
            if (abs(row2 - rowP) == abs(col2 - colP) && checkers.board[rowP][colP] == Figure.WHITE_QUEEN && checkers.board[row2][col2] == Figure.EMPTY)
                return true
        }
        return false
    }

    private fun possJumps(player: Figure, rowP: Int, colP: Int, row: Int, col: Int, rowF: Int, colF: Int) : Boolean { //, row2: Int, col2: Int, row3: Int, col3: Int) : Boolean  {
        if (rowP < 0 || rowP >= 8 || colP < 0 || colP >= 8) {
            return false
        }
        if (abs(rowP - rowF) == 1) {
            return false
        }
        if (player == Figure.EMPTY)
            return false
        else if (player == Figure.BLACK) {
            if (abs(row - rowP) == 1 && abs(col - colP) == 1 && abs(colF - colP) == 2 && abs(rowF - rowP) == 2 && abs(col - colF) == 1 && abs(row - rowF) == 1 && checkers.board[rowP][colP] == Figure.BLACK && (checkers.board[row][col] == Figure.WHITE || checkers.board[row][col] == Figure.WHITE_QUEEN) && (checkers.board[rowF][colF] == Figure.EMPTY)) {
                return true
            }
        } else if (player == Figure.WHITE) {
            if (abs(row - rowP) == 1 && abs(col - colP) == 1 && abs(colF - colP) == 2 && abs(rowF - rowP) == 2 && abs(col - colF) == 1 && abs(row - rowF) == 1 && checkers.board[rowP][colP] == Figure.WHITE && (checkers.board[row][col] == Figure.BLACK || checkers.board[row][col] == Figure.BLACK_QUEEN) && (checkers.board[rowF][colF] == Figure.EMPTY)) {
                return true}
        } else if (player == Figure.BLACK_QUEEN) {
            var k = false
            /*for (i in 0..7) {
                if ((checkers.board[rowP-i][colP-i] == Figure.WHITE || checkers.board[rowP-i][colP-i] == Figure.WHITE_QUEEN) && checkers.board[rowP-i-1][colP-i-1] == Figure.EMPTY) {
                    k = true
                    break
                } else if ((checkers.board[rowP-i][colP+i] == Figure.WHITE || checkers.board[rowP-i][colP+i] == Figure.WHITE_QUEEN) && checkers.board[rowP-i-1][colP+i+1] == Figure.EMPTY) {
                    k = true
                    break
                } else if ((checkers.board[rowP+i][colP-i] == Figure.WHITE || checkers.board[rowP+i][colP-i] == Figure.WHITE_QUEEN) && checkers.board[rowP+i+1][colP-i-1] == Figure.EMPTY) {
                    k = true
                    break
                } else if ((checkers.board[rowP+i][colP+i] == Figure.WHITE || checkers.board[rowP+i][colP+i] == Figure.WHITE_QUEEN) && checkers.board[rowP+i+1][colP+i+1] == Figure.EMPTY) {
                    k = true
                    break
                }
            }*/
            k
        } else {
            var k = false
            /*for (i in 0..7) {
                if (rowP - 1 < i || colP - 1 < i || rowP + i > 7 || colP + i > 7) {
                    return false
                }
                if ((checkers.board[rowP - i][colP - i] == Figure.BLACK || checkers.board[rowP - i][colP - i] == Figure.BLACK_QUEEN) && checkers.board[rowP - i - 1][colP - i - 1] == Figure.EMPTY) {
                    k = true
                    break
                }
                if ((checkers.board[rowP - i][colP + i] == Figure.BLACK || checkers.board[rowP - i][colP + i] == Figure.BLACK_QUEEN) && checkers.board[rowP - i - 1][colP + i + 1] == Figure.EMPTY) {
                    k = true
                    break
                }
                if ((checkers.board[rowP + i][colP - i] == Figure.BLACK || checkers.board[rowP + i][colP - i] == Figure.BLACK_QUEEN) && checkers.board[rowP + i + 1][colP - i - 1] == Figure.EMPTY) {
                    k = true
                    break
                }
                if ((checkers.board[rowP + i][colP + i] == Figure.BLACK || checkers.board[rowP + i][colP + i] == Figure.BLACK_QUEEN) && checkers.board[rowP + i + 1][colP + i + 1] == Figure.EMPTY) {
                    k = true
                    break
                }
            }*/
            k
        }
        return false
    }

    private var jPanel2: JPanel? = null

    init {
        initComponents()
    }

    internal inner class Panel2 : JPanel() {
        init {
            preferredSize = Dimension(500, 500)
        }

        public override fun paintComponent(g: Graphics) {
            g.drawRect(0, 0, 423, 423)
            g.color = Color.black
            for (row in 0..7) {
                for (col in 0..7) {
                    if (row % 2 == col % 2)
                        g.color = Color(45, 69, 244)
                    else
                        g.color = Color(130, 160, 30)
                    g.fillRect(2 + col * 50, 2 + row * 50, 50, 50)

                }
            }
            for (row in 0..7) {
                g.color = Color.black
                g.drawString("${8 - row}", 410, (row + 1) * 50 - 17)
            }
            val a = arrayOf("a", "b", "c", "d", "e", "f", "g", "h")
            for (col in 7 downTo 0) {
                g.color = Color.black
                g.drawString(a[7 - col], (col + 1) * 50 - 23, 417)
            }
            for (col in 0..7) {
                for (row in 0..7) {
                    if (checkers.board[row][col] == Figure.WHITE) {
                        g.color = Color.WHITE
                        g.fillOval(9 + col * 50, 9 + row * 50, 35, 35)
                    } else if (checkers.board[row][col] == Figure.BLACK) {
                        g.color = Color.BLACK
                        g.fillOval(9 + col * 50, 9 + row * 50, 35, 35)
                    } else if (checkers.board[row][col] == Figure.WHITE_QUEEN) {
                        g.color = Color.LIGHT_GRAY
                        g.fillOval(9 + col * 50, 9 + row * 50, 35, 35)
                    } else if (checkers.board[row][col] == Figure.BLACK_QUEEN) {
                        g.color = Color.DARK_GRAY
                        g.fillOval(9 + col * 50, 9 + row * 50, 35, 35)
                    }

                }
            }
            var ck = ""
            var jumps = 0
            for (row1 in 0..7) {
                for (col1 in 0..7) {
                    for (row2 in 0..7) {
                        for (col2 in 0..7) {
                            for (row3 in 0..7) {
                                for (col3 in 0..7) {
                                    val player = checkers.board[row1][col1]
                                    if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        jumps = 1
                                        ck += "$row1*$col1 "
                                        g.color = Color.yellow
                                        g.drawRect(1 + col1 * 50, 1 + row1 * 50, 49, 49)
                                        g.drawRect(2 + col1 * 50, 2 + row1 * 50, 49, 49)
                                        g.drawRect(3 + col1 * 50, 3 + row1 * 50, 49, 49)
                                    }
                                    if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possJumps(player, row1, col1, row2, col2, row3, col3)) {
                                        jumps = 1
                                        ck += "$row1*$col1 "
                                        g.color = Color.yellow
                                        g.drawRect(1 + col1 * 50, 1 + row1 * 50, 49, 49)
                                        g.drawRect(2 + col1 * 50, 2 + row1 * 50, 49, 49)
                                        g.drawRect(3 + col1 * 50, 3 + row1 * 50, 49, 49)
                                    }

                                }
                            }
                        }
                    }
                }
            }
            if (jumps == 0) {
                for (row1 in 0..7) {
                    for (col1 in 0..7) {
                        for (row2 in 0..7) {
                            for (col2 in 0..7) {
                                val player = checkers.board[row1][col1]
                                if (play == 0 && (player == Figure.WHITE_QUEEN || player == Figure.WHITE) && possMoves(player, row1, col1, row2, col2)) {
                                    ck += "$row1*$col1 "
                                    g.color = Color.red
                                    g.drawRect(1 + col1 * 50, 1 + row1 * 50, 49, 49)
                                    g.drawRect(2 + col1 * 50, 2 + row1 * 50, 49, 49)
                                    g.drawRect(3 + col1 * 50, 3 + row1 * 50, 49, 49)
                                }
                                if (play == 1 && (player == Figure.BLACK_QUEEN || player == Figure.BLACK) && possMoves(player, row1, col1, row2, col2)) {
                                    ck += "$row1*$col1 "
                                    g.color = Color.red
                                    g.drawRect(1 + col1 * 50, 1 + row1 * 50, 49, 49)
                                    g.drawRect(2 + col1 * 50, 2 + row1 * 50, 49, 49)
                                    g.drawRect(3 + col1 * 50, 3 + row1 * 50, 49, 49)
                                }
                            }
                        }
                    }
                }
            }
            if (takedrow >= 0  && "$takedrow*$takedcol" in ck) {
                g.color = Color.GREEN
                g.drawRect(1 + takedcol * 50, 1 + takedrow * 50, 49, 49)
                g.drawRect(2 + takedcol * 50, 2 + takedrow * 50, 49, 49)
                g.drawRect(3 + takedcol * 50, 3 + takedrow * 50, 49, 49)
                if (jumps == 1) {
                    for (row2 in 0..7) {
                        for (col2 in 0..7) {
                            for (row3 in 0..7) {
                                for (col3 in 0..7) {
                                    val player = checkers.board[takedrow][takedcol]
                                    if (possJumps(player, takedrow, takedcol, row2, col2, row3, col3)) {
                                        g.color = Color.ORANGE
                                        g.drawRect(1 + col3 * 50, 1 + row3 * 50, 49, 49)
                                        g.drawRect(2 + col3 * 50, 2 + row3 * 50, 49, 49)
                                        g.drawRect(3 + col3 * 50, 3 + row3 * 50, 49, 49)

                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (row2 in 0..7) {
                        for (col2 in 0..7) {
                            val player = checkers.board[takedrow][takedcol]
                            if (possMoves(player, takedrow, takedcol, row2, col2)) {
                                g.color = Color.CYAN
                                g.drawRect(1 + col2 * 50, 1 + row2 * 50, 49, 49)
                                g.drawRect(2 + col2 * 50, 2 + row2 * 50, 49, 49)
                                g.drawRect(3 + col2 * 50, 3 + row2 * 50, 49, 49)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EventQueue.invokeLater { JavaPaintUI().isVisible = true }
        }
    }
}
