package com.cydeer.demo.rmi.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhangsong on 16/8/11.
 */
public class ConsumerService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

	private CountDownLatch latch = new CountDownLatch(1);

	/**
	 * 存储提供者的url
	 */
	private volatile List<String> urlList = new ArrayList<>();

	/**
	 * 构造函数中链接zk
	 */
	public ConsumerService() {
		ZooKeeper zk = connectZookeeper();
		if (zk != null) {
			watchNode(zk);
		}
	}

	/**
	 * 观察节点的变化
	 *
	 * @param zk
	 */
	private void watchNode(ZooKeeper zk) {
		try {
			List<String> nodeList = zk.getChildren(Constant.path, new Watcher() {
				@Override public void process(WatchedEvent event) {
					LOGGER.info("自节点有变更");
					if (event.getType().equals(Event.EventType.NodeChildrenChanged)) {
						watchNode(zk);
					}
				}
			});
			LOGGER.info("所有的节点:{}", nodeList);
			List<String> dataList = new ArrayList<>();
			for (String node : nodeList) {
				byte[] data = zk.getData(Constant.path + "/" + node, false, null);
				dataList.add(new String(data));
			}
			LOGGER.info("url:{}", dataList);
			urlList = dataList;
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
	 */
	private ZooKeeper connectZookeeper() {
		ZooKeeper zooKeeper = null;
		try {
			zooKeeper = new ZooKeeper(Constant.connectString, Constant.timeOut, new Watcher() {
				@Override public void process(WatchedEvent event) {
					if (event.getState().equals(Event.KeeperState.SyncConnected)) {
						latch.countDown();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return zooKeeper;
	}

	/**
	 * 寻找提供者的服务
	 *
	 * @param <T>
	 * @return
	 */
	public <T> T lookUp() {
		T service = null;
		if (urlList.size() > 0) {
			String url;
			if (urlList.size() == 1) {
				url = urlList.get(0);
			} else {
				url = urlList.get(ThreadLocalRandom.current().nextInt(urlList.size()));
			}
			LOGGER.info("提供者,url:{}", url);
			service = lookUpService(url);
		}

		return service;
	}

	/**
	 * 根据url获取对应的提供者服务类
	 *
	 * @param url
	 * @param <T>
	 * @return
	 */
	private <T> T lookUpService(String url) {
		T remote = null;
		try {
			remote = (T) Naming.lookup(url);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return remote;
	}
}
