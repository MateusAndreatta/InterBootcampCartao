package com.mateusandreatta.cartaofidelidade.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mateusandreatta.cartaofidelidade.App
import com.mateusandreatta.cartaofidelidade.R
import com.mateusandreatta.cartaofidelidade.adapters.ColorsAdapter
import com.mateusandreatta.cartaofidelidade.data.FidelityCard
import com.mateusandreatta.cartaofidelidade.databinding.ActivityAddBinding
import com.mateusandreatta.cartaofidelidade.databinding.CardFidelityBinding
import com.mateusandreatta.cartaofidelidade.util.TextWatcherCardPreview
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException


class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    var selectedColor: String = "#EAEAEA"
    var selectedImageBytes: ByteArray? = null
    var editMode = false
    var fidelityCardOld: FidelityCard? = null

    var resultLauncher = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data

            if(selectedImageUri != null){

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    cardPreview.imageViewLogo.visibility = View.VISIBLE
                    cardPreview.imageViewLogo.setImageBitmap(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    selectedImageBytes = stream.toByteArray()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
    }

    private lateinit var cardPreview: CardFidelityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cardPreview = binding.cardPreview

        setupListeners()

        val colors = resources.getStringArray(R.array.colors)
        binding.rvColors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvColors.adapter = ColorsAdapter(colors) { item ->
            selectedColor = item
            updateCardPreviewColor(item)
        }

        fidelityCardOld = intent.getSerializableExtra("card") as? FidelityCard
        if(fidelityCardOld != null)
            populateView(fidelityCardOld)
    }

    private fun updateCardPreviewColor(color: String){
        cardPreview.fidelityCardView.setCardBackgroundColor(Color.parseColor(color))
    }

    private fun setupListeners(){

        binding.buttonImage.setOnClickListener {
            getImageFromGallery()
        }

        binding.buttonAdd.setOnClickListener {

            if(binding.editTextClientName.editText?.text.toString().isBlank() || binding.editTextCompanyName.editText?.text.toString().isBlank() ||
                    binding.editTextDescription.editText?.text.toString().isBlank() || selectedColor == null){
                Toast.makeText(this,"Preencha todos os campos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if( editMode ){
                fidelityCardOld?.apply {
                    companyName = binding.editTextCompanyName.editText?.text.toString()
                    clientName = binding.editTextClientName.editText?.text.toString()
                    description = binding.editTextDescription.editText?.text.toString()
                    background = selectedColor
                    companyImage = selectedImageBytes
                }
                fidelityCardOld?.let { card -> mainViewModel.update(card) }
                Toast.makeText(this,"Editado com sucesso!",Toast.LENGTH_SHORT).show()
            }else{
                val fidelityCard = FidelityCard (
                    companyName = binding.editTextCompanyName.editText?.text.toString(),
                    clientName = binding.editTextClientName.editText?.text.toString(),
                    description = binding.editTextDescription.editText?.text.toString(),
                    background = selectedColor,
                    companyImage = selectedImageBytes
                )
                mainViewModel.insert(fidelityCard)
                Toast.makeText(this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        binding.editTextCompanyNameInput.addTextChangedListener(TextWatcherCardPreview(cardPreview.tvCompany))
        binding.editTextClientNameInput.addTextChangedListener(TextWatcherCardPreview(cardPreview.tvClient))
        binding.editTextDescriptionInput.addTextChangedListener(TextWatcherCardPreview(cardPreview.tvDescription))
    }

    private fun getImageFromGallery(){
        ImagePicker.with(this)
            .galleryOnly()
            .cropSquare()
            .compress(512)
            .maxResultSize(270, 270)
            .createIntent { intent ->
                resultLauncher.launch(intent)
            }
    }

    private fun populateView(fidelityCard: FidelityCard?){
        editMode = true
        binding.editTextCompanyName.editText?.setText(fidelityCard?.companyName)
        binding.editTextClientName.editText?.setText(fidelityCard?.clientName)
        binding.editTextDescription.editText?.setText(fidelityCard?.description)
        fidelityCard?.background?.let {
            selectedColor = it
            updateCardPreviewColor(it)
        }

        if(fidelityCard?.companyImage != null){
            selectedImageBytes = fidelityCard.companyImage
            val bmp = BitmapFactory.decodeByteArray(fidelityCard.companyImage, 0, fidelityCard.companyImage!!.size)
            cardPreview.imageViewLogo.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp,
                    bmp.width,
                    bmp.height,
                    false
                )
            )
            cardPreview.imageViewLogo.visibility = View.VISIBLE
        }
    }

}