package ru.bulkashmak;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class AppTest {

    /*
    * Задание 14.2 Selenium
    * Для сайта https://howtodoinjava.com/ реализовать следующий тест:
    * Зайти в 4 различных (по вашему выбору) секции уроков Java Core.
    * Для каждой из секций проверить что:
    * Присутствует левый блок тематического меню.
    * Присутствует верхний блок навигационного меню.
    * Присутствует тумблер переключения темной/светлой темы.
    * Тумблер переключить. Состояние тумблера изменилось.
    */

    static ChromeDriver driver;

    @BeforeAll
    static void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver","bin/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void test(String coreJavaLink) {

        driver.get("https://howtodoinjava.com/");

        WebElement coreJava = driver.findElement(By.xpath(String.format("//a[@href='%s']", coreJavaLink)));
        coreJava.click();

//        WebElement leftMenu = driver.findElement(By.xpath("//div[@class='ocs-trigger ocs-toggle ocs-toggle-abc']"));
        assertNotNull(driver.findElement(By.xpath("//div[@id='ocs-abc']")),
                "Left menu doesn't exist");

        assertNotNull(driver.findElement(By.xpath("//div[@id='nav-links']")),
                "Top nav bar doesn't exist");

        WebElement themeSwitch = driver.findElement(By.xpath("//li[@id='menu-item-17363']/div"));
        assertNotNull(themeSwitch, "Theme switch doesn't exist");

        themeSwitch.click();
        assertNotNull(driver.findElement(By.xpath("//li[@id='menu-item-17363']/div[@class='wpnm-button style-2']")), "Theme switch button doesn't work");
    }

    public static List<String> dataProvider(){
        
        String strings1 = "https://howtodoinjava.com/java/basics/java-tutorial/";
        String strings2 = "https://howtodoinjava.com/java14/java14-new-features/";
        String strings3 = "https://howtodoinjava.com/java11/features-enhancements/";
        String strings4 = "https://howtodoinjava.com/java9/java9-new-features-enhancements/";
        String strings5 = "https://howtodoinjava.com/java7/java-7-changes-features-and-enhancements/";

        return List.of(strings1, strings2, strings3, strings4, strings5);
    }
}
