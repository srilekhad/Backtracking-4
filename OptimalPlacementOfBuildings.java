//Time Complexity: O(C(H*W, N) * H*W) (Combinatorial placements of N buildings times BFS for each grid)
//Space Complexity: O(H*W) for the BFS queue and visited matrix.

// Use DFS/backtracking to generate all combinations of placing n buildings on the H×W grid (mark cell as building, recurse, then unmark).
// For each placement, run multi-source BFS from all building cells to expand layer by layer and compute the maximum distance to the nearest building (dist - 1).
// Keep a global minimum of this maximum distance across all placements and return it as the optimal value.

import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class OptimalPlacementOfBuildings {
    public static void main(String[] args) {
        BuildingPlacement buildingPlacement = new BuildingPlacement();
        System.out.println(buildingPlacement.findMinDistance(7, 7, 3));
    }

    public static class BuildingPlacement {

        int H;
        int W;
        int result;

        public int findMinDistance(int h, int w, int n) {
            this.H = h;
            this.W = w;
            this.result = Integer.MAX_VALUE;

            int[][] grid = new int[H][W];
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    grid[i][j] = -1;
                }
            }

            dfs(grid, 0, 0, n);
            return result;
        }

        private void dfs(int[][] grid, int i, int j, int n) {
            if (n == 0) {
                bfs(grid);
                return;
            }

            if (j == W) {
                i++;
                j = 0;
            }

            for (int r = i; r < H; r++) {
                for (int c = (r == i ? j : 0); c < W; c++) {
                    grid[r][c] = 0;
                    dfs(grid, r, c + 1, n - 1);
                    grid[r][c] = -1;
                }
            }
        }

        private void bfs(int[][] grid) {
            Queue<int[]> q = new LinkedList<>();
            boolean[][] visited = new boolean[H][W];
            int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (grid[i][j] == 0) {
                        q.add(new int[]{i, j});
                        visited[i][j] = true;
                    }
                }
            }

            int dist = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int k = 0; k < size; k++) {
                    int[] curr = q.poll();

                    for (int[] dir : dirs) {
                        int r = dir[0] + curr[0];
                        int c = dir[1] + curr[1];

                        if (r >= 0 && c >= 0 && r < H && c < W && !visited[r][c]) {
                            q.add(new int[]{r, c});
                            visited[r][c] = true;
                        }
                    }
                }
                dist++;
            }

            if (result > dist - 1) {
                System.out.println(Arrays.deepToString(grid));
            }
            result = Math.min(result, dist - 1);
        }
    }
}
