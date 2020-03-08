/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.time.ZoneId;

/**
 *
 * @author Elias Wilfinger
 */
public class Testing {
    public static void main(String[] args) {
        ZoneId.getAvailableZoneIds().forEach(System.out::println);
    }
}
