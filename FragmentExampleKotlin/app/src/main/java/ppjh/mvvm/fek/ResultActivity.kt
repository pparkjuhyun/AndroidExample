package ppjh.mvvm.fek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent()
            intent.putExtra("data", "hello")
            setResult(RESULT_OK, intent)
        }
    }
}