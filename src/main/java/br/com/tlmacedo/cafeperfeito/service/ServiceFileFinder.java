package br.com.tlmacedo.cafeperfeito.service;

import java.io.File;
import java.io.FilenameFilter;

public class ServiceFileFinder {
    public static File finder(String dirName, String arqName, String extensao) {
        File dir = new File(dirName);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(arqName.replaceAll("\\D", ""));
            }
        });
        if (!extensao.equals(null))
            for (File file : files)
                if (file.getName().endsWith(extensao))
                    return file;
        return files[0];
    }
}
