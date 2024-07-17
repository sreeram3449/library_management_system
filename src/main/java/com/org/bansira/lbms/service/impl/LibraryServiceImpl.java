package com.org.bansira.lbms.service.impl;

import com.org.bansira.lbms.data.BookRepository;
import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    BookRepository bookRepository;
}
