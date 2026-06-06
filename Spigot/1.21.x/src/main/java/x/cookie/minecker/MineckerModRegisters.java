package x.cookie.minecker;

import java.util.jar.JarFile;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.File;

public class MineckerModRegisters {
	private static final List<Consumer<Class<?>>> reflects = new ArrayList<>();

	public static void registerAll(File pluginJar) {
		try (JarFile jarFile = new JarFile(pluginJar)) {
			var entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				var entry = entries.nextElement();
				if (entry.getName().endsWith(".class")) {
					String classPath = entry.getName().replace('/', '.');
					Class<?> cls = Class.forName(classPath.substring(0, classPath.length() - 6));
					for (Consumer<Class<?>> re : reflects) {
						re.accept(cls);
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void registerReflect(Consumer<Class<?>> consumer) {
		reflects.add(consumer);
	}
}