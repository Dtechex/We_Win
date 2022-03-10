package com.co.wewin.views.postLoginFragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.co.wewin.databinding.FragmentCommonProfileUpdateBinding

import android.content.Intent

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UpdateProfileCommonResponse
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.postLogin.CommonProfileUpdateViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.co.wewin.R
import com.co.wewin.model.requests.ChangeNickNameRequest
import com.co.wewin.model.requests.ChangePasswordRequest
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.views.PostLoginActivity
import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream


class CommonProfileUpdateFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentCommonProfileUpdateBinding? = null
    val binding get() = _binding!!
    lateinit var commonProfileUpdateViewModel: CommonProfileUpdateViewModel
    lateinit var photoResultLauncher:ActivityResultLauncher<Intent>
    lateinit var file:File
    private var type=""
    private val _requestGalleryPhoto = 101
    private val TAG = "CommonProfileUpdateFrag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         photoResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                setImageToImageView(result.data!!.data!!)
            }else {
                ToastUtils.showShortToast("You haven't picked Image")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommonProfileUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        binding.floatingActionButton.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
    }

    private fun init() {
        val bundle = this.arguments
        if (bundle != null) {
            type = bundle.getString("type","")
        }
        if (type!=""){
            when(type){
                getString(R.string.picture)->{
                    binding.editTextTextPersonName.visibility=View.INVISIBLE
                    binding.editTextTextPersonName2.visibility=View.INVISIBLE
                    binding.button5.setText("Change Avatar")
                }
                getString(R.string.nickName)->{
                    binding.imageView9.visibility=View.INVISIBLE
                    binding.floatingActionButton.visibility=View.INVISIBLE
                    binding.editTextTextPersonName2.visibility=View.INVISIBLE
                    binding.editTextTextPersonName.hint="Enter your Name Here"
                    binding.button5.setText("Change NickName")


                }
                getString(R.string.password)->{
                    binding.imageView9.visibility=View.INVISIBLE
                    binding.floatingActionButton.visibility=View.INVISIBLE
                    binding.editTextTextPersonName.hint="Old Password"
                    binding.editTextTextPersonName.inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.editTextTextPersonName.transformationMethod=PasswordTransformationMethod.getInstance()
                    binding.button5.text = "Change Password"


                }
            }
        }
    }

    private fun setImageToImageView(data:Uri) {
        try {
            val imageUri: Uri = data
            val imageStream: InputStream? = activity?.contentResolver?.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)

            val picturePath = getPath(requireActivity().applicationContext, imageUri)
            file=File(picturePath)

            Log.e(TAG, "setImageToImageView: "+picturePath )

            binding.imageView9.setImageBitmap(selectedImage)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            ToastUtils.showShortToast("Something went wrong")
        }
    }


    private fun setViewModel() {
        commonProfileUpdateViewModel= ViewModelProvider(this, WeWinViewModelFactory()).get(CommonProfileUpdateViewModel::class.java)
        commonProfileUpdateViewModel.upoadProfilePictureResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessProfileUpdate(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        commonProfileUpdateViewModel.updateNickNameResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessProfileUpdate(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        commonProfileUpdateViewModel.updatePasswordResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessProfileUpdate(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })


    }

    private fun onSuccessProfileUpdate(response: BaseResponse<UpdateProfileCommonResponse?>) {
        ToastUtils.showShortToast(""+response.message)
        NavigationUtils.popBackStack(activity as PostLoginActivity)
    }

    private fun checkPermissionAndOpenGallery(activity: Activity) {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                _requestGalleryPhoto
            )
            Toast.makeText(
                activity,
                "Please provide Storage Permission to proceed",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            openGalleryForImage()
        }
    }

    fun getPath(context: Context, uri: Uri?): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri!!, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    private fun openGalleryForImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        photoPickerIntent.type = "image/*"
        photoResultLauncher.launch(photoPickerIntent)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.floatingActionButton->{
                checkPermissionAndOpenGallery(activity as PostLoginActivity)
            }
            binding.button5->{
                if (type!=""){
                    when(type){
                        getString(R.string.picture)->{
                            val filePart: MultipartBody.Part = createFormData(
                                "files",
                                file.name,
                                file.asRequestBody("image/*".toMediaTypeOrNull())
                            )
                            commonProfileUpdateViewModel.callDashboardGameListResult(filePart)
                        }
                        getString(R.string.nickName)->{
                            if (binding.editTextTextPersonName.equals("")){
                                ToastUtils.showShortToast("Please Enter Name First")
                            }else{
                                commonProfileUpdateViewModel.callChangeNickName(
                                    ChangeNickNameRequest(
                                        ""+binding.editTextTextPersonName.text
                                    ))

                            }


                        }
                        getString(R.string.password)->{
                            if (binding.editTextTextPersonName.equals("") || binding.editTextTextPersonName2.equals("")){
                                ToastUtils.showShortToast("Fields Cannot Be Empty")
                            } else{
                                commonProfileUpdateViewModel.callUpdatePassword(
                                    ChangePasswordRequest(
                                        ""+binding.editTextTextPersonName.text,
                                        ""+binding.editTextTextPersonName2.text
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}