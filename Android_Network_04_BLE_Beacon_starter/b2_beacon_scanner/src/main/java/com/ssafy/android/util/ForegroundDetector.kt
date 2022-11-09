package com.ssafy.android.util

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

// 현재 Application이 Foreground에 있는지 감지한다.(Activity중 하나라도 Foreground에 있는지)
class ForegroundDetector(application: Application) : ActivityLifecycleCallbacks {
    internal enum class State {
        None, Foreground, Background
    }

    companion object {
        lateinit var instance: ForegroundDetector
    }

    private lateinit var application: Application
    private lateinit var state: State
    private var isChangingConfigurations = false
    private var running = 0

    private lateinit var listener: Listener

    init {
        instance = this
        application.registerActivityLifecycleCallbacks(this)
    }

    interface Listener {
        fun onBecameForeground()
        fun onBecameBackground()
    }

    fun unregisterCallbacks() {
        application.unregisterActivityLifecycleCallbacks(this)
    }

    fun addListener(listener: Listener) {
        state = State.None
        this.listener = listener
    }

    fun removeListener() {
        state = State.None
    }

    val isBackground: Boolean
        get() = state == State.Background
    val isForeground: Boolean
        get() = state == State.Foreground

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        if (++running == 1 && !isChangingConfigurations) {
            state = State.Foreground
            listener.onBecameForeground()
        }
    }

    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {
        isChangingConfigurations = activity.isChangingConfigurations
        if (--running == 0 && !isChangingConfigurations) {
            state = State.Background
            listener.onBecameBackground()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}


}