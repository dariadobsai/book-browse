package hu.dobszai.bookbrowse.utils.network

import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

/**
 * Inspired by:
 * https://github.com/mitchtabian/food2fork-compose/blob/master/app/src/main/java/com/codingwithmitch/food2forkcompose/interactors/app/DoesNetworkHaveInternet.kt
 */
object DoesNetworkHaveInternet {

    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Timber.d("Success.")
            true
        } catch (e: IOException) {
            Timber.e("No internet. $e")
            false
        }
    }
}
