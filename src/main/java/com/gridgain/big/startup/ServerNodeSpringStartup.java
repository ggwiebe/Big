package com.gridgain.big.startup;

import org.apache.ignite.Ignition;

/** This file was generated by Ignite Web Console (10/15/2020, 16:48) **/
public class ServerNodeSpringStartup {
    /**
     * Start up node with specified configuration.
     * 
     * @param args Command line arguments, none required.
     * @throws Exception If failed.
     **/
    public static void main(String[] args) throws Exception {
        Ignition.start("Big-server.xml");
    }
}