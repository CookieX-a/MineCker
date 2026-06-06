package x.cookie.minecker.procedures;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;

public class OnChatDockerProcedure implements Listener {
	@EventHandler
	public void onMessageSent(AsyncPlayerChatEvent event) {
		execute(event, event.getPlayer(), event.getMessage());
	}

	public static void execute(Entity entity, String text) {
		execute(null, entity, text);
	}

	private static void execute(@Nullable Event event, Entity entity, String text) {
		if (entity == null || text == null)
			return;
		String textmcr = "";
		Entity entitymcr = null;
		entitymcr = entity;
		textmcr = text;// 使用局部变量 textmcr 和 entitymcr（名字你可以自定义）
		if (textmcr == null || !textmcr.startsWith("!docker"))
			return;
		// 获取玩家（Spigot 中实体通常是 Player）
		if (!(entitymcr instanceof org.bukkit.entity.Player))
			return;
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entitymcr;
		// 权限检查（OP）
		if (!player.isOp()) {
			player.sendMessage(org.bukkit.ChatColor.RED + "需要 OP 权限");
			return;
		}
		String dockerArgs = textmcr.substring(7).trim();
		if (dockerArgs.isEmpty()) {
			player.sendMessage(org.bukkit.ChatColor.YELLOW + "用法：!docker <docker命令> 例如 !docker ps");
			return;
		}
		// 危险符号黑名单（通用）
		String[] dangerous = {"|", "&", ";", "&&", "||", "$", "`", "(", ")", "<", ">", "\n", "\r"};
		for (String sym : dangerous) {
			if (dockerArgs.contains(sym)) {
				player.sendMessage(org.bukkit.ChatColor.RED + "不允许使用危险符号: " + sym);
				return;
			}
		}
		// 检测操作系统
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("win");
		// 构建完整命令
		String fullCommand;
		if (isWindows) {
			fullCommand = "cmd /c docker " + dockerArgs;
		} else {
			String escaped = dockerArgs.replace("'", "'\\''");
			fullCommand = "bash -c 'docker " + escaped + "'";
		}
		try {
			Process process = Runtime.getRuntime().exec(fullCommand);
			java.io.BufferedReader stdInput = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
			java.io.BufferedReader stdError = new java.io.BufferedReader(new java.io.InputStreamReader(process.getErrorStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = stdInput.readLine()) != null)
				output.append(line).append("\n");
			while ((line = stdError.readLine()) != null)
				output.append(line).append("\n");
			process.waitFor();
			String result = output.toString();
			if (result.isEmpty()) {
				player.sendMessage(org.bukkit.ChatColor.GRAY + "命令执行成功，无输出内容");
			} else {
				player.sendMessage(result);
			}
		} catch (Exception e) {
			player.sendMessage(org.bukkit.ChatColor.RED + "执行错误: " + e.getMessage());
		}
	}
}