package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DotEnvUtils {
	/**
	 * Apenas le as variaveis de ambiente do arquivo .env e adiciona
	 * as properties do sistema caso o profile for de desenvolvimento
	 */
	public static boolean load(String profile) {
		Path project = getProjectDir();
		if (!Files.exists(project.resolve(".env"))) {
			log.info("Arquivo .env não encontrado, pulando carregamento!");
			return false;
		}

		log.info("Carregando .env no diretorio '{}'", project.toString());
		Dotenv dotenv = Dotenv.configure().directory(project.toString()).ignoreIfMissing().load();

		if (!isProfileMatch(dotenv, profile)) {
			return false;
		}

		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		log.info("Variáveis de ambiente do arquivo .env foram incluidas nas propriedades da aplicação!");
		return true;
	}

	private static Path getProjectDir() {
		Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
		var classpath = Arrays.stream(System.getProperty("java.class.path").split(";"))
			.filter(s -> s.endsWith("classes")).findFirst();

		Path project = root;
		if (classpath.isPresent()) {
			var path = Path.of(classpath.get());
			if (Files.exists(path)) {
				project = path.getParent().getParent();
			}
		}

		return project;
	}

	private static boolean isProfileMatch(Dotenv dotenv, String profile) {
		String activeProfiles = dotenv.get("SPRING_PROFILES_ACTIVE");
		if (activeProfiles == null || activeProfiles.isEmpty()) {
			log.info("Profile de execução nulo, pulando carregamento do arquivo .env");
			return false;
		}

		String[] profiles = activeProfiles.split(",");
		if (!ArrayUtils.contains(profiles, profile)) {
			log.info("Profiles '{}' de execução não inclui '{}', pulando carregamento do arquivo .env", profiles, profile);
			return false;
		}

		return true;
	}
}
