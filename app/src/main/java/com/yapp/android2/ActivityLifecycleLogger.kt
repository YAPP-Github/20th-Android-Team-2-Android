package com.yapp.android2

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber
import javax.inject.Inject

class ActivityLifecycleLogger @Inject constructor() : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.d(
            "---- onCreate:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.d(
            "---- onStart:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.d(
            "---- onResume:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.d(
            "---- onPause:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.d(
            "---- onStop:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.d(
            "---- onSaveInstanceState:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.d(
            "---- onDestroy:: %s%s",
            activity::class.java.simpleName,
            "(${activity.hashCode()})"
        )
    }
}
