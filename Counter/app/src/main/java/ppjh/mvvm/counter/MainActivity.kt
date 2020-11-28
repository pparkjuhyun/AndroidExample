package ppjh.mvvm.counter

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    val viewModel by viewModels<MainViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: ")
        tv_counter.text = "${viewModel.count}"
        
        btn_add.setOnClickListener {
            viewModel.count++
            tv_counter.text = "${viewModel.count}"
        }

        btn_sub.setOnClickListener {
            viewModel.count--
            tv_counter.text = "${viewModel.count}"
        }

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                Log.d(TAG, "onActivityPaused: ")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "onActivityStarted: ")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(TAG, "onActivityDestroyed: ")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d(TAG, "onActivitySaveInstanceState: ")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d(TAG, "onActivityStopped: ")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "onActivityCreated: ")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d(TAG, "onActivityResumed: ")
            }

        })
    }
}