package sinno.seacs.mvvmappdemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sinno.seacs.mvvmappdemo.R
import sinno.seacs.mvvmappdemo.databinding.ActivityAddNoteBinding
import sinno.seacs.mvvmappdemo.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {

        binding.signinFabBack.setOnClickListener {

        }

        binding.signinButton.setOnClickListener {

        }

        binding.signinTvForgotpasswd.setOnClickListener {

        }

        binding.signinTvSignup.setOnClickListener {

        }
    }
}