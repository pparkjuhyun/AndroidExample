package ppjh.mvvm.fek

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment(R.layout.fragment_first) {

//    val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)

        setFragmentResultListener("requestKey") {
                resultKey, bundle ->
            val result = bundle.getString("bundleKey", "")
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener {
//            mainViewModel.data = "hello"
            setFragmentResult("requestKey", bundleOf("bundleKey" to "result"))
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}