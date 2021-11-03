package com.example.googlelogintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val btnLogin = findViewById<SignInButton>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            getResultText.launch(signInIntent)
        }

        val btnLoout = findViewById<AppCompatButton>(R.id.btn_logout)
        btnLoout.setOnClickListener {
            googleSignInClient.signOut()
                .addOnCompleteListener(this
                ) { Log.d("lhb1", "logout complete")}
        }

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val acct = task.getResult(ApiException::class.java)

                if (acct != null) {
                    val name = acct.displayName
                    val givenName = acct.givenName
                    val familyName = acct.familyName
                    val email = acct.email
                    val id = acct.id
                    val photoUri = acct.photoUrl

                    Log.d("lhb1", "name = $name")
                    Log.d("lhb1", "givenName = $givenName")
                    Log.d("lhb1", "familyName = $familyName")
                    Log.d("lhb1", "email = $email")
                    Log.d("lhb1", "id = $id")
                    Log.d("lhb1", "photo = $photoUri")
                    Log.d("lhb1", "serverAuthCode = ${acct.serverAuthCode}")
                    Log.d("lhb1", "zaa = ${acct.zaa()}")
                    Log.d("lhb1", "zab = ${acct.zab()}")
                    Log.d("lhb1", "idToken = ${acct.idToken}")
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                Log.e("lhb1", "signInResult:failed code = ${e.statusCode}")
            }
        }
    }
}