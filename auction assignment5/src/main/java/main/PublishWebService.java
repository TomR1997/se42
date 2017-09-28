/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.xml.ws.Endpoint;
import web.Auction;
import web.Registration;

/**
 *
 * @author Tomt
 */
public class PublishWebService {

    private static final String URL_AUCTION = "http://localhost:8080/Auction";
    private static final String URL_REGISTRATION = "http://localhost:8080/Registration";

    public static void main(String[] args) {
        Endpoint.publish(URL_AUCTION, new Auction());
        Endpoint.publish(URL_REGISTRATION, new Registration());
    }
}
