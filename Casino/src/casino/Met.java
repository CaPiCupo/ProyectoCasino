package casino;

import java.io.IOException;

public class Met {
	// Codigo sacado de --> https://www.quora.com/What-is-the-way-to-clear-eclipse-console-using-Java 
	// Funciona identificando la consola (si es cmd o de kernet UNIX) y escribiendo el comando de limpieza
	// Entiendo como va pero poco mas. Es solo estetico y ni funciona en la consola de Eclipse asi que se puede borrar sin muchas complicaciones
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
            		//Esto si no es ni de Windows ni de Linux, empujara toda la consola hacia abajo.
        } catch (IOException | InterruptedException e) { 
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    public void moverCursorInicio() {
        System.out.print("\r");
    }
    public long factorial(int n) {
        if (n < 0) {
            return 0;
        } else {
        		long resultado = 1; 
        		for (int i = 2; i <= n; i++) { 
        			resultado *= i; 
        }
        		return resultado;
        }
    }
}