package com.photo.starsnap.main.screen.auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.photo.starsnap.main.viewmodel.auth.OAuthViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.photo.starsnap.main.R
import java.security.MessageDigest
import java.util.UUID

@Composable
fun SignupScreen(oauthViewModel: OAuthViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val startAddAccountIntentLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            // Once the account has been added, do sign in again.
            doGoogleSignIn(context, coroutineScope, oauthViewModel, null)
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            doGoogleSignIn(
                context, coroutineScope, oauthViewModel, startAddAccountIntentLauncher
            )
        }, content = {
            Text(text = "Hello")
        })

        Button(onClick = {
//            createPassKey(
//                context, coroutineScope, oauthViewModel, "", true
//            )
        }, content = {
            Text(text = "PassKey")
        })
    }
}

private fun doGoogleSignIn(
    context: Context,
    coroutineScope: CoroutineScope,
    oauthViewModel: OAuthViewModel,
    startAddAccountIntentLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
) {
    val credentialManager = CredentialManager.create(context)

    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it ->
        str + "%02x".format(it)
    }

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.google_client_id))
        .setAutoSelectEnabled(false)
        .setNonce(hashedNonce)
        .build()

    val request: GetCredentialRequest = Builder()
        .addCredentialOption(googleIdOption)
        .build()

    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                // Use an activity-based context to avoid undefined system UI
                // launching behavior.
                context = context,
                request = request
            )
            oauthViewModel.handleSignIn(result)
        } catch (e: NoCredentialException) {
            startAddAccountIntentLauncher?.launch(getAddGoogleAccountIntent())
            Log.d("SignupScreen", e.toString())
        }
    }


}

fun getAddGoogleAccountIntent(): Intent {
    val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
    intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
    return intent
}