package br.com.tlmacedo.cafeperfeito.service;

import java.io.File;
import java.io.FilenameFilter;

public class ServiceFileFinder {
    public static File finder(String dirName, String arqName) {
        File dir = new File(dirName);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(arqName);
            }
        });
        return files[0];
    }
}
