/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class IO_Access {
    public static String getSQLStatementString(File file) throws FileNotFoundException{
        return new BufferedReader(new FileReader(file)).lines().filter(s -> !s.isBlank())
                .map(String::new)
                .collect(Collectors.joining("\n"));
    }
}
