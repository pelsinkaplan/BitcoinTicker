package com.pelsinkaplan.bitcointicker.ui.utils

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Pelşin KAPLAN on 17.05.2022.
 */
object Validation {

    fun loginValidation(email: String, password: String, view: View) {
        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter email!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(
                password.trim { it <= ' ' }) -> {
                Toast.makeText(
                    view.context,
                    "Please enter password!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            else -> {
                val emailControl = email.trim { it <= ' ' }
                val passwordControl = password.trim { it <= ' ' }

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailControl, passwordControl)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = task.result.user
                            Toast.makeText(
                                view.context,
                                "Login Successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            var prefences = view.context.getSharedPreferences(
                                "userId",
                                AppCompatActivity.MODE_PRIVATE
                            )
                            var editor = prefences.edit()
                            editor.putString("userId", firebaseUser!!.uid)
                            editor.apply()
                            NavigationManager().actionLoginFragmentToAllCoinFragment(
                                view,
                                firebaseUser!!.uid
                            )
                        } else
                            Toast.makeText(
                                view.context,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        }
    }

    fun registerValidation(email: String, password: String, repassword: String, view: View) {
        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(view.context, "Please enter email!", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(
                password.trim { it <= ' ' }) -> {
                Toast.makeText(
                    view.context,
                    "Please enter password!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            TextUtils.isEmpty(
                repassword.trim { it <= ' ' }) -> {
                Toast.makeText(
                    view.context,
                    "Please enter repassword!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                val emailControl = email.trim() { it <= ' ' }
                val passwordControl = password.trim() { it <= ' ' }
                val repasswordControl = repassword.trim() { it <= ' ' }

                if (passwordControl != repasswordControl)
                    Toast.makeText(
                        view.context,
                        "Passwords do not match!",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailControl, passwordControl)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    view.context,
                                    "Registration Successful!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                NavigationManager().actionRegisterFragmentToLoginFragment(view)
                            } else
                                Toast.makeText(
                                    view.context,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                }
            }
        }
    }

}