package com.example.kneethy2.ui.home

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kneethy2.R
import com.example.kneethy2.databinding.FragmentHomeBinding
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import java.io.File
import java.lang.reflect.MalformedParameterizedTypeException

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val button: MaterialButton = root.findViewById(R.id.save_button)
        button.setOnClickListener {
            Log.d("BUTTON","Button Clicked")
            val newDiaryEntry: EditText = root.findViewById(R.id.editTextTextMultiLine)
            val txt: String = newDiaryEntry.getText().toString()
            Log.d("TXT", txt)
            saveMyText(root)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun saveMyText(v: View) {
        val filename = "kneethy-entries.json"
//        val appSpecificExternalDir = File(context?.getExternalFilesDir(null), filename)
//        val pastNotes = appSpecificExternalDir.readText()
    }


    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}