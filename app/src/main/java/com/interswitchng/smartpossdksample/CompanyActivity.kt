package com.interswitchng.smartpossdksample

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.interswitchng.smartpossdksample.databinding.ActivityCompanyBinding
import com.interswitchng.smartpossdksample.utils.PrefUtils
import com.interswitchng.smartpossdksample.utils.ViewBindingProvider
import com.interswitchng.smartpossdksample.utils.setToolBarTitle

class CompanyActivity : AppCompatActivity(), ViewBindingProvider {

    private var _binding: ActivityCompanyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBarTitle(binding.toolBar, "Company Logo", this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSavedCompanyOption()
        attachViewListeners()
    }

    private fun setSavedCompanyOption() {
        setCheckMarkForOption(PrefUtils.getSavedCompanyLogoOption(this))
    }

    private fun attachViewListeners() {
        binding.run {
            layoutCompanyInterswitch.setOnClickListener { saveCompanyOption(CompanyOption.INTERSWITCH) }
            layoutCompanyGoogle.setOnClickListener { saveCompanyOption(CompanyOption.GOOGLE) }
            layoutCompanyAmazon.setOnClickListener { saveCompanyOption(CompanyOption.AMAZON) }
            layoutCompanyApple.setOnClickListener { saveCompanyOption(CompanyOption.APPLE) }
        }
    }

    private fun saveCompanyOption(option: CompanyOption) {
        clearCheckMarks()
        PrefUtils.saveCompanyLogoOption(option, this)
        setCheckMarkForOption(option)
    }

    private fun setCheckMarkForOption(option: CompanyOption) {
        when(option) {
            CompanyOption.INTERSWITCH -> binding.ivCheckMarkInterswitch.visibility = View.VISIBLE
            CompanyOption.GOOGLE -> binding.ivCheckMarkGoogle.visibility = View.VISIBLE
            CompanyOption.AMAZON -> binding.ivCheckMarkAmazon.visibility = View.VISIBLE
            CompanyOption.APPLE -> binding.ivCheckMarkApple.visibility = View.VISIBLE
            CompanyOption.NONE -> {}
        }
    }

    private fun clearCheckMarks() {
        binding.run {
            ivCheckMarkInterswitch.visibility = View.INVISIBLE
            ivCheckMarkGoogle.visibility = View.INVISIBLE
            ivCheckMarkAmazon.visibility = View.INVISIBLE
            ivCheckMarkApple.visibility = View.INVISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getRootView(): View = binding.root


}

enum class CompanyOption {
    INTERSWITCH,
    GOOGLE,
    APPLE,
    AMAZON,
    NONE
}