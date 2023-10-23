package nl.kjuba.helloworld

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.kjuba.helloworld.databinding.ActivityMainBinding
import nl.kjuba.helloworld.service.MyListener
import nl.kjuba.helloworld.service.NotificationService


class MainActivity : AppCompatActivity(), MyListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("KUBA", "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
        NotificationService().setListener(this)
        configureNotifications()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun configureButtons() {
        // generate button
        binding.button.setOnClickListener(View.OnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val mBuilder = NotificationCompat.Builder(this)
            mBuilder.setContentTitle("My Notification")
            mBuilder.setContentText("Notification Listener Service Example")
            mBuilder.setTicker("Notification Listener Service Example")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setAutoCancel(true)
            val notificationChannel = NotificationChannel(
                "NOTIFICATION_CHANNEL_ID",
                "NOTIFICATION_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_HIGH
            )
            mBuilder.setChannelId("NOTIFICATION_CHANNEL_ID")
            manager.createNotificationChannel(notificationChannel)
            manager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
        })
        // configure button
        binding.button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        })
    }

    private fun configureNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "NOTIFICATION_CHANNEL_ID"
            val channelName = "NOTIFICATION_CHANNEL_NAME"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun setValue(packageName: String?) {
        Log.e("KUBA", "setValue")
        binding.textView.append("\n" + packageName)
    }
}