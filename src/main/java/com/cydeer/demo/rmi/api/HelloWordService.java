package com.cydeer.demo.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by zhangsong on 16/8/11.
 */
public interface HelloWordService extends Remote {
	String sayHello(String name) throws RemoteException;
}
