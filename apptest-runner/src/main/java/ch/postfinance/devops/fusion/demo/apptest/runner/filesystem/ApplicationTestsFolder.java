// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.filesystem;

import java.io.File;

public class ApplicationTestsFolder extends File {

    private static final String APPLICATION_TEST_FOLDER = "tests/01-financial-service";

    public ApplicationTestsFolder() throws NullPointerException {
        super(Thread.currentThread().getContextClassLoader().getResource(APPLICATION_TEST_FOLDER).getPath());
    }
}
