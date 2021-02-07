package ppjh.mvvm.fek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

class SecondFragment : Fragment(R.layout.fragment_second) {

    val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setFragmentResultListener("requestKey") {
                resultKey, bundle ->
            val result = bundle.getString("bundleKey", "")
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        }

        val button2 = view.findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to "result"))
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }
}