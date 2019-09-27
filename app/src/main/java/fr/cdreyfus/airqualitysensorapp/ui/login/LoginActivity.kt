package fr.cdreyfus.airqualitysensorapp.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import fr.cdreyfus.airqualitysensorapp.R
import fr.cdreyfus.airqualitysensorapp.model.User
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            loginViewModel.login(io_key_edittext.text.toString())
        }

        val userObserver = Observer<User?> { user ->
            Toast.makeText(
                this,
                user?.let { "Welcome ${user.username}" } ?: "Something went wrong...",
                Toast.LENGTH_SHORT).show()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        loginViewModel.user.observe(this, userObserver)

    }
}
