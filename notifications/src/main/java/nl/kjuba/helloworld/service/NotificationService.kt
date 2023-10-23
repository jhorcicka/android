package nl.kjuba.helloworld.service

import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log


class NotificationService : NotificationListenerService() {
    private val TAG = this.javaClass.simpleName
    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        Log.e("KUBA", "onCreate2")
        context = applicationContext
    }

    override fun onNotificationPosted(statusBarNotification: StatusBarNotification) {
        Log.e("KUBA", "onPosted")
        Log.i(TAG, "********** onNotificationPosted")
        Log.i(
            TAG,
            "ID :" + statusBarNotification.id + " \t "
                    + statusBarNotification.notification.tickerText
                    + " \t " + statusBarNotification.packageName
        )
        myListener?.setValue("Post: " + statusBarNotification.packageName)
    }

    override fun onNotificationRemoved(statusBarNotification: StatusBarNotification) {
        Log.e("KUBA", "onRemoved")
        Log.i(TAG, "********** onNotificationRemoved")
        Log.i(
            TAG,
            "ID :" + statusBarNotification.id + " \t "
                    + statusBarNotification.notification.tickerText
                    + " \t " + statusBarNotification.packageName
        )
        myListener?.setValue("Remove: " + statusBarNotification.packageName)
    }

    fun setListener(myListener: MyListener?) {
        Log.e("KUBA", "setListener")
        Companion.myListener = myListener
    }

    companion object {
        var myListener: MyListener? = null
    }
}