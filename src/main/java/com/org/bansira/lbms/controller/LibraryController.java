package com.org.bansira.lbms.controller;

import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;
}
