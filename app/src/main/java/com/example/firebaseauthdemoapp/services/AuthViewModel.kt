package com.example.firebaseauthdemoapp.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signup(
        email: String,
        password: String,
        nomComplet: String,
        numeroTelephone: String,
        role: String
    ) {
        if (email.isEmpty() || password.isEmpty() || nomComplet.isEmpty() || numeroTelephone.isEmpty()||role.isEmpty()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }
        _authState.value = AuthState.Loading

        // Create user in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User created, now save user details in Firestore
                    val user = auth.currentUser
                    val userDetails = hashMapOf(
                        "nomComplet" to nomComplet,
                        "numeroTelephone" to numeroTelephone,
                        "role" to role,
                    )

                    // Save user details in Firestore (in the 'utilisateurs' collection)
                    user?.let {
                        db.collection("utilisateurs")
                            .document(user.uid)  // Use the Firebase UID as document ID
                            .set(userDetails, SetOptions.merge())  // Add user data to Firestore
                            .addOnSuccessListener {
                                _authState.value = AuthState.Authenticated
                            }
                            .addOnFailureListener { exception ->
                                _authState.value = AuthState.Error("Failed to save user details: ${exception.message}")
                            }
                    }
                }
                else
                {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
