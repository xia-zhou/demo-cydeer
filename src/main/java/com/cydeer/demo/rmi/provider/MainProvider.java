package com.cydeer.demo.rmi.provider;

import com.cydeer.demo.rmi.api.HelloWordServiceImpl;
import com.cydeer.demo.rmi.zk.ProviderService;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * Created by zhangsong on 16/8/11.
 */
public class MainProvider {
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		//HelloWordService helloWordService = new HelloWordServiceImpl();
		/*int port = 9088;
		String url = "rmi://localhost:" + port + "/helloWordService";
		LocateRegistry.createRegistry(port);
		Naming.rebind(url, new HelloWordServiceImpl());*/
		ProviderService providerService = new ProviderService();
		providerService.publish(new HelloWordServiceImpl(), "localhost", 9088);
		Thread.sleep(300 * 10000);
	}
}
