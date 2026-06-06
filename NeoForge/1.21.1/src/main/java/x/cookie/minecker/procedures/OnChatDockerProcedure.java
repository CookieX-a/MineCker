package x.cookie.minecker.procedures;

import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OnChatDockerProcedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer(), event.getRawText());
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
		textmcr = text;// 使用局部变量 textmcr 和 entitymcr
		if (textmcr == null || !textmcr.startsWith("!docker"))
			return;
		if (!(entitymcr instanceof net.minecraft.server.level.ServerPlayer))
			return;
		net.minecraft.server.level.ServerPlayer player = (net.minecraft.server.level.ServerPlayer) entitymcr;
		if (!player.hasPermissions(4)) {
			player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c需要 OP 权限"));
			return;
		}
		String dockerArgs = textmcr.substring(7).trim();
		if (dockerArgs.isEmpty()) {
			player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§e用法：!docker <docker命令>  例如 !docker ps"));
			return;
		}
		// 检测操作系统
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("win");
		// 危险符号列表（根据系统不同）
		String[] dangerous;
		if (isWindows) {
			// Windows cmd 的危险符号：|, &, &&, ||, ; 等
			dangerous = new String[]{"|", "&", ";", "&&", "||"};
		} else {
			// Linux/Unix shell 的危险符号：|, &, ;, &&, ||, $, `, (, ), <, >, 换行等
			dangerous = new String[]{"|", "&", ";", "&&", "||", "$", "`", "(", ")", "<", ">", "\n", "\r"};
		}
		for (String sym : dangerous) {
			if (dockerArgs.contains(sym)) {
				player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c不允许使用危险符号: " + sym));
				return;
			}
		}
		// 根据操作系统构建完整命令
		String fullCommand;
		if (isWindows) {
			fullCommand = "cmd /c docker " + dockerArgs;
		} else {
			// Linux/Unix 使用 bash -c，并对单引号简单转义
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
				player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§7命令执行成功，无输出内容"));
			} else {
				player.sendSystemMessage(net.minecraft.network.chat.Component.literal(result));
			}
		} catch (Exception e) {
			player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c执行错误: " + e.getMessage()));
		}
	}
}