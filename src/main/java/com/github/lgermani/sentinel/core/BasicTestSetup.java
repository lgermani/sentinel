package com.github.lgermani.sentinel.core;

import com.thoughtworks.gauge.BeforeSuite;

/**
 * Created by lgermani on 02/01/2018.
 */
public class BasicTestSetup {

    @BeforeSuite
    public void suiteSetup(){
        EnviromentParams.getInstance();
        SeleniumParams.getInstance();
    }
}
