package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private Label sizeLabel, console;

    @FXML
    private TextField putKeyTextField, containsTextField, removeTextField;

    @FXML
    private Button putButton, containsButton, removeButton, putRandom, clearButton;

    @FXML
    private TextField rootXTextField, rootYTextField, spaceXTextField, spaceYTextField, widthTextField, heightTextField;

    private Tree<Integer> tree;
    private TreePainter treePainter;

    @FXML
    private void initialize() {
        tree = new Tree<>(Integer::compareTo);
        treePainter = new TreePainter(tree, canvas);

        putButton.setOnAction(actionEvent -> put());
        containsButton.setOnAction(actionEvent -> contains());
        removeButton.setOnAction(actionEvent -> remove());
        putRandom.setOnAction(actionEvent -> putRandom());
        clearButton.setOnAction(actionEvent -> clear());

        putKeyTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                put();
        });
        containsTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                contains();
        });
        removeTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                remove();
        });

        rootXTextField.setText("" + treePainter.rootX);
        rootYTextField.setText("" + treePainter.rootY);
        spaceXTextField.setText("" + treePainter.spaceX);
        spaceYTextField.setText("" + treePainter.spaceY);
        widthTextField.setText("" + treePainter.width);
        heightTextField.setText("" + treePainter.height);

        setOnKeyPressed(rootXTextField, value -> treePainter.rootX = value);
        setOnKeyPressed(rootYTextField, value -> treePainter.rootY = value);
        setOnKeyPressed(spaceXTextField, value -> treePainter.spaceX = value);
        setOnKeyPressed(spaceYTextField, value -> treePainter.spaceY = value);
        setOnKeyPressed(widthTextField, value -> treePainter.width = value);
        setOnKeyPressed(heightTextField, value -> treePainter.height = value);

        onTreeDataChanged();
    }

    private void setOnKeyPressed(TextField textField, IntConsumer setter) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                setter.accept(intOf(textField));
                treePainter.repaint();
            }
        });
    }

    private int intOf(TextField textField) {
        return parseInt(textField.getText());
    }
    private int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void onTreeDataChanged() {
        sizeLabel.setText("Size = " + tree.size());
        treePainter.repaint();
    }

    private void put() {
        tree.put(intOf(putKeyTextField));
        onTreeDataChanged();
        console.setText("Completed");
    }
    private void contains() {
        console.setText(tree.contains(intOf(containsTextField)) ? "Key contains" : "Key not contains");
    }
    private void remove() {
        if (tree.remove(intOf(removeTextField))) {
            onTreeDataChanged();
            console.setText("Key removed");
        } else {
            console.setText("Key not found");
        }
    }
    private void putRandom() {
        tree.put((int) (Math.random() * 50) - 20);
        onTreeDataChanged();
    }
    private void clear() {
        tree.clear();
        onTreeDataChanged();
        console.setText("Cleared");
    }

    @FXML
    private void preorder() {
        console.setText(printTree(builder -> tree.preorder(value -> builder.append(value).append(',').append(' '))));
    }
    @FXML
    private void inorder() {
        console.setText(printTree(builder -> tree.inorder(value -> builder.append(value).append(',').append(' '))));
    }
    @FXML
    private void postorder() {
        console.setText(printTree(builder -> tree.postorder(value -> builder.append(value).append(',').append(' '))));
    }

    private String printTree(Consumer<StringBuilder> printer) {
        if (tree.isEmpty()) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append('[');
            printer.accept(builder);
            builder.setLength(builder.length() - 2);
            builder.append(']');
            return builder.toString();
        }
    }
}