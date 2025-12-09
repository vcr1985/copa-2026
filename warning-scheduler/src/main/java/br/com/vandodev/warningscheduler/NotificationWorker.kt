package br.com.vandodev.warningscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val matchName = inputData.getString(KEY_MATCH_NAME) ?: return Result.failure()
        val teamA = inputData.getString(KEY_TEAM_A) ?: return Result.failure()
        val teamB = inputData.getString(KEY_TEAM_B) ?: return Result.failure()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Match Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(matchName)
            .setContentText("A partida entre $teamA e $teamB está prestes a começar!")
            // You should add a small icon for the notification
            // .setSmallIcon(R.drawable.ic_notification)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        return Result.success()
    }

    companion object {
        const val KEY_MATCH_NAME = "KEY_MATCH_NAME"
        const val KEY_TEAM_A = "KEY_TEAM_A"
        const val KEY_TEAM_B = "KEY_TEAM_B"
        private const val CHANNEL_ID = "copa-2022-notifications"
    }
}