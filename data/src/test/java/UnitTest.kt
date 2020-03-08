import com.google.gson.Gson
import com.xinh.data.rx.NetworkHandler
import com.xinh.data.rx.RxAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {
    private val mockServer = MockWebServer()
    private var retrofit: Retrofit = getRetrofit()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getHttpUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxAdapterFactory(mockNetworkHandler()))
            .client(getOkHttpClient())
            .build()
    }

    private fun getHttpUrl(): HttpUrl{
        return mockServer.url("/")
    }

    private fun mockNetworkHandler(): NetworkHandler {
        return object : NetworkHandler {
            override fun isConnected(): Boolean {
                return true
            }
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()

            .addInterceptor(dynamicUrlInterceptor())

            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()
    }

    private fun dynamicUrlInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val builder = request.newBuilder().url(getHttpUrl())
            return chain.proceed(builder.build())
        }
    }

    protected fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun mockResponse(fileName: String, responseCode: Int = HttpURLConnection.HTTP_OK) {
        mockResponseFromJson(getJson(fileName), responseCode)
    }

    private fun mockResponseFromJson(json: String, responseCode: Int = HttpURLConnection.HTTP_OK) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(json)
        )

    inline fun <reified T> convertFrom(json: String): T {
        return Gson().fromJson(json, T::class.java)
    }

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}