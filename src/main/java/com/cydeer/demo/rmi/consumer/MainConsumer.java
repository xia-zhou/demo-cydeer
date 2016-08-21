package com.cydeer.demo.rmi.consumer;

import com.cydeer.demo.rmi.api.HelloWordService;
import com.cydeer.demo.rmi.zk.ConsumerService;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by zhangsong on 16/8/11.
 */
public class MainConsumer {

	public static void main(String[] args)
			throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
		/*int port = 9088;
		String url = "rmi://localhost:" + port + "/helloWordService";
		HelloWordService helloWordService = (HelloWordService) Naming.lookup(url);
		String name = helloWordService.sayHello("轻卡");
		System.out.println(name);*/
		ConsumerService consumerService = new ConsumerService();
		int i = 0;
		while (true) {
			HelloWordService helloWordService = consumerService.lookUp();
			String name = helloWordService.sayHello("夏舟" + i);
			System.out.println(name);
			i++;
			Thread.sleep(3000);
		}
	}
}
