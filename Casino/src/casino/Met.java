package casino;

import java.io.IOException;

public class Met {
	// Codigo sacado de --> https://www.quora.com/What-is-the-way-to-clear-eclipse-console-using-Java (No tengo ni idea de como funciona)
    public void limpiarConsola() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Para Windows
                Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "cls"});
                process.waitFor(); 
            } else {
                //Para todo lo demas (Linux)
                Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "clear"});
                process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    public void moverCursorInicio() {
        System.out.print("\r");
    }
}