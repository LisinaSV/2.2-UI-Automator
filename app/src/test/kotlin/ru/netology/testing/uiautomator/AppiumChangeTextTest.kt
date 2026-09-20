package ru.netology.testing.uiautomator

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

class AppiumChangeTextTest {

    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait

    private val inputId = "userInput"
    private val buttonId = "buttonChange"

    @BeforeEach
    fun setUp() {
        val options = UiAutomator2Options()
            .setDeviceName("emulator-5554")
            .setAppPackage("ru.netology.testing.uiautomator")
            .setAppActivity("ru.netology.testing.uiautomator.MainActivity")
            .setNoReset(true)
            .setNewCommandTimeout(Duration.ofSeconds(60))

        println("Запуск драйвера... Подключение к Appium на http://127.0.0.1:4723")

        driver = AndroidDriver(URL("http://127.0.0.1:4723"), options)
        wait = WebDriverWait(driver, Duration.ofSeconds(60))

        println("Драйвер успешно создан!")
    }

    @AfterEach
    fun tearDown() {
        println("Завершение сессии...")
        driver.quit()
    }

    @Test
    fun testEmptyString() {
        println("Поиск поля ввода с ID: \$inputId")
        val inputField: WebElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id(inputId))
        )

        println("Поле ввода найдено. Очистка и ввод пустой строки.")
        inputField.clear()
        inputField.sendKeys("")

        println("Поиск кнопки с ID: \$buttonId")
        val button: WebElement = wait.until(
            ExpectedConditions.elementToBeClickable(By.id(buttonId))
        )

        println("Кнопка найдена. Проверка текста (игнорируя регистр)...")

        val expectedText = "Change Text".lowercase()
        val actualText = button.text.lowercase()

        assertEquals(expectedText, actualText) { "Ожидался текст 'Change Text', но получен: '\${button.text}'" }

        println("Тест testEmptyString пройден!")
    }

    @Test
    fun testNewActivity() {
        println("Запуск теста testNewActivity...")
        val inputField: WebElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id(inputId))
        )
        val button: WebElement = wait.until(
            ExpectedConditions.elementToBeClickable(By.id(buttonId))
        )

        inputField.sendKeys("Hello Netology")
        println("Клик по кнопке...")
        button.click()

        println("Тест testNewActivity пройден!")
    }
}
