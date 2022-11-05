package com.picpay.desafio.android.main

import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.MainActivity
import com.picpay.desafio.android.main.robot.MainRobot
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainTest {
    private val robot = MainRobot()
    private val server = MockWebServer()

    //@get:Rule
    //var mainActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule( MainActivity::class.java)

    @Test
    fun viewTest() {
        /*robot.checkTitle()
        robot.checkProgress()
        robot.checkRecycler()*/
    }
}