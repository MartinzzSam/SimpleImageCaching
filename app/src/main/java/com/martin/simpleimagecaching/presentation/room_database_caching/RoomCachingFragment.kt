package com.martin.simpleimagecaching.presentation.room_database_caching

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.martin.simpleimagecaching.R
import com.martin.simpleimagecaching.databinding.FragmentRoomCachingBinding
import com.martin.simpleimagecaching.presentation.room_database_caching.component.RoomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RoomCachingFragment : Fragment() {

    private var _binding: FragmentRoomCachingBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { RoomAdapter() }
    private val viewModel : RoomViewModel by viewModels()
    var imageBitmap : Bitmap? = null
    val IMAGE_REQUEST_CODE = 2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomCachingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEntry.adapter = adapter



        binding.buttonAdd.setOnClickListener {
            openGallery()

        }

        // Listen to delete Click from recycler view and delete the specific entry
        lifecycleScope.launchWhenStarted{
            adapter.clickedEvent.collectLatest { entry ->
                viewModel.onEvent(RoomCachingEvent.DeleteEntry(entry))
                viewModel.readEntries.collect() {
                    adapter.setData(it)
                }
            }

        }


        binding.buttonNav.setOnClickListener {

            findNavController().navigate(R.id.action_roomCachingFragment_to_internalStorageCachingFragment)

        }


        lifecycleScope.launch {
            viewModel.readEntries.collect { entry ->
                adapter.setData(entry)
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            val contentResolver = context?.contentResolver
            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            viewModel.onEvent(RoomCachingEvent.AddImage(imageBitmap!!))


        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent , IMAGE_REQUEST_CODE)

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}