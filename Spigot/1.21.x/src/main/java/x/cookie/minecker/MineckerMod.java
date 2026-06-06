package x.cookie.minecker;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.io.StringWriter;
import java.io.PrintWriter;

public class MineckerMod extends JavaPlugin {
	public static boolean isPaper;
	public static MineckerMod INSTANCE = null;

	public MineckerMod() {
		INSTANCE = this;
	}

	@Override
	public void onLoad() {
		//check the environment
		try {
			Class.forName("io.papermc.paper.ServerBuildInfo");
			isPaper = true;
		} catch (ClassNotFoundException e) {
			isPaper = false;
		}
		MineckerModRegisters.registerReflect(a -> {
			if (Listener.class.isAssignableFrom(a)) {
				getLogger().info("Register Listener: " + a.getName());
				try {
					getServer().getPluginManager().registerEvents((Listener) a.getConstructor().newInstance(), this);
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					MCRLogger.info(e);
				}
			}
		});
	}

	@Override
	public void onEnable() {
		//Do Something(
		// Start of user code block mod
		// End of user code block mod
		MineckerModRegisters.registerAll(getFile());
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		// Start of user code block mod
		// End of user code block mod
	}

	public static class MCRLogger {
		private static final int MAX_DEPTH = 2;

		private static String toDeepString(Object object, List<String> ignoredMethod, int depth) {
			if (depth > MAX_DEPTH) {
				return String.valueOf(object);
			}
			List<String> list = new ArrayList<>(ignoredMethod);
			list.addAll(List.of("copy", "clone", "spigot"));
			HashMap<String, String> parameters = new HashMap<>();
			var methods = object.getClass().getMethods();
			for (Method method : methods) {
				if (!method.getReturnType().equals(Void.TYPE) && method.getParameters().length == 0) {
					String value;
					try {
						if (list.contains(method.getName())) {
							continue;
						}
						if (method.getName().equals("getClass")) {
							value = CommandLine.Help.Ansi.Style.fg_green.on() + "\"" + object.getClass().getName() + "\"" + CommandLine.Help.Ansi.Style.fg_green.off();
						} else {
							value = toString(method.invoke(object), depth + 1);
						}
					} catch (IllegalAccessException | InvocationTargetException e) {
						value = e.toString();
					}
					parameters.put(method.getName(), value);
				}
			}
			return parameters.toString();
		}

		private static String toString(Object object, int depth) {
			if (object instanceof Entity entity) {
				return toDeepString(entity, List.of("eject", "leaveVehicle"), depth);
			} else if (object instanceof Event event) {
				return toDeepString(event, List.of("callEvent", "getHandlers", "getHandlerList"), depth);
			} else if (object instanceof String string) {
				return "\"" + string + "\"";
			} else if (object instanceof Exception e) {
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				return stringWriter.toString();
			}
			// Start of user code block
			// End of user code block
			return String.valueOf(object);
		}

		public static void info(String prefix, Object o) {
			INSTANCE.getLogger().info(prefix + toString(o, 0));
		}

		public static void info(Object o) {
			INSTANCE.getLogger().info(toString(o, 0));
		}
	}
}