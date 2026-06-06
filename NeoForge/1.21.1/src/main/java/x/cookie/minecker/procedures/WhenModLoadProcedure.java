package x.cookie.minecker.procedures;

import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import javax.annotation.Nullable;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class WhenModLoadProcedure {
	@SubscribeEvent
	public static void init(FMLDedicatedServerSetupEvent event) {
		execute();
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		// 获取服务端的运行目录
		java.io.File gameDir = net.neoforged.fml.loading.FMLPaths.GAMEDIR.get().toFile();
		// 然后在这个目录下创建 LocalDockerAPI 文件夹
		java.io.File localDockerAPIDir = new java.io.File(gameDir, "LocalDockerAPI");
		if (!localDockerAPIDir.exists()) {
			localDockerAPIDir.mkdirs();
			System.out.println("已创建 LocalDockerAPI 文件夹: " + localDockerAPIDir.getAbsolutePath());
		} else {
			System.out.println("LocalDockerAPI 文件夹已存在: " + localDockerAPIDir.getAbsolutePath());
		}
	}
}