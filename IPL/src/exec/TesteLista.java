package exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class TesteLista {
	private static String SOURCES;
	private static String MINGW;
	private static String GABARITOS;
	private static int contadorQuestoes = 1;
	BufferedReader respostasBufferedReader;
	private static Logger logger = Logger.getGlobal();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Properties properties = new Properties();
		InputStream is = TesteLista.class.getResourceAsStream("/config.properties");
		properties.load(is);
		String drive = properties.getProperty("drive");
		SOURCES = drive + properties.getProperty("sources");
		MINGW = drive + properties.getProperty("mingw");
		GABARITOS = drive + properties.getProperty("gabaritos");
		logger.setLevel(Level.INFO);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		File file = new File(GABARITOS + "s" + contadorQuestoes + ".txt");
		InputStream is = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(is);
		respostasBufferedReader = new BufferedReader(isr);
	}

	@AfterEach
	void tearDown() {
		contadorQuestoes++;
	}

	/**
	 * O "value" deve ser alterado de acordo com a quantidade de questões da lista
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RepeatedTest(value = 2)
	void testarQuestoes() throws IOException, InterruptedException {
		compile();
		execute();
	}

	private void execute() throws IOException, InterruptedException {
		String[] command = { "cmd", "/c", "q" + contadorQuestoes + ".exe" };
		ProcessBuilder probuilder = new ProcessBuilder(command);
		probuilder.directory(new File(SOURCES));

		run(command, probuilder, "O comando de execução não funcionou corretamente", true);
	}

	private void compile() throws IOException, InterruptedException {
		String[] command = { "cmd", "/c",
				"gcc " + SOURCES + "q" + contadorQuestoes + ".c" + " -o " + SOURCES + "q" + contadorQuestoes + ".exe" };
		ProcessBuilder probuilder = new ProcessBuilder(command);
		probuilder.directory(new File(MINGW));

		run(command, probuilder, "O comando de compilação não funcionou corretamente", false);
	}

	private void run(String[] command, ProcessBuilder probuilder, String mensagem, boolean teste)
			throws IOException, InterruptedException {
		Process process;
		process = probuilder.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line, gabarito;

		if (teste) {
			while ((gabarito = respostasBufferedReader.readLine()) != null) {
				logger.info("Gabarito: " + gabarito);
				if ((line = br.readLine()) != null) {
					logger.info("Resposta: " + line);
					assertEquals(gabarito.trim(), line.trim(), "A saída não corresponde ao gabarito");
				} else {
					fail("A saída contem menos linhas que o gabarito");
				}
			}
		}

		int exitValue = process.waitFor();
		assertEquals(0, exitValue, mensagem);

	}

}
