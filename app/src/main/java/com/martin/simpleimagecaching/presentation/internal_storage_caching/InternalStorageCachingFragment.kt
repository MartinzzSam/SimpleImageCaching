package com.martin.simpleimagecaching.presentation.internal_storage_caching

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.martin.simpleimagecaching.databinding.FragmentInternalStorageCachingBinding
import com.martin.simpleimagecaching.presentation.internal_storage_caching.components.InternalStorageCachingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InternalStorageCachingFragment : Fragment() {

    private var _binding: FragmentInternalStorageCachingBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { InternalStorageCachingAdapter() }
    val viewModel: InternalStorageCachingViewModel by viewModels()
    val IMAGE_REQUEST_CODE = 2
    var getItemJob: Job? = null
    var imageBitmap: Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInternalStorageCachingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            getItem()
        }

        lifecycleScope.launchWhenStarted {
            adapter.clickedEvent.collect { file ->
                viewModel.onEvent(InternalStorageEvent.DeleteFile(file))
                viewModel.onEvent(InternalStorageEvent.RefreshData)
            }

        }
        binding.rvInternalEntry.adapter = adapter


        binding.buttonAdd.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            val getContentResolver = context?.contentResolver
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver, imageUri)
            viewModel.onEvent(
                InternalStorageEvent.AddFile(
                    System.currentTimeMillis().toString(),
                    imageBitmap!!
                )
            )
            getItem()
        }
    }

    private fun getItem() {
        getItemJob?.cancel()
        getItemJob = lifecycleScope.launch {
            viewModel.state.collect {
                adapter.setData(it)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

