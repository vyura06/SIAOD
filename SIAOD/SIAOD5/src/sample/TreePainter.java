package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TreePainter {
    final Tree<?> tree;
    final Canvas canvas;
    Font font;
    int rootX, rootY, spaceX, spaceY;
    int width, height;

    public TreePainter(Tree<?> tree, Canvas canvas) {
        this.tree = tree;
        this.canvas = canvas;
        this.font = Font.font("Times new roman", FontWeight.BOLD, 15);
        rootX = (int) ((canvas.getWidth() / 2) - 50);
        rootY = 50;
        spaceX = 220;
        spaceY = 50;
        width = 100;
        height = 35;
    }

    public void repaint() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE/*GRAY*/);
        context.setFont(font);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintNode(context, tree.root, rootX, rootY, spaceX, spaceY);
    }
    private void paintNode(GraphicsContext context, Tree.Node<?> root, int x, int y, int dx, int dy) {
        if (root != null) {
            int newDx = dx >> 1;
            paintNode(context, root.left, x - dx, y + dy, newDx, dy);

            context.setFill(Color.RED);
            context.fillRect(x, y, width, height);

            context.setFill(Color.WHITE);
            context.fillText("Key='" + root.key + "'", x + 10, y + (height >> 1));

            paintNode(context, root.right, x + dx, y + dy, newDx, dy);
        }
    }
}
