package com.co.wewin.utilities.progressUtils

import android.app.Activity
import android.util.Log
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.network.NetworkConstants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket
import org.json.JSONObject

object SocketUtils {
    var mSocket: Socket? = null

    fun setUpSocket(activity: Activity){
        val opts = IO.Options()
        opts.transports = arrayOf(WebSocket.NAME)
        opts.upgrade = false
        mSocket = IO.socket(NetworkConstants.BASE_URL, opts)
        startSocketService(activity)
    }

    fun startSocketService(activity: Activity) {
        if(mSocket!=null){
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
            }
        }else {
            mSocket!!.connect()
        }
        setConnectEventsListeners(activity)
    }

    fun setConnectEventsListeners(activity: Activity){
        mSocket!!.on(Socket.EVENT_CONNECT, Emitter.Listener() {
            activity.runOnUiThread {
                Log.e("SocketConeect", "onConeected socketid " + mSocket!!.id())
                if (mSocket!!.id() != null) {
                    ToastUtils.showShortToast("Connected to socket")
                }
            }
        })

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR, Emitter.Listener() {
            mSocket!!.disconnect()
            activity.runOnUiThread {
                Log.e("SocketConnectError", "onConeectedError" + it[0].toString())
                if (mSocket!!.id() != null) {
                    ToastUtils.showShortToast("Socket connect error")
                }
            }
        })
    }

    fun customSocketEmit(event:String,data: JSONObject){
        if(mSocket!=null) {
            if (mSocket!!.connected()) {
                mSocket!!.emit(""+event, data)
            }
        }
    }

    fun emitSpecificEventListener(event:String):JSONObject{
        var data=JSONObject()
        mSocket!!.on(event) { args ->
             data = args[0] as JSONObject
        }
        return data
    }

    fun disconnectSocket(){
        if (mSocket!=null){
            if (mSocket!!.connected())
                mSocket!!.disconnect()
        }
    }

}