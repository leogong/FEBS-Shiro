package cc.mrbird.core.util;

import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author leo on 30/01/2018.
 */
public class ShellUtil {
    private static final String REBOOT = "reboot";

    public static String runShell(String shell) throws InterruptedException, IOException {
        Assert.hasText(shell, "shell must not be null");
        Process p = Runtime.getRuntime().exec(shell);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static void reboot() throws IOException, InterruptedException {
        runShell(REBOOT);
    }
}
