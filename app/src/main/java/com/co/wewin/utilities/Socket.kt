package com.co.wewin.utilities

import android.util.Log
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.utilities.network.NetworkConstants
import io.socket.client.IO
import io.socket.engineio.client.transports.WebSocket
import java.net.URI
import java.net.URISyntaxException

object Socket {
    private val TAG=Socket::class.java.simpleName
    private const val SOCKET_URL=NetworkConstants.BASE_URL
    private val TRANSPORT = arrayOf(
        WebSocket.NAME
    )

    var instance:io.socket.client.Socket?=null
        get(){
            if (field==null){
                try {
                    val options: IO.Options=IO.Options()
                    options.transports= TRANSPORT
                    options.forceNew=true
                    field=IO.socket(URI.create(SOCKET_URL),options)
                }catch (e:URISyntaxException){
                    Log.e(TAG, e.toString() )
                }
            }
            return field
        }
    private set
}