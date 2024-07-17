package com.org.bansira.lbms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class LibraryMenuService implements CommandLineRunner {

    @Autowired
    private LibraryService libraryService;

    @Override
    public void run(String... args) throws Exception {

    }
}
