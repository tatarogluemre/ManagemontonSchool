@login
Feature: Login
  @login_possitive
  Scenario: Login giris dogrulama
    Given admin anasayfaya gider
    When admin login linkine tiklar
    Then admin login penceresinde oldugunu dogrular
    When admin username alanina admin adini girer
    When admin password alanina sifresini girer
    And admin login butonuna tiklar
    Then admin login oldugunu dogrular dogrular
    Then admin admin yonetim sayfasinda oldugunu dogrular

