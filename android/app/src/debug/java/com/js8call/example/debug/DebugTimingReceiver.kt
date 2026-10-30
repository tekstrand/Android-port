package com.js8call.example.debug

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.js8call.example.service.JS8EngineService

/** Forwards adb timing suggestion injections to [JS8EngineService]. */
class DebugTimingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val forward = Intent(context, JS8EngineService::class.java).apply {
            action = JS8EngineService.ACTION_DEBUG_INJECT_TIMING
            intent.extras?.let { putExtras(it) }
        }
        try {
            context.startService(forward)
        } catch (e: IllegalStateException) {
            // Refused from the background unless the engine is running
            Log.w(TAG, "Injected timing suggestion dropped: bring the app to the front")
        }
    }

    companion object {
        private const val TAG = "DebugTimingReceiver"
    }
}
