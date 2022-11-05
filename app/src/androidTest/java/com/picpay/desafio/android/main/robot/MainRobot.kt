package com.picpay.desafio.android.main.robot

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.MainActivity
import com.picpay.desafio.android.MainActivityTest
import com.picpay.desafio.android.R
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MainRobot {
    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }


    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)

        launchActivity<MainActivity>().apply {
            // TODO("validate if list displays items returned by server")
            val expectedUserName = "@eduardo.santos"
            server.url("/users")

            onView(withText(expectedUserName)).check(matches(isDisplayed()))
        }

        server.close()
    }
/*    fun checkTitle() = onView(withText(R.string.title)).check(matches(isDisplayed()))

    fun checkRecycler() = onView(withId(ID_RECYCLER)).check(matches(isDisplayed()))

    fun checkProgress() = onView(withId(ID_PROGRESS)).check(matches(isDisplayed()))*/

    companion object {
        val ID_RECYCLER = R.id.recyclerView
        val ID_PROGRESS = R.id.user_list_progress_bar
        private const val serverPort = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}
