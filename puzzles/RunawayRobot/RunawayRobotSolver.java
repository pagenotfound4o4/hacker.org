import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RunawayRobotSolver {
    private static String username, password;
    private static int[][] dir = {{0, 1}, {1, 0}};
    private static String[] dirname = {"D", "R"};
    public int insMin, insMax, boardX, boardY, level;
    public String[] board;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: javac RunawayRobotSolver username password");
        }
        username = args[0]; password = args[1];

        RunawayRobotSolver rrs = new RunawayRobotSolver();
        int tries = 0;
        while (true) {
            try {
                boolean flag = rrs.go();
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
        URL url = new URL("http://www.hacker.org/runaway/index.php");
        String params = "name=" + username + "&password=" + password;
        InputStream is = sendPost(url, params);
        html2input(is);
        System.out.print("Level #" + level + ": ");
        return submit();
    }

    private boolean submit() throws Exception {
        long startTime = System.currentTimeMillis();
        String path = solve(board, boardX, boardY, insMin, insMax);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) * 1.0 / 1000.0 + "s");
        if (path.equals("path not found")) {
            System.out.println(path);
            return false;
        }
        URL url = new URL("http://www.hacker.org/runaway/index.php");
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
        String line;

        while ((line = in.readLine()) != null) {
            if (!line.startsWith("<PARAM NAME=FlashVars"))
                continue;

            line = line.split("\"")[1];
            String[] paras = line.split("&|=");
            // parse parameters
            insMax = Integer.parseInt(paras[3]);
            insMin = Integer.parseInt(paras[5]);
            boardX = Integer.parseInt(paras[7]);
            boardY = Integer.parseInt(paras[9]);
            level = Integer.parseInt(paras[11]);
            board = new String[boardY];
            for (int i=0; i<boardY; i++) {
                board[i] = paras[1].substring(i*boardX, (i+1)*boardX);
            }
        }
    }

    public String solve(String[] mat, int boardX, int boardY, int insMin, int insMax) {
        Result ret;

        for (int step = insMin; step <= insMax; step++) {
            for (int stepD = 0; stepD <= step; stepD++) {
                int stepR = step - stepD;
                char[][] part = new char[stepD+1][stepR+1];
                // init part map
                for (int i=0; i<=stepD; i++) {
                    for (int j=0; j<=stepR; j++) {
                        part[i][j] = '.';
                    }
                }
                int x = 0, y = 0;
                // copy graphs
                while (x<boardX && y<boardY) {
                    for (int tx=x; tx<x+stepD+1; tx++) { // row
                        for (int ty=y; ty<y+stepR+1; ty++) { // column
                            if (tx<boardX && ty<boardY) {
                                if (part[tx-x][ty-y] == '.' && mat[tx].charAt(ty) == 'X') {
                                    part[tx-x][ty-y] = 'X';
                                }
                            }
                        }
                    }
                    x += stepD; y += stepR;
                }
                ret = dfs(part, stepD+1, stepR+1);
                if (ret.isValid) return ret.path;
            }
        }
        return "path not found";
    }

    public Result dfs(char[][] mat, int N, int M) {
        if (mat[0][0] != '.') {
            return new Result(false);
        }
        boolean[] vis = new boolean[N*M];
        int[] fa = new int[N*M];
        for (int i=0; i<N*M; i++) {
            fa[i] = i;
            vis[i] = false;
        }
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(0);
        // search path
        while (!que.isEmpty()) {
            int current = que.poll();
            int curX = current / M;
            int curY = current % M;
            if (curX == N-1 && curY == M-1) {
                break;
            }

            for (int i=0; i<dir.length; i++) {
                int tx = curX + dir[i][0];
                int ty = curY + dir[i][1];
                if (tx<N && ty<M && mat[tx][ty] == '.' && !vis[tx*M+ty]) {
                    que.offer(tx*M+ty);
                    vis[tx*M+ty] = true;
                    fa[tx*M+ty] = current;
                }
            }
        }
        // get path
        Stack<Character> sta = new Stack<Character>();
        int p = N * M - 1;
        while (fa[p] != p) {
            if (p - fa[p] == 1) {
                sta.push('R');
            } else if (p - fa[p] == M) {
                sta.push('D');
            } else {
                sta.push('U'); // unknown step
            }
            p = fa[p];
        }
        Result ret = new Result(false);
        StringBuilder sb = new StringBuilder();
        while (!sta.isEmpty()) {
            sb.append(sta.pop());
        }
        ret.path = sb.toString();
        if (validate(ret.path)) ret.isValid = true;
        return ret;
    }

    public boolean validate(String path) {
        if (path.length() < 1) {
            return false;
        }
        int curX = 0, curY = 0;
        while (curX < boardX && curY < boardY) {
            if (board[curX].charAt(curY) == 'X') {
                return false;
            }
            for (int i=0; i<path.length(); i++) {
                if (path.charAt(i) == 'R') {
                    ++curY;
                } else if  (path.charAt(i) == 'D') {
                    ++curX;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    class Result {
        public boolean isValid;
        public String path;

        public Result(boolean isValid) {
            this.isValid = isValid;
        }
    }
}
