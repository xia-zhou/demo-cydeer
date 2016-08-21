package com.cydeer.demo.rmi.zk;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhangsong on 16/8/11.
 * 提供者的处理类
 */
public class ProviderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderService.class);

	private CountDownLatch countDownLatch = new CountDownLatch(1);

	/**
	 * 注册提供者
	 * <p>
	 * 注册服务拿到url,把url注册到zk上面
	 *
	 * @param remote
	 * @param host
	 * @param port
	 */
	public void publish(Remote remote, String host, Integer port) {
		String url = publishService(remote, host, port);
		if (StringUtils.isBlank(url)) {
			LOGGER.info("远程服务发布失败");
			return;
		}
		ZooKeeper zooKeeper = null;
		try {
			zooKeeper = connectZookeeper();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (zooKeeper != null) {
			createNode(zooKeeper, url);
		}
	}

	/**
	 * 创建提供者节点
	 *
	 * @param zooKeeper
	 * @param url
	 */
	private void createNode(ZooKeeper zooKeeper, String url) {
		try {
			String path = zooKeeper.create(Constant.path + "/provider", url.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			LOGGER.debug("create zookeeper node ({} => {})", path, url);
			LOGGER.info("创建zookeeper节点成功");
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接zk
	 *
	 * @return
	 * @throws InterruptedException
	 */
	private ZooKeeper connectZookeeper() throws InterruptedException {
		ZooKeeper zooKeeper = null;
		try {
			zooKeeper = new ZooKeeper(Constant.connectString, Constant.timeOut, new Watcher() {
				@Override public void process(WatchedEvent watchedEvent) {
					LOGGER.info("触发了zookeeper的观察事件,path:{},状态:{}", watchedEvent.getPath(), watchedEvent.getState());
					if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
						LOGGER.info("zookeeper链接成功");
						countDownLatch.countDown();
					}
				}
			});
		} catch (IOException e) {
			LOGGER.info("zookeeper链接建立异常");
			e.printStackTrace();
		}
		countDownLatch.await();
		return zooKeeper;
	}

	/**
	 * 暴漏服务
	 *
	 * @param remote
	 * @param host
	 * @param port
	 * @return
	 */
	private String publishService(Remote remote, String host, Integer port) {
		String url = String.format("rmi://%s:%s/%s", host, port, remote.getClass().getName());
		LOGGER.info("对应的url:{}", url);
		try {
			LocateRegistry.createRegistry(port);
			Naming.rebind(url, remote);
		} catch (RemoteException e) {
			LOGGER.info("remote异常");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			LOGGER.info("rebind异常");
			e.printStackTrace();
		}
		return url;
	}

}
