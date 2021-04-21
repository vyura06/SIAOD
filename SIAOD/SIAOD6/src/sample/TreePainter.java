package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TreePainter {
    final Tree tree;
    final Canvas canvas;
    Font font;
    int rootX, rootY, spaceX, spaceY;
    int width, height;

    public TreePainter(Tree tree, Canvas canvas) {
        this.tree = tree;
        this.canvas = canvas;
        this.font = Font.font("Consolas", FontWeight.BOLD, 13);
        rootX = ((int) canvas.getWidth() >> 1) - 10;
        rootY = 40;
        spaceX = 230;
        spaceY = 60;
        width = 90;
        height = 50;
    }

    public void repaint() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.setFont(font);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paint(context, tree.root, rootX, rootY);
        if (!tree.isEmpty())
            paintNode(context, tree.root.left, rootX - 50, rootY + spaceY, spaceX, spaceY);
    }

    private void paint(GraphicsContext context, Tree.Node node, int x, int y) {
        context.setFill(Color.RED);
        context.fillRect(x, y, width, height);

        context.setFill(Color.BLACK);
        context.strokeRect(x, y, width, height);

        context.setFill(Color.WHITE);
        String text = "Key=" + node.key +
                "\nLeft=" + node.left.key +
                "\nRight=" + node.right.key;
        context.fillText(text, x + 5, y + (height >> 2));
    }
    private void paintNode(GraphicsContext context, Tree.Node node, int x, int y, int dx, int dy) {
        int newDx = dx >> 1;
        if (!node.leftThread)
            paintNode(context, node.left, x - dx, y + dy, newDx, dy);
        paint(context, node, x, y);
        if (!node.rightThread)
            paintNode(context, node.right, x + dx, y + dy, newDx, dy);
    }
}
