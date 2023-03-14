package com.example.omega1.ui.other

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.omega1.AuthActivity
import com.example.omega1.databinding.FragmentEyeFeatureBinding


class EyeFeature : Fragment() {
    private var _binding: FragmentEyeFeatureBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = EyeFeature()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEyeFeatureBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.button.setOnClickListener(){
            startActivity(Intent(context,AuthActivity::class.java))
        }
        return root
    }

}