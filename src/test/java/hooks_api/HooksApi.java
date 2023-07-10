package hooks_api;

import io.cucumber.java.Before;

import static base_url.BaseUrl.setUp;

public class HooksApi {

    @Before()
    public void beforeApi(){

        setUp();
    }

//    @After
//    public void tearDownScenarios(Scenario scenario) {
//        System.out.println("Afher Metotu");
////        Eger bir Scenario FAIL ederse, ekran goruntusunu al ve rapora ekle
//        if (scenario.isFailed()) {
//            final byte[] failedScreenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
////                       ekran goruntusu    file tipi                  ekran goruntusunun adi
//            scenario.attach(failedScreenshot, "image/png", "failed_scenario_" + scenario.getName());
//            Driver.closeDriver();
//        }
    }

