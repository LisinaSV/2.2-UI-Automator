package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 10000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

        waitForPackage(MODEL_PACKAGE)
    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
            device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
            Thread.sleep(1000)
        } else {
            throw AssertionError("Не удалось найти пакет: \$packageName")
        }
    }

    @Test
    fun testChangeText() {
        val inputText = "Hello Netology"

        val inputField = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "userInput")), TIMEOUT)
        inputField.text = inputText

        val changeButton = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "buttonChange")), TIMEOUT)
        changeButton.click()

        val resultTextObj = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")), TIMEOUT)

        assertEquals("Текст не обновился после нажатия кнопки", inputText, resultTextObj.text)
    }

    @Test
    fun testEmptyString() {
        val initialText = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")), TIMEOUT).text

        val inputField = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "userInput")), TIMEOUT)
        inputField.text = "   "

        val changeButton = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "buttonChange")), TIMEOUT)
        changeButton.click()

        val resultTextObj = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")), TIMEOUT)
        assertEquals("Текст изменился при пустом вводе (ошибка логики приложения)", initialText, resultTextObj.text)
    }

    @Test
    fun testNewActivity() {
        val inputText = "Test from UI Automator"

        val inputField = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "userInput")), TIMEOUT)
        inputField.text = inputText

        val activityButton = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "buttonActivity")), TIMEOUT)
        activityButton.click()

        val newScreenTextObj = device.wait(Until.findObject(By.res(MODEL_PACKAGE, "text")), TIMEOUT)

        assertEquals("Текст на втором экране не совпадает с введенным", inputText, newScreenTextObj.text)

        device.pressBack()
        device.wait(Until.hasObject(By.res(MODEL_PACKAGE, "userInput")), TIMEOUT)
    }
}
