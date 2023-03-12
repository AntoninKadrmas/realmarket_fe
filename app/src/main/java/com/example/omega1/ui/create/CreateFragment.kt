package com.example.omega1.ui.create
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omega1.R
import com.example.omega1.databinding.FragmentCreateBinding
import com.example.omega1.ui.create.image.ImageAdapter
import kotlinx.android.synthetic.main.create_image.view.*
import java.io.File


class CreateFragment : Fragment() {

    private lateinit var imageAdapter: ImageAdapter
    private val maxImage = 5
    private var actualImage = 0
    private var pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxImage-actualImage)) { uris ->
            handleOutput(uris)
        }
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val createView =
//            ViewModelProvider(this).get(CreateViewModel::class.java)
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        imageAdapter = ImageAdapter(mutableListOf(), clickDelete = {
            removeUri:Uri->imageClickDelete(removeUri)
        },clickAdd={
            addUri:Uri->imageClickAdd(addUri)
        })
        binding.recyclerView.adapter = imageAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(FragmentActivity(),LinearLayoutManager.HORIZONTAL,false)
        imageAdapter.addNewImage(Uri.parse("android.resource://com.example.omega1/mipmap/camera_white"))
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun imageClickDelete(uri:Uri){
        actualImage--
        val indexOfImage = imageAdapter.positionOfImage(uri)
        imageAdapter.removeNewImage(uri)
        imageAdapter.notifyItemRemoved(indexOfImage)
        binding.imageCounter.setText("$actualImage/$maxImage")
        Log.i("PhotoPicker","$actualImage $indexOfImage")
        if(indexOfImage==1&&actualImage>0)(binding.recyclerView.findViewHolderForLayoutPosition(2) as ImageAdapter.ImageAdapterHolder).itemView.cover_image.visibility = View.VISIBLE
    }
    private fun imageClickAdd(uri:Uri){
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        Log.d("PhotoPicker", "No media selected")
    }
    private fun handleOutput(uris:List<Uri>){
        if (uris.isNotEmpty()) {
            Log.i("PhotoPicker", "Number of items selected: ${uris.size}")
            var actualMaximum:Int = maxImage-actualImage
            Log.i("PhotoPicker","actual_maximum $actualMaximum, actual image $actualImage")
            if(uris.size<=actualMaximum)actualMaximum = uris.size
            else Toast.makeText(context,"You can use just 5 photos",Toast.LENGTH_SHORT).show()
            actualImage += if(uris.size<=actualMaximum) uris.size else actualMaximum
            actualMaximum--
            for(value in 0..actualMaximum){
                imageAdapter.addNewImage(uris[value])
                Log.i("PhotoPicker",uris[value].toString())
            }
            if(++actualMaximum>0){
                imageAdapter.notifyDataSetChanged()
                binding.imageCounter.setText("$actualImage/$maxImage")
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }

    }
}