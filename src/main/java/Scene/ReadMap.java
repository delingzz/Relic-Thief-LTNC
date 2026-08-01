package Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadMap {

    public static int[][] loadMap(String path) {

        ArrayList<int[]> rows = new ArrayList<>();
        InputStream is = ReadMap.class.getResourceAsStream(path);
        if(is == null) {
            return new int[0][0];
        }
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(is))
        ) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;

                String[] s = line.split("\\s+");

                int[] row = new int[s.length];

                for (int i = 0; i < s.length; i++) {
                    row[i] = Integer.parseInt(s[i]);
                }

                rows.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] map = new int[rows.size()][];

        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(i);
        }

        return map;
    }
}