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

    private Tree tree;
    private TreePainter treePainter;

    @FXML
    private void initialize() {
        tree = new Tree();
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
                tryAdvance(textField, setter);
                treePainter.repaint();
            }
        });
    }
    private void tryAdvance(TextField textField, IntConsumer consumer) {
        try {
            consumer.accept(Integer.parseInt(textField.getText()));
        } catch (NumberFormatException ignored) {
        }
    }
    private void onTreeDataChanged() {
        sizeLabel.setText("Size = " + tree.size());
        treePainter.repaint();
    }

    private void put() {
        tryAdvance(putKeyTextField, value -> {
            tree.add(value);
            onTreeDataChanged();
            console.setText("Added");
        });
    }
    private void contains() {
        tryAdvance(containsTextField, value -> console.setText(tree.contains(value) ? "Key contains" : "Key not contains"));
    }
    private void remove() {
        tryAdvance(removeTextField, value -> {
            if (tree.remove(value)) {
                onTreeDataChanged();
                console.setText("Key removed");
            } else {
                console.setText("Key not found");
            }
        });
    }

    private void putRandom() {
        tree.add((int) (Math.random() * 50) - 20);
        onTreeDataChanged();
    }
    private void clear() {
        tree.clear();
        onTreeDataChanged();
        console.setText("Cleared");
    }

    @FXML
    private void ascending() {
        console.setText(treeToString(tree::ascending));
    }
    @FXML
    private void descending() {
        console.setText(treeToString(tree::descending));
    }

    public String treeToString(Consumer<IntConsumer> temp) {
        if (tree.isEmpty()) {
            return "[]";
        } else {
            StringBuilder out = new StringBuilder().append('[');
            temp.accept(value -> out.append(value).append(',').append(' '));
            out.setLength(out.length() - 2);
            return out.append(']').toString();
        }
    }
}