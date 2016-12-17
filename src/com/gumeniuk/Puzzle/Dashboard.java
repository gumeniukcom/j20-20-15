package com.gumeniuk.Puzzle;

import com.gumeniuk.Puzzle.Builders.MenuBuilder;
import com.gumeniuk.Puzzle.Builders.MenuItemBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AbstractFrame {

    private final List<GameButton> buttons;
    private final Runnable shuffle;

    private int empty;
    private static int size;

    Dashboard() throws HeadlessException {
        setTitle("Puzzle");
        setResizable(false);
        size = empty = GameButton.DIMENSION * GameButton.DIMENSION - 1;
        this.buttons = new ArrayList<>();

        ActionListener listener = getActionListenerImpl();
        for (int i = 0; i < size; i++) {
            buttons.add(new GameButton(i, listener));
        }

        shuffle = new Shuffle(buttons);
        shuffle.run();
    }

    public static void main(String[] args) {
        initLookAndFeel();

        new Dashboard().setVisible(true);
    }

    private Component createMenu() {


        ActionListener listener = getActionListenerImpl();
        JMenuBar menuBar = new JMenuBar();

//        JMenu file = new JMenu("File");
//
////        JMenuItem item = new JMenuItem("New game");
////        item.addActionListener(listener);
////
////        file.add(item);
//
//        MenuItemBuilder builder = new MenuItemBuilder();
//
//        builder.addListener(listener);
//        file.add(
//            builder
//                .setText("New game")
//                .build()
//        );
//
//        file.add(
//                builder
//                        .setText("Exit")
//                        .setAccelerator("control Q")
//                        .build()
//        );
//
////        JMenuItem itemExit = new JMenuItem("Exit");
////        itemExit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
////        itemExit.addActionListener(listener);
////
////        file.add(itemExit);

        menuBar.add(
                new MenuBuilder("File", listener)
                        .add("New game")
                        .add("Exit", "control Q")
                        .build()
        );


//        menuBar.add(file);
//        menuBar.add(builder.build());
        return menuBar;
    }

    private Component createGameField() {
        JPanel panel = new JPanel(null);

        int size = GameButton.size * GameButton.DIMENSION;
        panel.setPreferredSize(new Dimension(size, size));

        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    @Override
    protected void onInit() {
        add(createMenu(), BorderLayout.NORTH);

        add(createGameField(), BorderLayout.CENTER);
        pack();
    }

    @Override
    protected void onButtonClick(JButton src) {
        GameButton button = (GameButton) src;
        if (button.canMoveTo(empty)) {
            empty = button.moveTo(empty);
            if (hasWin()) {
                showMessage("Congratulations", "You won!");
                shuffle.run();
            }
        }
    }

    private boolean hasWin() {
        for (GameButton btn : buttons) {
            if (!btn.hasValidPosition()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onMenuItemClick(String command) {
        switch (command) {
            case "New game":
                shuffle.run();
                break;
            case "Exit":
                dispose();
                break;
        }
    }
}
