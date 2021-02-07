package ppjh.mvvm.fek

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment(R.layout.fragment_first) {

    //val mainViewModel by activityViewModels<MainViewModel>()
    var imageView : ImageView? = null
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageView?.setImageURI(it)
    }

    val getStartActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {activityResult ->
        activityResult.data?.let {intent ->
            intent.extras?.let {bundle ->
                Log.d("FirstFragment", "result: ${bundle.getString("data", "world")}")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)
        imageView = view.findViewById(R.id.imageView)

        setFragmentResultListener("requestKey") {
                resultKey, bundle ->
            val result = bundle.getString("bundleKey", "")
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener {
//            mainViewModel.data = "hello"
//            setFragmentResult("requestKey", bundleOf("bundleKey" to "result"))
//            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            //MIME TYPE 전달
            //getContent.launch("image/*")
            Intent(requireContext(), ResultActivity::class.java).apply {
                getStartActivityForResult.launch(this)
            }
        }
    }
}