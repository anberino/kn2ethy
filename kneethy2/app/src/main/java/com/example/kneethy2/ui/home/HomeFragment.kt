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
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.lang.reflect.MalformedParameterizedTypeException
import java.nio.charset.Charset

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var storage = Firebase.storage("gs://kneethy2.appspot.com")

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

        val journal: String = loadDiaryFromCloud()

        val button: MaterialButton = root.findViewById(R.id.save_button)
        button.setOnClickListener {
            Log.d("BUTTON","Button Clicked")
            saveMyText(root)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveMyText(v: View) {
        val newDiaryEntry: EditText = v.findViewById(R.id.editTextTextMultiLine)
        val txt: String = newDiaryEntry.text.toString()
        Log.d("TXT", txt)

    }

    private fun loadDiaryFromCloud() : String {
        val storageRef = storage.reference
        val curDiaryRef = storageRef.child("diary.txt")
        var diary = ""
        curDiaryRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
            diary = it.toString(Charset.defaultCharset())
            Log.d("SUCCESS", diary)
        }.addOnFailureListener {
            Log.d("FAILURE", "cocô")
        }
        return diary
    }
}