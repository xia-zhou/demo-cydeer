package com.cydeer.demo.rmi.api;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by zhangsong on 16/8/11.
 */
public class HelloWordServiceImpl extends UnicastRemoteObject implements HelloWordService {

	public HelloWordServiceImpl() throws RemoteException {
	}

	@Override public String sayHello(String name) throws RemoteException {
		System.out.println("hello " + name);
		return name;
	}
}
