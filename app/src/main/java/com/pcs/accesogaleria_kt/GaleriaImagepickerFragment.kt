package com.pcs.accesogaleria_kt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getError


class GaleriaImagepickerFragment : Fragment() {

    private lateinit var ivFoto: ImageView
    private lateinit var btnSeleccionar: Button

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_galeria_imagepicker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivFoto = view.findViewById(R.id.iv_foto)
        btnSeleccionar = view.findViewById(R.id.bt_seleccionar)

        btnSeleccionar.setOnClickListener {
            if(!flagsControl){
                flagsControl = true

                /*ImagePicker.with(this)
                    .galleryOnly()
                    .start()*/

                ImagePicker.with(this)
                    .galleryOnly()
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }

                /*ImagePicker.with(this)
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }*/
            }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                imageUri = fileUri
                ivFoto.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tarea cancelada", Toast.LENGTH_SHORT).show()
            }

            flagsControl = false
        }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            ivFoto.setImageURI(imageUri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Tarea cancelada", Toast.LENGTH_SHORT).show()
        }

        flagsControl = false
    }*/

    companion object {
        private var flagsControl = false

        @JvmStatic
        fun newInstance() = GaleriaImagepickerFragment()
    }
}