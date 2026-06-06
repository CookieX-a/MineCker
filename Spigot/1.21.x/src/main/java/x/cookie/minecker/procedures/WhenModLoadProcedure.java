package x.cookie.minecker.procedures;

import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WhenModLoadProcedure implements Listener {
	@EventHandler
	public void onServerLoad(ServerLoadEvent event) {
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		// 获取服务器工作目录（即启动服务器时的当前文件夹）
		java.io.File workDir = new java.io.File(".");
		// 创建 LocalDockerAPI 文件夹
		java.io.File localDockerAPIDir = new java.io.File(workDir, "LocalDockerAPI");
		if (!localDockerAPIDir.exists()) {
			localDockerAPIDir.mkdirs();
			System.out.println("已创建 LocalDockerAPI 文件夹: " + localDockerAPIDir.getAbsolutePath());
		} else {
			System.out.println("LocalDockerAPI 文件夹已存在: " + localDockerAPIDir.getAbsolutePath());
		}
	}
}