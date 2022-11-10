package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    private lateinit var mainActivityRule: ActivityTestRule<MainActivity>

    private val server = MockWebServer()
    private lateinit var okHttp3IdlingResource: OkHttp3IdlingResource
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun before() {
        mainActivityRule = ActivityTestRule( MainActivity::class.java)

        val client = OkHttpClient()
        okHttp3IdlingResource = OkHttp3IdlingResource.create(
            "okhttp",
            client
        )
        IdlingRegistry.getInstance().register(
            okHttp3IdlingResource
        )
        server.start(serverPort)
    }

    @After
    fun after() {
        server.shutdown()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {

        //server.url("/users")
//        server.start(serverPort)
        val scenario = launchActivity<MainActivity>()

        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return successResponse
                /*return when (request.path) {
                        "/users" -> successResponse
                        else -> errorResponse
                    }*/
            }
        }
        onView(withId(ID_PROGRESS))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        //onView(withId(ID_RECYCLER))
        //    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        scenario.close()
/*
        launchActivity<MainActivity>().apply {
             TODO("validate if list displays items returned by server")
            onView(withId(ID_PROGRESS))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
            val expectedUserName = "@eduardo.santos"
            moveToState(Lifecycle.State.RESUMED)
            RecyclerViewMatchers.checkRecyclerViewItem(ID_RECYCLER, 0, withText("Eduardo Santos"))
            onView(withText(expectedUserName)).check(matches(isDisplayed()))
        }

        server.close()*/
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