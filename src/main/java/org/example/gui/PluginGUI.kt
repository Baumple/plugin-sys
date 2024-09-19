package org.example.gui

import notification.plugin.NotificationPlugin;
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

class PluginGUI(private val plugins: List<NotificationPlugin>) : JFrame() {
    private val selectionState =
            plugins.associateWith { false }.toMutableMap()

    init {
        size = Dimension(400, 400)

        font = Font("serif", Font.PLAIN, 24)

        layout = BorderLayout()

        val label = JLabel("Hello")
        label.font = font

        this.add(label, BorderLayout.NORTH)

        this.createPluginCheckBoxes()

        this.createMessageInput()

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

    private fun createPluginCheckBoxes() {
        val centerPane = JPanel()
        this.add(centerPane, BorderLayout.CENTER)

        for (plugin in plugins) {
            val cb = JCheckBox(plugin.name)
            cb.font = font
            cb.isSelected = selectionState[plugin] ?: continue
            cb.addActionListener {
                selectionState[plugin] = !selectionState[plugin]!!
            }
            centerPane.add(cb)
        }
    }

    private fun createMessageInput() {
        val textPane = JPanel()
        textPane.layout = BorderLayout()

        val label = JLabel("Message:")
        label.font = font

        val ta = JTextArea()
        ta.font = font
        ta.rows = 10

        val sb = JButton("Send")
        sb.font = font
        sb.addActionListener {
            plugins
                    .filter { selectionState[it] ?: false }
                    .forEach {
                        JOptionPane.showMessageDialog(
                                this,
                                "Sent message via ${it.name}"
                        )
                    }
        }

        textPane.add(label, BorderLayout.NORTH)
        textPane.add(ta, BorderLayout.CENTER)
        textPane.add(sb, BorderLayout.SOUTH)

        this.add(textPane, BorderLayout.SOUTH)
    }
}
