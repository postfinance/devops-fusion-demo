// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.context;

public class ThreadLocalApplicationTestContext {

    private static final ThreadLocal<ApplicationTestContext> context = ThreadLocal.withInitial(ApplicationTestContext::new);

    public ApplicationTestContext get() {
        return context.get();
    }
}
