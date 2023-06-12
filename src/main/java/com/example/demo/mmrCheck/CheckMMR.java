package com.example.demo.mmrCheck;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.Map;


public class CheckMMR {

    public String inputData(String username, String server, Map<String, Object> response) {


        Proxy proxy = new Proxy();
        proxy.setHttpProxy("http://drafasenos317:BsRdqfq3oX@185.228.192.215:59100");
        proxy.setSslProxy("https://drafasenos317:BsRdqfq3oX@185.228.192.215:59100");


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless","--window-size=1920,1080", "--start-maximized", "--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
        options.setCapability("proxy", proxy);
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver(options);


        driver.get("https://mylolmmr.com/");
        String resultServer = switch (server) {
            case "RU" -> "ru";
            case "EUW" -> "euw1";
            case "NA" -> "na1";
            case "EUNE" -> "eun1";
            case "JP" -> "jp1";
            case "KR" -> "kr";
            case "BR" -> "br1";
            case "LAN" -> "la1";
            case "OC" -> "oc1";
            case "TR" -> "tr1";
            default -> "Server error";
        };

        WebDriverWait wait = new WebDriverWait(driver, 5);

        List<WebElement> cookieAcceptList = driver.findElements(By.cssSelector("button.fc-button.fc-cta-consent.fc-primary-button"));
        if (!cookieAcceptList.isEmpty()) {
            WebElement cookieAccept = cookieAcceptList.get(0);
            wait.until(ExpectedConditions.visibilityOf(cookieAccept));
            cookieAccept.click();
        }
        Proxy usedProxy = (Proxy) ((ChromeDriver) driver).getCapabilities().getCapability("proxy");
        String usedHttpProxy = usedProxy.getHttpProxy();
        String usedSslProxy = usedProxy.getSslProxy();
        System.out.println(usedHttpProxy);
        System.out.println(usedSslProxy);


        String reg = String.format("div[data-value='%s']", resultServer);

        WebElement inputElement = driver.findElement(By.id("username"));
        WebElement searchButton = driver.findElement(By.id("searchIcon"));
        WebElement region = driver.findElement(By.cssSelector(reg));

        region.click();
        inputElement.sendKeys(username);
        searchButton.click();

        try{
            WebElement rank = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.approximrank span")));
            WebElement mmr = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.rankstatsn")));
            response.put("elo", rank.getText());
            response.put("mmr", mmr.getText());
            driver.quit();
            return "RESULTS FOR " + username;
        }catch (TimeoutException e){
            driver.quit();
            return "USERNAME OR REGION IS INCORRECT";
        }

    }

}
