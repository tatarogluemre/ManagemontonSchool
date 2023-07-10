@login
Feature: Login
  @login_negative
  Scenario: Login bosgiris dogrulama
    Given admin anasayfaya gider
    When admin login linkine tiklar
    Then admin login penceresinde oldugunu dogrular
    When admin username alanini bos gecer
    Then admin username alanininda required fields uyarisini gorur
    When admin password alanini bos gecer
    Then admin password alanininda required fields uyarisini gorur
    And admin login butonuna tiklar
    Then admin login olamadigini dogrular dogrular

