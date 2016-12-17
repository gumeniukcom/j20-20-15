package com.gumeniuk.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class Dashboard extends AbstractFrame {

    private final Collection<GameButton> buttons;

    private int empty = 15;

    Dashboard() throws HeadlessException {
        this.buttons = new ArrayList<>();

        ActionListener listener = getActionListenerImpl();
        for (int i = 0; i < 15; i++) {
            buttons.add(new GameButton(i, listener));
        }
    }

    public static void main(String[] args) {
        initLookAndFeel();

        new Dashboard().setVisible(true);
    }

    @Override
    protected void onInit() {
        setTitle("Puzzle");
        setLayout(null);
        setResizable(false);

        int size = GameButton.size * GameButton.DIMENSION;
        getContentPane().setPreferredSize(new Dimension(size, size));

        for (JButton button : buttons) {
            add(button);
        }
        pack();
    }

    @Override
    protected void onButtonClick(JButton src) {
        GameButton button = (GameButton) src;
        if (button.canMoveTo(empty)) {
            empty = button.moveTo(empty);
        }

    }
}
