package hu.dobszai.bookbrowse.api

import okhttp3.OkHttpClient

class BookHttpClient : OkHttpClient() {

    companion object {

        // NOTE:

        // It is recommended practice to hide api keys,
        // since it's a demo project for educational purposes
        // I decided to leave it here for easier testing.

        // If you are going to use this code,
        // please generate a new API https://cloud.google.com/docs/authentication/api-keys

        private const val API_KEY = "AIzaSyBXLBOHVdmzM8YmdgmC-rDjum56C9jMyUs"

        fun getClient(): OkHttpClient {
            return Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                    val request = original
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }
}
