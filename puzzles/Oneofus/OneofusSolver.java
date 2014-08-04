import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OneofusSolver {
    private static final String NO_ANSWER = "no answer";
    private static String username, password;
    private int level, boardX, boardY;
    private Node[][] board;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: RunawayRobotSolver username password");
        }
        username = args[0]; password = args[1];

        OneofusSolver ous = new OneofusSolver();
        int tries = 0;
        while (true) {
            try {
                boolean flag = ous.go();
                if (!flag) {
                    System.out.println("Wrong Answer!!!");
                    break;
                } else {
                    tries = 0;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (ConnectException e) {
                System.out.println("Connection Error!");
                tries++;
                if (tries > 3) break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private InputStream sendPost(URL url, String params) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        out.write(params);
        out.flush();
        return connection.getInputStream();
    }

    private boolean go() throws Exception {
        URL url = new URL("http://www.hacker.org/oneofus/index.php");
        String params = "name=" + username + "&password=" + password;
        InputStream is = sendPost(url, params);
        html2input(is);
        System.out.print("Level #" + level + ": ");
        return submit();
    }

    private boolean submit() throws Exception {
        long startTime = System.currentTimeMillis();
        String path;
        do {
            path = solve(board, boardX, boardY);
        }while (path.equals(NO_ANSWER));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) * 1.0 / 1000.0 + "s");
        URL url = new URL("http://www.hacker.org/oneofus/index.php");
        String params = "name=" + username + "&password=" + password + "&path=" + path;
        InputStream is = sendPost(url, params);
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.contains("your solution sucked"))
                return false;
        }
        return true;
    }

    private void html2input(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        Matcher matcher;
        String line, para = "";

        while ((line = in.readLine()) != null) {
            if (line.contains("<param name=\"FlashVars\"")) { // parse parameters
                matcher = Pattern.compile("x=\\d+").matcher(line);
                if (matcher.find()) {
                    boardX = Integer.parseInt(matcher.group(0).split("=")[1]);
                }
                matcher = Pattern.compile("y=\\d+").matcher(line);
                if (matcher.find()) {
                    boardY = Integer.parseInt(matcher.group(0).split("=")[1]);
                }
                matcher = Pattern.compile("board=[\\S]+").matcher(line);
                if (matcher.find()) {
                    para = matcher.group(0).split("=")[1];
                    para = para.substring(0, para.length() - 1);
                }
            } else if (line.contains("gotolevel")) { // parse level
                matcher = Pattern.compile("(?<=value=\")\\d+(?=\")").matcher(line);
                if (matcher.find()) {
                    level = Integer.parseInt(matcher.group(0));
                    board = new Node[boardY][boardX];
                    if (level < 242) {
                        for (int i=0; i<para.length(); i+=2) {
                            int x = (i >> 1) % boardX;
                            int y = (i >> 1) / boardX;
                            String type = String.valueOf(para.charAt(i));
                            String color = String.valueOf(para.charAt(i + 1));
                            //board[y][x] = new Node(para.charAt(i), para.charAt(i+1), x, y);
                            board[y][x] = new Node(type, color, x, y);
                        }
                    } else {
                        for (int i=0; i<para.length(); i+=4) {
                            int x = (i >> 2) % boardX;
                            int y = (i >> 2) / boardX;
                            String type = para.substring(i, i + 2);
                            String color = para.substring(i + 2, i + 4);
                            board[y][x] = new Node(type, color, x, y);
                        }
                    }
                }
            }
        }
    }

    public String solve(Node[][] board, int boardX, int boardY) {
        final int TOTAL = boardX * boardY;
        boolean[] visit = new boolean[TOTAL];
        Random random = new Random();
        int fails = 0;

        // initialize start point(randomly)
        Deque<Node> path = new ArrayDeque<Node>();
        Node cur = board[random.nextInt(boardY)][random.nextInt(boardX)];
        path.add(cur);
        visit[cur.y*boardX+cur.x] = true;

        while (path.size() < TOTAL) {
            // add possible next points to list
            cur = path.getLast();
            List<Node> not_visited_list = new ArrayList<Node>();
            List<Node> visited_list = new ArrayList<Node>();
            for (int x=0; x<boardX; x++) {
                if (x!=cur.x && board[cur.y][cur.x].hasLink(board[cur.y][x])) {
                    if (visit[cur.y*boardX+x]) {
                        visited_list.add(board[cur.y][x]);
                    } else {
                        not_visited_list.add(board[cur.y][x]);
                    }
                }
            }
            for (int y=0; y<boardY; y++) {
                if (y!=cur.y && board[cur.y][cur.x].hasLink(board[y][cur.x])) {
                    if (visit[y*boardX+cur.x]) {
                        visited_list.add(board[y][cur.x]);
                    } else {
                        not_visited_list.add(board[y][cur.x]);
                    }
                }
            }

            // no possible candidate points, rotate path
            if (not_visited_list.size() == 0) {
                if (visited_list.size() > 0) {
                    Node chosen = visited_list.get(random.nextInt(visited_list.size()));
                    Deque<Node> newPath = new ArrayDeque<Node>();
                    while (path.size()>0 && !path.getFirst().equals(chosen)) {
                        newPath.add(path.removeFirst());
                    }
                    newPath.add(path.removeFirst());
                    while (path.size() > 0) {
                        newPath.add(path.removeLast());
                    }
                    path = newPath;
                    fails++;
                }
                if (fails > TOTAL) {
                    return NO_ANSWER;
                }
                continue;
            }
            fails = 0;

            // randomly choose next point
            Node next = not_visited_list.get(random.nextInt(not_visited_list.size()));
            visit[next.y*boardX+next.x] = true;
            path.add(next);
        }
        return pathToString(path);
    }

    public String pathToString(Deque<Node> path) {
        String ans = "";
        int cnt = 0;
        while (path.size() > 0) {
            if (cnt > 0) ans += "_";
            Node cur = path.removeFirst();
            ans += Integer.toHexString(cur.x) + "," + Integer.toHexString(cur.y);
            cnt++;
        }
        return ans;
    }

    class Node{
        public int x, y;
        public String type, color;

        public Node(String type, String color, int x, int y) {
            this.type = type;
            this.color = color;
            this.x = x;
            this.y = y;
        }

        public boolean hasLink(Node o) {
            return (this.type.equals(o.type) || this.color.equals(o.color));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Node node = (Node)obj;
            return (node.x==this.x && node.y==this.y);
        }
    }
}
