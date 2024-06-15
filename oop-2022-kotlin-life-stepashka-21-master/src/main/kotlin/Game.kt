import java.awt.*
import java.awt.event.*
import java.io.File
import java.util.concurrent.Executors
import javax.swing.*
import kotlin.Any
import kotlin.Int
import kotlin.Pair
import kotlin.arrayOf
import kotlin.system.exitProcess
import java.util.concurrent.TimeUnit

class Game(val grid: Int) {
    init {
        val frame = JFrame("Жизнь")
        val panel = Draw(Board(grid))
        val file123 = "s.txt"
        var scrollFrame = updateScrollFrame(frame, null, panel)
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowOpened(we: WindowEvent) {
                val text = File(file123).readText().trim().split(" ")
                val str: HashSet<Pair<Int, Int>> = HashSet()
                for (i in text.indices) {
                    if (i % 2 == 0) {
                        str.add(Pair(text[i].toInt(), text[i + 1].toInt()))
                    }
                }
                panel.board.coordinates = str
            }

            override fun windowActivated(we: WindowEvent) {
                frame.repaint()
            }
        })

        val button1 = Button("next")
        val button2 = Button("random")
        val button3 = Button("save")
        val button4 = Button("open")
        val button5 = Button("restart")
        val button6 = Button("+")
        val button7 = Button("-")
        val button8 = Button("auto next")

        button1.addActionListener { panel.board.next(); frame.repaint(); }
        button2.addActionListener { panel.board.shuffle(); frame.repaint(); }
        button3.addActionListener {
            val file = JFileChooser()
            val ret = file.showDialog(null, "cохранить файл")
            if (ret == JFileChooser.APPROVE_OPTION) {
                val name = file.selectedFile.path.toString()
                var text = ""
                for (i in panel.board.coordinates) {
                    text += i.first.toString() + " "
                    text += i.second.toString() + " "
                }
                File(name).writeText(text.trim())
            }
        }
        button4.addActionListener {
            val file = JFileChooser()
            val ret = file.showDialog(null, "oткрыть файл")
            if (ret == JFileChooser.APPROVE_OPTION) {
                val name = file.selectedFile.path.toString()
                val text = File(name).readText().split(" ")
                val str: HashSet<Pair<Int, Int>> = HashSet()
                for (i in text.indices) {
                    if (i % 2 == 0) {
                        str.add(Pair(text[i].toInt(), text[i + 1].toInt()))
                    }
                }
                panel.board.coordinates = str
            }
            frame.repaint()
        }
        button5.addActionListener { panel.board.clear(); frame.repaint(); }
        button6.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                val currentSize = panel.preferredSize
                panel.preferredSize = Dimension(currentSize.width + grid, currentSize.height + grid)
                scrollFrame = updateScrollFrame(frame, scrollFrame, panel)
            }
        })
        button7.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                val currentSize = panel.preferredSize
                val width = scrollFrame.size.width
                val height = scrollFrame.size.height
                if (width.coerceAtLeast(height) < currentSize.width - grid) {
                    panel.preferredSize = Dimension(currentSize.width - grid, currentSize.height - grid)
                    scrollFrame = updateScrollFrame(frame, scrollFrame, panel)
                }
            }
        })
        var executorService = Executors.newSingleThreadScheduledExecutor()
        var kolvo = 0
        var count = 1
        button8.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                kolvo += 1
                if (kolvo % 2 != 0) {
                    if (count == 0) {
                        executorService = Executors.newSingleThreadScheduledExecutor()
                    }
                    executorService.scheduleAtFixedRate({ panel.board.next(); frame.repaint() }, 0, 1, TimeUnit.SECONDS)
                } else {
                    executorService.shutdown()
                    count = 0
                }
            }
        })
        panel.add(button1)
        panel.add(button8)
        panel.add(button2)
        panel.add(button3)
        panel.add(button4)
        panel.add(button5)
        panel.add(button6)
        panel.add(button7)
        val menuPanel = JPanel(GridLayout(8, 1));
        menuPanel.add(button1)
        menuPanel.add(button8)
        menuPanel.add(button2)
        menuPanel.add(button3)
        menuPanel.add(button4)
        menuPanel.add(button5)
        menuPanel.add(button6)
        menuPanel.add(button7)
        frame.add(menuPanel, BorderLayout.EAST)
        frame.repaint()
        panel.gridlines = !panel.gridlines;
        frame.isVisible = true
        frame.size = Dimension(
            Toolkit.getDefaultToolkit().screenSize.width / 7,
            Toolkit.getDefaultToolkit().screenSize.height / 7
        )
        frame.extendedState = JFrame.MAXIMIZED_BOTH

        panel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val cX = (e.x * grid.toFloat() / panel.height + 0.2f).toInt()
                val cY = (e.y * grid.toFloat() / panel.height + 0.2f).toInt()
                if (SwingUtilities.isLeftMouseButton(e) && cX in 0 until grid && cY in 0 until grid) {
                    if (Pair(cX, cY) in panel.board.coordinates) {
                        panel.board.removeValue(cX, cY)
                    } else {
                        panel.board.addValue(cX, cY)
                    }
                } else {
                    panel.board.removeValue(cX, cY)
                }
                frame.repaint()
            }
        })

        frame.add(panel)
        panel.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val cX = (e.x * grid.toFloat() / panel.height + 0.2f).toInt()
                val cY = (e.y * grid.toFloat() / panel.height + 0.2f).toInt()
                if (SwingUtilities.isLeftMouseButton(e) && cX % grid in 0 until grid && cY in 0 until grid) {
                    panel.board.addValue(cX, cY)
                } else {
                    panel.board.removeValue(cX, cY)
                }
                frame.repaint()
            }
        })
        frame.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                val options = arrayOf<Any>("да", "нет")
                val n = JOptionPane.showOptionDialog(
                    e.window, "закрыть?",
                    "подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]
                )
                if (n == JOptionPane.YES_OPTION) {
                    var text = ""
                    for (i in panel.board.coordinates) {
                        text += i.first.toString() + " "
                        text += i.second.toString() + " "
                    }
                    File(file123).writeText(text.trim())
                    exitProcess(0)
                }
            }
        })
        frame.addWindowStateListener {
            panel.preferredSize = dimension(grid)
            scrollFrame = updateScrollFrame(frame, scrollFrame, panel)
        }
        frame.extendedState = JFrame.MAXIMIZED_BOTH
    }

    private fun dimension(grid: Int): Dimension {
        var original = grid * 0.1
        val width = Toolkit.getDefaultToolkit().screenSize.width
        val height = Toolkit.getDefaultToolkit().screenSize.height
        val max = (if (width > height) width else height).toDouble()
        while (original <= max) {
            original *= 1.5
        }
        val size = kotlin.math.ceil(original / grid).toInt() * grid
        return Dimension(size, size)
    }

    fun updateScrollFrame(frame: JFrame, scrollFrame: JScrollPane?, panel: Draw): JScrollPane {
        if (scrollFrame != null) frame.remove(scrollFrame)
        val scrollFrame = JScrollPane(panel)
        frame.add(scrollFrame)
        frame.revalidate()
        return scrollFrame
    }
}

