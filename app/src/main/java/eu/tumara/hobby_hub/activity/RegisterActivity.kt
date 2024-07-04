package eu.tumara.hobby_hub.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yalantis.ucrop.UCrop
import eu.tumara.hobby_hub.R
import eu.tumara.hobby_hub.fragment.AdaptiveBottomSheetFragment
import eu.tumara.hobby_hub.model.AdaptiveBottomSheetItem
import eu.tumara.hobby_hub.model.AdaptiveBottomSheetItemAction
import java.io.File
import java.util.UUID


@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var btnBack: LinearLayout
    private lateinit var imgProfile: ImageView

    private var isDefaultImageProfile = true

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_IMAGE_PICK = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    private fun init() {
        btnBack = findViewById(R.id.btnBack)
        imgProfile = findViewById(R.id.imgProfile)

        btnBack.setOnClickListener {
            finish()
        }
        imgProfile.setOnClickListener {
            selectProfileImage()
        }
    }

    private fun selectProfileImage() {
        val list: MutableList<AdaptiveBottomSheetItem> = mutableListOf(
            AdaptiveBottomSheetItem(
                "Camera",
                R.drawable.baseline_camera,
                object : AdaptiveBottomSheetItemAction {
                    override fun onClick() {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
                    }
                }
            ),
            AdaptiveBottomSheetItem(
                "Gallery",
                R.drawable.baseline_photo_library,
                object : AdaptiveBottomSheetItemAction {
                    override fun onClick() {
                        val pickPhoto = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK)
                    }
                }
            )
        )
        if (!isDefaultImageProfile) list +=
            AdaptiveBottomSheetItem(
                "Clear",
                R.drawable.baseline_delete,
                object : AdaptiveBottomSheetItemAction {
                    override fun onClick() {
                        imgProfile.setImageResource(R.drawable.profile_picture)
                        isDefaultImageProfile = true
                    }
                }
            )

        AdaptiveBottomSheetFragment(list).show(
            supportFragmentManager,
            "AdaptiveBottomSheetFragment"
        )
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val bitmap = data?.extras?.get("data") as Bitmap
                val image = File(cacheDir, UUID.randomUUID().toString() + ".jpg")
                val destUri = StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, image.outputStream())
                UCrop.of(image.toUri(), Uri.fromFile(File(cacheDir, destUri)))
                    .start(this)
            }

            REQUEST_IMAGE_PICK -> {
                val imageUri = data?.data ?: return
                val destUri = StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()
                UCrop.of(imageUri, Uri.fromFile(File(cacheDir, destUri)))
                    .start(this)
            }

            UCrop.REQUEST_CROP -> {
                imgProfile.setImageURI(UCrop.getOutput(data!!))
                isDefaultImageProfile = false
            }
        }
    }
}