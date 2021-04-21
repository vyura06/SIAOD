package sample;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Graph<String, Integer> graph = new Graph<>();

        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");
        graph.addNode("4");
        graph.addNode("5");
        graph.addNode("6");

        graph.putEdge("1", "2", 7);
        graph.putEdge("1", "3", 9);
        graph.putEdge("1", "6", 14);

        graph.putEdge("2", "3", 10);
        graph.putEdge("2", "4", 15);

        graph.putEdge("3", "6", 2);
        graph.putEdge("3", "4", 11);

        graph.putEdge("4", "5", 6);

        graph.putEdge("6", "5", 9);

        String message = "Print graph = 0\nAdd node = 1\nContains node = 2\nRemove node = 3\nRename node = 4\n" +
                "Contains edge = 5\nPut edge = 6\nGet edge = 7\nRemove edge = 8\n" +
                "Count links = 9\nSearch paths = 10\nFind center = 11\nExit = 12\n";
        boolean inLoop = true;
        do {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "0":
                    System.out.println(graph.toString());
                    break;
                case "1":
                    addNode(graph);
                    break;
                case "2":
                    containsNode(graph);
                    break;
                case "3":
                    removeNode(graph);
                    break;
                case "4":
                    rename(graph);
                    break;
                case "5":
                    containsEdge(graph);
                    break;
                case "6":
                    putEdge(graph);
                    break;
                case "7":
                    getEdge(graph);
                    break;
                case "8":
                    removeEdge(graph);
                    break;
                case "9":
                    System.out.println(countLinks(graph));
                    break;
                case "10":
                    System.out.println("Enter start node");
                    String n1 = scanner.nextLine();
                    System.out.println("Enter end node");
                    String n2 = scanner.nextLine();
                    System.out.println(searchPaths(graph, n1, n2));
                    break;
                case "11":
                    System.out.println(findCenter(graph));
                    break;
                case "12":
                    inLoop = false;
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        } while (inLoop);
    }
    public static int getInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("This not number");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static void addNode(Graph<String, Integer> graph) {
        System.out.println("Enter node");
        System.out.println(graph.addNode(scanner.nextLine()) ? "Added" : "Key contains");
    }
    private static void containsNode(Graph<String, Integer> graph) {
        System.out.println("Enter node");
        System.out.println(graph.containsNode(scanner.nextLine()) ? "Contains" : "Not contains");
    }
    private static void removeNode(Graph<String, Integer> graph) {
        System.out.println("Enter node");
        graph.removeNode(scanner.nextLine());
        System.out.println("Removed");
    }
    private static void rename(Graph<String, Integer> graph) {
        System.out.println("Enter old");
        String old = scanner.nextLine();
        System.out.println("Enter new");
        String newNode = scanner.nextLine();
        System.out.println(graph.rename(old, newNode) ? "Renamed" : "Invalid input");
    }
    private static void containsEdge(Graph<String, Integer> graph) {
        System.out.println("Enter start node");
        String n1 = scanner.nextLine();
        System.out.println("Enter end node");
        String n2 = scanner.nextLine();
        System.out.println(graph.containsEdge(n1, n2) ? "Contains" : "Not contains");
    }
    private static void putEdge(Graph<String, Integer> graph) {
        System.out.println("Enter start node");
        String n1 = scanner.nextLine();
        System.out.println("Enter end node");
        String n2 = scanner.nextLine();
        System.out.println("Enter node weight");
        int weight = getInt(scanner);
        System.out.println(graph.putEdge(n1, n2, weight));
    }
    private static void getEdge(Graph<String, Integer> graph) {
        System.out.println("Enter start node");
        String n1 = scanner.nextLine();
        System.out.println("Enter end node");
        String n2 = scanner.nextLine();
        System.out.println(graph.removeEdge(n1, n2));
    }
    private static void removeEdge(Graph<String, Integer> graph) {
        System.out.println("Enter start node");
        String n1 = scanner.nextLine();
        System.out.println("Enter end node");
        String n2 = scanner.nextLine();
        System.out.println(graph.removeEdge(n1, n2));
    }

    public static <N> String countLinks(Graph<String, N> graph) {
        if (graph.map().isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            HashMap<String, LinksCounter> map = new HashMap<>();
            LinksCounter.count(graph, map);
            map.forEach((node, c) -> sb.append(node).append("->").append(c)
                    .append(c.in == 0 ? " - Source\n" : (c.out == 0) ? " - Result\n" : "\n"));
            return sb.toString();
        }
    }
    public static String searchPaths(Graph<String, Integer> graph, String start, String end) {
        List<Path<String>> paths = new ArrayList<>();
        createPaths(graph, start, end, paths);
        if (paths.isEmpty()) {
            return "Paths:\nMin path\nMax path\n";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Paths:\n");
            for (Path<String> path : paths)
                sb.append(path.toString()).append('\n');
            sb.append("Min path:\n").append(paths.get(0).toString()).append('\n');
            sb.append("Max path:\n").append(paths.get(paths.size() - 1).toString()).append('\n');
            return sb.toString();
        }
    }
    public static String findCenter(Graph<String, Integer> graph) {
        if (graph.map().isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            String line = "_".repeat(30) + "\n";
            findCenter(graph, String::compareTo, (matrix, maxRow, nodes) -> {
                sb.append("Nodes\n");
                sb.append(nodes.toString());
                sb.append('\n');
                sb.append(line);
                sb.append("Matrix\n");
                printMatrix(sb, matrix);
                sb.append(line);
                sb.append("Max row\n");
                printRow(sb, maxRow);
                sb.append('\n');
                sb.append(line);
            }, (center, path) -> sb.append("Center: ").append(center).append("\nPath:").append('\n').
                    append(path.toString()).append('\n').append(line));
            return sb.toString();
        }
    }

    public static <N> void printMatrix(StringBuilder sb, ArrayList<ArrayList<Path<N>>> pathMatrix) {
        for (ArrayList<Path<N>> row : pathMatrix) {
            printRow(sb, row);
            sb.append('\n');
        }
    }
    public static <N> void printRow(StringBuilder sb, ArrayList<Path<N>> row) {
        for (Path<N> path : row) {
            if (path == null) {
                sb.append('-');
            } else {
                sb.append(path.path.isEmpty() ? '0' : Integer.toString(path.totalWeight));
            }
            sb.append(' ');
        }
    }

    public static class Path<N> {
        public ArrayList<N> path;
        public int totalWeight;

        public Path(ArrayList<N> path, int totalWeight) {
            this.path = path;
            this.totalWeight = totalWeight;
        }

        public void calcTotal(Graph<N, Integer> map) {
            totalWeight = calcTotal(path, map);
        }

        public String toString() {
            return path + ", total=" + totalWeight;
        }

        public static int compare(Path<?> o1, Path<?> o2) {
            return Integer.compare(o1.totalWeight, o2.totalWeight);
        }

        public static <N> int calcTotal(ArrayList<N> path, Graph<N, Integer> graph) {
            if (path.isEmpty())
                return 0;
            int total = 0;
            N prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                N next = path.get(i);
                total += graph.map().get(prev).get(next);
                prev = next;
            }
            return total;
        }
    }
    private static class PathNode<V> {
        final V value;
        final PathNode<V> prev;

        PathNode(V value, PathNode<V> prev) {
            this.value = value;
            this.prev = prev;
        }
    }
    public static <N> void createPaths(Graph<N, Integer> graph, N start, N end, List<Path<N>> paths) {
        createPaths(graph, start, end, path -> paths.add(new Path<>(path, Path.calcTotal(path, graph))));
        paths.sort(Path::compare);
    }
    public static <N, W> void createPaths(Graph<N, W> graph, N start, N end, Consumer<ArrayList<N>> pathConsumer) {
        for (N to : graph.map().get(start).keySet()) {
            if (Objects.equals(to, end)) {
                ArrayList<N> path = new ArrayList<>(2);
                path.add(start);
                path.add(to);
                pathConsumer.accept(path);
            } else {
                temp(graph, to, end, pathConsumer, new PathNode<>(to, new PathNode<>(start, null)));
            }
        }
    }
    private static <N, W> void temp(Graph<N, W> graph, N from, N end,
                                    Consumer<ArrayList<N>> out, PathNode<N> last) {
        Map<N, W> map = graph.map().get(from);
        if (map == null)
            return;
        next:
        for (N to : map.keySet()) {
            for (PathNode<N> n = last; n != null; n = n.prev)
                if (Objects.equals(to, n.value))
                    continue next;
            if (Objects.equals(to, end)) {
                ArrayList<N> path = new ArrayList<>();
                copyPath(last, path);
                path.add(to);
                out.accept(path);
            } else {
                temp(graph, to, end, out, new PathNode<>(to, last));
            }
        }
    }
    private static <N> void copyPath(PathNode<N> last, ArrayList<N> path) {
        int length = 0;
        for (PathNode<N> n = last; n != null; n = n.prev) {
            path.add(null);
            length++;
        }
        int i = length - 1;
        for (PathNode<N> n = last; n != null; n = n.prev)
            path.set(i--, n.value);
    }


    public interface CallBack<N> {
        void accept(ArrayList<ArrayList<Path<N>>> matrix, ArrayList<Path<N>> maxRow, ArrayList<N> nodes);
    }
    public static <N> void findCenter(Graph<N, Integer> graph, Comparator<N> comparator,
                                      CallBack<N> callBack, BiConsumer<N, Path<N>> consumer) {
        Set<N> ns = graph.map().keySet();
        if (ns.isEmpty())
            return;
        ArrayList<ArrayList<Path<N>>> pathMatrix = new ArrayList<>(ns.size());
        ArrayList<Path<N>> max = new ArrayList<>(ns.size());
        ArrayList<N> nodes = new ArrayList<>(ns);
        if (comparator != null)
            nodes.sort(comparator);
        buildMatrix(graph, pathMatrix, nodes);
        buildMaxRow(pathMatrix, max);
        if (callBack != null)
            callBack.accept(pathMatrix, max, nodes);
        findMinPaths(max, nodes, consumer);
    }
    private static <N> void buildMatrix(Graph<N, Integer> graph, ArrayList<ArrayList<Path<N>>> mat, ArrayList<N> nodes) {
        ArrayList<Path<N>> list = new ArrayList<>();
        for (N from : nodes) {
            ArrayList<Path<N>> row = new ArrayList<>();
            for (N to : nodes) {
                createPaths(graph, from, to, list);
                row.add(list.isEmpty() || from == to ? null : list.get(0));
                list.clear();
            }
            mat.add(row);
        }
    }
    private static <N> void buildMaxRow(ArrayList<ArrayList<Path<N>>> mat, ArrayList<Path<N>> maxRow) {
        maxRow.addAll(mat.get(0));
        for (ArrayList<Path<N>> row : mat) {
            for (int x = 0; x < row.size(); x++) {
                Path<N> minPaths = row.get(x);
                Path<N> paths = maxRow.get(x);
                if (minPaths != null && !minPaths.path.isEmpty())
                    if (paths.path.isEmpty() || minPaths.totalWeight > paths.totalWeight)
                        maxRow.set(x, minPaths);
            }
        }
    }
    private static <N> void findMinPaths(ArrayList<Path<N>> maxRow, ArrayList<N> nodes, BiConsumer<N, Path<N>> result) {
        final int infinity = Integer.MAX_VALUE;
        int min = infinity;
        for (Path<N> minPaths : maxRow)
            if (minPaths != null)
                min = Math.min(min, minPaths.path.isEmpty() ? 0 : minPaths.totalWeight);

        Predicate<Path<N>> predicate;
        final int finalMin = min;
        switch (finalMin) {
            case 0:
                predicate = paths -> paths != null && (paths.path.isEmpty() || finalMin == paths.totalWeight);
                break;
            case infinity:
                predicate = paths -> paths == null || (!paths.path.isEmpty() && finalMin == paths.totalWeight);
                break;
            default:
                predicate = paths -> paths != null && (!paths.path.isEmpty() && finalMin == paths.totalWeight);
        }

        for (int i = 0; i < maxRow.size(); i++) {
            Path<N> p = maxRow.get(i);
            if (predicate.test(p))
                result.accept(nodes.get(i), p);
        }
    }
}